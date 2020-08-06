
package com.jj.postspark.parser

import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan

import scala.language.implicitConversions
import scala.util.matching.Regex
import scala.util.parsing.combinator.PackratParsers
import scala.util.parsing.combinator.syntactical.StandardTokenParsers

abstract class ParserBase extends StandardTokenParsers with PackratParsers {
  import lexical.Identifier
  implicit def regexToParser(regex: Regex): Parser[String] = acceptMatch(
    s"identifier matching regex ${regex}",
    { case Identifier(str) if regex.unapplySeq(str).isDefined => str }
  )

  def parse(input: String): LogicalPlan = synchronized {
    // Initialize the Keywords.
    initLexical
    phrase(start)(new lexical.Scanner(input)) match {
      case Success(plan, _) => plan
      case failureOrError => sys.error(failureOrError.toString)
    }
  }
  /* One time initialization of lexical.This avoid reinitialization of  lexical in parse method */
  protected lazy val initLexical: Unit = lexical.initialize(reservedWords)

  protected case class Keyword(str: String) {
    def normalize: String = lexical.normalizeKeyword(str)
    def parser: Parser[String] = normalize
  }

  protected implicit def asParser(k: Keyword): Parser[String] = k.parser

  // By default, use Reflection to find the reserved words defined in the sub class.
  // NOTICE, Since the Keyword properties defined by sub class, we couldn't call this
  // method during the parent class instantiation, because the sub class instance
  // isn't created yet.
  protected lazy val reservedWords: Seq[String] =
  this
    .getClass
    .getMethods
    .filter(_.getReturnType == classOf[Keyword])
    .map(_.invoke(this).asInstanceOf[Keyword].normalize)

  // Set the keywords as empty by default, will change that later.
  override val lexical = new SQLLexical

  protected def start: Parser[LogicalPlan]

  // Returns the whole input string
  protected lazy val wholeInput: Parser[String] = new Parser[String] {
    def apply(in: Input): ParseResult[String] =
      Success(in.source.toString, in.drop(in.source.length()))
  }

  // Returns the rest of the input string that are not parsed yet
  protected lazy val restInput: Parser[String] = new Parser[String] {
    def apply(in: Input): ParseResult[String] =
      Success(
        in.source.subSequence(in.offset, in.source.length()).toString,
        in.drop(in.source.length()))
  }
}
