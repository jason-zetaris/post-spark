
package org.apache.spark.sql

import org.apache.hadoop.conf.Configuration
import org.apache.spark.sql.catalyst.catalog._
import org.apache.spark.sql.internal.SharedState
import org.apache.spark.sql.internal.StaticSQLConf.WAREHOUSE_PATH

class PostSparkSharedState(pss: PostSparkSession,
                           initialConfigs: scala.collection.Map[String, String]) extends SharedState(pss.sparkContext, initialConfigs) {
  // Refer parent class
  private val (conf, hadoopConf) = {
    SharedState.loadHiveConfFile(sparkContext.conf, sparkContext.hadoopConfiguration)
    val confClone = sparkContext.conf.clone()
    val hadoopConfClone = new Configuration(sparkContext.hadoopConfiguration)
    initialConfigs.foreach {
      case (k, _)  if k == "hive.metastore.warehouse.dir" || k == WAREHOUSE_PATH.key =>
        logWarning(s"Not allowing to set ${WAREHOUSE_PATH.key} or hive.metastore.warehouse.dir " +
          s"in SparkSession's options, it should be set statically for cross-session usages")
      case (k, v) =>
        logDebug(s"Applying initial SparkSession options to SparkConf/HadoopConf: $k -> $v")
        confClone.set(k, v)
        hadoopConfClone.set(k, v)

    }
    (confClone, hadoopConfClone)
  }

  // Refer parent class
  override lazy val externalCatalog = {
    val externalCatalog = new PostSparkExternalCatalog(pss, pss.sparkContext.conf, pss.sqlContext.sparkContext.hadoopConfiguration)

    val defaultDbDefinition = CatalogDatabase(
      SessionCatalog.DEFAULT_DATABASE,
      "default database",
      CatalogUtils.stringToURI(conf.get(WAREHOUSE_PATH)),
      Map())

    if (!externalCatalog.databaseExists(SessionCatalog.DEFAULT_DATABASE)) {
      externalCatalog.createDatabase(defaultDbDefinition, ignoreIfExists = true)
    }

    val wrapped = new ExternalCatalogWithListener(externalCatalog)
    wrapped.addListener((event: ExternalCatalogEvent) => sparkContext.listenerBus.post(event))
    wrapped
  }
}
