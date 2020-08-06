
package org.apache.spark.sql

import org.apache.hadoop.conf.Configuration
import org.apache.spark.SparkConf
import org.apache.spark.sql.catalyst.catalog.{CatalogTable, InMemoryCatalog}

class PostSparkExternalCatalog(pss: PostSparkSession, conf: SparkConf, hadoopConf: Configuration) extends InMemoryCatalog(conf, hadoopConf) {
  override def dropTable(db: String, table: String, ignoreIfNotExists: Boolean, purge: Boolean): Unit = {
    ???
  }

  override def renameTable(db: String, oldName: String, newName: String): Unit = {
    ???
  }

  override def getTable(db: String, table: String): CatalogTable = {
    ???
  }

  override def databaseExists(db: String): Boolean = {
    ???
  }

  override def tableExists(db: String, table: String): Boolean = {
    ???
  }

  override def listTables(db: String, pattern: String): Seq[String] = {
    ???
  }
}
