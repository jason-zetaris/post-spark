
package org.apache.spark.sql

import org.apache.spark.SparkContext
import org.apache.spark.sql.internal.{SessionState, SharedState}

class PostSparkSession(@transient override val sparkContext: SparkContext,
                       @transient private val existingSharedState: Option[SharedState],
                       @transient private val parentSessionState: Option[SessionState]) extends SparkSession(sparkContext) {
  override lazy val sharedState: SharedState = {
    existingSharedState.getOrElse(new PostSparkSharedState(sparkContext, initialSessionOptions))
  }
}
