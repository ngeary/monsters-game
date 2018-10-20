/**
 * @author Nick Geary
 *
 */

package monsters;

import java.util.HashMap;

public class MonsterType {

	private int mtID;
	private String mtName;
	private int mtBaseMaxHP;
	private int mtBaseAttack;
	private int mtBaseDefense;
	private int mtBaseSpeed;
	private int mtBaseMaxDiff;
	private static HashMap<Integer, MonsterType> monsterTypesMap = new HashMap<Integer, MonsterType>();
	
	public MonsterType(int ID, String name, int baseMaxHP, int baseAttack, int baseDefense, int baseSpeed, int baseMaxDiff) {
		this.mtID=ID;
		this.mtName=name;
		this.mtBaseMaxHP=baseMaxHP;
		this.mtBaseAttack=baseAttack;
		this.mtBaseDefense=baseDefense;
		this.mtBaseSpeed=baseSpeed;
		this.mtBaseMaxDiff=baseMaxDiff;
		monsterTypesMap.put(this.mtID, this);
	}
	
	public static void fillMap() {
		boolean useFlatFile = true;
		if (useFlatFile) {
			fillMapFromFile();
		}
	}
	
	private static void fillMapFromFile() {
		fillMapFromString(MonstersFileUtilities.loadFile(GameSettings.getMonsterTypeFilename()));		
	}

	public static void fillMapFromString(String fileString) {
		String[] lines = fileString.split("\n");
		for (String line:lines) {
			String[] monsterTypeLine = line.split("\\|");
			try {
				int ID = Integer.parseInt(monsterTypeLine[0]);
				String name = monsterTypeLine[1];
				int baseMaxHP = Integer.parseInt(monsterTypeLine[2]);
				int baseAttack = Integer.parseInt(monsterTypeLine[3]);
				int baseDefense = Integer.parseInt(monsterTypeLine[4]);
				int baseSpeed = Integer.parseInt(monsterTypeLine[5]);
				int baseMaxDiff = Integer.parseInt(monsterTypeLine[6]);
				new MonsterType(ID,name,baseMaxHP,baseAttack,baseDefense,baseSpeed,baseMaxDiff); //instantiate MonsterType to add it to the map
			} catch (Exception e) {
				//Exception expected when reading comments line
			}
		}
	}
	
	public static MonsterType getMonsterType(int ID) {
		if (monsterTypesMap.size()<1) {
			fillMap();
		}
		return monsterTypesMap.get(ID);
	}
	
	public static int getNumMonsterTypes() {
		if (monsterTypesMap.size()<1) {
			fillMap();
		}
		return monsterTypesMap.size();
	}
	
	public static int getValidMonsterTypeID(int ID) { //TODO need to handle when numeric IDs aren't perfectly sequential; for this case I could check if the key exists
		int n = getNumMonsterTypes();
		if (n>0) {
			int validID = ID % n; //modulo function to ensure that database has a monster type for the ID
			if (validID==0) {
				validID = n; //correction for when modulo result is 0
			}
			return validID;
		} else {
			MonstersTextUtilities.printlnWithDelay("There are no monster types in this dictionary so there are no valid monster type IDs!");
			return 0;
		}
	}
	
	public int getID() {
		return mtID;
	}
	
	public String getName() {
		return mtName;
	}
	
	public int getBaseMaxHP() {
		return mtBaseMaxHP;
	}
	
	public int getBaseAttack() {
		return mtBaseAttack;
	}
	
	public int getBaseDefense() {
		return mtBaseDefense;
	}
	
	public int getBaseSpeed() {
		return mtBaseSpeed;
	}
	
	public int getBaseMaxDiff() {
		return mtBaseMaxDiff;
	}	
	
}
