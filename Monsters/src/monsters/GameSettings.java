/**
 * @author Nick Geary
 *
 */

package monsters;

public class GameSettings {
	
	private static long textDelay;
	private static int moneyDeductionForLosing = 25;
	private static int costToHealAllMonsters = 100;
	private static int rockCost=1;
	private static int monsterBallCost=10;
	private static String monsterTypeFilename;
	private static String topDirectory;
	private static String opponentFilename = getTopDirectory() + "\\data\\opponents\\opponents.txt";
	//TODO add battleMoveFilename in all the necessary places
	private static String battleMoveFilename = getTopDirectory() + "\\data\\battle_moves\\generic_battle_moves.txt";
	
	private GameSettings() {
		
	}
	
	public static void setAllSettings(String rawString) {
		if (isValidRawString(rawString)) {
			String[] gameSettingsInfo = rawString.split("\\|");
			setTextDelay(Long.parseLong(gameSettingsInfo[0]));
			setMoneyDeductionForLosing(Integer.parseInt(gameSettingsInfo[1]));
			setCostToHealAllMonsters(Integer.parseInt(gameSettingsInfo[2]));
			setRockCost(Integer.parseInt(gameSettingsInfo[3]));
			setMonsterBallCost(Integer.parseInt(gameSettingsInfo[4]));
			setMonsterTypeFilename(gameSettingsInfo[5]);
			setOpponentFilename(gameSettingsInfo[6]);
		} else {
			MonstersTextUtilities.printlnWithDelay("Could not set game settings. Invalid raw string.");
		}
	}
	
	public static String toRawString() {
		String rawString = textDelay + "|" + moneyDeductionForLosing + "|" + costToHealAllMonsters
							+ "|" + rockCost + "|" + monsterBallCost + "|" + monsterTypeFilename
							+ "|" + opponentFilename;
		return rawString;
	}
	
	public static boolean isValidRawString(String rawString) { //TODO do better
		boolean isValid = false;
		String[] gameSettingsInfo = rawString.split("\\|");  //needed two backslashes to escape the pipe special character
		
		try {
			Long.parseLong(gameSettingsInfo[0]);
			Integer.parseInt(gameSettingsInfo[1]);
			Integer.parseInt(gameSettingsInfo[2]);
			Integer.parseInt(gameSettingsInfo[3]);
			Integer.parseInt(gameSettingsInfo[4]);
			isValid = (gameSettingsInfo[5] instanceof String) && (gameSettingsInfo[6] instanceof String);
		} catch (Exception e) {
			isValid = false;
		}
			
		return isValid;
	}
	
	public static boolean allSettingsDefined() {
		if (textDelay<1||moneyDeductionForLosing<1||costToHealAllMonsters<1||rockCost<1||monsterBallCost<1||monsterTypeFilename==null||opponentFilename==null) {
			return false;
		} else {
			return true;
		}
	}

	public static long getTextDelay() {
		return textDelay;
	}

	public static void setTextDelay(long td) {
		textDelay = td;
	}

	public static String getMonsterTypeFilename() {
		return monsterTypeFilename;
	}

	public static void setMonsterTypeFilename(String mtfn) {
		monsterTypeFilename = mtfn;
	}
	
	public static int getMoneyDeductionForLosing() {
		return moneyDeductionForLosing;
	}

	public static void setMoneyDeductionForLosing(int m) {
		moneyDeductionForLosing = m;
	}

	public static int getCostToHealAllMonsters() {
		return costToHealAllMonsters;
	}

	public static void setCostToHealAllMonsters(int c) {
		costToHealAllMonsters = c;
	}

	public static int getRockCost() {
		return rockCost;
	}
	
	public static void setRockCost(int rc) {
		rockCost = rc;
	}

	public static int getMonsterBallCost() {
		return monsterBallCost;
	}
	
	public static void setMonsterBallCost(int mbc) {
		monsterBallCost = mbc;
	}

	public static String getOpponentFilename() {
		return opponentFilename;
	}

	public static void setOpponentFilename(String ofn) {
		opponentFilename = ofn;
	}

	public static String getBattleMoveFilename() {
		return battleMoveFilename;
	}

	public static void setBattleMoveFilename(String battleMoveFilename) {
		GameSettings.battleMoveFilename = battleMoveFilename;
	}

	public static String getTopDirectory() {
		if (topDirectory == null) {
			topDirectory = System.getProperty("user.dir");
			String keyString = "Monsters";
			topDirectory = topDirectory.substring(0, topDirectory.indexOf(keyString) + keyString.length());
			
		}
		return topDirectory;
	}

	public static void setTopDirectory(String topDirectory) {
		GameSettings.topDirectory = topDirectory;
	}

}
