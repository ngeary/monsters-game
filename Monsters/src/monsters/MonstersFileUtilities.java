/**
 * @author Nick Geary
 *
 */

package monsters;
import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Scanner;

public class MonstersFileUtilities {
	
	private static PrintWriter pw;
	private static Scanner fileScanner;
	private static LineNumberReader lnr;

	private MonstersFileUtilities() {
		//private constructor-- class should not be instantiated
	}
	
	public static boolean saveGameToFile(Game g) {
		return saveGameToFile(g.getMainPlayer(), g.getNumMonsters());
	}
	
	public static boolean saveGameToFile(MainPlayer mp, int numMonsters) {
		boolean savedFile = false;
		String filename = createFilename(mp.getName());
		boolean createdFile = createFile(filename);
		if (createdFile) {
			try {
				pw = new PrintWriter(filename);
				pw.print(numMonsters + "\n" + GameSettings.toRawString() + "\n" + mp.toRawString() + "\n" + mp.monsterListToRawString());
				pw.close();
				savedFile=true;
			} catch (Exception e) {
				MonstersTextUtilities.printlnWithDelay("There was an exception while attempting to save the file.\n\t" + e.toString());
			}
		}
		return savedFile;
	}
	
	public static Game loadGameFromFile() { //TODO this belongs in the Game class I would think
		Game loadedGame = null;
		
		try {
			File loadFile = chooseFile();
			fileScanner = new Scanner(loadFile);
			
			int numMonsters = 1000;  //if we can't find numMonsters (older versions did not save this) then start counting at 1000
			
			if (fileScanner.hasNextInt()) {
				numMonsters = fileScanner.nextInt();
				fileScanner.nextLine();
			}
				
			MainPlayer mainPlayer = null;

			String fileString;
			while (fileScanner.hasNextLine()) {
				fileString = fileScanner.nextLine();
				if (GameSettings.isValidRawString(fileString)) {
					GameSettings.setAllSettings(fileString);
				} else if (mainPlayer==null&&MainPlayer.isValidRawString(fileString)) {
					mainPlayer = new MainPlayer(fileString);
				} else if (Monster.isValidRawString(fileString)) {
					mainPlayer.addMonster(new Monster(fileString));
				}
			}
			
			if (numMonsters>-1&&GameSettings.allSettingsDefined()&&mainPlayer!=null) {
				loadedGame = new Game(mainPlayer, numMonsters);
			}		
		
		} catch (Exception e) {
			MonstersTextUtilities.printlnWithDelay("An exception occured while loading the game.\n" + e.toString());
		}	
		
		return loadedGame;
	}
	
	public static String loadFile(String filename) {
		try {
			File f = new File(filename);
			fileScanner = new Scanner(f);
			StringBuilder fileString = new StringBuilder();
			while (fileScanner.hasNextLine()) {
				fileString.append(fileScanner.nextLine()+"\n");
			}
			return fileString.toString();
		} catch (FileNotFoundException fnfe) {
			MonstersTextUtilities.printlnWithDelay(fnfe.getMessage());
			return null;
		}		
	}
	
	public static boolean savesExist() {
		File[] saveFiles = findFiles(GameSettings.getTopDirectory() + "\\saves\\",".monsters");
		
		if (saveFiles == null || saveFiles.length < 1) {
			return false;
		}
		
		return true;
	}

	public static File chooseFile() {

		File[] monstersFiles = findFiles(GameSettings.getTopDirectory() + "\\saves\\",".monsters");
		File chosenFile = null;
		
		if (monstersFiles.length>0) {
			StringBuilder sb = new StringBuilder();
			sb.append("\nWhich game would you like to load?\n");
			int numFiles = 0;
			for (File curFile : monstersFiles) {
				numFiles++;
				sb.append("\t[" + numFiles + "] " + curFile.getName() + "\n");
			}
			int fileChoice = (int) MonstersTextUtilities.getNumericChoice(sb.toString(),1,numFiles);
			chosenFile = monstersFiles[fileChoice-1];
		} else {
			MonstersTextUtilities.printlnWithDelay("There are no saved games!");
		}
		return chosenFile;
	}
	
	private static File[] findFiles (String dirName, String extension) {
		File directory = new File(dirName);
		return directory.listFiles(new FilenameFilter() {
			public boolean accept(File directory, String filename) {
				return filename.endsWith(extension);
			}
		});
	}
	
	private static String createFilename(String playerName) {
		LocalDateTime ldt = LocalDateTime.now();
		NumberFormat nf = new DecimalFormat("00");
		String dateTimeString = ldt.getYear() + "_" 
				+ nf.format(ldt.getMonthValue()) + "_" 
				+ nf.format(ldt.getDayOfMonth()) + "_" 
				+ nf.format(ldt.getHour()) 
				+ nf.format(ldt.getMinute()) 
				+ nf.format(ldt.getSecond());
		return GameSettings.getTopDirectory() + "\\saves\\" + playerName + "_" + dateTimeString + ".monsters";
	}
	
	private static boolean createFile(String filename) {
		boolean createdFile = false;
		try {
			File file = new File(filename);
			createdFile = file.createNewFile();
		} catch (Exception e) {
			MonstersTextUtilities.printlnWithDelay("There was an exception while attempting to create the file.\n\t" + e.toString());
		}		
		return createdFile;
	}
	
	public static int getLinesInFile(String filename) {
		int numLines = -1;		
		try {
			File file = new File(filename);
			lnr = new LineNumberReader(new FileReader(file));
			lnr.skip(Long.MAX_VALUE);
			numLines = lnr.getLineNumber() + 1; //lnr starts at line 0
			lnr.close();
		} catch (IOException ioe) {
			//I should probably handle this differently
		}		
		return numLines;
	}
	
}
