package org.typelevel.discipline

import org.specs2.Specification

import org.scalacheck.Properties

class DisciplineSpec extends Specification { def is = s2"""
  A RuleSet should compute the properties correctly
    for rings                   ${ring}
    for additive groups         ${additiveGroup}
    for multiplicative groups   ${multiplicativeGroup}
  """

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

  val ring =
    expect(RingLaws.ring, ringExpected)

  val additiveGroup =
    expect(RingLaws.additiveGroup, additiveGroupExpected)

  val multiplicativeGroup =
    expect(RingLaws.multiplicativeGroup, multiplicativeGroupExpected)

  def expect(ruleSet: Laws#RuleSet, props: List[String]) =
    ruleSet.all.properties.map(_._1) must containTheSameElementsAs(props)

}

// vim: expandtab:ts=2:sw=2
