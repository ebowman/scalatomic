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

import java.util.concurrent.atomic.{AtomicInteger, AtomicReference}

trait Atomic[T] {
  def ?=(test: T, newVal: T): Boolean

  def <=(value: T)

  def -> : T

  def <=>(newValue: T): T
}

case class AtomicRef[T](ref: AtomicReference[T]) extends Atomic[T] {

  def ?=(test: T, newVal: T): Boolean = ref.compareAndSet(test, newVal)

  def <=(value: T) {
    ref.set(value)
  }

  def -> = ref.get()

  def <=>(newValue: T) = ref.getAndSet(newValue)
}

case class AtomicInt(ref: AtomicInteger) extends Atomic[Int] {

  def <=>(newValue: Int) = ref.getAndSet(newValue)

  def -> = ref.get

  def <=(value: Int) {
    ref.set(value)
  }

  def ?=(test: Int, newVal: Int) = ref.compareAndSet(test, newVal)
}

object Atomic {
  def apply[T](t: T): Atomic[T] = {
    t match {
      case t: Int => new AtomicInt(new AtomicInteger(t)).asInstanceOf[Atomic[T]]
      case t => new AtomicRef(new AtomicReference(t))
    }
  }
}

