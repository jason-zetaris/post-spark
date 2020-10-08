package com.jj.postspark.sql

import org.apache.spark.sql.{PostSparkSession, Row}
import org.apache.spark.sql.catalyst.TableIdentifier

case class CreateTable(table: TableIdentifier, query: String) extends PostSparkCommand {
  override def hookCommand(pss: PostSparkSession): Seq[Row] = {
    //TODO: add indexed post_spark_seq column with auto increment, which is used to build auto partition.
    ???
  }
}

