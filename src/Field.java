
public class Field {
	
	private int _proximity;
	public int getProximity() {
		return this._proximity;
	}
	public void setProximity(int proximity) {
		this._proximity = proximity;
	}
	
	private FieldZustand _zustand = FieldZustand.Verdeckt;
	public FieldZustand getZustand() {
		return this._zustand;
	}
	public void setZustand(FieldZustand _zustand) {
		this._zustand = _zustand;
	}

	private boolean _mine;
	public boolean istMine() {
		return _mine;
	}
	public void setMine(boolean _mine) {
		this._mine = _mine;
	}
	
	public boolean istAufgedeckt() {
		if(_zustand != FieldZustand.Offen)
			if(_zustand == FieldZustand.Markiert && _mine)
				return true;
		return false;
	}
	
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
