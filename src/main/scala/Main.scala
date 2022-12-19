import scalikejdbc._
import org.slf4j.LoggerFactory
import java.sql.Connection
//import ch.qos.logback.core.util.StatusPrinter
//import ch.qos.logback.classic.LoggerContext

/*
 Create a directory to store the log file:
    mkdir C:\var\log
    or mkdir /var/log in Unix like OS
* */


object Main extends App{
println("Application Started! \n")
val logger = LoggerFactory.getLogger(this.getClass)
  logger.info("Application Started!.....")


  logger.debug("Created logger.")
  //Class.forName("org.postgresq.Driver")
  ConnectionPool.singleton("jdbc:postgresql://localhost:5432/postgres", "postgres", "lorex2018")
  logger.debug("Setup connection pool.")


  implicit val session = AutoSession

  logger.info("Query data from employee table")
  val employees: List[Map[String,Any]] = sql"select first_name, last_name, email, phone from employee".map(_.toMap).list.apply()
  //employees.foreach(println)

  logger.info("Query data from city table")
  val cities: List[Map[String, Any]] = sql"Select * from city".map(_.toMap).list.apply()
 // cities.foreach(println)

  logger.info("Query data from country table")
  val ctys: List[Map[String, Any]] = sql"select * from country".map(_.toMap).list.apply()
  //ctys.foreach(println)


  case class Country(
            code: String,
            name: String,
            continent: String,
            region: String,
            surfacearea: Float,
            indepyear: Option[Int],
            population: Int,
            lifeexpectancy: Option[Float],
            gnp: Int,
            gnpold: Option[Int],
            localname: String,
            governmentform: String,
            headofstate: Option[String],
            capital: Option[Int],
            code2: String
                    )

  object Country extends SQLSyntaxSupport[Country] {
    override val tableName = "Country"

    def apply(rs: WrappedResultSet) = new Country(
      rs.string("code"),
      rs.string("name"),
      rs.string("continent"),
      rs.string("region"),
      rs.float("surfacearea"),
      rs.intOpt("indepyear"),
      rs.int("population"),
      rs.floatOpt("lifeexpectancy"),
      rs.int("gnp"),
      rs.intOpt("gnpold"),
      rs.string("localname"),
      rs.string("governmentform"),
      rs.stringOpt("headofstate"),
      rs.intOpt("capital"),
      rs.string("code2")
    )
  }

  //val ctys: List[Map[String, Any]]= sql"select * from country".map(_.toMap).list.apply()
  val countrie: List[Country] = sql"select * from country".map(rs1 => Country(rs1)).list.apply()
    println("##########################################")
    countrie.foreach(println)
    println("###########################################")

    val sea = countrie.filter(_.continent=="Sounth America")
    var indexS = 0

    for(country <-sea){
      indexS += 1
      println(s"$indexS - South amerias: ${country.code}, ${country.name}, ${country.continent}")
    }

  logger.info(">> *** Completed Normally! ***")

  //Main$ - >> *** Completed Normally! ***
    println("\n*** Application completed Normally! ***")


  }
