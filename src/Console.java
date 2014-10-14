import java.nio.charset.Charset;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenCharacterStyle;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalPosition;
import com.googlecode.lanterna.terminal.TerminalSize;


public class Console {
	
	Screen _screen;
	int _currentLine = 0;
	
	private Console()
	{
	    Terminal terminal = TerminalFacade.createTerminal(System.in, System.out, Charset.forName("UTF8"));
	    _screen = TerminalFacade.createScreen(terminal);
	    _screen.startScreen();
	    
	    _screen.refresh();
	}
	
	public void clearScreen()
	{
		_screen.clear();
		_screen.refresh();
		_currentLine = 0;
	}
	
	public void quitScreen() {
		_screen.stopScreen();
	}
	
	public void writeLine(String message)
	{
		writeAtPosition(0, _currentLine, message);
		_currentLine++;
		_screen.setCursorPosition(0, _currentLine);
		_screen.refresh();
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

	public TerminalPosition getSelectedPosition(int startingRow, int startingCol, int numberOfElements, int horizontalDistance,
			int verticalDistance, int initialRow, int initialCol) {
		
		int hMax = startingCol + ((numberOfElements - 1) * horizontalDistance); 
		int vMax = startingRow + ((numberOfElements - 1) * verticalDistance);
		
		TerminalPosition origPos = _screen.getCursorPosition();
		TerminalPosition selectedPos = new TerminalPosition(
				((initialCol-1)*horizontalDistance)+startingCol,
				((initialRow-1)*verticalDistance)+startingRow);
		_screen.setCursorPosition(selectedPos);
		while(true)
		{
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Key k = _screen.readInput();
			
			if(k == null)
				continue;
			
			if(k.getKind() == Key.Kind.Enter)
				break;
			
			if(k.getKind() == Key.Kind.ArrowDown && selectedPos.getRow() < vMax)
				selectedPos.setRow(selectedPos.getRow() + verticalDistance);
			if(k.getKind() == Key.Kind.ArrowUp && selectedPos.getRow() > startingRow)
				selectedPos.setRow(selectedPos.getRow() - verticalDistance);
			
			if(k.getKind() == Key.Kind.ArrowRight && selectedPos.getColumn() < hMax)
				selectedPos.setColumn(selectedPos.getColumn() + horizontalDistance);
			if(k.getKind() == Key.Kind.ArrowLeft && selectedPos.getColumn() > startingCol)
				selectedPos.setColumn(selectedPos.getColumn() - horizontalDistance);
			
			_screen.setCursorPosition(selectedPos);
		}
		
		_screen.setCursorPosition(origPos);
		
		_screen.putString(selectedPos.getColumn(), selectedPos.getRow(), "X", Terminal.Color.BLUE, Terminal.Color.DEFAULT, ScreenCharacterStyle.Blinking );
		
		// calculate x,y position and make it 1-based
		selectedPos.setRow(((selectedPos.getRow()-startingRow) / verticalDistance)+1) ;
		selectedPos.setColumn(((selectedPos.getColumn() - startingCol) / horizontalDistance)+1);
		
		return selectedPos;
		
	}
}
