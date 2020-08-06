
package com.jj.postspark.dictionary.jpa

import com.jj.postspark.dictionary.jpa.entity.{PSDatabase, PSPartition, PSSchema, PSTable}

trait PostSparkDAO {
  def saveDatabase(db: PSDatabase): Unit

  def loadDatabase(name: String): Option[PSDatabase]

  def saveSchema(schema: PSSchema): Unit

  def loadSchema(db: String, schema: String): Option[PSSchema]

  def saveTable(table: PSTable): Unit

  def loadTable(db: String, table: String): Option[PSTable]

  def addPartition(table: String, part: PSPartition): Unit

  def loadPartitionS(db: String, schema: String, table: String): List[PSPartition]

}
