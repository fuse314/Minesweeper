public class HighscoreEntry 
{

	private int _dateTime;
	private String _name;
	
	//Konstruktor
	public HighscoreEntry(int _datetime, String _name)
	{
		setDateTime(_dateTime);
		setName(_name);
	}
	
	public void setName(String _name)
	{
		this._name = _name;
	}
	
	public String getName()
	{
		return this._name;
	}
	
	public void setDateTime(int _dateTime)
	{
		this._dateTime = _dateTime;
	}
	
	public int getDateTime()
	{
		return this._dateTime;
	}
	
	//Statische Methode
	//Entscheided ob dieser Highscore gršsser/kleiner/gleich ist
	public int compareTo(HighscoreEntry h)
	{
		return new Integer(this._dateTime).compareTo(h.getDateTime());
		
	}
	
}
