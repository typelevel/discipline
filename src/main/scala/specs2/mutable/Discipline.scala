package org.typelevel.discipline
package specs2.mutable

import org.scalacheck.Properties

import org.specs2.specification.Fragments
import org.specs2.matcher.Parameters
import org.specs2.matcher.ScalaCheckMatchers
import org.specs2.mutable.SpecificationLike

trait Discipline extends ScalaCheckMatchers { self: SpecificationLike =>

  def checkAll(name: String, ruleSet: Laws#RuleSet)(implicit p: Parameters) =
    addFragments(name + " " + ruleSet.name,
      for ((id, prop) ← ruleSet.all.properties) yield { id in check(prop)(p) }
      , "must satisfy"
    )

}

// vim: expandtab:ts=2:sw=2
