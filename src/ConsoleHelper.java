import java.io.Console;


public final class ConsoleHelper {
	public static void clearConsole()
	{
		for(int i = 0; i < 50; i++)
		{
			System.out.println("");
		}
	}
	
	public static String askQuestion(String question, String... possibleAnswers)
	{
		Console c = System.console();
		
		String answerList = "(";
		for(int i = 0; i < possibleAnswers.length; i++)
		{
			answerList += possibleAnswers[i];
		}
		answerList += ")";
		
		System.out.println(question + " " + answerList);
		
		String retVal = c.readLine();
		
		clearConsole();
		
		return retVal;
	}
}
