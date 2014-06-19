package de.schauderhaft.degraph.check.hamcrest

import org.scalatest.matchers.{ BeMatcher => SMatcher }
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.Description

case class HamcrestWrapper[T](sMatcher: SMatcher[T]) extends TypeSafeMatcher[T] {
  override def matchesSafely(item: T): Boolean = sMatcher(item).matches
  override def describeMismatchSafely(item: T, mismatchDescription: Description): Unit =
    mismatchDescription.appendText(sMatcher(item).failureMessage)
  override def describeTo(description: Description): Unit = description.appendValue(sMatcher.getClass().getName())
}