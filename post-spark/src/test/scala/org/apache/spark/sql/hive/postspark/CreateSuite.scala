
package org.apache.spark.sql.hive.postspark

class CreateSuite extends PostSparkTestBase {
  import org.apache.spark.sql.hive.test.TestPostSpark._

  test("should capture Create Table") {
    sql("create table schema.films(code char(5) PRIMARY KEY, title varchar(40))")
  }
}
