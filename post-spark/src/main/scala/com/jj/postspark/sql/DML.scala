package com.jj.postspark.sql

import org.apache.spark.sql.{PostSparkSession, Row}

abstract class DML(dml: String) extends PostSparkCommand {
  override def hookCommand(pss: PostSparkSession): Seq[Row] = {
    ???
  }
}
