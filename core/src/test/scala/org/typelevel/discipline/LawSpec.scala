/*
 * Copyright (c) 2013-2021 Typelevel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
    ruleSet.all.properties.map(_._1).sameElements(props)

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
