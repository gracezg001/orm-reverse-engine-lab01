package models

import scalikejdbc.specs2.mutable.AutoRollback
import org.specs2.mutable._
import scalikejdbc._


class CitySpec extends Specification {

  "City" should {

    val c = City.syntax("c")

    "find by primary keys" in new AutoRollback {
      val maybeFound = City.find(123)
      maybeFound.isDefined should beTrue
    }
    "find by where clauses" in new AutoRollback {
      val maybeFound = City.findBy(sqls.eq(c.id, 123))
      maybeFound.isDefined should beTrue
    }
    "find all records" in new AutoRollback {
      val allResults = City.findAll()
      allResults.size should be_>(0)
    }
    "count all records" in new AutoRollback {
      val count = City.countAll()
      count should be_>(0L)
    }
    "find all by where clauses" in new AutoRollback {
      val results = City.findAllBy(sqls.eq(c.id, 123))
      results.size should be_>(0)
    }
    "count by where clauses" in new AutoRollback {
      val count = City.countBy(sqls.eq(c.id, 123))
      count should be_>(0L)
    }
    "create new record" in new AutoRollback {
      val created = City.create(id = 123, name = "MyString", countrycode = "MyString", district = "MyString", population = 123)
      created should not beNull
    }
    "save a record" in new AutoRollback {
      val entity = City.findAll().head
      // TODO modify something
      val modified = entity
      val updated = City.save(modified)
      updated should not equalTo(entity)
    }
    "destroy a record" in new AutoRollback {
      val entity = City.findAll().head
      val deleted = City.destroy(entity) == 1
      deleted should beTrue
      val shouldBeNone = City.find(123)
      shouldBeNone.isDefined should beFalse
    }
    "perform batch insert" in new AutoRollback {
      val entities = City.findAll()
      entities.foreach(e => City.destroy(e))
      val batchInserted = City.batchInsert(entities)
      batchInserted.size should be_>(0)
    }
  }

}
