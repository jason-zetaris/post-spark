
package org.apache.spark.sql.hive

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.internal.config
import org.apache.spark.internal.config.UI.UI_ENABLED
import org.apache.spark.sql.catalyst.optimizer.ConvertToLocalRelation
import org.apache.spark.sql.hive.test.TestHiveContext
import org.apache.spark.sql.internal.SQLConf
import org.apache.spark.sql.internal.StaticSQLConf.WAREHOUSE_PATH

object TestPostSpark extends TestHiveContext(
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
