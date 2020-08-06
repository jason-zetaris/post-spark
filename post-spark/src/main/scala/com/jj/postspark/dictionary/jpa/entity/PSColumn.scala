
package com.jj.postspark.dictionary.jpa.entity

import javax.persistence.{Column, Entity, GeneratedValue, GenerationType, Id, JoinColumn, ManyToOne, Table}
import org.hibernate.annotations.{OnDelete, OnDeleteAction}

@Entity
@Table(name = "PS_SCHEMA")
class PSColumn {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  var id: Long = _

  @ManyToOne(optional = true)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "FK_TABLE_ID")
  var table: PSTable = _

  @Column(name = "NAME", nullable = false)
  var name: String = _

  @Column(name = "UPPER_CASE_NAME", nullable = false)
  var upperCaseName: String = _

  @Column(name = "ORG_DATA_TYPE", nullable = false)
  var orgDataType: String = _

  @Column(name = "DATA_TYPE", nullable = false)
  var dataType: String = _

  @Column(name = "NULLABLE", nullable = false)
  var nullable: Boolean = _

  @Column(name = "PRIMARY_KEY", nullable = true)
  var primaryKey: Boolean = _

  @Column(name = "REFERENCE_TABLE", nullable = true)
  var referenceTable: String = _

  @Column(name = "REFERENCE_COLUMN", nullable = true)
  var referenceColumn: String = _

  @Column(name = "INDEX_NAME", nullable = true)
  var indexName: String = _

  @Column(name = "AUTOINCREMENT", nullable = true)
  var autoIncrement: Boolean = _
}
