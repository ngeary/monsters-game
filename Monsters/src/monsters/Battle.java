/**
 * @author Nick Geary
 *
 */

package monsters;

import java.util.Random;

public class Battle {

	private static Random randNumGen = new Random();
	private static int numOpponents;
	
	private Battle() {
		//private constructor-- class should not be instantiated
	}
	
	public static void doBattle(MainPlayer mp) {
		Trainer opponent = getOpponent();
		printOpponent(opponent);
		Monster mpMon = mp.getNextMonster();
		Monster opMon = opponent.getNextMonster();
		while (mpMon!=null&&opMon!=null) {
			doRoundOfBattle(mpMon, opMon);
			if (mpMon.getMonsterHP()<1) {
				mpMon = mp.getNextMonster();
			}
			if (opMon.getMonsterHP()<1) {
				opMon = opponent.getNextMonster();
			}
		}
		if (mpMon==null) {
			loseBattle(mp);
		} else {
			winBattle(mp,opponent);
		}
	}
	
	private static void doRoundOfBattle(Monster mpMon, Monster opMon) {
		while (mpMon.getMonsterHP()>0&&opMon.getMonsterHP()>0) {
			opMon.printMonsterB(true); //print monster on opponent side of screen
			mpMon.printMonsterB(false); //print monster on main player side of screen
			String choice = getBattleChoice(mpMon.getMonsterNickname());
			if (choice.equals("E")) {
				MonstersTextUtilities.printlnWithDelay("Can't escape!");
			} else {
				mpMon.setBattleMove(BattleMove.getBattleMove(choice));
				opMon.setBattleMove(getOpponentMove());
				doAttacks(mpMon,opMon);
				clearBattleMoves(mpMon,opMon);
			}
		}		
	}
	
	private static void clearBattleMoves(Monster mpMon, Monster opMon) {
		mpMon.setBattleMove(null);
		opMon.setBattleMove(null);		
	}

	private static void doAttacks(Monster mpMon, Monster opMon) {
		Monster firstMon;
		Monster secondMon;
		
		if (mpMon.getBattleSpeed()>=opMon.getBattleSpeed()) {
			firstMon = mpMon;
			secondMon = opMon;
		} else {
			firstMon = opMon;
			secondMon = mpMon;
		}
		
		attackMonster(firstMon,secondMon);
		if (mpMon.getMonsterHP()>0&&opMon.getMonsterHP()>0) {
			attackMonster(secondMon,firstMon);
		}			
	}
	
	private static boolean attackMonster(Monster attacker, Monster defender) {
		//MonstersTextUtilities.printlnWithDelay(attacker.getBattleMove().toString());
		int rand = randNumGen.nextInt(100); //give me a number between 0 and 99 inclusive
		if (rand>attacker.getBattleMove().getHitThreshold()) { //is random number high enough for a hit?	
			int hpChange = calcDamage(attacker, defender);
			boolean alive = defender.changeMonsterHP(-1*Math.max(hpChange, 1));
			MonstersTextUtilities.printlnWithDelay(attacker.getMonsterNickname()+"'s " + attacker.getBattleMove().getMoveName() + " did " + hpChange + " damage!");
			if (!alive) {
				MonstersTextUtilities.printlnWithDelay(defender.getMonsterNickname()+" fainted!");
			}
			return true;
		} else {
			MonstersTextUtilities.printlnWithDelay(attacker.getMonsterNickname()+"'s " + attacker.getBattleMove().getMoveName() + " missed!");
			return false;
		}
	}

	private static int calcDamage(Monster attacker, Monster defender) {
		//see http://bulbapedia.bulbagarden.net/wiki/Damage#Damage_formula
		double STAB = 1;
		double type = 1;
		double critical = 1;
		if (randNumGen.nextDouble()*256<attacker.getBattleSpeed()) { //chance of a critical hit is speed/256
			critical = 2;
			MonstersTextUtilities.printWithDelay("Critical hit! ");
		}
		double rand = 0.85 + (randNumGen.nextDouble()*3/20); //give me a random double between 0.85 and 1.00
		double modifier = STAB * type * critical * rand;
		double piece1 = (2.0 * attacker.getMonsterLevel() + 10) / 250;
		double piece2 = ((double) attacker.getMonsterAttack())/defender.getMonsterDefense();
		double piece3 = attacker.getBattleMove().getAttackPower();
		double damage = (piece1 * piece2 * piece3 + 2) * modifier;
		//MonstersTextUtilities.printlnWithDelay("Piece 1: " + piece1 + " Piece 2: " + piece2 + " Piece 3: " + piece3 + " Damage: " + damage);
		return (int) Math.round(damage);
	}
	
	/*private static void healMonster(Monster mon) {
		
	}*/

	private static void loseBattle(MainPlayer mp) {
		int m = GameSettings.getMoneyDeductionForLosing();
		MonstersTextUtilities.printlnWithDelay("You lost! You gave up $" + m + " for losing!");
		mp.setMoney(mp.getMoney()-m);
		if (mp.getMoney()<0) {
			MonstersTextUtilities.printlnWithDelay("You are out of money! Game over!");
			System.exit(0);
		}
	}
	
	private static void winBattle(MainPlayer mp, Trainer op) {
		MonstersTextUtilities.printlnWithDelay("You won! You earned $" + op.getMoney() + " for winning!");
		mp.setMoney(mp.getMoney()+op.getMoney());
		op.healAllMonsters();
	}

	private static BattleMove getOpponentMove() {
		String[] battleMoves = BattleMove.getMoveIDs();
		int i = randNumGen.nextInt(battleMoves.length);
		return BattleMove.getBattleMove(battleMoves[i]);
	}

	private static String getBattleChoice(String monsterName) {
		StringBuilder sb = new StringBuilder();
		sb.append("\nWhat should " + monsterName + " do?\n");
		sb.append(BattleMove.getMovesMenu());
		sb.append("\t[E] Escape\n");
		return MonstersTextUtilities.getMenuChoice(sb.toString());
	}

	private static void printOpponent(Trainer opponent) {
		MonstersTextUtilities.printlnWithDelay("Your oppenent is " + opponent.getName() + "!");
		MonstersTextUtilities.printlnWithDelay("Here are " + opponent.getName() + "'s monsters:");
		opponent.printMonsterList();		
	}

	private static Trainer getOpponent() {
		int opponentID = randNumGen.nextInt(getNumOpponents()) + 1;
		Trainer opponent = BasicOpponent.getBasicOpponent(opponentID);
		return opponent;
	}

	public static int getNumOpponents() {
		if (numOpponents<1) {
			numOpponents = MonstersFileUtilities.getLinesInFile(GameSettings.getOpponentFilename())-1; //subtract one line of comments		
		}
		return numOpponents;
	}

	public static void setNumOpponents(int numOpponents) {
		Battle.numOpponents = numOpponents;
	}
	
}
