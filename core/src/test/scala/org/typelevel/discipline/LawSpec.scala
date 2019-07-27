package org.typelevel.discipline

import org.scalacheck._

class DisciplineSpec extends Properties("A RuleSet should compute the properties correctly") {
  property("for rings") = Prop {
    expect(RingLaws.ring, ringExpected)
  }
  property("for additive groups") = Prop {
    expect(RingLaws.additiveGroup, additiveGroupExpected)
  }

  property("for multiplicative groups") = Prop {
    expect(RingLaws.multiplicativeGroup, multiplicativeGroupExpected)
  }
  def expect(ruleSet: Laws#RuleSet, props: List[String]) =
    ruleSet.all.properties.map(_._1) sameElements props

  val ringExpected = List(
    "ring.additive:group.base:group.associative",
    "ring.additive:group.base:group.identity",
    "ring.additive:group.base:group.inverse",
    "ring.multiplicative:monoid.base:monoid.associative",
    "ring.multiplicative:monoid.base:monoid.identity",
    "ring.distributive"
  )

  val additiveGroupExpected = List(
    "group.base:group.associative",
    "group.base:group.identity",
    "group.base:group.inverse"
  )

  val multiplicativeGroupExpected = List(
    "group.base:group.associative",
    "group.base:group.identity",
    "group.base:group.inverse",
    "group.reciprocal consistent"
  )
}

// vim: expandtab:ts=2:sw=2
