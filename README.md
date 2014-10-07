![](http://i.imgur.com/xswo3w8.png "test")
==============================================


Description
-------------
A text-based game written in Java. It was originally created as a project of the [progether subreddit](http://www.reddit.com/r/progether) and worked on by Applzor, add7, geniuus, Malfunction, bdong_, Qasaur, and tamul. It was revived by Hawk554.

Status
-------------
**Under Construction**

Developers are Welcome
-------------

Everyone is welcome to help with the creation of JAdventure!
    
The best way to find out more about the game and the ideas that are there, is to have a look at
[Reddit - JAdventure](http://www.reddit.com/search?q=jadventure).

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

Contributing
-------------
**Don't make a pull request to the main repo's master if you don't want your commit merged yet. Don't make pull requests if you're not willing to merge that code.**

Rather than opening a pull request, consider opening an issue to discuss the problem you're fixing. Keep the work-in-progress updates to your fork, maybe merging the updates to a separate branch in the main repo if there's enough there to warrant it.

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
-----------------

You can download the game with:

    git clone https://github.com/progether/JAdventure

###Run with Ant###

Install apache-ant

Run the game with:

    ant -emacs run

It will automatically compile, test, and run the game.


Playing the Game
--------------

To start a new game:

    start

To save a game:

    s

Get a list of commands with:

    h

To get a list of monsters around you:

    m

To view details about your player:

    v<s,e,b> - view status, equipped items, backpack

To quit the game:

    exit

To move:

    gn - go north
    gs - go south
    ge - go east
    gw - go west

To pick up an item:

    p<itemName>

To drop an item:

    d<itemName>

To equip/unequip item:

    e<itemName>
    ue<itemName>

To attack:
    
    a<monster>

To look around:
 
    la

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
 1. Comments 4 lines or greater in length should be multi-line commented
