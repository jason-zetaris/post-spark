
package org.apache.spark.sql

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
}
