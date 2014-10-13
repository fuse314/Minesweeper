import java.util.Timer;
import java.util.TimerTask;


class UpdateTimeTimerTask extends TimerTask {
	Game _gameInstance;
	Timer _timer;
	public UpdateTimeTimerTask(Game game, Timer timer)
	{
		_gameInstance = game;
		_timer = timer;
	}
	
    public void run() {
      System.out.println("Time's up!");
      
    }
  }
