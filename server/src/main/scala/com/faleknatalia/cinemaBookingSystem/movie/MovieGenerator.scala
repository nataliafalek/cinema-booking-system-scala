package com.faleknatalia.cinemaBookingSystem.movie

import java.net.URI
import java.time.ZonedDateTime

import com.faleknatalia.cinemaBookingSystem.cinemahall.CinemaHall

import scala.util.Random

object MovieGenerator {

  def generateScheduledMovies(movies: Seq[Movie], cinemaHalls: Seq[CinemaHall]): List[ScheduledMovie] = {
    (0 to 6).flatMap { weekIdx =>
      val today = ZonedDateTime.now().withHour(10).withMinute(0)
        .withSecond(0).withNano(0).plusDays(weekIdx)
      generateDailySchedule(today, movies, cinemaHalls)
    }.toList
  }

  private def generateDailySchedule(day: ZonedDateTime,
                                    movies: Seq[Movie],
                                    cinemaHalls:
                                    Seq[CinemaHall]): Seq[ScheduledMovie] = {
    cinemaHalls.flatMap { cinemaHall =>
      (1 to 12 by 2).map { hourIdx =>
        val randomMovie = movies(Random.nextInt(movies.size))
        ScheduledMovie(randomMovie.id, day.plusHours(hourIdx), cinemaHall.id)
      }
    }
  }

  def generateMovies: List[Movie] = {
    List(
      Movie("Bad Boy", "Great movie", 4000, new URI("https://fwcdn.pl/fph/61/62/846162/880934_1.1.jpg")),
      Movie("Pętla", "Great movie", 4000, new URI("https://fwcdn.pl/fph/88/26/848826/946918.1.jpg")),
      Movie("Kobiety Mafii 2", "Great movie", 4000, new URI("https://fwcdn.pl/fph/29/99/812999/799865_1.1.jpg")),
      Movie("Polityka", "Great movie", 4000, new URI("https://fwcdn.pl/fph/31/72/833172/820971_1.1.jpg")),
      Movie("Botoks", "Great movie", 4000, new URI("https://fwcdn.pl/fph/64/10/786410/717936_1.1.jpg")),
      Movie("PITBULL. NOWE PORZĄDKI", "Great movie", 4000, new URI("https://fwcdn.pl/fph/74/33/747433/589472_1.1.jpg")),
      Movie("Ciacho", "Great movie", 4000, new URI("https://fwcdn.pl/fph/35/35/533535/167638.1.jpg")),
      Movie("Last Minute", "Great movie", 4000, new URI("https://fwcdn.pl/fph/35/32/673532/372700.1.jpg"))
    )
  }
}
