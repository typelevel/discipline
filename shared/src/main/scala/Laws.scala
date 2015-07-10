package org.typelevel.discipline

import scala.collection.SortedMap

import org.scalacheck.{Prop, Properties}

/**
 * Root trait of the law cake.
 *
 * Defines a wrapper around scalacheck's `Properties` ([[RuleSet]]), and some
 * default implementations.
 *
 * Extend this trait if you want to define a set of laws.
 */
trait Laws {

  /**
   * This trait abstracts over the various ways how the laws of a type class
   * can depend on the laws of other type classes. An instance of this trait is
   * called a ''rule set''.
   *
   * For that matter, we divide type classes into ''kinds'', where the classes
   * of one kind share the number of operations and meaning. For example,
   * `Semigroup`, `Monoid` and `Group` all belong to the same kind. On the
   * other hand, their additive variants also belong to a common kind, but to
   * a different one.
   *
   * Users of this trait should extend the outer trait [[Laws]] and create
   * specialized subtypes for each kind of type class. (See
   * [[DefaultRuleSet]] for an example.)
   *
   * Consider this example hierarchy:
   * <pre>
   * Semigroup
   *     |   \
   *  Monoid   AdditiveSemigroup
   *     |   \        |
   *  Group     AdditiveMonoid
   *         \        |
   *            AdditiveGroup
   * </pre>
   * They all define their own laws, as well as a couple of parent classes.
   * If we want to check the laws of `AdditiveGroup`, we want to avoid checking
   * properties twice, i.e. do not want to check `Monoid` laws via `Group` and
   * also via `AdditiveMonoid`.
   *
   * To address this problem, we define the parent in the same kind as
   * ''parent'', and other parents as ''bases''. In this example, the parent of
   * `AdditiveGroup` is `Group`, and its only basis is `Group`. On the other
   * hand, the parent of `Group` is `Monoid`, and it does not have any bases.
   *
   * The set of all properties of a certain class is now defined as union of
   * these sets:
   *  - the properties of the class itself
   *  - recursively, the properties of all its parents (ignoring their bases)
   *  - recursively, the set of ''all'' properties of its bases
   *
   * Looking at our example, that means that `AdditiveGroup` includes the
   * `Monoid` law only once, because it is the parent of its basis. The
   * same laws are ignored by its parent `AdditiveMonoid`, hence no redundant
   * checks occur.
   *
   * Of course, classes can have multiple parents and multiple (named) bases.
   * The only requirement here is that ''inside one kind'', the identifier of
   * a property is unique, since duplicates are eliminated. To avoid name
   * clashes ''between different kinds'', the names of properties pulled in
   * via a basis are prefixed with the name of the basis.
   *
   * For better type-safety, ''parents'' are only allowed to come from the
   * same outer instance of [[Laws]], whereas ''bases'' are allowed to come
   * from anywhere.
   */
  trait RuleSet {
    def name: String
    def bases: Seq[(String, Laws#RuleSet)]
    def parents: Seq[RuleSet]
    def props: Seq[(String, Prop)]

    private def collectParentProps: SortedMap[String, Prop] =
      SortedMap(props: _*) ++ parents.flatMap(_.collectParentProps)

    /** Assembles all properties. For the rules, see [[RuleSet]]. */
   final def all: Properties = new AllProperties(name , bases ,collectParentProps)
  }

  /**
   * Convenience trait to mix into subclasses of [[RuleSet]] for
   * rule sets which only have one parent.
   */
  trait HasOneParent { self: RuleSet =>
    def parent: Option[RuleSet]

    final def parents = parent.toList
  }

  /**
   * Convenience class for rule sets which may have a parent, but no bases.
   */
  class DefaultRuleSet(
    val name: String,
    val parent: Option[RuleSet],
    val props: (String, Prop)*
  ) extends RuleSet with HasOneParent {
    val bases = Seq.empty
  }

  /**
   * Convenience class for rule sets without parents and bases.
   */
  class SimpleRuleSet(
    name: String,
    props: (String, Prop)*
  ) extends DefaultRuleSet(name, None, props: _*)

  /** Empty rule set. */
  def emptyRuleSet: RuleSet = new SimpleRuleSet(
    name = "<empty>"
  )

}

class AllProperties(name: String, bases: Seq[(String, Laws#RuleSet)], collectParentProps: => SortedMap[String, Prop]) extends Properties(name) {
  for {
    (baseName, baseProps) ← bases.sortBy(_._1)
    (name, prop) ← baseProps.all.properties
  } property(baseName + ":" + name) = prop

  for ((name, prop) ← collectParentProps)
    property(name) = prop
}

// vim: expandtab:ts=2:sw=2
