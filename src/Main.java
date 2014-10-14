import java.nio.charset.Charset;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenCharacterStyle;
import com.googlecode.lanterna.terminal.Terminal;


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
			
			//fragen ob nochmals gespielt werden soll?
			String answer = ConsoleHelper.askQuestion("Mšchten Sie nocheinmal spielen? (Ja/Nein)", "j", "n");
			
			if(answer.equals("n"))
			{
				shouldExit = true;
			}
		}
	}
}
