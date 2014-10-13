import java.util.TimerTask;


class UpdateTimeTimerTask extends TimerTask {
	Game _gameInstance;
	
	public UpdateTimeTimerTask(Game game)
	{
		_gameInstance = game;
	}
	
    public void run() {
      System.out.println("Time's up!");
    }
  }
