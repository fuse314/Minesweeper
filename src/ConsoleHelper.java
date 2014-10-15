import com.googlecode.lanterna.terminal.Terminal;

/**
 * @author Maurus KŸhne
 * ConsoleHelper, stellt div. Funktionen fŸr den einfacheren Zugriff auf die Konsole bereit
 */
public final class ConsoleHelper {
	
	/**
	 * Clears the console.
	 */
	public static void clearConsole()
	{
		Console.getInstance().clearScreen();
	}
	
	/**
	 * Writes a single line.
	 *
	 * @param text text to display
	 */
	public static void writeLine(String text)
	{
		Console.getInstance().writeLine(text);
	}
	
	/**
	 * Writes Text to console
	 * @param text to write
	 * @param style numeric style for easy fore/background color usage
	 * @param linebreak after text (like println())
	 */
	public static void write(String text, int style, boolean linebreak) {
		Terminal.Color fcolor;
		Terminal.Color bcolor;
		switch(style) {
		case 0: fcolor = Terminal.Color.WHITE; bcolor = Terminal.Color.BLACK; break;
		case 1: fcolor = Terminal.Color.GREEN; bcolor = Terminal.Color.BLACK; break;
		case 2: fcolor = Terminal.Color.CYAN; bcolor = Terminal.Color.BLACK; break;
		case 3: fcolor = Terminal.Color.BLUE; bcolor = Terminal.Color.BLACK; break;
		case 4: fcolor = Terminal.Color.YELLOW; bcolor = Terminal.Color.BLACK; break;
		case 5: fcolor = Terminal.Color.RED; bcolor = Terminal.Color.BLACK; break;
		case 6: fcolor = Terminal.Color.BLACK; bcolor = Terminal.Color.GREEN; break;
		case 7: fcolor = Terminal.Color.BLACK; bcolor = Terminal.Color.CYAN; break;
		case 8: fcolor = Terminal.Color.BLACK; bcolor = Terminal.Color.BLUE; break;
		case 9: fcolor = Terminal.Color.BLACK; bcolor = Terminal.Color.YELLOW; break;
		case 10: fcolor = Terminal.Color.BLACK; bcolor = Terminal.Color.RED; break;
		case 11: fcolor = Terminal.Color.BLACK; bcolor = Terminal.Color.BLACK; break;
		case 12: fcolor = Terminal.Color.MAGENTA; bcolor = Terminal.Color.BLACK; break;
		default: fcolor = Terminal.Color.WHITE; bcolor = Terminal.Color.BLACK; break;
		}
		write(text,fcolor,bcolor,linebreak);
	}
	
	/**
	 * Writes Text to console
	 * @param text to write
	 * @param fcolor foreground color
	 * @param bcolor background color
	 * @param linebreak after text (like println() )
	 */
	public static void write(String text, Terminal.Color fcolor, Terminal.Color bcolor, boolean linebreak) {
		Console.getInstance().write(text,fcolor,bcolor, linebreak);
	}
	
	
	
	/**
	 * Asks a question.
	 *
	 * @param question the question
	 * @param possibleAnswers the possible answers
	 * @return the answer
	 */
	public static String askQuestion(String question, String... possibleAnswers)
	{
		String retVal = "";
		String answerList = "";
		boolean gotAnswer = false;
		
		if(possibleAnswers.length > 0)
		{
			// first entry in possibleAnswers is default entry (just press enter)
			possibleAnswers[0] = possibleAnswers[0].toUpperCase();
			answerList = "(";
			for(int i = 0; i < possibleAnswers.length; i++)
			{
				answerList += possibleAnswers[i] + "/";
			}
			
			answerList = answerList.substring(0, answerList.length() - 1);
			
			answerList += ")";
		}
		
		while(!gotAnswer)
		{
			writeLine(question + " " + answerList);
			
			retVal = Console.getInstance().readLine();
			
			if(possibleAnswers.length > 0)
			{
				// first entry in possibleAnswers is default entry (just press enter)
				if(retVal == "") {
					retVal = possibleAnswers[0].toLowerCase();
				}
				for(int i = 0; i < possibleAnswers.length; i++)
				{
					if(possibleAnswers[i].toUpperCase().equals(retVal.toUpperCase()))
					{
						gotAnswer = true;
						break;
					}
				}
				
				if(!gotAnswer)
					writeLine("Die Eingabe entspricht nicht der Antwortliste! Bitte ŸberprŸfe die Eingabe");
			}
			else
			{
				if(retVal.length() > 0)
					gotAnswer = true;
				else
					writeLine("Es muss eine Eingabe gemacht werden.");
			}
		}
		
		return retVal;
	}

	public static void updateStatusbar(int elapsedSeconds, String currentPlayerName, int remainingLives, boolean isMultiPlayer, int foundMines) 
	{
		String str = "Zeit: " + elapsedSeconds;
		int consoleWidth = Console.getInstance().getWidth();
		int xCoord = consoleWidth - 15;
		Console.getInstance().writeAtPosition(xCoord, 0, str);
		Console.getInstance().writeAtPosition(xCoord, 1, "Spieler: " + currentPlayerName);
		if(!isMultiPlayer)
			Console.getInstance().writeAtPosition(xCoord, 2, "Leben: " + remainingLives);
		else
			Console.getInstance().writeAtPosition(xCoord, 2, "gef. Minen: " + foundMines);
	}

}
