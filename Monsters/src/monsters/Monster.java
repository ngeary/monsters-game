/**
 * @author Nick Geary
 *
 */

package monsters;

public class Monster {

	public static int numMonsters=0;
	private int monsterSerialNumber;
	private int monsterID;
	private String monsterNickname;
	private int monsterLevel;
	private int monsterMaxHP;
	private int monsterHP;
	private int monsterAttack;
	private int monsterDefense;
	private int monsterSpeed;
	private int monsterMaxDiff;
	private BattleMove battleMove; //temporary move for a round of battle

	public Monster(MonsterType mt, int level, int statBoost, String name) { //TODO make this the only constructor and deprecate others
		numMonsters++;
		this.monsterSerialNumber=numMonsters;
		this.monsterID=mt.getID();
		if (name.equals("")|name.equals("\"\"")) {
			this.monsterNickname=mt.getName();
		} else {
			this.monsterNickname=name;
		}		
		this.monsterLevel=1;
		this.monsterMaxHP=Math.max(mt.getBaseMaxHP()+statBoost,1);
		this.monsterHP=Math.max(monsterMaxHP,1);
		this.monsterAttack=Math.max(mt.getBaseAttack()+statBoost,1);
		this.monsterDefense=Math.max(mt.getBaseDefense()+statBoost,1);
		this.monsterSpeed=Math.max(mt.getBaseSpeed()+statBoost,1);
		this.monsterMaxDiff=Math.max(mt.getBaseMaxDiff()+statBoost,1);
		for (;level>1;level--) {
			levelUpMonster();
		}
	}
	
	public Monster(String rawString) { //be sure to call isValidRawString before calling this constructor
		String[] monsterInfo = rawString.split("\\|");
		this.monsterSerialNumber = Integer.parseInt(monsterInfo[0]);
		this.monsterID = Integer.parseInt(monsterInfo[1]);
		this.monsterNickname = monsterInfo[2];
		this.monsterLevel = Integer.parseInt(monsterInfo[3]);
		this.monsterMaxHP = Integer.parseInt(monsterInfo[4]);
		this.monsterHP = Integer.parseInt(monsterInfo[5]);
		this.monsterAttack = Integer.parseInt(monsterInfo[6]);
		this.monsterDefense = Integer.parseInt(monsterInfo[7]);
		this.monsterSpeed = Integer.parseInt(monsterInfo[8]);
		this.monsterMaxDiff = Integer.parseInt(monsterInfo[9]);
	}
	
	public void printMonster() {
		MonstersTextUtilities.printlnWithDelay("\n\tName:\t\t" + monsterNickname
				+ "\n\tSerial Number:\t" + monsterSerialNumber
				+ "\n\tID:\t\t" + monsterID
				+ "\n\tMonster Type:\t" + MonsterType.getMonsterType(monsterID).getName()
				+ "\n\tLevel:\t\t" + monsterLevel
				+ "\n\tMax HP:\t\t" + monsterMaxHP
				+ "\n\tCurrent HP:\t" + monsterHP
				+ "\n\tAttack:\t\t" + monsterAttack
				+ "\n\tDefense:\t" + monsterDefense
				+ "\n\tSpeed:\t\t" + monsterSpeed
				+ "\n\tMax Diff:\t" + monsterMaxDiff
				+ "\n\tDifficulty:\t" + calcMonsterDifficulty()
				);				
	}
	
	public void printMonsterB() { //print version for battle mode
		MonstersTextUtilities.printlnWithDelay("\n\tName:\t\t" + monsterNickname
				+ "\n\tCurrent HP:\t" + monsterHP
				+ "\n\tAttack:\t\t" + monsterAttack
				+ "\n\tDefense:\t" + monsterDefense
				+ "\n\tSpeed:\t\t" + monsterSpeed
				);				
	}
	
	public void printMonsterB(boolean isOpponent) { //print version for battle mode
		if (isOpponent) {
			printMonsterBRight();
		} else {
			printMonsterBLeft();
		}
	}
	
	private void printMonsterBRight() {
		int nameWidth = Math.max(10, monsterNickname.length());
		int buffer = Monsters.getScreenSize() - nameWidth;
		String formatString = "%" + buffer + "s%" + nameWidth + "s";
		String monsterString = String.format(formatString, "Name: ",monsterNickname)
				+ "\n" + String.format(formatString, "Current HP: ",monsterHP)
				+ "\n" + String.format(formatString, "Attack: ",monsterAttack)
				+ "\n" + String.format(formatString, "Defense: ",monsterDefense)
				+ "\n" + String.format(formatString, "Speed: ",monsterSpeed);
		
		
		StringBuilder sb = new StringBuilder();
		sb.append(String.format(formatString, "Name: ",monsterNickname));
		sb.append(String.format(formatString, "HP: ",monsterHP));
		sb.append(String.format(formatString, "Attack: ",monsterAttack));
		sb.append(String.format(formatString, "Defense: ",monsterDefense));
		sb.append(String.format(formatString, "Speed: ",monsterSpeed));
		MonstersTextUtilities.printlnWithDelay(monsterString);
	}
	
	private void printMonsterBLeft() {
		MonstersTextUtilities.printlnWithDelay("\nName:\t\t" + monsterNickname
				+ "\nCurrent HP:\t" + monsterHP
				+ "\nAttack:\t\t" + monsterAttack
				+ "\nDefense:\t" + monsterDefense
				+ "\nSpeed:\t\t" + monsterSpeed
				);	
	}
	
	public boolean changeMonsterHP(int change) {
		monsterHP+=change;
		monsterHP=Math.max(monsterHP,0);
		return (monsterHP>0);
	}
	
	public int calcMonsterDifficulty() {
		double fraction = (double)monsterHP/(double)monsterMaxHP; //fraction of max HP remaining
		int newDiff = (int) Math.round(monsterMaxDiff*fraction);
		return Math.max(newDiff, 1);
	}
	
	public void levelUpMonster() {
		monsterLevel+=1;
		monsterMaxHP+=2;
		monsterHP+=2;
		monsterAttack+=2;
		monsterDefense+=2;
		monsterSpeed+=2;
		monsterMaxDiff+=2;
	}
	
	public String toRawString() {
		String rawString = monsterSerialNumber + "|"
				+ monsterID + "|"
				+ monsterNickname + "|"
				+ monsterLevel + "|"
				+ monsterMaxHP + "|"
				+ monsterHP + "|"
				+ monsterAttack + "|"
				+ monsterDefense + "|"
				+ monsterSpeed + "|"
				+ monsterMaxDiff;
		return rawString;
	}
	
	public static boolean isValidRawString(String rawString) {
		boolean isValid = false;
		String[] monsterInfo = rawString.split("\\|");  //needed two backslashes to escape the pipe special character
		
		try {
			int[] intSpots = {0,1,3,4,5,6,7,8,9}; //the spots in the parsed string array that should have integer values
			for (int i:intSpots) {
				Integer.parseInt(monsterInfo[i]); //will throw exception if any are not integers
			}			
			isValid = monsterInfo[2] instanceof String; //if we make it to this point, we have a valid string as long as the third chunk can be a string
		} catch (Exception e) {
			isValid = false;
		}
			
		return isValid;		
	}

	public int getMonsterID() {
		return monsterID;
	}

	public int getMonsterSerialNumber() {
		return monsterSerialNumber;
	}

	public int getMonsterAttack() {
		return monsterAttack;
	}

	public void setMonsterAttack(int monsterAttack) {
		this.monsterAttack = monsterAttack;
	}

	public int getMonsterDefense() {
		return monsterDefense;
	}

	public void setMonsterDefense(int monsterDefense) {
		this.monsterDefense = monsterDefense;
	}

	public int getMonsterLevel() {
		return monsterLevel;
	}

	/*public void setMonsterLevel(int monsterLevel) { //should only be used when loading game?
		this.monsterLevel = monsterLevel;
	}*/

	public String getMonsterNickname() {
		return monsterNickname;
	}

	public void setMonsterNickname(String monsterNickname) {
		this.monsterNickname = monsterNickname;
	}

	public int getMonsterMaxHP() {
		return monsterMaxHP;
	}

	public void setMonsterMaxHP(int monsterMaxHP) {
		this.monsterMaxHP = monsterMaxHP;
	}

	public int getMonsterHP() {
		return monsterHP;
	}
	
	private void setMonsterHP(int hp) {
		this.monsterHP = hp;
	}

	public int getMonsterSpeed() {
		return monsterSpeed;
	}

	public void setMonsterSpeed(int monsterSpeed) {
		this.monsterSpeed = monsterSpeed;
	}

	/*public void setMonsterHP(int monsterHP) {
		this.monsterHP = monsterHP;
	}*/
	
	public static void setNumMonsters(int nm) {
		numMonsters = nm;
	}
	
	public static int getNumMonsters() {
		return numMonsters;
	}

	public void heal() {
		this.setMonsterHP(monsterMaxHP);		
	}

	public int getBattleSpeed() {
		if (battleMove==null) {
			return monsterSpeed;
		} else {
			return monsterSpeed + battleMove.getSpeedModifier();
		}
	}

	public BattleMove getBattleMove() {
		return battleMove;
	}

	public void setBattleMove(BattleMove battleMove) {
		this.battleMove = battleMove;
	}

}
