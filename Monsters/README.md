# Monsters

Monsters is a text-based game in which players encounter, catch, and battle monsters.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. The instructions and examples are based on a machine running Windows 10.

### Prerequisites

Java must be installed to run this program. This project was developed using Java 8. The Java SE Development Kit for version 8 can be downloaded [here](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).

### Installing

1. Download the **monsters-game** repository from GitHub.
2. Extract the files from the zipped folder and move them to your desired installation directory.

### Running

#### Option 1: Command Prompt

1. Open the Command Prompt and navigate to the **Monsters\bin** directory within your installation directory.
<pre>
<b>C:\Users\myuser\></b>cd C:\Users\myuser\myworkspace\Monsters\bin
</pre>
2. Run the **java -version** command to confirm that Java is installed and your user environment variables are set correctly.
<pre>
<b>C:\Users\myuser\myworkspace\Monsters\bin></b>java -version
java version "1.8.0_191"
Java(TM) SE Runtime Environment (build 1.8.0_191-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.191-b12, mixed mode)
</pre>
3. Launch the program by executing **java monsters.Monsters** at the command prompt.
<pre>
<b>C:\Users\myuser\myworkspace\Monsters\bin></b>java monsters.Monsters
Welcome to Monsters!
</pre>
4. Follow the menu-based prompts to play the game.

#### Option 2: Eclipse

1. Download and install the [Eclipse IDE for Java Developers](http://www.eclipse.org/downloads/packages/release/2018-09/r/eclipse-ide-java-and-dsl-developers).
2. Launch Eclipse. Go to File > Open Projects from File System. For the import source, use the Monsters directory within your installation directory.
3. Use the Package Explorer view to navigate to Monsters > src > monsters > Monsters.java. Right click on Monsters.java and select **Run as Java Application**.
4. Follow the menu-based prompts to play the game.

Note: Any other IDEs that are compatible with Java may also be used.

## Running Automated Tests

Automated tests have not yet been added for this project.

## Screenshots

![image][img1]

*The main menu*

![image][img2]

*Encountering a monster*

![image][img3]

*Catching a monster*

![image][img4]

*Battling an opponent*

![image][img5]

*Saving the game*

![image][img6]

*Loading the saved game*

[img1]: https://github.com/ngeary/monsters-game/tree/master/Monsters/img/screenshots/main_menu.png
[img2]: https://github.com/ngeary/monsters-game/tree/master/Monsters/img/screenshots/encounter.png
[img3]: https://github.com/ngeary/monsters-game/tree/master/Monsters/img/screenshots/catch.png
[img4]: https://github.com/ngeary/monsters-game/tree/master/Monsters/img/screenshots/battle_03.png
[img5]: https://github.com/ngeary/monsters-game/tree/master/Monsters/img/screenshots/save_game.png
[img6]: https://github.com/ngeary/monsters-game/tree/master/Monsters/img/screenshots/load_game.png

## Author

Nick Geary

## License

This project is licensed under the GNU General Public License v3.0. See the LICENSE file for details.