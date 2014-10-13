import java.io.Console;


public final class ConsoleHelper {
	public static void clearConsole()
	{
		for(int i = 0; i < 50; i++)
		{
			System.out.println("");
		}
	}
	
	public static void writeLine(String text)
	{
		System.out.println(text);
	}
	
	public static String askQuestion(String question, String... possibleAnswers)
	{
		String retVal = "";
		String answerList = "";
		boolean gotAnswer = false;
		Console c = System.console();
		
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
			retVal = c.readLine();
			
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
					System.out.println("Die Eingabe entspricht nicht der Antwortliste! Bitte ŸberprŸfe die Eingabe");
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
