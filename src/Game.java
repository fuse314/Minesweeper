/*
 * 
 */
import java.util.Timer;

/**
 * @author Maurus KŸhne
 * The Game class. Contains the game logic and manages the board, highscores and players
 */
public class Game {
	Board _board;
	int elapsedSeconds;
	int _schwierigkeitsgrad;
	Player _player1;
	Player _player2;
	Player _activePlayer;
	Timer _timer;
	UpdateTimeTimerTask _updateTimeTask;
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
	 * Initializes a new game
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
		
		answer = ConsoleHelper.askQuestion("WŠhle eine Schwierigkeit: [e]infach, [m]ittel, [s]chwer, [u]nmšglich", "e", "m", "s", "u");
		
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
		
		
		answer = ConsoleHelper.askQuestion("WŠhle eine Spielbrettgršsse: 5x5 / 7x7 / 10x10", "5", "7", "10");
		
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
			name = ConsoleHelper.askQuestion("Gib einen Namen an (max 3 Zeichen)");
			if(name.length() > 3 || name.length() == 0)
				ConsoleHelper.writeLine("Der Name darf max. 3 Zeichen lang sein!");
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
			ConsoleHelper.clearConsole();
			
			ConsoleHelper.updateStatusbar(elapsedSeconds,_activePlayer.getNickname(), _activePlayer.getLives(), 
					getIsMultiplayer(), _activePlayer.getFoundMines());
				
			if(getIsMultiplayer())
			{
				ConsoleHelper.writeLine("Punkte von " + _player1.getNickname() + ": " + _player1.getFoundMines());
				ConsoleHelper.writeLine("Punkte von " + _player2.getNickname() + ": " + _player2.getFoundMines());
			}
			
			_board.zeichnen(false);
			
			ConsoleHelper.writeLine("");
			
			boolean gotCorrectLocation = false;
			String location = "";
			while(!gotCorrectLocation)
			{
				location = ConsoleHelper.askQuestion("WŠhle ein Feld, welches du aufdecken oder markieren mšchtest, z.b. A1:");
				location = location.toUpperCase();
				if((location.length() == 2 && Character.isLetter(location.charAt(0)) && Character.isDigit(location.charAt(1))) ||
				   (location.length() == 3 && Character.isLetter(location.charAt(0)) && Character.isDigit(location.charAt(1))
						   && Character.isDigit(location.charAt(2))))
					gotCorrectLocation = true;
				else
					ConsoleHelper.writeLine("Die Eingabe ist keine gŸltige Position!");
			}
			
			int xCoord = location.charAt(0) - 64;
			int yCoord = Integer.parseInt(location.substring(1));
			
			String action = ConsoleHelper.askQuestion("Soll das Feld [m]arkiert, oder [a]ufgedeckt werden?", "m", "a");
			
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
						if(!(_activePlayer.getLives() >= 0))
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
		ConsoleHelper.clearConsole();
		
		if(!getIsMultiplayer())
		{
			if(_player1.getLives() <= 0)
			{
				ConsoleHelper.writeLine(" _____ ____  _      _____   ____  _     _____ ____"); 
				ConsoleHelper.writeLine("/  __//  _ \\/ \\__/|/  __/  /  _ \\/ \\ |\\/  __//  __\\");
				ConsoleHelper.writeLine("| |  _| / \\|| |\\/|||  \\    | / \\|| | //|  \\  |  \\/|");
				ConsoleHelper.writeLine("| |_//| |-||| |  |||  /_   | \\_/|| \\// |  /_ |    /");
				ConsoleHelper.writeLine("\\____\\\\_/ \\|\\_/  \\|\\____\\  \\____/\\__/  \\____\\\\_/\\_\\");
				ConsoleHelper.writeLine("");
			}
			else
				_highscore.addHighscore(new HighscoreEntry(elapsedSeconds, _player1.getNickname()));
			
			_highscore.zeichnen();
		}
		ConsoleHelper.writeLine("");
		ConsoleHelper.writeLine("Auflšsung:");
		_board.zeichnen(true);
		ConsoleHelper.writeLine("");
		if(getIsMultiplayer())
		{
			Player winner;
			if(_player1.getFoundMines() > _player2.getFoundMines())
				winner = _player1;
			else
				winner = _player2;
			
			ConsoleHelper.writeLine("Spieler " + winner.getNickname() + " gewinnt mit " + winner.getFoundMines() + " Punkten.");

			ConsoleHelper.writeLine("Punkte von " + _player1.getNickname() + ": " + _player1.getFoundMines());
			ConsoleHelper.writeLine("Punkte von " + _player2.getNickname() + ": " + _player2.getFoundMines());
			ConsoleHelper.writeLine("");
		}
		
		_timer.cancel();
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
		ConsoleHelper.updateStatusbar(elapsedSeconds,_activePlayer.getNickname(), _activePlayer.getLives(), 
				getIsMultiplayer(), _activePlayer.getFoundMines());
	}

}
