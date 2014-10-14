/*
 * 
 */
import java.util.Timer;
import java.util.TimerTask;


// TODO: Auto-generated Javadoc
/**
 * The Class UpdateTimeTimerTask.
 */
class UpdateTimeTimerTask extends TimerTask {
	
	/** The _game instance. */
	Game _gameInstance;
	
	/** The _timer. */
	Timer _timer;
	
	/**
	 * Instantiates a new update time timer task.
	 *
	 * @param game the game
	 * @param timer the timer
	 */
	public UpdateTimeTimerTask(Game game, Timer timer)
	{
		_gameInstance = game;
		_timer = timer;
	}
	
    /* (non-Javadoc)
     * @see java.util.TimerTask#run()
     */
    public void run() {
    	_gameInstance.updateTime();
    }
  }
