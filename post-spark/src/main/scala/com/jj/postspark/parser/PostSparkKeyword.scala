
package com.jj.postspark.parser

import org.apache.spark.sql.catalyst.TableIdentifier

trait PostSparkKeyword {
  self: ParserBase =>

  protected val SELECT = Keyword("SELECT")
  protected val INSERT = Keyword("INSERT")
  protected val UPDATE = Keyword("UPDATE")
  protected val DELETE = Keyword("DELETE")

  protected val CREATE = Keyword("CREATE")
  protected val ALTER = Keyword("ALTER")
  protected val DROP = Keyword("DROP")
  protected val TABLE = Keyword("TABLE")

}
