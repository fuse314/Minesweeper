/**
 * @author Maurus KŸhne
 * The Player class. Data container for all informations needed for a player
 */
public class Player {
	private String _nickname;
	private int _foundMines;
	private int _lives;
	
	/**
	 * Instantiates a new player.
	 *
	 * @param nickname the nickname of the player
	 */
	public Player(String nickname) 
	{
		_nickname = nickname;
		_foundMines = 0;
		_lives = 5; 
	}
	
	/**
	 * Gets the found mines.
	 *
	 * @return the found mines
	 */
	public int getFoundMines() {
		return _foundMines;
	}

	/**
	 * Sets the found mines.
	 *
	 * @param sets the new amount of found mines
	 */
	public void setFoundMines(int foundMines) {
		_foundMines = foundMines;
	}
	
	/**
	 * Sets the lives.
	 *
	 * @param lives the new amount of lives
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
