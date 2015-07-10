package org.typelevel.discipline
package scalatest

import org.scalacheck.Properties

import org.scalatest.FunSuiteLike
import org.scalatest.prop.Checkers

trait Discipline extends Checkers { self: FunSuiteLike =>

  def checkAll(name: String, ruleSet: Laws#RuleSet) {
    for ((id, prop) ← ruleSet.all.properties)
      test(name + "." + id) {
        check(prop)
      }
  }

}

// vim: expandtab:ts=2:sw=2
