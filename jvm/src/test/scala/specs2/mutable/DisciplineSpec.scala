package org.typelevel.discipline
package specs2.mutable

import org.specs2.mutable.Specification

class MutableDisciplineSpec extends Specification with Discipline {
  checkAll("Int", RingLaws.ring)
}

// vim: expandtab:ts=2:sw=2
