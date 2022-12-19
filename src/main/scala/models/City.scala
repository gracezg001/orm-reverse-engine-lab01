

package models

import scalikejdbc._

case class City(
  id: Int,
  name: String,
  countrycode: String,
  district: String,
  population: Int) {

  def save()(implicit session: DBSession = City.autoSession): City = City.save(this)(session)

  def destroy()(implicit session: DBSession = City.autoSession): Int = City.destroy(this)(session)

}


object City extends SQLSyntaxSupport[City] {

  override val schemaName = Some("public")

  override val tableName = "city"

  override val columns = Seq("id", "name", "countrycode", "district", "population")

  def apply(c: SyntaxProvider[City])(rs: WrappedResultSet): City = apply(c.resultName)(rs)
  def apply(c: ResultName[City])(rs: WrappedResultSet): City = new City(
    id = rs.get(c.id),
    name = rs.get(c.name),
    countrycode = rs.get(c.countrycode),
    district = rs.get(c.district),
    population = rs.get(c.population)
  )

  val c = City.syntax("c")

  override val autoSession = AutoSession

  def find(id: Int)(implicit session: DBSession = autoSession): Option[City] = {
    withSQL {
      select.from(City as c).where.eq(c.id, id)
    }.map(City(c.resultName)).single.apply()
  }

  def findAll()(implicit session: DBSession = autoSession): List[City] = {
    withSQL(select.from(City as c)).map(City(c.resultName)).list.apply()
  }

  def countAll()(implicit session: DBSession = autoSession): Long = {
    withSQL(select(sqls.count).from(City as c)).map(rs => rs.long(1)).single.apply().get
  }

  def findBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Option[City] = {
    withSQL {
      select.from(City as c).where.append(where)
    }.map(City(c.resultName)).single.apply()
  }

  def findAllBy(where: SQLSyntax)(implicit session: DBSession = autoSession): List[City] = {
    withSQL {
      select.from(City as c).where.append(where)
    }.map(City(c.resultName)).list.apply()
  }

  def countBy(where: SQLSyntax)(implicit session: DBSession = autoSession): Long = {
    withSQL {
      select(sqls.count).from(City as c).where.append(where)
    }.map(_.long(1)).single.apply().get
  }

  def create(
    id: Int,
    name: String,
    countrycode: String,
    district: String,
    population: Int)(implicit session: DBSession = autoSession): City = {
    withSQL {
      insert.into(City).namedValues(
        column.id -> id,
        column.name -> name,
        column.countrycode -> countrycode,
        column.district -> district,
        column.population -> population
      )
    }.update.apply()

    City(
      id = id,
      name = name,
      countrycode = countrycode,
      district = district,
      population = population)
  }

  def batchInsert(entities: collection.Seq[City])(implicit session: DBSession = autoSession): List[Int] = {
    val params: collection.Seq[Seq[(String, Any)]] = entities.map(entity =>
      Seq(
        "id" -> entity.id,
        "name" -> entity.name,
        "countrycode" -> entity.countrycode,
        "district" -> entity.district,
        "population" -> entity.population))
    SQL("""insert into city(
      id,
      name,
      countrycode,
      district,
      population
    ) values (
      {id},
      {name},
      {countrycode},
      {district},
      {population}
    )""").batchByName(params.toSeq: _*).apply[List]()
  }

  def save(entity: City)(implicit session: DBSession = autoSession): City = {
    withSQL {
      update(City).set(
        column.id -> entity.id,
        column.name -> entity.name,
        column.countrycode -> entity.countrycode,
        column.district -> entity.district,
        column.population -> entity.population
      ).where.eq(column.id, entity.id)
    }.update.apply()
    entity
  }

  def destroy(entity: City)(implicit session: DBSession = autoSession): Int = {
    withSQL { delete.from(City).where.eq(column.id, entity.id) }.update.apply()
  }

}
