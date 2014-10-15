import java.nio.charset.Charset;
import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.ScreenCharacterStyle;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalPosition;

/**
 * Wrapper class around the lanterna console framework, so we can easily draw to the console
 * @author Maurus KŸhne
 *
 */
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
	
	/**
	 * clears the console screen
	 */
	public void clearScreen()
	{
		_screen.clear();
		_screen.refresh();
		_currentLine = 0;
	}
	
	/**
	 * stops the console and closes it
	 */
	public void quitScreen() {
		_screen.stopScreen();
	}
	
	/**
	 * Writes a single line.
	 *
	 * @param text text to display
	 */
	public void writeLine(String text)
	{
		writeLine(text, ScreenCharacterStyle.Bold);
	}
	
	/**
	 * Writes a single line.
	 * @param text text to display
	 * @param style a special style that should be used to draw the text, eg. blinking, underlined, ...
	 */
	public void writeLine(String text, ScreenCharacterStyle style)
	{
		writeAtPosition(0, _currentLine, text, style);
		_currentLine++;
		_screen.setCursorPosition(0, _currentLine);
		_screen.refresh();
	}
	
	/**
	 * writes text at a specified coordinate
	 * @param xCoord the x coordinate
	 * @param yCoord the y coordinate
	 * @param message the text to be written
	 */
	public void writeAtPosition(int xCoord, int yCoord, String message)
	{
		writeAtPosition(xCoord, yCoord, message, ScreenCharacterStyle.Bold);
	}
	
	/**
	 * writes text at a specified coordinate
	 * @param xCoord the x coordinate
	 * @param yCoord the y coordinate
	 * @param message the text to be written
	 * @param style a special style that should be used to draw the text, eg. blinking, underlined, ...
	 */
	public void writeAtPosition(int xCoord, int yCoord, String message, ScreenCharacterStyle style)
	{
		_screen.putString(xCoord, yCoord, message, Terminal.Color.WHITE, Terminal.Color.BLACK, style, ScreenCharacterStyle.Bold );
		_screen.refresh();
	}
	
	/**
	 * reads a single line of text
	 * @return the read text
	 */
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
	/**
	 * gets the singleton instance of the console
	 * @return the console...
	 */
	public static Console getInstance()
	{
		if(_instance == null)
			_instance = new Console();
		
		return _instance;
	}

	/**
	 * gets the width of the console
	 * @return the width
	 */
	public int getWidth() {
		return _screen.getTerminalSize().getColumns();
	}

	/**
	 * lets the user select a position within the minesweeper board
	 * @param startingRow the console row in which the board starts
	 * @param startingCol the console column in which the board starts
	 * @param numberOfElements the width/height of the board in fields
	 * @param horizontalDistance the horizontal difference in console-coordinates between two board fields
	 * @param verticalDistance the vertical difference in console-coordinates between two board fields
	 * @param initialRow the initially selected row
	 * @param initialCol the initially selected column
	 * @return the console coordinates of the field the user selected
	 */
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

			_screen.refresh();
			
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

	/**
	 * Asks a question.
	 *
	 * @param question the question
	 * @param possibleAnswers the possible answers
	 * @return the answer
	 */
	public String askQuestion(String question, String... possibleAnswers)
	{
		String retVal = "";
		String answerList = "";
		boolean gotAnswer = false;
		
		if(possibleAnswers.length > 0)
		{
			// first entry in possibleAnswers is default entry (just press enter)
			possibleAnswers[0] = possibleAnswers[0].toUpperCase();
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
			writeLine(question + " " + answerList);
			
			retVal = getInstance().readLine();
			
			if(possibleAnswers.length > 0)
			{
				// first entry in possibleAnswers is default entry (just press enter)
				if(retVal == "") {
					retVal = possibleAnswers[0].toLowerCase();
				}
				for(int i = 0; i < possibleAnswers.length; i++)
				{
					if(possibleAnswers[i].toUpperCase().equals(retVal.toUpperCase()))
					{
						gotAnswer = true;
						break;
					}
				}
				
				if(!gotAnswer)
					writeLine("Die Eingabe entspricht nicht der Antwortliste! Bitte ŸberprŸfe die Eingabe");
			}
			else
			{
				if(retVal.length() > 0)
					gotAnswer = true;
				else
					writeLine("Es muss eine Eingabe gemacht werden.");
			}
		}
		
		return retVal;
	}

	/**
	 * updates the top right statusbar with the current values
	 * @param elapsedSeconds the elapsed time since the game has been started
	 * @param currentPlayerName the name of the currently active player
	 * @param remainingLives the remaining lives of the currently active player
	 * @param isMultiPlayer is this game a multiplayer game?
	 * @param foundMines the number of mines the current player has found (only for multiplayer)
	 */
	public void updateStatusbar(int elapsedSeconds, String currentPlayerName, int remainingLives, boolean isMultiPlayer, int foundMines) 
	{
		String str = "Zeit: " + elapsedSeconds;
		int consoleWidth = getInstance().getWidth();
		int xCoord = consoleWidth - 15;
		getInstance().writeAtPosition(xCoord, 0, str);
		getInstance().writeAtPosition(xCoord, 1, "Spieler: " + currentPlayerName);
		if(!isMultiPlayer)
			writeAtPosition(xCoord, 2, "Leben: " + remainingLives);
		else
			writeAtPosition(xCoord, 2, "gef. Minen: " + foundMines);
	}
}
