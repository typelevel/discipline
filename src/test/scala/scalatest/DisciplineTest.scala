package org.typelevel.discipline
package scalatest

import org.scalatest.FunSuite

class LawTests extends FunSuite with Discipline {
  checkAll("Int", RingLaws.ring)
}

// vim: expandtab:ts=2:sw=2
