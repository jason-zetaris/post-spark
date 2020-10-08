
package com.jj.postspark.sql

import org.apache.spark.sql.{PostSparkSession, Row, SparkSession}
import org.apache.spark.sql.execution.command.RunnableCommand

abstract class PostSparkCommand extends RunnableCommand {
  def hookCommand(pss: PostSparkSession): Seq[Row]

  override def run(sparkSession: SparkSession): Seq[Row] = {
    val pss = sparkSession.asInstanceOf[PostSparkSession]
    hookCommand(pss)
  }

  def runDML(dml: String): Int = ???
}
