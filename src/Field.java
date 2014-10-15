/**
 * @author Gottfried Mayer
 * Field class - one field on the game board.
 */
public class Field {
	
	/** number of mines in fields next to this */
	private int _proximity;
	
	/**
	 * Gets the proximity.
	 *
	 * @return number of mines in fields next to this
	 */
	public int getProximity() {
		return this._proximity;
	}
	
	/**
	 * Sets the proximity.
	 *
	 * @param new proximity
	 */
	public void setProximity(int proximity) {
		this._proximity = proximity;
	}
	
	/** The state (FieldZustand) of the field (see enum). */
	private FieldZustand _zustand = FieldZustand.Verdeckt;
	
	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public FieldZustand getZustand() {
		return this._zustand;
	}
	
	/**
	 * Sets the state.
	 *
	 * @param new field state
	 */
	public void setZustand(FieldZustand _zustand) {
		this._zustand = _zustand;
	}

	/** Is a Mine in this field? */
	private boolean _mine;
	
	/**
	 * Checks if a mine is in this field.
	 *
	 * @return true, if a mine is present
	 */
	public boolean istMine() {
		return _mine;
	}
	
	/**
	 * Sets the mine.
	 *
	 * @param to mine or not to mine...
	 */
	public void setMine(boolean _mine) {
		this._mine = _mine;
	}
	
	/**
	 * function to check if a field is uncovered (to check for game end state)
	 *
	 * @return true, if the field is uncovered
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
	 * string to represent the field state.
	 *
	 * @param showMines show all the mines (after game over)
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
