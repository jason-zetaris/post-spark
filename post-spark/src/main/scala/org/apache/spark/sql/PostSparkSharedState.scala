
package org.apache.spark.sql

import org.apache.spark.SparkContext
import org.apache.spark.sql.internal.SharedState

class PostSparkSharedState(sparkContext: SparkContext,
                           initialConfigs: scala.collection.Map[String, String]) extends SharedState(sparkContext, initialConfigs) {

}
