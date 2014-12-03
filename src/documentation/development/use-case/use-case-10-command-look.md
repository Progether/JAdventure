# SUC-10 Command Look

## Summary

Shows the player it's current location, by describing the location.

When there are possibilities to go to other location, from this location, then they are also described.

When there are items at this location, then they are also described.

When there are monsters at this location, then they are also described.

When there are non playing characters at this location, then they are also described.
  
## Main Success Scenario

- player types command _lookround_ **la**
- Return a description of the location and of the things possibly seen here like:
  - exits
  - items
  - monsters
  - non playing characters
