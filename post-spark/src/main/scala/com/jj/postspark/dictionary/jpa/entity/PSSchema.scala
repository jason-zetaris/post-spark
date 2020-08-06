
package com.jj.postspark.dictionary.jpa.entity

import javax.persistence.{Column, Entity, GeneratedValue, GenerationType, Id, JoinColumn, ManyToOne, Table}
import org.hibernate.annotations.{OnDelete, OnDeleteAction}

@Entity
@Table(name = "PS_SCHEMA")
class PSSchema {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Long = _

  @Column(name = "NAME", nullable = false)
  var name: String = _

  @Column(name = "UPPER_CASE_NAME", nullable = false)
  var upperCaseName: String = _

  @ManyToOne
  @JoinColumn(name = "FK_DATABASE_ID")
  @OnDelete(action = OnDeleteAction.CASCADE)
  var database: PSDatabase = _
}
