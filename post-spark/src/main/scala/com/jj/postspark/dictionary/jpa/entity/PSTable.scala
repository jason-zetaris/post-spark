
package com.jj.postspark.dictionary.jpa.entity

import javax.persistence.{CascadeType, Column, Entity, JoinColumn, ManyToOne, OneToMany, OrderBy, Table}
import org.hibernate.annotations.{LazyCollection, LazyCollectionOption, OnDelete, OnDeleteAction}

@Entity
@Table(name = "PS_TABLE")
class PSTable {
  @Column(name = "NAME", nullable = false)
  var name: String = _

  @Column(name = "UPPER_CASE_NAME", nullable = false)
  var upperCaseName: String = _

  @ManyToOne
  @JoinColumn(name = "FK_SCHEMA_ID")
  @OnDelete(action = OnDeleteAction.CASCADE)
  var schema: PSSchema = _

  @OneToMany(mappedBy = "table", targetEntity = classOf[PSColumn], orphanRemoval=true, cascade = Array(CascadeType.ALL))
  @LazyCollection(LazyCollectionOption.FALSE)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @OrderBy("id ASC")
  var columns: java.util.List[PSColumn] = new java.util.ArrayList()

  def addColumn(column: PSColumn): Unit = {
    column.table = this
    columns.add(column)
  }
}
