/**
 * @author Nick Geary
 *
 */

package monsters;
import java.util.*;

public abstract class Trainer {

	protected String name;
	protected ArrayList<Monster> monsterList;
	protected int money;
		
	public boolean addMonster(Monster m) {
		return monsterList.add(m);
	}
	
	public void printMonsterList() {
		if (monsterList.size()>0) {
			for (Monster curMonster : monsterList) {
				curMonster.printMonster();
			}
		} else {
			MonstersTextUtilities.printlnWithDelay("No monsters!");
		}
	}
	
	public void healAllMonsters() {
		for (Monster m : monsterList) {
			m.heal();
		}
	}
	
	public void printRawMonsterList() {
		if (monsterList.size()>0) {
			for (Monster curMonster : monsterList) {
				MonstersTextUtilities.printlnWithDelay(curMonster.toRawString());
			}
		} else {
			MonstersTextUtilities.printlnWithDelay("No monsters!");
		}
	}
	
	public String monsterListToRawString() {
		StringBuilder sb = new StringBuilder();
		for (Monster curMonster : monsterList) {
			sb.append(curMonster.toRawString() + "\n");
		}
		if (sb.length()>0) {
			sb.setLength(sb.length()-1); //trim last new line character
		}
		return sb.toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Monster> getMonsterList() {
		return monsterList;
	}
	
	public void setMonsterList(ArrayList<Monster> ml) {
		this.monsterList = ml;
	}
	
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
	
	public Monster getMonster(int position) {
		if (monsterList.size()>=position) {
			return monsterList.get(position);
		} else {
			return null;
		}
	}
	
	public Monster getNextMonster() {
		for (int i=0; i<monsterList.size(); i++) {
			if (getMonster(i).getMonsterHP()>0) {
				return getMonster(i);
			}
		}
		return null;
	}
	
	public void print() {
		MonstersTextUtilities.printlnWithDelay("\nName:\t\t\t" + this.name
				+ "\nMoney:\t\t\t$" + this.money
				+ "\nNumber of monsters:\t" + this.monsterList.size());
	}
		
}
