/**
 * @author Nick Geary
 *
 */

package monsters;
import java.util.ArrayList;

public class MainPlayer extends Trainer {
	
	private int score;

	public MainPlayer(String n, int m) {
		this.name = n;
		this.score = 0;
		this.money = m;
		this.monsterList = new ArrayList<Monster>();
	}
	
	public MainPlayer(String rawString) { //be sure to call isValidRawString before calling this constructor
		String[] mainPlayerInfo = rawString.split("\\|");
		this.name = mainPlayerInfo[0];
		this.score = Integer.parseInt(mainPlayerInfo[1]);
		this.money = Integer.parseInt(mainPlayerInfo[2]);
		this.monsterList = new ArrayList<Monster>();
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void print() {
		super.print();
		MonstersTextUtilities.printlnWithDelay("Score:\t\t\t" + this.score); //this does cause two print delays instead of one on the whole block of text
	}
	
	public String toRawString() {
		String rawString = name + "|" + score + "|" + money;
		return rawString;
	}
	
	public static boolean isValidRawString(String rawString) {
		boolean isValid = false;
		String[] mainPlayerInfo = rawString.split("\\|");  //needed two backslashes to escape the pipe special character
		
		try {
			int[] intSpots = {1,2}; //the spots in the parsed string array that should have integer values
			for (int i:intSpots) {
				Integer.parseInt(mainPlayerInfo[i]); //will throw exception if any are not integers
			}
			isValid = mainPlayerInfo[0] instanceof String; //if we make it to this point, we have a valid string as long as the third chunk can be a string
		} catch (Exception e) {
			isValid = false;
		}
			
		return isValid;		
	}
	
	public void setMoney(int money) {
		this.money = money;
		if (this.money<0) {
			MonstersTextUtilities.printlnWithDelay("You are out of money! Game over!");
			System.exit(0);
		}		
	}
	
}
