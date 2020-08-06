
package com.jj.postspark.parser

import scala.util.parsing.combinator.lexical.StdLexical
import scala.util.parsing.input.CharArrayReader.EofCh

class SQLLexical extends StdLexical {
  case class DecimalLit(chars: String) extends Token {
    override def toString: String = chars
  }

  def initialize(keywords: Seq[String]): Unit = {
    reserved.clear()
    reserved ++= keywords
  }

  /* Normal the keyword string */
  def normalizeKeyword(str: String): String = str.toLowerCase

  delimiters += (
    "@", "*", "+", "-", "<", "=", "<>", "!=", "<=", ">=", ">", "/", "(", ")",
    ",", ";", "%", "{", "}", ":", "[", "]", ".", "&", "|", "^", "~", "<=>"
  )

  protected override def processIdent(name: String) = {
    val token = normalizeKeyword(name)
    if (reserved contains token) Keyword(token) else Identifier(name)
  }

  override lazy val token: Parser[Token] =
    ( rep1(digit) ~ scientificNotation ^^ { case i ~ s => DecimalLit(i.mkString + s) }
      | '.' ~> (rep1(digit) ~ scientificNotation) ^^
      { case i ~ s => DecimalLit("0." + i.mkString + s) }
      | rep1(digit) ~ ('.' ~> digit.*) ~ scientificNotation ^^
      { case i1 ~ i2 ~ s => DecimalLit(i1.mkString + "." + i2.mkString + s) }
      | digit.* ~ identChar ~ (identChar | digit).* ^^
      { case first ~ middle ~ rest => processIdent((first ++ (middle :: rest)).mkString) }
      | rep1(digit) ~ ('.' ~> digit.*).? ^^
      {
        case i ~ None => NumericLit(i.mkString)
        case i ~ Some(d) => DecimalLit(i.mkString + "." + d.mkString)
      }
      | '\'' ~> chrExcept('\'', '\n', EofCh).* <~ '\'' ^^
      { case chars => StringLit(chars mkString "") }
      | '"' ~> chrExcept('"', '\n', EofCh).* <~ '"' ^^
      { case chars => StringLit(chars mkString "") }
      | '`' ~> chrExcept('`', '\n', EofCh).* <~ '`' ^^
      { case chars => Identifier(chars mkString "") }
      | EofCh ^^^ EOF
      | '\'' ~> failure("unclosed string literal")
      | '"' ~> failure("unclosed string literal")
      | delim
      | failure("illegal character")
      )

  override def identChar: Parser[Elem] = letter | elem('_')

  private lazy val scientificNotation: Parser[String] =
    (elem('e') | elem('E')) ~> (elem('+') | elem('-')).? ~ rep1(digit) ^^ {
      case s ~ rest => "e" + s.mkString + rest.mkString
    }

  override def whitespace: Parser[Any] =
    ( whitespaceChar
      | '/' ~ '*' ~ comment
      | '/' ~ '/' ~ chrExcept(EofCh, '\n').*
      | '#' ~ chrExcept(EofCh, '\n').*
      | '-' ~ '-' ~ chrExcept(EofCh, '\n').*
      | '/' ~ '*' ~ failure("unclosed comment")
      ).*

}
