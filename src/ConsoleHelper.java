import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Maurus Kühne
 * ConsoleHelper, stellt div. Funktionen für den einfacheren Zugriff auf die Konsole bereit
 */
public final class ConsoleHelper {
	
	/**
	 * Clears the console.
	 */
	public static void clearConsole()
	{
		for(int i = 0; i < 50; i++)
		{
			System.out.println("");
		}
	}
	
	/**
	 * Writes a single line.
	 *
	 * @param text text to display
	 */
	public static void writeLine(String text)
	{
		System.out.println(text);
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
		//Console c = System.console();
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
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
			System.out.println(question + " " + answerList);
			try {
				retVal = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
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
					System.out.println("Die Eingabe entspricht nicht der Antwortliste! Bitte überprüfe die Eingabe");
			}
			else
			{
				if(retVal.length() > 0)
					gotAnswer = true;
				else
					System.out.println("Es muss eine Eingabe gemacht werden.");
			}
		}
		
		return retVal;
	}
}
