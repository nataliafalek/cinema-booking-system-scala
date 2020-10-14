package com.faleknatalia.cinemaBookingSystem.movie

import java.net.URI
import java.time.format.DateTimeFormatter
import java.time.{DayOfWeek, ZonedDateTime}

import com.faleknatalia.cinemaBookingSystem.dbutils.Tables
import com.faleknatalia.cinemaBookingSystem.movie.MovieService.daysOrder
import slick.jdbc
import slick.jdbc.PostgresProfile.api._

import scala.collection.immutable.SortedMap
import scala.concurrent.{ExecutionContext, Future}

class MovieService(db: jdbc.JdbcBackend.Database)(implicit ec: ExecutionContext) {
  private lazy val moviesTable = Tables.moviesTable
  private lazy val scheduledMoviesTable = Tables.scheduledMoviesTable
  private lazy val seatsTable = Tables.seatsTable
  private lazy val scheduledMovieWithSeatsTable = Tables.scheduledMovieWithSeatsTable

  def addNewMovie(movieForm: AddMovie): Future[Unit] = {
    val movie = Movie(
      title = movieForm.title,
      description = movieForm.description,
      durationInSeconds = movieForm.durationInSeconds,
      imageUrl = URI.create(movieForm.imageUrl))
    val addMovie = moviesTable += movie
    db.run(addMovie).map(_ => ())
  }

  def addNewScheduledMovie(addScheduledMovieDto: AddScheduledMovieDto): Future[Unit] = {
    val scheduledMovie = ScheduledMovie(addScheduledMovieDto.movieId, addScheduledMovieDto.dateOfProjection, addScheduledMovieDto.cinemaHallId)
    val addScheduledMovie = scheduledMoviesTable returning scheduledMoviesTable.map(_.id) += scheduledMovie
    db.run(addScheduledMovie).map(id => generateSeatsForScheduledMovie(id))
  }

  def generateSeatsForScheduledMovie(scheduledMovieId: Long): Future[Unit] = {
    val selectSeatsForScheduledMovie = for {
      scheduledMovie <- scheduledMoviesTable if scheduledMovie.id === scheduledMovieId
      seat <- seatsTable if seat.cinemaHallId === scheduledMovie.cinemaHallId
    } yield (seat.id, scheduledMovie.cinemaHallId)

    val runSelect = db.run(selectSeatsForScheduledMovie.result)

    val saveScheduledMovieWithSeats = runSelect.map { result =>
      result.map { case (seatId, cinemaHallId) =>
        ScheduledMovieWithSeat(scheduledMovieId, cinemaHallId, isFree = true, seatId)}
    }

    saveScheduledMovieWithSeats.map { seats =>
      db.run(scheduledMovieWithSeatsTable ++= seats)
    }
  }

  def findAllMovies(): Future[Seq[Movie]] = db.run(moviesTable.result)

  def findAllMoviesByDayOfTheWeek(): Future[Map[DayOfWeek, Seq[MovieCardDto]]] = {
    val allScheduledMoviesQuery = scheduledMoviesTable.filter { movie =>
      movie.dateOfProjection.between(ZonedDateTime.now(), ZonedDateTime.now().plusDays(7))
    }.join(moviesTable).on(_.movieId === _.id)

    db.run(allScheduledMoviesQuery.result).map { scheduledMovies =>
      fromScheduledMoviesToMoviesByDayOfTheWeek(scheduledMovies)
    }
  }

  private case class ScheduledMovieDetails(movieId: Long, title: String, start: ZonedDateTime, imageUrl: String)

  private def fromScheduledMoviesToMoviesByDayOfTheWeek(scheduledMovies: Seq[(ScheduledMovie, Movie)]): SortedMap[DayOfWeek, Seq[MovieCardDto]] = {
    val moviesByDayOfTheWeek = scheduledMovies.map { case (scheduledMovie, movie) =>
      (movie, scheduledMovie.dateOfProjection, scheduledMovie.id)
    }.groupBy(_._2.getDayOfWeek)

    val movieCardsByDayOfTheWeek = moviesByDayOfTheWeek.toSeq.map { case (dayOfTheWeek, moviesWithStartTime) =>
      val groupedByTitle = moviesWithStartTime.groupBy(movieWithStartTime => movieWithStartTime._1.title)
      val dailyRepertoire = moviesWithStartTime.distinct.map { case (movie, start, scheduledMovieId) =>
        val movieSchedules = groupedByTitle(movie.title)
        val scheduledMovieIdsWithStartHours = movieSchedules.map{ case (movie, startTime, scheduledMovieID) =>
          scheduledMovieID -> startTime.format(DateTimeFormatter.ISO_LOCAL_TIME)
        }
        MovieCardDto(movie.title, movie.id, scheduledMovieIdsWithStartHours, movie.imageUrl.toString)
      }
      (dayOfTheWeek, dailyRepertoire)
    }
    SortedMap.from(movieCardsByDayOfTheWeek)(Ordering.by(day => daysOrder(day)))
  }

  def findAllScheduledMovies(): Future[Seq[ScheduledMovieDto]] = {
    val allScheduledMoviesQuery = scheduledMoviesTable.join(moviesTable).on(_.movieId === _.id)
    db.run(allScheduledMoviesQuery.result).map { scheduledMovies =>
      toScheduledMovieDto(scheduledMovies)
    }
  }

  private def toScheduledMovieDto(scheduledMovies: Seq[(ScheduledMovie, Movie)]): Seq[ScheduledMovieDto] = {
    scheduledMovies.map { case (scheduledMovie, movie) =>
      val start = scheduledMovie.dateOfProjection
      ScheduledMovieDto(movie.id, movie.title, start, start.plusSeconds(movie.durationInSeconds))
    }
  }
}

object MovieService {
  val daysOrder = (0 to 6).map { value =>
    ZonedDateTime.now().plusDays(value).getDayOfWeek -> value
  }.toMap
}