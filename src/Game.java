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
		_timer.schedule(_updateTimeTask, 1000);
	}

	public void initialize() {
		
		
	}

	public void runLoop() {
		// TODO Auto-generated method stub
		
	}

}
