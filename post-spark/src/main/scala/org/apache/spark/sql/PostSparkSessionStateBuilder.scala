
package org.apache.spark.sql

import com.jj.postspark.parser.PostSparkParser
import com.jj.postspark.sql.execution.PostSparkSqlParser
import org.apache.spark.sql.catalyst.parser.ParserInterface
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.hive.{HiveSessionStateBuilder, PostSparkSessionCatalog}
import org.apache.spark.sql.internal.SessionState

class PostSparkSessionStateBuilder (session: SparkSession, parentState: Option[SessionState] = None)
  extends HiveSessionStateBuilder(session, parentState){
  private def externalCatalog: PostSparkExternalCatalog =
    session.sharedState.asInstanceOf[PostSparkSharedState].externalCatalog.unwrapped.asInstanceOf[PostSparkExternalCatalog]

  override protected lazy val catalog: PostSparkSessionCatalog = {
    val catalog = new PostSparkSessionCatalog(externalCatalog,
      session,
      functionRegistry,
      conf,
      sqlParser,
      resourceLoader)
    parentState.foreach(_.catalog.copyStateTo(catalog))
    catalog
  }

  override lazy val sqlParser: ParserInterface = new PostSparkSqlParser(conf, parsePostSpark)

  private val postSparkParser = new PostSparkParser

  def parsePostSpark(sql: String): LogicalPlan = postSparkParser.parse(sql)
}
