# UC-11 Command Goto

## Summary

Lets the player move between locations (based on coordinates).
  
## Main Success Scenario

* player types command 'goto ${direction}' where direction is one of [north, south,
  east, west, up, down]
    
* if the direction is available, from the current location (of the player)
  * move the player to the new location
  * give the player some information about the new location, he is now (like he gave the
    command 'look'
* if the direction is NOT available, give some message that the direction is not available.
  Give a tip about which directions are available.
