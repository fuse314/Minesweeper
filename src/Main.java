
public final class Main {
	
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
