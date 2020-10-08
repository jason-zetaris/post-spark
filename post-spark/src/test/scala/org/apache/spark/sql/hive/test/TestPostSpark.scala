
package org.apache.spark.sql.hive.test

import org.apache.spark.internal.config
import org.apache.spark.internal.config.UI.UI_ENABLED
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.sql.catalyst.optimizer.ConvertToLocalRelation
import org.apache.spark.sql.hive.HiveUtils
import org.apache.spark.sql.hive.client.HiveClient
import org.apache.spark.sql.internal.SQLConf
import org.apache.spark.sql.internal.StaticSQLConf.WAREHOUSE_PATH
import org.apache.spark.{SparkConf, SparkContext}

object TestPostSpark extends TestPostContext(
  new SparkContext(
    System.getProperty("spark.sql.test.master", "local[1]"),
    "TestSQLContext",
    new SparkConf()
      .set("spark.sql.test", "")
      .set(SQLConf.CODEGEN_FALLBACK.key, "false")
      .set(HiveUtils.HIVE_METASTORE_BARRIER_PREFIXES.key, "org.apache.spark.sql.hive.execution.PairSerDe")
      .set(WAREHOUSE_PATH.key, TestHiveContext.makeWarehouseDir().toURI.getPath)
      .set(UI_ENABLED, false)
      .set(config.UNSAFE_EXCEPTION_ON_MEMORY_LEAK, true)
      .set("spark.hadoop.hive.metastore.disallow.incompatible.col.type.changes", "false")
      .set(SQLConf.OPTIMIZER_EXCLUDED_RULES.key, ConvertToLocalRelation.ruleName)))

class TestPostContext(@transient override val sparkSession: TestPostSparkSession) extends SQLContext(sparkSession) {

  /**
   * If loadTestTables is false, no test tables are loaded. Note that this flag can only be true
   * when running in the JVM, i.e. it needs to be false when calling from Python.
   */
  def this(sc: SparkContext) {
    this(new TestPostSparkSession(HiveUtils.withHiveExternalCatalog(sc)))
  }

  def this(sc: SparkContext, hiveClient: HiveClient) {
    this(new TestPostSparkSession(HiveUtils.withHiveExternalCatalog(sc), hiveClient))
  }

  override def newSession(): TestPostContext = {
    new TestPostContext(sparkSession.newSession())
  }

  override def sql(sql: String): DataFrame = sparkSession.sql(sql)
}