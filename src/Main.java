/**
 * @author Maurus KŸhne
 * The main class, contains the main entry point/main method.
 */
public final class Main {
	
	/**
	 * The main method.
	 *
	 * @param args console arguments
	 */
	public static void main(String[] args)
	{
		Boolean shouldExit = false;
		
		while(!shouldExit) 
		{
			ConsoleHelper.clearConsole();
			Game game = new Game();
			
			game.initialize();
			
			game.runLoop();
			
			//fragen ob nochmals gespielt werden soll?
			String answer = ConsoleHelper.askQuestion("Mšchten Sie nocheinmal spielen? ([J]a/[N]ein)", "j", "n");
			
			if(answer.equals("n"))
			{
				shouldExit = true;
			}
		}
	}
}
