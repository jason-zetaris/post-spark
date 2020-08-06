
package com.jj.postspark.dictionary.jpa.entity

import javax.persistence.{CascadeType, Column, Entity, GeneratedValue, GenerationType, Id, JoinColumn, ManyToOne, OneToOne, Table}
import org.hibernate.annotations.{OnDelete, OnDeleteAction}

@Entity
@Table(name = "PS_PARTITION")
class PSPartition {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Long = _

  @Column(name = "PART_COLUMN", nullable = false)
  var partColumn: String = _

  @Column(name = "PART_COUNT", nullable = true)
  var partCount: Int = _

  @Column(name = "LOWERBOUND", nullable = true)
  var lowerBound: Int = _

  @Column(name = "UPPERBOUND", nullable = true)
  var upperBound: Int = _

  @ManyToOne(optional = true)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "FK_TABLE_ID")
  var table: PSTable = _
}
