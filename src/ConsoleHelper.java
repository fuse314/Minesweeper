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
				for(int i = 0; i < possibleAnswers.length; i++)
				{
					if(possibleAnswers[i].toUpperCase().equals(retVal.toUpperCase()))
					{
						gotAnswer = true;
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
