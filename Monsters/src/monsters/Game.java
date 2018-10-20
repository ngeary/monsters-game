/**
 * @author Nick Geary
 *
 */

package monsters;

public class Game {
	
	private MainPlayer mainPlayer;
	private int numMonsters;
	
	public Game(MainPlayer mp, int nm) {
		this.mainPlayer = mp;
		this.numMonsters = nm;
	}

	public MainPlayer getMainPlayer() {
		return mainPlayer;
	}

	public void setMainPlayer(MainPlayer mainPlayer) {
		this.mainPlayer = mainPlayer;
	}
	
	public String toRawString() {
		String rawString = numMonsters + "\n" + GameSettings.toRawString() + "\n" + mainPlayer.toRawString();
		return rawString;
	}

	public int getNumMonsters() {
		return numMonsters;
	}

}
