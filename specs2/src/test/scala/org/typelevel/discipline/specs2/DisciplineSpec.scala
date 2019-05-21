package org.typelevel.discipline
package specs2

import org.specs2.Specification

class DisciplineSpec extends Specification with Discipline { def is =
  checkAll("Int", RingLaws.ring)
}

// vim: expandtab:ts=2:sw=2
