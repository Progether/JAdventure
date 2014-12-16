# Architecture

## The Layers

**Warning** [2014.12.16] Currently not there, still need to be implemented.

There are three [layers](http://en.wikipedia.org/wiki/Multitier_architecture):  

- User Interface Layer (presentation)
- Game Engine Layer (logic layer)
- Persistence Layer (data layer)

There is also an diagram [layer architecture](https://raw.githubusercontent.com/Progether/JAdventure/master/src/site/resources/images/diagrams/overview_layer_architecture.png)

The most important class here is the _GameEngine_ as it is the [facade](http://en.wikipedia.org/wiki/Facade_pattern) to the game.

The most important method is the _GameEngine_ is _execute(String commandText)_ which returns the result of the command / action.

```
    GameEngine gameEngine = new GameEngine();
    String result = gameEngine.execute("get key");
    System.out.println(result);
```
