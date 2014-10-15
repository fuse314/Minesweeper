import java.util.Timer;
import com.googlecode.lanterna.terminal.TerminalPosition;

/**
 * @author Maurus KŸhne
 * The Game class. Contains the game logic and manages the board, highscores and players
 */
public class Game {
	Console _console;
	Board _board;
	int elapsedSeconds;
	int _schwierigkeitsgrad;
	Player _player1;
	Player _player2;
	Player _activePlayer;
	Timer _timer;
	UpdateTimeTimerTask _updateTimeTask;
	Highscore _highscore;
	int _xpos=1;
	int _ypos=1;
	
	/**
	 * Instantiates a new game.
	 */
	public Game()
	{
		_console = Console.getInstance();
		_timer = new Timer(true);
		_updateTimeTask = new UpdateTimeTimerTask(this, _timer);
		elapsedSeconds = 0;
	}

	/**
	 * Initializes a new game
	 */
	public void initialize() {
		String answer = _console.askQuestion("Spielst du [a]lleine oder zu [z]weit?", "a", "z");
		
		if(answer.equals("a"))
		{
			String name = askForPlayerName();
			_player1 = new Player(name);
		}
		else
		{
			_console.writeLine("Spieler 1: ");
			String name = askForPlayerName();
			_player1 = new Player(name);
			
			_console.writeLine("Spieler 2: ");
			name = askForPlayerName();
			_player2 = new Player(name);
		}
		_activePlayer = _player1;
		
		answer = _console.askQuestion("WŠhle eine Schwierigkeit: [e]infach, [m]ittel, [s]chwer, [u]nmšglich", "e", "m", "s", "u");
		
		switch(answer.charAt(0))
		{
		case 'e':
			_schwierigkeitsgrad = 10;
			break;
		case 'm':
			_schwierigkeitsgrad = 25;
			break;
		case 's':
			_schwierigkeitsgrad = 50;
			break;
		case 'u':
			_schwierigkeitsgrad = 99;
		}
		
		
		answer = _console.askQuestion("WŠhle eine Spielbrettgršsse: 5x5 / 7x7 / 10x10", "5", "7", "10");
		
		int brettGroesse = Integer.parseInt(answer);
		
		_board = new Board(brettGroesse, _schwierigkeitsgrad);
		_highscore = new Highscore(_schwierigkeitsgrad, brettGroesse);
		_timer.scheduleAtFixedRate(_updateTimeTask,1000, 1000);
	}
	
	/**
	 * Ask for the name of a player.
	 *
	 * @return the name
	 */
	private String askForPlayerName()
	{
		boolean foundName = false;
		String name = "";
		while(!foundName)
		{
			name = _console.askQuestion("Gib einen Namen an (max 3 Zeichen)");
			if(name.length() > 3 || name.length() == 0)
				_console.writeLine("Der Name darf max. 3 Zeichen lang sein!");
			else
				foundName = true;
		}
		return name;
	}
	
	/**
	 * Run loop of the game.
	 */
	public void runLoop() {
		boolean gameFinished = false;
		
		while(!gameFinished)
		{
			_console.clearScreen();
			
			_console.updateStatusbar(elapsedSeconds,_activePlayer.getNickname(), _activePlayer.getLives(), 
					getIsMultiplayer(), _activePlayer.getFoundMines());
				
			if(getIsMultiplayer())
			{
				_console.writeLine("Punkte von " + _player1.getNickname() + ": " + _player1.getFoundMines());
				_console.writeLine("Punkte von " + _player2.getNickname() + ": " + _player2.getFoundMines());
			}
			
			_console.writeLine("");
			
			_board.zeichnen(false);
			
			_console.writeLine("");
			
			
			int startingRow = getIsMultiplayer() ? 4 : 2;
			int numberOfElements = _board.getSize();
			TerminalPosition pos = Console.getInstance().getSelectedPosition(startingRow, _board.getHorizontalFieldStart(), numberOfElements, 
					_board.getHorizontalFieldDistance(), _board.getVerticalFieldDistance(),_ypos,_xpos);
			
			int xCoord = pos.getColumn();
			_xpos = xCoord;
			int yCoord = pos.getRow();
			_ypos = yCoord;
			
			String action = _console.askQuestion("Soll das Feld [m]arkiert, oder [a]ufgedeckt werden?", "a", "m");
			
			if(action.equals("m"))
				_board.markieren(xCoord, yCoord);
			else
			{
				boolean foundMine = !_board.aufdecken(xCoord, yCoord);
				if(foundMine)
				{
					if(!getIsMultiplayer())
					{
						_activePlayer.setLives(_activePlayer.getLives() - 1);
						if((_activePlayer.getLives() <= 0))
							gameFinished = true;
					}
					else
						_activePlayer.setFoundMines(_activePlayer.getFoundMines() + 1);
				}
			}
			
			if(getIsMultiplayer())
			{
				if(_activePlayer.getFoundMines() >= (_board.getAnzahlMinen() / 2) + 1)
					gameFinished = true;
			}
			else
			{
				if(_board.alleFelderAufgedeckt())
					gameFinished = true;
			}
			
			if(getIsMultiplayer())
				if(_activePlayer == _player1)
					_activePlayer = _player2;
				else
					_activePlayer = _player1;
		}
		
		showGameOverStats();
	}
	
	
	/**
	 * Show game over stats.
	 */
	private void showGameOverStats() {
		//Spiel ist fertig, entweder gewonnen oder alle leben verloren
		
		HighscoreEntry newEntry = null;
		
		//deaktiviere den Timer
		_timer.cancel();
		
		_console.clearScreen();
		
		if(!getIsMultiplayer())
		{
			_console.clearScreen();
			GameOverAnimation anim;
			if(_player1.getLives() <= 0)
			{
				anim = new GameOverAnimation(30,100,true); // animation - game over
			}
			else
			{
				newEntry = new HighscoreEntry(elapsedSeconds, _player1.getNickname());
				_highscore.addHighscore(newEntry);
				
				anim = new GameOverAnimation(30,100,false); // animation - you win!
			}
			anim.play();
			_console.clearScreen();
			_highscore.zeichnen(newEntry);
		}
		
		_console.writeLine("");
		_console.writeLine("Auflšsung:");
		_board.zeichnen(true);
		_console.writeLine("");
		
		if(getIsMultiplayer())
		{
			Player winner;
			if(_player1.getFoundMines() > _player2.getFoundMines())
				winner = _player1;
			else
				winner = _player2;
			
			_console.writeLine("Spieler " + winner.getNickname() + " gewinnt mit " + winner.getFoundMines() + " Punkten.");

			_console.writeLine("Punkte von " + _player1.getNickname() + ": " + _player1.getFoundMines());
			_console.writeLine("Punkte von " + _player2.getNickname() + ": " + _player2.getFoundMines());
			_console.writeLine("");
		}
	}
	
	
	/**
	 * checks if this game is a multiplayer game
	 *
	 * @return true if this is a multiplayer game
	 */
	private boolean getIsMultiplayer()
	{
		return _player2 != null;
	}

	/**
	 * Updates the current time.
	 */
	public void updateTime() {
		elapsedSeconds++;
		_console.updateStatusbar(elapsedSeconds,_activePlayer.getNickname(), _activePlayer.getLives(), 
				getIsMultiplayer(), _activePlayer.getFoundMines());
	}

}
