
package org.apache.spark.sql

import org.apache.hadoop.conf.Configuration
import org.apache.spark.SparkConf
import org.apache.spark.sql.catalyst.catalog.{CatalogTable, InMemoryCatalog}
import org.apache.spark.sql.hive.HiveExternalCatalog
import org.slf4j.LoggerFactory

class PostSparkExternalCatalog(pss: PostSparkSession, conf: SparkConf, hadoopConf: Configuration) extends HiveExternalCatalog(conf, hadoopConf) {
  val LOGGER = LoggerFactory.getLogger(getClass)

  override def dropTable(db: String, table: String, ignoreIfNotExists: Boolean, purge: Boolean): Unit = {
    super.dropTable(db, table, ignoreIfNotExists, purge)
  }

  override def renameTable(db: String, oldName: String, newName: String): Unit = {
    super.renameTable(db, oldName, newName)
  }

  override def getTable(db: String, table: String): CatalogTable = {
    super.getTable(db, table)
  }

  override def databaseExists(db: String): Boolean = {
    super.databaseExists(db)
  }

  override def tableExists(db: String, table: String): Boolean = {
    super.tableExists(db, table)
  }

  override def listTables(db: String, pattern: String): Seq[String] = {
    super.listTables(db, pattern)
  }
}
