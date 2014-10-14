// TODO: Auto-generated Javadoc
/**
 * The Class HighscoreEntry.
 */
public class HighscoreEntry 
{

	/** The _date time. */
	private int _dateTime;
	
	/** The _name. */
	private String _name;
	
	//Konstruktor
	/**
	 * Instantiates a new highscore entry.
	 *
	 * @param _datetime the _datetime
	 * @param _name the _name
	 */
	public HighscoreEntry(int _datetime, String _name)
	{
		setDateTime(_dateTime);
		setName(_name);
	}
	
	/**
	 * Sets the name.
	 *
	 * @param _name the new name
	 */
	public void setName(String _name)
	{
		this._name = _name;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return this._name;
	}
	
	/**
	 * Sets the date time.
	 *
	 * @param _dateTime the new date time
	 */
	public void setDateTime(int _dateTime)
	{
		this._dateTime = _dateTime;
	}
	
	/**
	 * Gets the date time.
	 *
	 * @return the date time
	 */
	public int getDateTime()
	{
		return this._dateTime;
	}
	
	//Statische Methode
	//Entscheided ob dieser Highscore gršsser/kleiner/gleich ist
	/**
	 * Compare to.
	 *
	 * @param h the h
	 * @return the int
	 */
	public int compareTo(HighscoreEntry h)
	{
		return new Integer(this._dateTime).compareTo(h.getDateTime());
		
	}
	
}
