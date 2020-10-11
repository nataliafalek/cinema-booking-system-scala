package com.faleknatalia.cinemaBookingSystem.movie

import java.net.URI
import java.time.format.DateTimeFormatter
import java.time.{DayOfWeek, ZonedDateTime}

import com.faleknatalia.cinemaBookingSystem.movie.MovieService.{daysOrder}
import slick.jdbc
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.collection.immutable.{ListMap, SortedMap, TreeMap}
import scala.concurrent.{ExecutionContext, Future}

class MovieService(db: jdbc.JdbcBackend.Database)(implicit ec: ExecutionContext) {

  private lazy val movies = TableQuery[MovieTable]
  private lazy val scheduledMovies = TableQuery[ScheduledMovieTable]

  def movieSchemaCreate(): Future[Unit] = {
    val ddl = movies.schema ++ scheduledMovies.schema
    db.run(ddl create)
  }

  def addNewMovie(movieForm: AddMovie): Future[Unit] = {
    val movie = Movie(
      title = movieForm.title,
      description = movieForm.description,
      durationInSeconds = movieForm.durationInSeconds,
      imageUrl = URI.create(movieForm.imageUrl))
    val addMovie = movies += movie
    db.run(addMovie).map(_ => ())
  }

  def addNewScheduledMovie(scheduledMovie: ScheduledMovie): Future[Unit] = {
    val addScheduledMovie = scheduledMovies += scheduledMovie
    db.run(addScheduledMovie).map(_ => ())
  }

  def findAllMovies(): Future[Seq[Movie]] = db.run(movies.result)

  def findAllMoviesByDayOfTheWeek(): Future[Map[DayOfWeek, Seq[MovieCardDto]]] = {
    val allScheduledMoviesQuery = scheduledMovies.filter { movie =>
      movie.dateOfProjection.between(ZonedDateTime.now(), ZonedDateTime.now().plusDays(7))
    }.join(movies).on(_.movieId === _.id)

    db.run(allScheduledMoviesQuery.result).map { scheduledMovies =>
      fromScheduledMoviesToMoviesByDayOfTheWeek(scheduledMovies)
    }
  }

  private case class ScheduledMovieDetails(movieId: Long, title: String, start: ZonedDateTime, imageUrl: String)

  private def fromScheduledMoviesToMoviesByDayOfTheWeek(scheduledMovies: Seq[(ScheduledMovie, Movie)]): SortedMap[DayOfWeek, Seq[MovieCardDto]] = {
    val moviesByDayOfTheWeek = scheduledMovies.map { case (scheduledMovie, movie) =>
      (movie, scheduledMovie.dateOfProjection)
    }.groupBy(_._2.getDayOfWeek)

    val movieCardsByDayOfTheWeek = moviesByDayOfTheWeek.toSeq.map { case (dayOfTheWeek, moviesWithStartTime) =>
      val groupedByTitle = moviesWithStartTime.groupBy(movieWithStartTime => movieWithStartTime._1.title)
      val dailyRepertoire = moviesWithStartTime.distinct.map { case (movie, start) =>
        val movieSchedules = groupedByTitle(movie.title)
        val startHours = movieSchedules.map(_._2.format(DateTimeFormatter.ISO_LOCAL_TIME))
        MovieCardDto(movie.title, movie.id, startHours, movie.imageUrl.toString)
      }
      (dayOfTheWeek, dailyRepertoire)
    }
    SortedMap.from(movieCardsByDayOfTheWeek)(Ordering.by(day => daysOrder(day)))
  }

  def findAllScheduledMovies(): Future[Seq[ScheduledMovieDto]] = {
    val allScheduledMoviesQuery = scheduledMovies.join(movies).on(_.movieId === _.id)
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