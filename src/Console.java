import java.nio.charset.Charset;
import java.util.Scanner;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenCharacterStyle;
import com.googlecode.lanterna.terminal.Terminal;


public class Console {
	
	Screen _screen;
	int _currentLine = 0;
	
	private Console()
	{
	    Terminal terminal = TerminalFacade.createTerminal(System.in, System.out, Charset.forName("UTF8"));
	    _screen = TerminalFacade.createScreen(terminal);
	    _screen.startScreen();
	    //_screen.putString(10, 5, "Hello Lanterna", Terminal.Color.RED, Terminal.Color.GREEN, ScreenCharacterStyle.Blinking);
	    
	    _screen.refresh();
	}
	
	public void clearScreen()
	{
		_screen.clear();
		_screen.refresh();
		_currentLine = 0;
	}
	
	public void writeLine(String message)
	{
		writeAtPosition(0, _currentLine, message);
		_currentLine++;
	}
	
	public void writeAtPosition(int xCoord, int yCoord, String message)
	{
		_screen.putString(xCoord, yCoord, message, Terminal.Color.WHITE, Terminal.Color.BLACK, ScreenCharacterStyle.Bold );
		_screen.refresh();
	}
	
	public String readLine()
	{
		String str ="";
		while(true)
		{
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Key k = _screen.readInput();
			
			if(k == null)
				continue;
			
			if(k.getKind() == Key.Kind.Enter)
				break;
			
			if(k.getKind() == Key.Kind.Backspace && str.length() > 0)
			{
				str = str.substring(0, str.length() - 1);
				writeAtPosition(str.length(), _currentLine, " ");
			}
			if(k.getKind() == Key.Kind.NormalKey)
				str += k.getCharacter();
			writeAtPosition(0, _currentLine, str);
			
		}
		
		_currentLine++;
		
		return str;
	}
	
	//Singleton
	private static Console _instance;
	public static Console getInstance()
	{
		if(_instance == null)
			_instance = new Console();
		
		return _instance;
	}

	public int getWidth() {
		return _screen.getTerminalSize().getColumns();
	}
}
