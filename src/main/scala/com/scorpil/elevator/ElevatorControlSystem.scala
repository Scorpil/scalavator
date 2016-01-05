package com.scorpil.elevator

/** Controls elevator list and distributes tasks between them
  * @constructor
  * @param elevatorCount number of elevators in a list
  */
class ElevatorControlSystem(elevatorCount: Int) {

  var elevators = List.range(0, elevatorCount).map(i => Elevator(i, 0, Set(), None))

  /** Aggregate status of all elevators  */
  def status = elevators.map(e => e.status)

  /** Dispatches least busy elevator to requested floor */
  def pickup(floor: Int, direction: Direction.Direction) {
    // start search for elevator
    val worker = elevators.find(
      e => e.directionOpt match {
        case None => true // if this elevator is not doing anything - take it
        case Some(elevatorDirection) => { // if elevator is on the move and moves in the same direction - take it
          val position = Direction.relative(e.currentFloor, floor)
          ((position == direction) && (direction == elevatorDirection))
        }
      }
    ) match {
      case Some(elevator) => elevator // if elevator is found - great :)
      case None => elevators.minBy(e => e.targetFloors.size) // else, return elevator with smalles numbers of targets
    }
    update(worker.id, Set(floor))
  }

  /** For specified elevator, adds new set of floors to the set of targeted floors */
  def update(id: Int, targetFloors: Set[Int]) {
    elevators = elevators.map(e => if (e.id == id) e.goTo(targetFloors) else e)
  }

  /** Perform one step of simulation */
  def step {
    elevators = elevators.map(e => e.step)
  }

  override def toString: String = elevators.mkString("\n")
}
