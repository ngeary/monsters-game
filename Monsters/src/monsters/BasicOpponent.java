/**
 * @author Nick Geary
 *
 */

package monsters;

import java.util.ArrayList;
import java.util.HashMap;

public class BasicOpponent extends Trainer {
	
	private int ID;
	private static HashMap<Integer, BasicOpponent> basicOpponentsMap = new HashMap<Integer, BasicOpponent>();
	
	public BasicOpponent(int ID, String name, int money, ArrayList<Monster> monsterList) {
		this.ID = ID;
		this.name = name;
		this.money = money;
		this.monsterList = monsterList;
		basicOpponentsMap.put(this.ID, this);
	}
	
	public static void fillMap() {
		boolean useFlatFile = true;
		if (useFlatFile) {
			fillMapFromFile();
		}
	}
	
	private static void fillMapFromFile() {
		fillMapFromString(MonstersFileUtilities.loadFile(GameSettings.getOpponentFilename()));		
	}

	public static void fillMapFromString(String fileString) {
		String[] lines = fileString.split("\n");
		for (String line:lines) {
			String[] opponentInfo = line.split("\\|");
			try {

				int ID = Integer.parseInt(opponentInfo[0]);
				String name = opponentInfo[1];
				int money = Integer.parseInt(opponentInfo[2]);
				ArrayList<Monster> monsterList = new ArrayList<Monster>();
				
				for (int i=3; i<(opponentInfo.length-3); i+=4) {
					int mtID = MonsterType.getValidMonsterTypeID(Integer.parseInt(opponentInfo[i]));
					MonsterType mt = MonsterType.getMonsterType(mtID);
					int level = Integer.parseInt(opponentInfo[i+1]);
					int statBoost = Integer.parseInt(opponentInfo[i+2]);
					String mName = opponentInfo[i+3];
					monsterList.add(new Monster(mt,level,statBoost,mName));				
				}
				new BasicOpponent(ID,name,money,monsterList); //instantiate BasicOpponent object to add it to the map
			} catch (Exception e) {
				//Exception expected when reading comments line
			}
		}
	}
	
	public static BasicOpponent getBasicOpponent(int ID) {
		if (basicOpponentsMap.size()<1) {
			fillMap();
		}
		return basicOpponentsMap.get(ID);
	}
	
	public String getName() {
		return name;
	}

}
