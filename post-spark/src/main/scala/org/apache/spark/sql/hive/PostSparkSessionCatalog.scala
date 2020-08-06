
package org.apache.spark.sql.hive

import org.apache.spark.sql.catalyst.TableIdentifier
import org.apache.spark.sql.catalyst.analysis.FunctionRegistry
import org.apache.spark.sql.catalyst.catalog.CatalogTable
import org.apache.spark.sql.catalyst.parser.ParserInterface
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.internal.{SQLConf, SessionState}
import org.apache.spark.sql.{PostSparkExternalCatalog, SparkSession}
import org.slf4j.LoggerFactory

class PostSparkSessionCatalog(override val externalCatalog: PostSparkExternalCatalog,
                              val ss: SparkSession,
                              functionRegistry: FunctionRegistry,
                              conf: SQLConf,
                              sqlParser: ParserInterface,
                              resourceLoader: HiveSessionResourceLoader)
  extends HiveSessionCatalog(() => externalCatalog, () => ss.sharedState.globalTempViewManager, new HiveMetastoreCatalog(ss), functionRegistry,
    conf, SessionState.newHadoopConf(ss.sparkContext.hadoopConfiguration, conf), sqlParser, resourceLoader){
  val LOGGER = LoggerFactory.getLogger(getClass)

  override def lookupRelation(name: TableIdentifier): LogicalPlan = {
    ???
  }

  override def tableExists(name: TableIdentifier): Boolean = {
    ???
  }

  override def getTableMetadata(name: TableIdentifier): CatalogTable = {
    ???
  }

  override def dropTable(name: TableIdentifier, ignoreIfNotExists: Boolean, purge: Boolean): Unit = {
    ???
  }

}
