
package com.jj.postspark.sql.execution

import org.apache.spark.sql.catalyst.parser.ParseException
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.execution.SparkSqlParser
import org.apache.spark.sql.internal.SQLConf

class PostSparkSqlParser(conf: SQLConf, preParse: String => LogicalPlan) extends SparkSqlParser(conf) {
  override def parsePlan(sqlText: String): LogicalPlan = {
    preParse(sqlText)

    try {
      super.parsePlan(sqlText)
    } catch {
      case e: ParseException => {
        preParse(sqlText)
      }
      case th: Throwable => throw th
    }
  }
}
