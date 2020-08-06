
package com.jj.postspark.dictionary.jpa.entity

import javax.persistence.{Column, Entity, GeneratedValue, GenerationType, Id, Table}

@Entity
@Table(name = "PS_DATABASE")
class PSDatabase {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  var id: Long = _

  @Column(name = "NAME", nullable = false)
  var name: String = _

  @Column(name = "UPPER_CASE_NAME", nullable = false)
  var upperCaseName: String = _
}
