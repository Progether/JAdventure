# UC-01 Load Saved Game

## Status

There is an issue with the current implementation.
  
* [Locations are copied, but not updated when game is saved](https://github.com/verhagen/JAdventure/issues/23)
  

## Summary

When starting the game, the user can choose to load a previous saved game.
The player starts at the location, where he / she was when the game was saved.

## Direct Actors

Player

## Priority

Essential

## Use Frequency

Often

## Main Success Scenario

1. launch the application, show a menu, with the option _load a saved game_
1. ask the user his name, used for loading an existing game
1. load game based on the _user-name_
1. show the room / location description as if the player just entered the room / location


### Not successful loading existing game

1. in case the game is not found, for the given player-name, ask again the user name and 
   try to load the game again.
 