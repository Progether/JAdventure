# Improvement Ideas

Project Collaboration takes place at [Reddit - Progether](https://www.reddit.com/r/progether/)
search there for [JAdventure](https://www.reddit.com/search?q=jadventure&sort=new).

Or drop by at our [IRC channel reddit-progether](http://webchat.freenode.net/?channels=reddit-progether&uio=d4)


## Suggestions

* Rename LocationRepository to WorldRepository, as it contains the games world.
* Add support for multiple games
  This should relative easy, as the game is already stored on disk (in json format).
  A concrete sollution could be to store each game in a separate file 'world-xxx.json'.
  Or each game in it's own directory like  game-data/${world-name}/world.json; items.json; etc

