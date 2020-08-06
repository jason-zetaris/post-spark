
package com.jj.postspark.parser
import com.jj.postspark.sql.Select
import org.apache.spark.sql.catalyst.TableIdentifier
import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan

class PostSparkParser extends ParserBase with PostSparkKeyword {
  override protected def start: Parser[LogicalPlan] = createTable | alterTable | dropTable

  lazy val identifier: Parser[String] = ( ident | "[_a-zA-Z0-9]*".r ^^ { case name => name } )

  lazy val lightningIdent: Parser[String] = ( ident | "[_a-zA-Z0-9]*".r ^^ { case name => name } )

  protected lazy val tableIdentifier: Parser[TableIdentifier] =
    (ident <~ ".").? ~ ident ^^ { case maybeDbName ~ tableName => TableIdentifier(tableName, maybeDbName) } |
      (lightningIdent <~ ".").? ~ lightningIdent ^^ { case maybeDbName ~ tableName => TableIdentifier(tableName, maybeDbName) }

  protected lazy val createTable: Parser[LogicalPlan] = {
    CREATE ~> TABLE ~> tableIdentifier ~ wholeInput ^^ {
      case table ~ query => Select(table, query)
    }
  }

  protected lazy val alterTable: Parser[LogicalPlan] = {
    ALTER ~> TABLE ~> tableIdentifier ~ wholeInput ^^ {
      case table ~ query => ???
    }
  }

  protected lazy val dropTable: Parser[LogicalPlan] = {
    DROP ~> TABLE ~> tableIdentifier ~ wholeInput ^^ {
      case table ~ query => ???
    }
  }
}
