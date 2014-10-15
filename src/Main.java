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
			Console c = Console.getInstance();
			c.clearScreen();
			
			c.writeLine("   _____   .__                                                                        ");
			c.writeLine("  /     \\  |__|  ____    ____    ________  _  __  ____   ____  ______    ____ _______ ");
			c.writeLine(" /  \\ /  \\ |  | /    \\ _/ __ \\  /  ___/\\ \\/ \\/ /_/ __ \\_/ __ \\ \\____ \\ _/ __ \\_  __ \\");
			c.writeLine("/    Y    \\|  ||   |  \\\\  ___/  \\___ \\  \\     / \\  ___/\\  ___/ |  |_> >\\  ___/ |  |\\/");
			c.writeLine("\\____|__  /|__||___|  / \\___  >/____  >  \\/\\_/   \\___  >\\___  >|   __/  \\___  >|__|   ");
			c.writeLine("        \\/          \\/      \\/      \\/               \\/     \\/ |__|         \\/        ");
			c.writeLine("                                                              (c) 2014 by ano,gma,mau");
			
			
			Game game = new Game();
			
			game.initialize();
			
			game.runLoop();
			
			//fragen ob nochmals gespielt werden soll?
			String answer = c.askQuestion("Mšchten Sie nocheinmal spielen? ([J]a/[N]ein)", "j", "n");
			
			if(answer.equals("n"))
			{
				shouldExit = true;
				c.quitScreen();
			}
		}
	}
}
