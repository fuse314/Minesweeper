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
		_updateTimeTask = new UpdateTimeTimerTask(this);
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
		
		
		_timer.schedule(_updateTimeTask, 1000);
	}
	
	private String askForPlayerName()
	{
		boolean foundName = false;
		String name = "";
		while(!foundName)
		{
			name = ConsoleHelper.askQuestion("Gib einen Namen an (max 3 Zeichen)");
			if(name.length() > 2 || name.length() == 0)
				ConsoleHelper.writeLine("Der Name darf max. 3 Zeichen lang sein!");
			else
				foundName = true;
				
		}
		return name;
	}

	public void runLoop() {
		// TODO Auto-generated method stub
		
	}

}
