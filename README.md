![](http://i.imgur.com/xswo3w8.png "test")
==============================================


Description
-------------
JAdventure is a Java (text) based implementation of a
[Role Playing Game](http://en.wikipedia.org/wiki/Role-playing_game) (RPG) -
[Single Player](http://en.wikipedia.org/wiki/Role-playing_game#Single-player).


Project Background
-------------
It was originally created as a project of the
[progether subreddit](http://www.reddit.com/r/progether)
and worked on by Applzor, add7, geniuus, Malfunction, bdong\_, Qasaur, and
tamul. It was revived by Hawk554.

Project Status
--------------
**Under Construction** This project is in a alpha status. The player can walk
through the game, find items and fight with some _nice_ characters!

Developers are Welcome
----------------------
Everyone is welcome to help with the creation of JAdventure!

The best way to find out more about the game and the ideas that are there, is
to have a look at [Reddit - JAdventure](https://www.reddit.com/search?q=jadventure).

Or drop by at our [IRC channel reddit-progether](http://webchat.freenode.net/?channels=reddit-progether&uio=d4)

All documentation is avaiable from the [JAdventure wiki](https://github.com/progether/JAdventure/wiki)
and from the [JAdventure website](https://progether.github.io/JAdventure). These
form the majority of the project's documentation and development guidelines.

### Contributors ###

 1. [Hawk554](https://github.com/hawk554)
 1. [projectdelphai](https://github.com/projectdelphai)
 1. [CageHN](https://github.com/CageHN)
 1. [kzisme](https://github.com/kzisme)
 1. [blackwolf12333](https://github.com/blackwolf12333)
 1. [MikesNorth](https://github.com/mikesnorth)
 1. [pthayer3](https://github.com/pthayer3)
 1. [Reinecker](https://github.com/reinecker)
 1. [tamul](https://github.com/tamul)
 1. [shkesar](https://github.com/shkesar)
 1. [paddatrapper](https://github.com/paddatrapper)
 1. [Dev-Osmium](https://github.com/Dev-Osmium)

Contributing
-------------
**Don't make a pull request to the main repo's master if you don't want your**
**commit merged yet. Don't make pull requests if you're not willing to merge**
**that code.**

Rather than opening a pull request, consider opening an issue to discuss the
problem you're fixing. Keep the work-in-progress updates to your fork, maybe
merging the updates to a separate branch in the main repo if there's enough
there to warrant it.

 1. Create an issue (optional)
 1. Fork the codebase
 1. Clone your fork
 1. Create a branch

    ```
    git branch <branchName>
    git checkout <branchName>
    ```

 1. Make your changes
 1. Push to your branch

    ```
    git push origin <branchName>
    ```

 1. Make a pull request between your branch and the progether master

Setting Up The Game For Development or Gameplay
-----------------------------------------------

### Gameplay
1. Download the game from [here](https://github.com/Progether/JAdventure/releases)
2. Extract the game files
3. Go into the game directory and run `java -jar jadventure-XX.jar` (where XX is the version)

### Run with Maven - Developers
1. Install [Maven](http://maven.apache.com)
1. Go into the game directory and run
   ```
   $ mvn test
   $ mvn exec:java
   ```

to play the game.

Maven has other useful targets such as `mvn compile` to compile and
`mvn install` to install a copy of JAdventure and all its dependencies to the
local Maven repo on your machine.

Playing the Game
----------------

To start a new game:

    start

To save a game:

    s

Get a list of commands with:

    h

To get a list of monsters around you:

    m

To view details about your player:

    v <s,e,b> - view status, equipped items, backpack

To quit the game:

    exit

To move:

    g n - go north
    g s - go south
    g e - go east
    g w - go west

To pick up an item:

    p <itemName>

To drop an item:

    d <itemName>

To equip/unequip item:

    e <itemName>
    ue <itemName>

To attack:

    a <monster>

To look around:

    la

To talk to a Non-player Character:

    t <npc>

Coding Standard/Convention/Style
--------------------------------
To help make code more readable, understandable, and consistent, each
contributor should follow the set guidelines laid out below. If you disagree
with something or come across a style that has not been decided upon, make an
issue or a pull request respectively for discussion on the best style. The
standard will be decided based on either majority rule or official documentation
(i.e. oracle coding standard).

 1. Spaces not tabs
 1. Space width is 4
 1. No beginning/trailing empty lines
 1. Each file should start with an import statement if necessary single-spaced
 1. After class declaration and between class methods, there should be a space.
 1. Within methods, there should be no spaces unless to separate specific chunks
    of code (i.e not for if statements, return values, or loops)
 1. Opening curly braces appear a space after the closing parentheses, not
    directly after.
 1. Closing curly braces appear on own line unless followed by an else etc.
    which will be a space after the curly brace.
 1. Comments should have one space after "//"
 1. Comments 4 lines or greater in length should be multi-line commented
