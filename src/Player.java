
public class Player {
	private String _nickname;
	private int _foundBombs;
	private int _lives;
	
	public Player(String nickname) 
	{
		_nickname = nickname;
		_foundBombs = 0;
		_lives = 5; 
	}
	
	public int getFoundBombs() {
		return _foundBombs;
	}

	public void setFoundBombs(int foundBombs) {
		_foundBombs = foundBombs;
	}

	public void setLives(int lives) {
		_lives = lives;
	}

	public int getLives() {
		return _lives;
	}

	public String getNickname() 
	{
		return _nickname;
	}
}
