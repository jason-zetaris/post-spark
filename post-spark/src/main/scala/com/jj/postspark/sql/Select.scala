
package com.jj.postspark.sql
import org.apache.spark.sql.catalyst.TableIdentifier
import org.apache.spark.sql.{PostSparkSession, Row}

case class Select(table: TableIdentifier, query: String) extends PostSparkCommand {
  override def hookCommand(pss: PostSparkSession): Seq[Row] = ???
}
