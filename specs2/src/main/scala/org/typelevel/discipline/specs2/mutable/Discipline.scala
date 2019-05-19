package org.typelevel.discipline
package specs2.mutable

import org.specs2.mutable.SpecificationLike
import org.specs2.ScalaCheck
import org.specs2.scalacheck.Parameters
import org.specs2.specification.core.Fragments

trait Discipline extends ScalaCheck { self: SpecificationLike =>

  def checkAll(name: String, ruleSet: Laws#RuleSet)(implicit p: Parameters) = {
    s"""${ruleSet.name} laws must hold for ${name}""".txt
    br
    Fragments.foreach(ruleSet.all.properties.toList) { case (id, prop) =>
      id in check(prop, p, defaultFreqMapPretty)
    }
  }

}

// vim: expandtab:ts=2:sw=2
