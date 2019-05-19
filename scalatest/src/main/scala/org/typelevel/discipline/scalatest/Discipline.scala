package org.typelevel.discipline
package scalatest

import org.scalacheck.Properties

import org.scalatest.funsuite.AnyFunSuiteLike
import org.scalatest.check.Checkers

trait Discipline extends Checkers { self: AnyFunSuiteLike =>

  def checkAll(name: String, ruleSet: Laws#RuleSet): Unit = {
    for ((id, prop) ← ruleSet.all.properties)
      test(name + "." + id) {
        check(prop)
      }
  }

}

// vim: expandtab:ts=2:sw=2
