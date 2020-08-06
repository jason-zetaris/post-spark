
package org.apache.spark.sql.hive

import org.apache.spark.SparkContext
import org.apache.spark.sql.hive.client.HiveClient
import org.apache.spark.sql.internal.SessionState
import org.apache.spark.sql.{PostSparkSessionStateBuilder, PostSparkSharedState, SparkSession}

class TestPostSparkSession(@transient private val sc: SparkContext,
                           @transient private val existingSharedState: Option[PostSparkSharedState],
                           @transient private val parentSessionState: Option[SessionState]) extends SparkSession(sc) {
  def this(sc: SparkContext) {
    this(
      sc,
      existingSharedState = None,
      parentSessionState = None)
  }

  def this(sc: SparkContext, hiveClient: HiveClient) {
    this(
      sc,
      existingSharedState = Some(new PostSparkSharedState(sc, initialSessionOptions)),
      parentSessionState = None)
  }

  SparkSession.setDefaultSession(this)
  SparkSession.setActiveSession(this)

  @transient
  override lazy val sharedState: PostSparkSharedState = {
    existingSharedState.getOrElse(new PostSparkSharedState(sc, initialSessionOptions))
  }

  @transient
  override lazy val sessionState: SessionState = new PostSparkSessionStateBuilder(this, parentSessionState).build()

  lazy val metadataHive: HiveClient = {
    sharedState.externalCatalog.unwrapped.asInstanceOf[HiveExternalCatalog].client.newSession()
  }
}
