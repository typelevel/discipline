package org.typelevel.discipline
package scalatest

import org.scalatest.funsuite.AnyFunSuiteLike

class LawTests extends AnyFunSuiteLike with Discipline {
  checkAll("Int", RingLaws.ring)
}

// vim: expandtab:ts=2:sw=2
