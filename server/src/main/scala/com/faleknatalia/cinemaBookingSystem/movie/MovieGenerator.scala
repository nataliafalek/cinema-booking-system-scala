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
      Movie("Bad Boy", "The rise and fall of a soccer club owner, discovering the harsh reality of the sport, often connected with crime and fraud.", 4000, new URI("https://fwcdn.pl/fph/61/62/846162/880934_1.1.jpg")),
      Movie("Pętla", "Based on a true story about a 2nd generation cop who has designs on becoming a detective but his goals descend into bribery, corruption and addiction.", 4000, new URI("https://fwcdn.pl/fph/88/26/848826/946918.1.jpg")),
      Movie("Kobiety Mafii 2", "Story of idealistic police officers in a deadly struggle with ruthless organized crime syndicates.", 4000, new URI("https://fwcdn.pl/fph/29/99/812999/799865_1.1.jpg")),
      Movie("Polityka", "Great movie", 4000, new URI("https://fwcdn.pl/fph/31/72/833172/820971_1.1.jpg")),
      Movie("Botoks", "Botoks is intended to be a record of the authentic history of strong, determined and expressive physicians who struggle with life's decisions and problems: discrimination, maternity pressures, the pursuit of youth, the fight for the right to free choice and own views.", 4000, new URI("https://fwcdn.pl/fph/64/10/786410/717936_1.1.jpg")),
      Movie("PITBULL. NOWE PORZĄDKI", "Policemen from two precincts join forces to fight a criminal band called the \"Mokotowska Group\".", 4000, new URI("https://fwcdn.pl/fph/74/33/747433/589472_1.1.jpg")),
      Movie("Ciacho", "Hilarious comedy that tells the fight of three brothers, something ridiculous, trying to free his sister police, unfairly accused of selling drugs.", 4000, new URI("https://fwcdn.pl/fph/35/35/533535/167638.1.jpg")),
      Movie("Last Minute", "Widowed geography teacher wins a trip to Egypt. Takes with him energetic mother, teenage daughter and son resolute. Life adventure soon turns into a range of problems - are lost luggage at the airport, the hotel family receives the wrong room, the tour operator goes bankrupt. Possessed cash disappears quickly, with no contact consulate. The downside of the Hotel Tom meets high-school love with the ideal of a man at his side.", 4000, new URI("https://fwcdn.pl/fph/35/32/673532/372700.1.jpg"))
    )
  }
}
