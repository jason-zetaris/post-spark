
package com.jj.postspark.dictionary.jpa
import com.jj.postspark.dictionary.jpa.entity.{PSDatabase, PSPartition, PSSchema, PSTable}
import javax.persistence.{EntityManager, PersistenceContext}
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository("postSparkDAO")
@Transactional(value = "transactionManager")
class PostSparDAOImpl extends PostSparkDAO {
  @PersistenceContext(unitName = "postSparkUnit")
  var entityManager: EntityManager = _

  override def saveDatabase(db: PSDatabase): Unit = ???

  override def loadDatabase(name: String): Option[PSDatabase] = ???

  override def saveSchema(schema: PSSchema): Unit = ???

  override def loadSchema(db: String, schema: String): Option[PSSchema] = ???

  override def saveTable(table: PSTable): Unit = ???

  override def loadTable(db: String, table: String): Option[PSTable] = ???

  override def addPartition(table: String, part: PSPartition): Unit = ???

  override def loadPartitionS(db: String, schema: String, table: String): List[PSPartition] = ???
}
