
package org.apache.spark.sql.hive.postspark

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, GivenWhenThen}

abstract class PostSparkTestBase extends AnyFunSuite with BeforeAndAfter with BeforeAndAfterAll with GivenWhenThen {
  def afterInit(): Unit = {}

  before {
    afterInit()
  }

  def clearAfter(): Unit = {}

  after {
    clearAfter()
  }
}
