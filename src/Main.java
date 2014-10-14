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
			
			ConsoleHelper.writeLine("   _____   .__                                                                        ");
			ConsoleHelper.writeLine("  /     \\  |__|  ____    ____    ________  _  __  ____   ____  ______    ____ _______ ");
			ConsoleHelper.writeLine(" /  \\ /  \\ |  | /    \\ _/ __ \\  /  ___/\\ \\/ \\/ /_/ __ \\_/ __ \\ \\____ \\ _/ __ \\_  __ \\");
			ConsoleHelper.writeLine("/    Y    \\|  ||   |  \\\\  ___/  \\___ \\  \\     / \\  ___/\\  ___/ |  |_> >\\  ___/ |  | \\/");
			ConsoleHelper.writeLine("\\____|__  /|__||___|  / \\___  >/____  >  \\/\\_/   \\___  >\\___  >|   __/  \\___  >|__|   ");
			ConsoleHelper.writeLine("        \\/          \\/      \\/      \\/               \\/     \\/ |__|         \\/        ");
			ConsoleHelper.writeLine(" ");
			
			
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
