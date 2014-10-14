/*
 * 
 */
import java.util.Timer;

// TODO: Auto-generated Javadoc
/**
 * The Class Game.
 */
public class Game {
	
	/** The _board. */
	Board _board;
	
	/** The elapsed seconds. */
	int elapsedSeconds;
	
	/** The _schwierigkeitsgrad. */
	int _schwierigkeitsgrad;
	
	/** The _player1. */
	Player _player1;
	
	/** The _player2. */
	Player _player2;
	
	/** The _active player. */
	Player _activePlayer;
	
	/** The _timer. */
	Timer _timer;
	
	/** The _update time task. */
	UpdateTimeTimerTask _updateTimeTask;
	
	/** The _highscore. */
	Highscore _highscore;
	
	/**
	 * Instantiates a new game.
	 */
	public Game()
	{
		_timer = new Timer(true);
		_updateTimeTask = new UpdateTimeTimerTask(this, _timer);
		elapsedSeconds = 0;
	}

	/**
	 * Initialize.
	 */
	public void initialize() {
		String answer = ConsoleHelper.askQuestion("Spielst du [a]lleine oder zu [z]weit?", "a", "z");
		
		if(answer.equals("a"))
		{
			String name = askForPlayerName();
			_player1 = new Player(name);
		}
		else
		{
			ConsoleHelper.writeLine("Spieler 1: ");
			String name = askForPlayerName();
			_player1 = new Player(name);
			
			ConsoleHelper.writeLine("Spieler 2: ");
			name = askForPlayerName();
			_player2 = new Player(name);
		}
		_activePlayer = _player1;
		
		answer = ConsoleHelper.askQuestion("W�hle eine Schwierigkeit: [e]infach, [m]ittel, [s]chwer, [u]nm�glich", "e", "m", "s", "u");
		
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
		
		_highscore = new Highscore(_schwierigkeitsgrad);
		
		answer = ConsoleHelper.askQuestion("W�hle eine Spielbrettgr�sse: 5x5 / 7x7 / 10x10", "5", "7", "10");
		
		int brettGroesse = Integer.parseInt(answer);
		
		_board = new Board(brettGroesse, _schwierigkeitsgrad);
		
		_timer.scheduleAtFixedRate(_updateTimeTask,1000, 1000);
	}
	
	/**
	 * Ask for player name.
	 *
	 * @return the string
	 */
	private String askForPlayerName()
	{
		boolean foundName = false;
		String name = "";
		while(!foundName)
		{
			name = ConsoleHelper.askQuestion("Gib einen Namen an (max 3 Zeichen)");
			if(name.length() > 3 || name.length() == 0)
				ConsoleHelper.writeLine("Der Name darf max. 3 Zeichen lang sein!");
			else
				foundName = true;
		}
		return name;
	}
	
	/**
	 * Run loop.
	 */
	public void runLoop() {
		boolean gameFinished = false;
		
		while(!gameFinished)
		{
			ConsoleHelper.clearConsole();
				
			printPlayerStats();
			
			_board.zeichnen(false);
			
			ConsoleHelper.writeLine("");
			
			boolean gotCorrectLocation = false;
			String location = "";
			while(!gotCorrectLocation)
			{
				location = ConsoleHelper.askQuestion("W�hle ein Feld, welches du aufdecken oder markieren m�chtest, z.b. A1:");
				location = location.toUpperCase();
				if(location.length() == 2 && Character.isLetter(location.charAt(0)) && Character.isDigit(location.charAt(1)))
					gotCorrectLocation = true;
				else
					ConsoleHelper.writeLine("Die Eingabe ist keine g�ltige Position!");
			}
			
			int xCoord = location.charAt(0) - 64;
			int yCoord = location.charAt(1) - 48;
			
			String action = ConsoleHelper.askQuestion("Soll das Feld [m]arkiert, oder [a]ufgedeckt werden?", "m", "a");
			
			if(action.equals("m"))
				_board.markieren(xCoord, yCoord);
			else
			{
				boolean foundBomb = !_board.aufdecken(xCoord, yCoord);
				if(foundBomb)
				{
					if(!getIsMultiplayer())
					{
						_activePlayer.setLives(_activePlayer.getLives() - 1);
						if(!(_activePlayer.getLives() > 0))
							//ConsoleHelper.writeLine("Auf dem Feld war eine Mine! Du hast noch " + _activePlayer.getLives() + " Leben!");
						//else
						{
							gameFinished = true;
						}
					}
					else
					{
						_activePlayer.setFoundBombs(_activePlayer.getFoundBombs() + 1);
						//ConsoleHelper.writeLine("Auf dem Feld war eine Mine! Du hast schon " + _activePlayer.getFoundBombs() + " Minen gefunden");
					}
				}
			}
			
			if(getIsMultiplayer())
			{
				if(_activePlayer.getFoundBombs() > (_board.getAnzahlMinen() / 2.0) + 1)
					gameFinished = true;
			}
			else
			{
				gameFinished = _board.alleFelderAufgedeckt();
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
		
		if(!getIsMultiplayer())
		{
			if(_player1.getLives() == 0)
			{
				ConsoleHelper.clearConsole();
				
				ConsoleHelper.writeLine(" _____ ____  _      _____   ____  _     _____ ____"); 
				ConsoleHelper.writeLine("/  __//  _ \\/ \\__/|/  __/  /  _ \\/ \\ |\\/  __//  __\\");
				ConsoleHelper.writeLine("| |  _| / \\|| |\\/|||  \\    | / \\|| | //|  \\  |  \\/|");
				ConsoleHelper.writeLine("| |_//| |-||| |  |||  /_   | \\_/|| \\// |  /_ |    /");
				ConsoleHelper.writeLine("\\____\\\\_/ \\|\\_/  \\|\\____\\  \\____/\\__/  \\____\\\\_/\\_\\");
			}
			
			_highscore.addHighscore(new HighscoreEntry(elapsedSeconds, _player1.getNickname()));
				
			ConsoleHelper.clearConsole();
			
			HighscoreEntry[] entries = _highscore.getHighscores();
			
			int i = 1;
			for(HighscoreEntry e : entries)
			{
				ConsoleHelper.writeLine(i + ". " + e.getName() + "\t\t\t\t\t" + e.getDateTime());
			}
		}
		
		_board.zeichnen(true);
		
		if(getIsMultiplayer())
		{
			Player winner;
			if(_player1.getFoundBombs() > _player2.getFoundBombs())
				winner = _player1;
			else
				winner = _player2;
			
			ConsoleHelper.writeLine("Spieler " + winner.getNickname() + " gewinnt mit " + winner.getFoundBombs() + " Punkten.");

			ConsoleHelper.writeLine("Punkte von " + _player1.getNickname() + ": " + _player1.getFoundBombs());
			ConsoleHelper.writeLine("Punkte von " + _player2.getNickname() + ": " + _player2.getFoundBombs());
		}
	}
	
	/**
	 * Prints the player stats.
	 */
	private void printPlayerStats()
	{
		ConsoleHelper.writeLine(_activePlayer.getNickname() + " ist am Zug!");
		ConsoleHelper.writeLine("Du hast noch " + _activePlayer.getLives() + " Leben");
		ConsoleHelper.writeLine("");
		
		if(getIsMultiplayer())
		{
			ConsoleHelper.writeLine("Punkte von " + _player1.getNickname() + ": " + _player1.getFoundBombs());
			ConsoleHelper.writeLine("Punkte von " + _player2.getNickname() + ": " + _player2.getFoundBombs());
		}
	}
	
	/**
	 * Gets the checks if is multiplayer.
	 *
	 * @return the checks if is multiplayer
	 */
	private boolean getIsMultiplayer()
	{
		return _player2 != null;
	}

	/**
	 * Update time.
	 */
	public void updateTime() {
		elapsedSeconds++;
	}

}
