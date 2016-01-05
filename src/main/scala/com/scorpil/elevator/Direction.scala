package com.scorpil.elevator

object Direction extends Enumeration {
  type Direction = Value
  val Up, Down = Value

  /** Returns relative direction from one floor to another */
  def relative(from: Int, to: Int): Direction = if (from < to) Up else Down // FIXME: handle from == to

  protected case class DirectionValue(direction: Value) {
    def reverse: Direction = direction match {
      case Up => Down
      case Down => Up
    }

    override def toString: String = direction match {
      case Up => "Up"
      case Down => "Down"
    }
  }

  implicit def valueToDirectionValue(direction: Value): DirectionValue = DirectionValue(direction)
}
