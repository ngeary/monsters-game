/**
 * @author Nick Geary
 *
 */

package monsters;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MonstersTextUtilities {
	
	private static Scanner scanner = new Scanner(System.in);
	
	private MonstersTextUtilities() {
		//private constructor-- class should not be instantiated
	}
	
	public static String getMenuChoice(String menuString) {
		ArrayList<String> validChoices = new ArrayList<String>();
		Pattern p = Pattern.compile("(?<=\\[)(.*?)(?=\\])");
		Matcher m = p.matcher(menuString);
		
		while (m.find()) {
			validChoices.add(m.group(1));
		}
		
		return getMenuChoice(menuString, validChoices);		
	}
	
	public static String getMenuChoice(String menuString, ArrayList<String> validChoices) {
		printlnWithDelay(menuString);
		String input = "error";
		boolean keepScanning=true;
		while (keepScanning) {
			printlnWithDelay("Please make a valid selection.");
			if (scanner.hasNextLine()) {
				input=scanner.nextLine().toUpperCase();
				keepScanning=!(validChoices.contains(input));
			}
		}
		return input;
	}
	
	public static <T> T getChoiceOfType(String prompt, T type) {
		//TODO Figure out how to get an input of an arbitrary type
		return type;
	}
	
	public static String getFreeTextChoice(String prompt) {
		printWithDelay(prompt);
		return scanner.nextLine();
	}
	
	public static double getNumericChoice(String prompt, double min, double max) {
		double number=min-1; //initialize number to a value that must be changed
		boolean keepScanning=true;
		while (keepScanning) {
			printWithDelay(prompt);
			if (scanner.hasNextDouble()) {
				number=scanner.nextDouble();				
				keepScanning=(number<min||number>max);
			}
			//TODO if the user enters in many new lines and then something that isn't whitespace... it gets a little messy
			scanner.nextLine();
		}
		//TODO Do I need to do something with this assertion?
		assert number>min;
		return number;
	}
	
	public static void printStringsWithDelay (String[] strArray) {
		printStringsWithDelay(strArray, GameSettings.getTextDelay());
	}
	
	public static void printStringsWithDelay (String[] strArray, long delay) {
		try {
			for (int i=0; i<strArray.length; i++) {
				Thread.sleep(delay);
				System.out.print(strArray[i]);
			}
			System.out.println();
		} catch (InterruptedException ie) {
			System.out.println("Don't interrupt! Rude.");
			printStringsWithDelay(strArray, delay);
		}
	}
	
	public static void printWithDelay (String str) {
		printWithDelay(str, GameSettings.getTextDelay());
	}
	
	public static void printWithDelay (String str, long delay) {
		try {
			Thread.sleep(delay);
			System.out.print(str);
		} catch (InterruptedException ie) {
			System.out.println("Don't interrupt! Rude.");
			printWithDelay(str, delay);
		}
	}
	
	public static void printlnWithDelay (String str) {
		printlnWithDelay(str, GameSettings.getTextDelay());
	}
	
	public static void printlnWithDelay (String str, long delay) {
		try {
			Thread.sleep(delay);
			System.out.println(str);
		} catch (InterruptedException ie) {
			System.out.println("Don't interrupt! Rude.");
			printlnWithDelay(str, delay);
		}
	}

}
