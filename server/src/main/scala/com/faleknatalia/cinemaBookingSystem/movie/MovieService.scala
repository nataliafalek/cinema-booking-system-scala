package com.faleknatalia.cinemaBookingSystem.movie

import slick.jdbc
import slick.jdbc.JdbcBackend
import slick.jdbc.PostgresProfile.api._
import slick.lifted.TableQuery

import scala.concurrent.{ExecutionContext, Future}

class MovieService() {

   def addNewMovie(db: jdbc.JdbcBackend.Database, movieForm: AddMovie, movies: TableQuery[MovieTable])(implicit ec: ExecutionContext) : Future[Unit] = {
     val movie = Movie(
       title = movieForm.title,
       description = movieForm.description,
       durationInSeconds = movieForm.durationInSeconds,
       imageUrl = movieForm.imageUrl)
     db.run(movies += movie).map( _ => ())
  }

  def findAllMovies(db: JdbcBackend.Database, movies: TableQuery[MovieTable])
                           (implicit ec: ExecutionContext): Future[Seq[Movie]] = {
    db.run(movies.map(movie =>
      (movie.id, movie.title, movie.description, movie.durationInSeconds, movie.imageUrl).mapTo[Movie]).result)
  }

  def findAllScheduledMovies(db: JdbcBackend.Database, scheduledMovies: TableQuery[ScheduledMovieTable], movies: TableQuery[MovieTable])
                                    (implicit ec: ExecutionContext): Future[Seq[ScheduledMovieDto]] = {
    val allScheduledMoviesQuery = for {
      (s, m) <- scheduledMovies.join(movies).on(_.movieId === _.id)
    } yield (m.title, s.dateOfProjection, m.durationInSeconds)
    db.run(allScheduledMoviesQuery.result).map(_.map { case (title, start, duration) => ScheduledMovieDto(title, start, start.plusSeconds(duration)) })
  }
}
