/**
 * @author Nick Geary
 *
 */

package monsters;

public class Monsters {
	private static String menuOption;
	private static MainPlayer mainPlayer;
	private static int screenSize = 60;
	
	public static void main(String[] args) {
		MonstersTextUtilities.printlnWithDelay("Welcome to Monsters!");
		boolean newGame = newOrLoad();
		if (newGame) {
			getInitialInputs();
		} else {
			getSavedGame();
		}

		menuOption = getMainMenuChoice();
		while (!menuOption.equals("Q")) {
			if (menuOption.equals("E")) {
				encounterMonster();
			} else if (menuOption.equals("V")) {
				viewMonsters();
			} else if (menuOption.equals("P")) {
				viewPlayerInfo();
			} else if (menuOption.equals("B")) {
				battleTrainer();
			} else if (menuOption.equals("M")) {
				addMoney();
			} else if (menuOption.equals("H")) {
				healAllMonsters();
			} else if (menuOption.equals("S")) {
				saveGame();
			} else if (menuOption.equals("O")) {
				viewOpponents();
			}
			menuOption = getMainMenuChoice();
		}
	}

	private static void healAllMonsters() {
		if (mainPlayer.getMoney()>=GameSettings.getCostToHealAllMonsters()) {
			mainPlayer.healAllMonsters();
			mainPlayer.setMoney(mainPlayer.getMoney()-GameSettings.getCostToHealAllMonsters());
			MonstersTextUtilities.printlnWithDelay("Monsters have been healed.");
		} else {
			MonstersTextUtilities.printlnWithDelay("You don't have enough money to heal your monsters!");
		}
	}

	private static void viewOpponents() {
		//TODO don't generate new monsters every time (should re-use Monster objects and serial numbers)
		for (int i=1; i<9; i++) {
			BasicOpponent bo = BasicOpponent.getBasicOpponent(i);
			System.out.println(bo.getName() + " ($" + bo.getMoney() + ")");
			bo.printMonsterList();
		}
	}

	private static void getSavedGame() {
		Game loadedGame = MonstersFileUtilities.loadGameFromFile();
		mainPlayer = loadedGame.getMainPlayer();
		Monster.setNumMonsters(loadedGame.getNumMonsters());
	}

	private static boolean newOrLoad() {
		if (MonstersFileUtilities.savesExist()) {
			boolean newGame = true;
			StringBuilder sb = new StringBuilder();
			sb.append("\nPick up where you left off?\n");
			sb.append("\t[L] Load game\n");
			sb.append("\t[N] New game\n");
			newGame = MonstersTextUtilities.getMenuChoice(sb.toString()).equals("N");
			return newGame;
		} else {
			return true;
		}
	}

	private static void addMoney() {
		int money = (int) MonstersTextUtilities.getNumericChoice("How much money do you want to add? (10 to 1000000): ", 10, 1000000);
		mainPlayer.setMoney(mainPlayer.getMoney()+money);		
	}

	private static void viewPlayerInfo() {
		mainPlayer.print();		
	}

	private static void getInitialInputs() {
		chooseTextSpeed();
		createMainPlayer();
		chooseMonsterTypeFile();
	}

	private static void chooseMonsterTypeFile() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nChose Monster Universe.\n");
		sb.append("\t[A] Animals\n");
		sb.append("\t[C] Crabshoot & Co.\n");
		String input = MonstersTextUtilities.getMenuChoice(sb.toString());
		if (input.equals("C")) {
			GameSettings.setMonsterTypeFilename(GameSettings.getTopDirectory() + "\\data\\monster_types\\britt_monsters.txt");
		} else {
			GameSettings.setMonsterTypeFilename(GameSettings.getTopDirectory() + "\\data\\monster_types\\animals.txt"); //this is the default if the input variable is somehow wonky
		}
		MonstersTextUtilities.printlnWithDelay("We'll use " + GameSettings.getMonsterTypeFilename() + " as the monster type file." );
	}

	private static void createMainPlayer() {
		mainPlayer = new MainPlayer(MonstersTextUtilities.getFreeTextChoice("Enter your name: "),0);
		int money = (int) MonstersTextUtilities.getNumericChoice("Starting money (10 to 1000000): ", 10, 1000000);
		mainPlayer.setMoney(money);
	}

	private static void chooseTextSpeed() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nChose text speed.\n");
		sb.append("\t[F] Fast \t(0.1 s delay)\n");
		sb.append("\t[M] Medium \t(0.5 s delay)\n");
		sb.append("\t[S] Slow \t(1.0 s delay)\n");
		String input = MonstersTextUtilities.getMenuChoice(sb.toString());
		if (input.equals("F")) {
			GameSettings.setTextDelay(100);
		} else if (input.equals("M")) {
			GameSettings.setTextDelay(500);
		} else if (input.equals("S")) {
			GameSettings.setTextDelay(1000);
		}		
	}

	private static void battleTrainer() {
		if (mainPlayer.getMonsterList().size()>0) {
			if (mainPlayer.getNextMonster()!=null) {
				Battle.doBattle(mainPlayer);
			} else {
				MonstersTextUtilities.printlnWithDelay("None of your monsters have any health!");
			}
		} else {
			MonstersTextUtilities.printlnWithDelay("You need to collect monsters before you can battle!");
		}
	}

	private static void viewMonsters() {
		mainPlayer.printMonsterList();
	}

	private static void encounterMonster() {
		Monster encMonster = Encounter.encounterMonster();
		if (encMonster==null) {
			MonstersTextUtilities.printlnWithDelay("You did not encounter a monster.");
		} else {
			Encounter.doEncounter(encMonster, mainPlayer);
		}
	}
	
	private static void saveGame() {
		boolean savedGame = MonstersFileUtilities.saveGameToFile(mainPlayer,Monster.getNumMonsters());
		if (savedGame) {
			MonstersTextUtilities.printlnWithDelay("Saved game successfully.");
		} else {
			MonstersTextUtilities.printlnWithDelay("Failed to save the game.");
		}
	}

	private static String getMainMenuChoice() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nWhat would you like to do?\n");
		sb.append("\t[E] Encounter a monster\n");
		sb.append("\t[V] View your monsters\n");
		sb.append("\t[P] View player info\n");
		sb.append("\t[B] Battle a monster trainer\n");
		sb.append("\t[M] Get more money\n");
		sb.append("\t[H] Heal all monsters (costs $" + GameSettings.getCostToHealAllMonsters() + ")\n");
		sb.append("\t[S] Save game\n");
		sb.append("\t[O] View Opponents\n");
		sb.append("\t[Q] Quit\n");
		return MonstersTextUtilities.getMenuChoice(sb.toString());
	}

	public static int getScreenSize() {
		return screenSize;
	}

	public static void setScreenSize(int screenSize) {
		Monsters.screenSize = screenSize;
	}

}
