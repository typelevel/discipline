package org.typelevel.discipline
package specs2

import org.scalacheck.Properties

import org.specs2.ScalaCheck
import org.specs2.SpecificationLike
import org.specs2.specification.core.Fragments
import org.specs2.scalacheck.Parameters

trait Discipline extends ScalaCheck { self: SpecificationLike =>

  def checkAll(name: String, ruleSet: Laws#RuleSet)(implicit p: Parameters) = {
    val fragments =
      Fragments.foreach(ruleSet.all.properties) { case (id, prop) =>
         s2""" ${id ! check(prop, p, defaultFreqMapPretty) }""" ^ br
      }

    s"""\n\n${ruleSet.name} laws must hold for ${name}\n\n""" ^
    fragments
  }

}

// vim: expandtab:ts=2:sw=2
