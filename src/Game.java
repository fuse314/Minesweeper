import java.io.IOException;
import java.util.Date;
import java.util.Timer;


public class Game {
	
	Board _board;
	
	Date _startZeit;
	
	int _schwierigkeitsgrad;
	
	Player _player1;
	
	Player _player2;
	
	Player _activePlayer;
	
	Timer _timer;
	UpdateTimeTimerTask _updateTimeTask;
	
	public Game()
	{
		_timer = new Timer(true);
		_updateTimeTask = new UpdateTimeTimerTask(this, _timer);
	}

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
		
		answer = ConsoleHelper.askQuestion("Wähle eine Schwierigkeit: [e]infach, [m]ittel, [s]chwer, [u]nmöglich", "e", "m", "s", "u");
		
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
		
		answer = ConsoleHelper.askQuestion("Wähle eine Spielbrettgrösse: 5x5 / 7x7 / 10x10", "5", "7", "10");
		
		int brettGroesse = Integer.parseInt(answer);
		
		_board = new Board(brettGroesse, _schwierigkeitsgrad);
		
		//_timer.scheduleAtFixedRate(_updateTimeTask,1000, 1000);
	}
	
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
				location = ConsoleHelper.askQuestion("Wähle ein Feld, welches du aufdecken oder markieren möchtest, z.b. A1:");
				
				if(location.length() == 2 && Character.isLetter(location.charAt(0)) && Character.isDigit(location.charAt(1)))
					gotCorrectLocation = true;
				else
					ConsoleHelper.writeLine("Die Eingabe ist keine gültige Position!");
			}
			
			int xCoord = location.charAt(0) - 64;
			int yCoord = location.charAt(1);
			
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
						if(_activePlayer.getLives() > 0)
							ConsoleHelper.writeLine("Auf dem Feld war eine Bombe! Du hast noch " + _activePlayer.getLives() + " Leben!");
						else
						{
							ConsoleHelper.writeLine("GAME OVER!!!!");
							gameFinished = true;
						}
					}
					else
					{
						_activePlayer.setFoundBombs(_activePlayer.getFoundBombs() + 1);
						ConsoleHelper.writeLine("Auf dem Feld war eine Bombe! Du hast schon " + _activePlayer.getFoundBombs() + "Bomben gefunden");
					}
				}
			}
			
			ConsoleHelper.writeLine("Drücke Enter um den nächsten Zug zu starten");
			
			try {
				System.in.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(getIsMultiplayer())
				if(_activePlayer == _player1)
					_activePlayer = _player2;
				else
					_activePlayer = _player1;
		}
	}
	
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
	
	private boolean getIsMultiplayer()
	{
		return _player2 != null;
	}

}
