
// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 */
public class Player {
	
	/** The _nickname. */
	private String _nickname;
	
	/** The _found bombs. */
	private int _foundBombs;
	
	/** The _lives. */
	private int _lives;
	
	/**
	 * Instantiates a new player.
	 *
	 * @param nickname the nickname
	 */
	public Player(String nickname) 
	{
		_nickname = nickname;
		_foundBombs = 0;
		_lives = 5; 
	}
	
	/**
	 * Gets the found bombs.
	 *
	 * @return the found bombs
	 */
	public int getFoundBombs() {
		return _foundBombs;
	}

	/**
	 * Sets the found bombs.
	 *
	 * @param foundBombs the new found bombs
	 */
	public void setFoundBombs(int foundBombs) {
		_foundBombs = foundBombs;
	}

	/**
	 * Sets the lives.
	 *
	 * @param lives the new lives
	 */
	public void setLives(int lives) {
		_lives = lives;
	}

	/**
	 * Gets the lives.
	 *
	 * @return the lives
	 */
	public int getLives() {
		return _lives;
	}

	/**
	 * Gets the nickname.
	 *
	 * @return the nickname
	 */
	public String getNickname() 
	{
		return _nickname;
	}
}
