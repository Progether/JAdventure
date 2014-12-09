Feature: Walk around in the game. No characters should be around, that can attack you.


Scenario: Start a new game as SewerRat 

Given starting a new game as 'SewerRat'
When giving command 'look'
Then the feedback should be 
"""Welcome to Silliya, look.

Cave:
    A large open cave.
Items:
    shiv

To the South: 
    Stairs up to the outside world.
To the North: 
    A very long dark corridor."""


Scenario: Start a new game as SewerRat. Go south

Given starting a new game as 'SewerRat'
When giving command 'gs'
Then the feedback should be 
"""Stairs:
    Stairs up to the outside world.

Heading up: 
    The entrance to a cave
To the North: 
    A large open cave."""

Scenario: Start a new game as SewerRat. Go north

Given starting a new game as 'SewerRat'
When giving command 'gn'
Then the feedback should be 
"""Dark Corridor:
    A very long dark corridor.

To the East: 
    A very long dark corridor
To the South: 
    A large open cave.
To the North: 
    A very long dark corridor"""
