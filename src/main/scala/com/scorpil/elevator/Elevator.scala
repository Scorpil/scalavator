package com.scorpil.elevator

/** Elevator state at one moment in time (step).
  *
  * @constructor
  * @param currentFloor position of the elevator at the moment
  * @param targetFloors list of floors elevator tries to reach
  * @param directionOpt current elevator travel direction. None represents that elevator stands still.
  */
case class Elevator(id: Int, currentFloor: Int, val targetFloors: Set[Int], directionOpt: Option[Direction.Direction]) {

  def status: (Int, Set[Int], Option[Direction.Direction]) = (currentFloor, targetFloors, directionOpt)

  /** Returns number of target floors in specified direction */
  def countTargets(direction: Direction.Direction): Int = {
    val selectFunc = direction match {
      case Direction.Up   => floor: Int => floor > currentFloor
      case Direction.Down => floor: Int => floor < currentFloor
    }
    targetFloors.count(selectFunc)
  }

  /** Returns new elevator instance after on step in specified direction */
  def move(direction: Direction.Direction): Elevator = {
    val newFloor = direction match {
      case Direction.Up   => currentFloor + 1
      case Direction.Down => currentFloor - 1
    }
    Elevator(id, newFloor, targetFloors - newFloor, Some(direction))
  }

  /** Returns preferable direction or None if no floors are targeted */
  def decideDirection: Option[Direction.Direction] = {
    if (targetFloors.isEmpty)
      None
    else if (countTargets(Direction.Up) > countTargets(Direction.Down))
      Some(Direction.Up)
    else
      Some(Direction.Down)
  }

  /** Continue in the same direction until all target floors in that direction are reached.
    * Reverse direction after that. Stop if no target floors present.
    */
  def step: Elevator =
    directionOpt match {
      case Some(direction) =>
        if (countTargets(direction) > 0)
          move(direction)
        else if (countTargets(direction.reverse) > 0)
          move(direction.reverse)
        else
          Elevator(id, currentFloor, Set(), None)
      case None =>
        decideDirection match {
          case Some(direction) => Elevator(id, currentFloor, targetFloors, Some(direction)).step
          case None => this // nothing to do
        }
    }

  /** Add floor to the list of target floors.
    * Emulates person pressing a button(s) _inside_ the elevator.
    */
  def goTo(newTargetFloors: Set[Int]): Elevator = {
    val tmpElevator = Elevator(id, currentFloor, targetFloors ++ newTargetFloors, directionOpt)
    Elevator(id, currentFloor, tmpElevator.targetFloors, tmpElevator.decideDirection)
  }

  override def toString: String = {
    val movement = directionOpt match {
      case None => "No"
      case Some(direction) => direction
    }
    s"[Elevator #$id = Floor: $currentFloor | Targets: $targetFloors | Moving: $movement]"
  }
}
