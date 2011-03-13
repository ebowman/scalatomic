/*
 * Copyright 2011 Eric Bowman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package scalatomic

import org.specs._

/**
 * Specifition for basic tests for AtomicRef and AtomicInt.
 *
 * @author Eric Bowman
 * @since 2011-03-13 22:30
 */

class AtomicSpec extends Specification {
  "Atomic" should {
    "construct correctly using the factory method based on type" in {
      Atomic(7).isInstanceOf[AtomicInt] must_== true
      Atomic("foo").isInstanceOf[AtomicRef[String]] must_== true
    }

    "get and set correctly" in {
      val atomic = Atomic(7)
      (atomic ->) must_== 7
      atomic <= 8
      (atomic ->) must_== 8

      val atomicStr = Atomic("foo")
      (atomicStr ->) must_== "foo"
      atomicStr <= "bar"
      (atomicStr ->) must_== "bar"
    }

    "compare and set correctly" in {
      val atomicInt = Atomic(7)
      atomicInt ?= (7, 8)
      (atomicInt ->) must_== 8
      atomicInt ?= (7, 9)
      (atomicInt ->) must_== 8

      val atomicStr = Atomic("foo")
      atomicStr ?= ("foo", "bar")
      (atomicStr ->) must_== "bar"
      atomicStr ?= ("foo", "FOO")
      (atomicStr ->) must_== "bar"
    }

    "swap correctly" in {
      val atomicInt = Atomic(7)
      (atomicInt <=> 8) must_== 7
      (atomicInt ->) must_== 8

      val atomicStr = Atomic("foo")
      (atomicStr <=> "bar") must_== "foo"
      (atomicStr ->) must_== "bar"
    }
  }
}
