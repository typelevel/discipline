package org.typelevel.discipline
package specs2

import org.scalacheck.Properties

import org.specs2.SpecificationLike
import org.specs2.specification.Fragments
import org.specs2.matcher.Parameters
import org.specs2.matcher.ScalaCheckMatchers

trait Discipline extends ScalaCheckMatchers with SpecificationLike {

  def checkAll(name: String, ruleSet: Laws#RuleSet)(implicit p: Parameters) = {
    val fragments =
      for ((id, prop) ← ruleSet.all.properties)
      yield s2""" ${id ! check(prop)(p) }""" ^ br

    val all =
      fragments.foldLeft(Fragments.createList())(_ append _)

    s"""\n\n${ruleSet.name} laws must hold for ${name}\n\n""" ^
    all
  }

}

// vim: expandtab:ts=2:sw=2
