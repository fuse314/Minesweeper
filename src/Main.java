
// TODO: Auto-generated Javadoc
/**
 * The Class Main.
 */
public final class Main {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
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
