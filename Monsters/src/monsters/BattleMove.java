/**
 * @author Nick Geary
 *
 */

package monsters;

import java.util.HashMap;

public class BattleMove {
	
	private String moveID;
	private String moveName;
	private int healPower;
	private int attackPower;
	private int hitThreshold;
	private int speedModifier;
	private static HashMap<String, BattleMove> movesMap = new HashMap<String, BattleMove>();
	private static String[] moveIDs;
	
	public BattleMove(String ID, String name, int healPower, int attackPower, int hitThreshold, int speedModifier) {
		this.moveID = ID;
		this.moveName = name;
		this.healPower = healPower;
		this.attackPower = attackPower;
		this.hitThreshold = hitThreshold;
		this.speedModifier = speedModifier;
		movesMap.put(moveID, this);
	}
	
	public String toString() {
		return "ID: " + moveID + " Name: " + moveName + " hPow: " + healPower + " aPow: " + attackPower + " hThresh: " + hitThreshold + " speedMod: " + speedModifier;
	}
	
	public static void fillMap() {
		boolean useFlatFile = true;
		if (useFlatFile) {
			fillMapFromFile();
		}
	}
	
	private static void fillMapFromFile() {
		fillMapFromString(MonstersFileUtilities.loadFile(GameSettings.getBattleMoveFilename()));		
	}

	public static void fillMapFromString(String fileString) {
		String[] lines = fileString.split("\n");
		for (String line:lines) {
			String[] battleMoveLine = line.split("\\|");
			try {
				String ID = battleMoveLine[0];
				String name = battleMoveLine[1];
				int healPower = Integer.parseInt(battleMoveLine[2]);
				int attackPower = Integer.parseInt(battleMoveLine[3]);
				int hitThreshold = Integer.parseInt(battleMoveLine[4]);
				int speedModifier = Integer.parseInt(battleMoveLine[5]);
				new BattleMove(ID,name,healPower,attackPower,hitThreshold,speedModifier); //instantiate BattleMove to add it to the map
			} catch (Exception e) {
				//Exception expected when reading comments line
			}
		}
	}
	
	public static String getMovesMenu() {
		if (movesMap.size()<1) {
			fillMap();
		}
		if (movesMap.size()>0) {
			StringBuilder sb = new StringBuilder();
			for (BattleMove bm : movesMap.values()) {
				sb.append("\t[" + bm.getMoveID() + "] " + bm.getMoveName() + "\n");
			}
			return sb.toString();
		} else {
			return null;
		}	
	}
	
	public static BattleMove getBattleMove(String ID) {
		if (movesMap.size()<1) {
			fillMap();
		}
		return movesMap.get(ID);
	}
	
	public static int getNumMoves() {
		if (movesMap.size()<1) {
			fillMap();
		}
		return movesMap.size();
	}
	
	public int getHitThreshold() {
		return hitThreshold;
	}
	
	public void setHitThreshold(int hitThreshold) {
		this.hitThreshold = hitThreshold;
	}
	
	public int getSpeedModifier() {
		return speedModifier;
	}
	
	public void setSpeedModifier(int speedModifier) {
		this.speedModifier = speedModifier;
	}
	
	public int getHealPower() {
		return healPower;
	}
	
	public void setHealPower(int healPower) {
		this.healPower = healPower;
	}
	
	public int getAttackPower() {
		return attackPower;
	}
	
	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}

	public String getMoveID() {
		return moveID;
	}

	public void setMoveID(String moveID) {
		this.moveID = moveID;
	}

	public String getMoveName() {
		return moveName;
	}

	public void setMoveName(String moveName) {
		this.moveName = moveName;
	}
	
	public static HashMap<String, BattleMove> getMovesMap() {
		if (movesMap.size()<1) {
			fillMap();
		}
		return movesMap;
	}

	public static void setMovesMap(HashMap<String, BattleMove> movesMap) {
		BattleMove.movesMap = movesMap;
	}

	public static String[] getMoveIDs() {
		if (moveIDs==null||moveIDs.length<1) {
			if (movesMap.size()<1) {
				fillMap();
			}
			moveIDs = movesMap.keySet().toArray(new String[movesMap.size()]);
		}
		return moveIDs;
	}

	public static void setMoveIDs(String[] moveIDs) {
		BattleMove.moveIDs = moveIDs;
	}

}
