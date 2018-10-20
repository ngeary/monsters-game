/**
 * @author Nick Geary
 *
 */

package monsters;

import java.util.Random;

public class Encounter {
	
	private static Random randNumGen = new Random();
	
	private Encounter() {
		//class should not be instantiated
	}
	
	public static Monster encounterMonster() {
		
		Monster encMonster=null;

		int mtNumber = randNumGen.nextInt(MonsterType.getNumMonsterTypes())+1; //give me a number between 1 and numMonsterTypes
		int level = randNumGen.nextInt(5)+1; //give me a number between 1 and 5
		int statBoost = randNumGen.nextInt(5)-2; //give me a number between -2 and 2
		
		MonsterType monsterType = MonsterType.getMonsterType(mtNumber);
		
		if (monsterType==null) {
			MonstersTextUtilities.printlnWithDelay("Oops! The monster type was null.");
		} else {
			encMonster = new Monster(monsterType,level,statBoost,"");
			MonstersTextUtilities.printlnWithDelay("You encountered a level-" + level 
					+ " " + encMonster.getMonsterNickname() + " with a stat boost of " + statBoost + "!");
			encMonster.printMonster();
		}

		return encMonster;
	}
	
	public static void doEncounter(Monster mon, MainPlayer mp) {

		String choice = null;
		boolean caughtMonster = false;
		boolean monsterFainted = false;
		while (!"L".equals(choice)&&!caughtMonster&&!monsterFainted) {
			choice = getMenuChoice();
			if (choice.equals("V")) {
				mon.printMonster();
			} else if (choice.equals("R")) {
				if (mp.getMoney()>=GameSettings.getRockCost()) {
					mp.setMoney(mp.getMoney()-GameSettings.getRockCost());
					monsterFainted = throwRock(mon);
				} else {
					MonstersTextUtilities.printlnWithDelay("You don't have enough money for a rock!");
				}
			} else if (choice.equals("M")) {
				if (mp.getMoney()>=GameSettings.getMonsterBallCost()) {
					mp.setMoney(mp.getMoney()-GameSettings.getMonsterBallCost());
					caughtMonster = throwMonsterBall(mon);
					if (caughtMonster) {
						mp.addMonster(mon);
					}
				} else {
					MonstersTextUtilities.printlnWithDelay("You don't have enough money for a MonsterBall!");
				}
			} else if (choice.equals("S")) {
				sing(mon);
			}
		}
	}
	
	private static void sing(Monster m) {
		MonstersTextUtilities.printlnWithDelay("Nothing happened because your singing is not very good!");
		MonstersTextUtilities.printlnWithDelay("And because I haven't built out any statuses yet.");
	}

	private static boolean throwMonsterBall(Monster m) {
		if (catchMonster(m)) {
			printSuccessfulCatch(m);
			return true;
		} else {
			printFailedCatch(m);
			return false;
		}	
	}

	private static boolean throwRock(Monster m) {
		boolean monsterFainted = false;
		int randNum = randNumGen.nextInt(10);
		if (randNum>1) {
			MonstersTextUtilities.printlnWithDelay("You hit the " + m.getMonsterNickname() + "!");
			if (!m.changeMonsterHP(-1)) { //returns true if monsterHP>0
				MonstersTextUtilities.printlnWithDelay("The " + m.getMonsterNickname() + " fainted!");
				monsterFainted = true;
			}
		} else {
			MonstersTextUtilities.printlnWithDelay("You missed the " + m.getMonsterNickname() + "!");
		}
		return monsterFainted;
	}
	
	private static boolean catchMonster(Monster m) {
		boolean caughtMonster = false;
		int diff = m.calcMonsterDifficulty();
		if (randNumGen.nextInt(100)>diff) {
			String[] str1 = {"Shake ",". ",". ","."};
			MonstersTextUtilities.printStringsWithDelay(str1);
			if (randNumGen.nextInt(100)>diff) {
				MonstersTextUtilities.printStringsWithDelay(str1);
				if (randNumGen.nextInt(100)>diff) {
					MonstersTextUtilities.printStringsWithDelay(str1);
					if (randNumGen.nextInt(100)>diff) {
						caughtMonster=true;
					}
				}
			}
		}
		return caughtMonster;
	}
	
	private static void printSuccessfulCatch (Monster m) {
		String[] strArray = {"Congratulations!"," The "+m.getMonsterNickname()+" was captured!"};
		MonstersTextUtilities.printStringsWithDelay(strArray);
		StringBuilder sb = new StringBuilder();
		sb.append("\nGive the "+m.getMonsterNickname()+" a nickname?\n");
		sb.append("\t[Y] Yes\n");
		sb.append("\t[N] No\n");
		if (MonstersTextUtilities.getMenuChoice(sb.toString()).equals("Y")) {
			m.setMonsterNickname(MonstersTextUtilities.getFreeTextChoice("Enter the nickname: "));
		}
	}
	
	private static void printFailedCatch (Monster m) {
		String[] strArray = {"Snap!"," The "+m.getMonsterNickname()+" escaped!"};
		MonstersTextUtilities.printStringsWithDelay(strArray);
	}

	private static String getMenuChoice() {
		StringBuilder sb = new StringBuilder();
		sb.append("\nWhat would you like to do?\n");
		sb.append("\t[V] View monster stats\n");
		sb.append("\t[R] Throw rock (costs $" + GameSettings.getRockCost() + ")\n");
		sb.append("\t[M] Throw MonsterBall (costs $" + GameSettings.getMonsterBallCost() + ")\n");
		sb.append("\t[S] Sing\n");
		sb.append("\t[L] Leave\n");
		return MonstersTextUtilities.getMenuChoice(sb.toString());
	}

}
