package com.faleknatalia.cinemaBookingSystem.movie

import java.net.URI
import java.time.ZonedDateTime

import slick.jdbc
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

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


  def findAllMoviesByDayOfTheWeek(): Future[Map[String, Seq[ScheduledMovieDto]]] = {
    val allScheduledMoviesQuery = scheduledMovies.filter(movie =>
      movie.dateOfProjection.between(ZonedDateTime.now(), ZonedDateTime.now().plusDays(7))).join(movies).on(_.movieId === _.id)
    db.run(allScheduledMoviesQuery.result).map { scheduledMovies =>
      toScheduledMovieDto(scheduledMovies).groupBy(scheduledMovie => scheduledMovie.start.getDayOfWeek.name())
    }
  }

  def findAllScheduledMovies(): Future[Seq[ScheduledMovieDto]] = {
    val allScheduledMoviesQuery = scheduledMovies.join(movies).on(_.movieId === _.id)
    db.run(allScheduledMoviesQuery.result).map { scheduledMovies =>
      toScheduledMovieDto(scheduledMovies)
    }
  }

  private def toScheduledMovieDto(scheduledMovies: Seq[(ScheduledMovie, Movie)]) = {
    scheduledMovies.map { case (scheduledMovie, movie) =>
      val start = scheduledMovie.dateOfProjection
      ScheduledMovieDto(movie.id, movie.title, start, start.plusSeconds(movie.durationInSeconds))
    }
  }
}
