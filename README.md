# Space Invaders

A Space Invaders game in Java Swing.
Move your cannon, shoot the alien formation before it reaches you, and survive wave after wave.

## Requirements

- Java JDK 17 or newer (check with 'java -version')
- Works on Windows, macOS and Linux. No extra libraries or configurations are needed.

## How to run

### Option A: the jar file (fastest)
From the unzipped folder:

    java -jar dist/SpaceInvaders.jar

### Option B: NetBeans
1. 'File > Open Project' and choose the unzipped 'SpaceInvaders' folder.
2. Press **F6** (Run Project)

### Option C: command line without NetBeans
macOS / Linux:

    mkdir -p out
    javac -d out $(find src -name "*.java")
    java -cp out spaceinvaders.Main

Windows (PowerShell):

    mkdir out
    javac -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
    java -cp out spaceinvaders.Main

The game opens on the start menu. Press ENTER to play.

## Controls 

| Key | Action |
|---|---|
| LEFT / RIGHT | In the menu: change theme. In the game: move the cannon |
| ENTER | Start the game (menu) or play again (game over) |
| SPACE | Shoot (hold to keep shooting) |
| P or ESC| Pause / resume |
| M | Back to the menu (while paused or after game over) |

## Rules 

- Aliens are worth 30, 20 or 10 points, depending on the row. The top row is worth the most.
- You start with 3 lives. A hit by an alien bullet costs one life, and the cannon blinks and is protected for 1.5 seconds.
- The game is over when you have no lives left, or when the aliens reach the cannon.
- Destroy all aliens to start a new wave. Every wave is faster. The aliens also speed up as fewer are left.


## Themes

Choose in the start menu with LEFT / RIGHT: SPACE, OCEAN, NEON, HALLOWEEN AND ARCADE. Each theme has its own background and pixel-art characters. The themes only change the look, not the rules.

## Project structure  

    src/spaceinvaders/            Main (window), GamePanel (game loop, input, drawing), Constants, Theme
    src/spaceinvaders/model/      Entity, Player, Alien, AlienFormation, Bullet, GameModel
    dist/SpaceInvaders.jar        Ready-to-run build

How it works in short: a `javax.swing.Timer` calls `update()` and `repaint()` about 60 times per second. `update()` only changes the game data and `paintComponent()` only draws it. All numbers that influence the feel (speeds, cooldowns, number of bullets, protection time) are in `Constants.java`.

## Team and tools

- Built in NetBeans with Git and Github