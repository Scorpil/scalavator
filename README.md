### Task

Design and implement an elevator control system. What data structures, interfaces and algorithms will you need? Your elevator control system should be able to handle a few elevators — say up to 16.

Can you please use Scala to implement it? In the end, your control system should provide an interface for:

1. Querying the state of the elevators (what floor are they on and where they are going),
2. receiving an update about the status of an elevator,
3. receiving a pickup request,
4. time-stepping the simulation.


### Solution

Usage:

    $ sbt console

    scala> import com.scorpil.elevator
    import com.scorpil.elevator

    scala> val ecs = elevator.ElevatorControlSystem(3)
    ecs: com.scorpil.elevator.ElevatorControlSystem =
    [Elevator #0 = Floor: 0 | Targets: Set() | Moving: No]
    [Elevator #1 = Floor: 0 | Targets: Set() | Moving: No]
    [Elevator #2 = Floor: 0 | Targets: Set() | Moving: No]

    scala> ecs.pickup(5, elevator.Direction.Up)

    scala> ecs.pickup(3, elevator.Direction.Down)

    scala> ecs.pickup(7, elevator.Direction.Up)

    scala> ecs
    res21: com.scorpil.elevator.ElevatorControlSystem =
    [Elevator #0 = Floor: 0 | Targets: Set(5, 7) | Moving: Up]
    [Elevator #1 = Floor: 0 | Targets: Set(3) | Moving: Up]
    [Elevator #2 = Floor: 0 | Targets: Set() | Moving: No]

    scala> ecs.step

    scala> ecs
    res23: com.scorpil.elevator.ElevatorControlSystem =
    [Elevator #0 = Floor: 1 | Targets: Set(5, 7) | Moving: Up]
    [Elevator #1 = Floor: 1 | Targets: Set(3) | Moving: Up]
    [Elevator #2 = Floor: 0 | Targets: Set() | Moving: No]

    scala> ecs.pickup(1, elevator.Direction.Down)

    scala> ecs
    res25: com.scorpil.elevator.ElevatorControlSystem =
    [Elevator #0 = Floor: 1 | Targets: Set(5, 7) | Moving: Up]
    [Elevator #1 = Floor: 1 | Targets: Set(3) | Moving: Up]
    [Elevator #2 = Floor: 0 | Targets: Set(1) | Moving: Up]

    scala> ecs.step

    scala> ecs
    res27: com.scorpil.elevator.ElevatorControlSystem =
    [Elevator #0 = Floor: 2 | Targets: Set(5, 7) | Moving: Up]
    [Elevator #1 = Floor: 2 | Targets: Set(3) | Moving: Up]
    [Elevator #2 = Floor: 1 | Targets: Set() | Moving: Up]

    scala> ecs.step

    scala> ecs
    res29: com.scorpil.elevator.ElevatorControlSystem =
    [Elevator #0 = Floor: 3 | Targets: Set(5, 7) | Moving: Up]
    [Elevator #1 = Floor: 3 | Targets: Set() | Moving: Up]
    [Elevator #2 = Floor: 1 | Targets: Set() | Moving: No

