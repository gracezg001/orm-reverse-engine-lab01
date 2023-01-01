package application

import scalikejdbc._
import org.slf4j.LoggerFactory
import models.City
import java.sql.Connection
//import ch.qos.logback.core.util.StatusPrinter
//import ch.qos.logback.classic.LoggerContext

/**
 * Generate Cityp model from table city
 * Command:
 *    sbt "scalikejdbcGen city"
 *
 * Or specify class name: City
 *    sbt "scalikejdbcGen city City"
 *
 */


object CityMayor extends App{
  ConnectionPool.singleton("jdbc:postgresql://localhost:5432/postgres", "postgres", "lorex2018")

  val logger = LoggerFactory.getLogger(this.getClass)
  logger.info("@@@ ### >>> CityMayor  started to work!.....")

//find all cities Map to City
  val cities = City.findAll()
 // cities.foreach(println)

  val CITY_CODE ="CAN"
  // filter our city of Canada which countrycode is CAN
  val canadaCities = cities.filter(_.countrycode == CITY_CODE)
  var indexS = 0
  for (city <- canadaCities) {
    indexS += 1
    println(s"Canada City #$indexS.-> ${city.name}, ${city.countrycode}, ${city.district}, ${city.population}")
    logger.info(s"Canada City #$indexS.->${city.name}, ${city.countrycode},${city.district}, ${city.population}")
  }

 // override val columns = Seq("id", "name", "countrycode", "district", "population")
 //var nc = City.create(id = 1000000, name = "Ford", countrycode = "CAN", district = "Ontario", population = 5000)
 var nc = City.create(id = 1000001, name = "Honda", countrycode = "CAN", district = "Ontario", population = 15000)

  println(nc)

  println("==================================Completed Normally!=======================================")









}
