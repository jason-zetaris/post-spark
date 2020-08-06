
package com.jj.postspark.sql

import org.apache.spark.sql.{PostSparkSession, Row}
import org.apache.spark.sql.catalyst.TableIdentifier

case class Update (table: TableIdentifier, query: String) extends PostSparkCommand {
  override def hookCommand(pss: PostSparkSession): Seq[Row] = ???
}
