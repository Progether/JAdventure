JAdventure
==============================================

Description
-------------
A text-based game written in Java. It was originally created as a project of the [progether subreddit](http://www.reddit.com/r/progether) and worked on by Applzor, add7, geniuus, Malfunction, bdong_, Qasaur, and rock-fish. It was revived by Hawk554.

![alt tag](http://i.imgur.com/Fhn2dEr.png)

Contributors
-------------

 1. [Hawk554](https://github.com/hawk554)
 1. [projectdelphai](https://github.com/projectdelphai)
 1. [CageHN](https://github.com/CageHN)
 2. [blackwolf12333](https://github.com/blackwolf12333)

Contributing
-------------

 1. Create an issue (optional)
 1. Fork the codebase
 1. Clone your fork
 1. Create a branch

      git branch <branchName>
      git checkout <branchName>

 1. Make your changes
 1. Push to your branch

      git push origin <branchName>

 1. Make a pull request between your branch and the progether master

Setting Up The Game For Development or Gameplay
-----------------

You can download the game with:

    git clone https://github.com/hawk554/JAdventure

###Run with Ant###

Install apache-ant

Run the game with:

    ant -emacs run

It will automatically compile, test, and run the game.


Playing the Game
--------------

To start a new game:

    start

Get a list of commands with:

    help

To get a list of monsters around you:

    monsters

To get your own current status/stats:

    stats

To quit the game:

    quit

=======
Todo
------------------
 1. Cleanup code
 1. Remove/fix methods that don't work or are not implemented

Coding Standard/Convention/Style
-------------------
To help make code more readable, understandable, and consistent, each contributor should follow the set guidelines lain out below. If you disagree with something or come across a style that has not been decided upon, make an issue or a pull request respectively for discussion on the best style. The standard will be decided based on either majority rule or official documentation (i.e. oracle coding standard).

 1. Spaces not tabs
 1. Space width is 4
 1. No beginning/trailing empty lines
 1. Each file should start with an import statement if necessary single-spaced
 1. After class declaration and between class methods, there should be a space.
 1. Within methods, there should be no spaces unless to separate specific chuncks of code (i.e not for if statements, return values, or loops)
 1. Opening curly braces appear a space after the closing parentheses, not directly after.
 1. Closing curly braces appear on own line unless followed by an else etc. which will be a space after the curly brace.
 1. Comments should have one space after "//"
 1. Comments 4 or greater in length should be multi-line commented

Changelog
------------

16NOV2013
- changed the variables from public to private in Entity class
- created setters and getters in Entity class
- added the singleton pattern in the player class so that only one player can ever be instasiated except when loading
- changed accessing public variables of Entity to accessing them through accessor "get" methods.
    - Instead of player.backpack it's player.getBackpack()
-cleaned up code in all the menus (PlayerMenu, DebugMenu...)

14NOV2013
- cleaned up code
- added basics for proper items

30OCT2013 
- projectdelphai cleaned code to new project standards. 
- Added projectdelphai as a contributor.
- Created milestone for completing menu system.
- started work on json menus
- made main menu
- started work for profile saves
