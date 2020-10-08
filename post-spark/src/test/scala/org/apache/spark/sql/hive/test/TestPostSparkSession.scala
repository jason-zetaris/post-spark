
package org.apache.spark.sql.hive.test

import org.apache.spark.SparkContext
import org.apache.spark.sql.hive.HiveExternalCatalog
import org.apache.spark.sql.hive.client.HiveClient
import org.apache.spark.sql.internal.{SQLConf, SessionState, WithTestConf}
import org.apache.spark.sql.{PostSparkSession, PostSparkSessionStateBuilder, PostSparkSharedState, SparkSession}

class TestPostSparkSession(@transient private val sc: SparkContext,
                           @transient private val existingSharedState: Option[PostSparkSharedState],
                           @transient private val parentSessionState: Option[SessionState]) extends PostSparkSession(sc, None, None) {
  def this(sc: SparkContext) {
    this(
      sc,
      existingSharedState = None,
      parentSessionState = None)
  }

  def this(sc: SparkContext, hiveClient: HiveClient) {
    this(
      sc,
      existingSharedState = None,
      parentSessionState = None)
  }

  SparkSession.setDefaultSession(this)
  SparkSession.setActiveSession(this)

  @transient
  override lazy val sharedState: PostSparkSharedState = {
    existingSharedState.getOrElse(new PostSparkSharedState(this, Map.empty[String, String]))
  }

  @transient
  override lazy val sessionState: SessionState = new TestPostSparkSessionStateBuilder(this, parentSessionState).build()

  lazy val metadataHive: HiveClient = {
    sharedState.externalCatalog.unwrapped.asInstanceOf[HiveExternalCatalog].client.newSession()
  }

  override def newSession(): TestPostSparkSession = {
    new TestPostSparkSession(sparkContext, Some(sharedState), parentSessionState = None)
  }
}

private[sql] object TestPostSparkContext {

  /**
   * A map used to store all confs that need to be overridden in sql/core unit tests.
   */
  val overrideConfs: Map[String, String] =
    Map(
      // Fewer shuffle partitions to speed up testing.
      SQLConf.SHUFFLE_PARTITIONS.key -> "5")
}

private[sql] class TestPostSparkSessionStateBuilder(session: SparkSession, state: Option[SessionState])
  extends PostSparkSessionStateBuilder(session, state) with WithTestConf {

  override def overrideConfs: Map[String, String] = TestPostSparkContext.overrideConfs
  override def newBuilder: NewBuilder = new TestPostSparkSessionStateBuilder(_, _)
}

