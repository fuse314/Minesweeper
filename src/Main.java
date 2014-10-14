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
			Game game = new Game();
			
			game.initialize();
			
			game.runLoop();
			
		}
	}
}
