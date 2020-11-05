package com.faleknatalia.cinemaBookingSystem.movie

import java.net.URI
import java.time.format.DateTimeFormatter
import java.time.{DayOfWeek, ZonedDateTime}

import com.faleknatalia.cinemaBookingSystem.dbutils.{DatabaseUtils, Tables}
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
  private lazy val cinemaHallsTable = Tables.cinemaHallsTable
  private val databaseUtils = new DatabaseUtils(db)(ec)

  def saveGeneratedMovies(): Future[Unit] = {
    val movies = MovieGenerator.generateMovies
    databaseUtils.insertDataIfNotExists(moviesTable, movies)
  }

  def addNewMovie(movieForm: AddMovie): Future[Unit] = {
    val movie = Movie(
      title = movieForm.title,
      description = movieForm.description,
      durationInSeconds = movieForm.durationInSeconds,
      imageUrl = URI.create(movieForm.imageUrl))
    val addMovie = moviesTable += movie
    db.run(addMovie).map(_ => ())
  }

  def addNewScheduledMovieDto(addScheduledMovieDto: AddScheduledMovieDto): Future[Unit] = {
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
        ScheduledMovieWithSeat(scheduledMovieId, cinemaHallId, isFree = true, seatId)
      }
    }

    saveScheduledMovieWithSeats.map { seats =>
      db.run(scheduledMovieWithSeatsTable ++= seats)
    }
  }

  def findMovieById(movieId: Long): Future[Option[Movie]] = {
    db.run(moviesTable.filter(movie => movie.id === movieId).result.headOption)
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

  def saveGeneratedScheduledMovies(): Future[Unit] = {
    hasScheduledForToday.map { hasSchedule =>
      if (!hasSchedule) {
        val allMovies = findAllMovies()
        val allCinemaHalls = db.run(cinemaHallsTable.result)
        val moviesAndCinemaHalls = for {
          movies <- allMovies
          cinemaHalls <- allCinemaHalls
        } yield (movies, cinemaHalls)
        val scheduledMoviesToSave = moviesAndCinemaHalls.map { case (movies, cinemaHalls) =>
          val generatedScheduledMovies = MovieGenerator.generateScheduledMovies(movies, cinemaHalls)
          generatedScheduledMovies
        }
        val savedScheduledMovies = scheduledMoviesToSave.flatMap { moviesToSave =>
          db.run(scheduledMoviesTable returning scheduledMoviesTable ++= moviesToSave)
        }
        savedScheduledMovies.flatMap { scheduledMovies =>
          generateSeatsForScheduledMovies(scheduledMovies.toList)
        }
      } else {
        Future.successful(())
      }
    }
  }

  private def generateSeatsForScheduledMovies(scheduledMoviesToSave: List[ScheduledMovie]): Future[Unit] = {
    Future.sequence(scheduledMoviesToSave.map { scheduledMovie =>
      generateSeatsForScheduledMovie(scheduledMovie.id)
    }
    ).map(_ => ())
  }

  def hasScheduledForToday: Future[Boolean] = {
    val today = ZonedDateTime.now()
    val todayOrLaterSchedule = scheduledMoviesTable.filter { movie =>
      movie.dateOfProjection > today
    }
    db.run(todayOrLaterSchedule.result).map { movies =>
      movies.nonEmpty
    }
  }

  private case class ScheduledMovieDetails(movieId: Long, title: String, start: ZonedDateTime, imageUrl: String)

  private def fromScheduledMoviesToMoviesByDayOfTheWeek(scheduledMovies: Seq[(ScheduledMovie, Movie)]): SortedMap[DayOfWeek, Seq[MovieCardDto]] = {
    val moviesByDayOfTheWeek = scheduledMovies.map { case (scheduledMovie, movie) =>
      (movie, scheduledMovie.dateOfProjection, scheduledMovie.id)
    }.groupBy(_._2.getDayOfWeek)

    val movieCardsByDayOfTheWeek = moviesByDayOfTheWeek.toSeq.map { case (dayOfTheWeek, moviesWithStartTime) =>
      val groupedByTitle = moviesWithStartTime.groupBy(movieWithStartTime => movieWithStartTime._1.title)
      val dailyRepertoire = moviesWithStartTime.distinct.map { case (movie, _, _) =>
        val movieSchedules = groupedByTitle(movie.title)
        val scheduledMovieIdsWithStartHours = movieSchedules.map { case (_, startTime, scheduledMovieID) =>
          scheduledMovieID -> startTime.format(DateTimeFormatter.ISO_LOCAL_TIME)
        }
        MovieCardDto(movie.title, movie.id, scheduledMovieIdsWithStartHours, movie.imageUrl.toString)
      }
      (dayOfTheWeek, dailyRepertoire.distinct)
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