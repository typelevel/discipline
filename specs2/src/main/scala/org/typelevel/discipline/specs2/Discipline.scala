package org.typelevel.discipline
package specs2

import org.scalacheck.Properties

import org.specs2.ScalaCheck
import org.specs2.SpecificationLike
import org.specs2.specification.core.Fragments
import org.specs2.scalacheck.Parameters

trait Discipline extends ScalaCheck { self: SpecificationLike =>

  def checkAll(name: String, ruleSet: Laws#RuleSet)(implicit p: Parameters) = {
    s"""${ruleSet.name} laws must hold for ${name}""" ^ br ^ t ^
    Fragments.foreach(ruleSet.all.properties.toList) { case (id, prop) =>
       id ! check(prop, p, defaultFreqMapPretty) ^ br
    } ^ br ^ bt
  }

}

// vim: expandtab:ts=2:sw=2
