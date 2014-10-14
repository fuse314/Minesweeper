/**
 * The Class Field.
 */
public class Field {
	
	/** The _proximity. */
	private int _proximity;
	
	/**
	 * Gets the proximity.
	 *
	 * @return the proximity
	 */
	public int getProximity() {
		return this._proximity;
	}
	
	/**
	 * Sets the proximity.
	 *
	 * @param proximity the new proximity
	 */
	public void setProximity(int proximity) {
		this._proximity = proximity;
	}
	
	/** The _zustand. */
	private FieldZustand _zustand = FieldZustand.Verdeckt;
	
	/**
	 * Gets the zustand.
	 *
	 * @return the zustand
	 */
	public FieldZustand getZustand() {
		return this._zustand;
	}
	
	/**
	 * Sets the zustand.
	 *
	 * @param _zustand the new zustand
	 */
	public void setZustand(FieldZustand _zustand) {
		this._zustand = _zustand;
	}

	/** The _mine. */
	private boolean _mine;
	
	/**
	 * Checks if is t mine.
	 *
	 * @return true, if is t mine
	 */
	public boolean istMine() {
		return _mine;
	}
	
	/**
	 * Sets the mine.
	 *
	 * @param _mine the new mine
	 */
	public void setMine(boolean _mine) {
		this._mine = _mine;
	}
	
	/**
	 * Checks if is t aufgedeckt.
	 *
	 * @return true, if is t aufgedeckt
	 */
	public boolean istAufgedeckt() {
		if(_zustand != FieldZustand.Offen) {
			if(_zustand == FieldZustand.Markiert && _mine)
				return true;
		} else {
			return true;
		}
		return false;
	}
	
	/**
	 * To string.
	 *
	 * @param showMines the show mines
	 * @return the string
	 */
	public String toString(boolean showMines) {
		if(showMines) {
			if(_mine) { return "X"; }
		}
		switch(_zustand) {
		case Markiert:
			return "M";
		case Offen:
			if(_mine) {
				return "X";
			} else {
				return Integer.toString(_proximity);
			}
		default:
			return " ";
		}
	}
}
