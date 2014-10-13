
public class Field {
	
	private int _proximity;
	public int getProximity() {
		return this._proximity;
	}
	public void setProximity(int proximity) {
		this._proximity = proximity;
	}
	
	private FieldZustand _zustand;
	public FieldZustand getZustand() {
		return this._zustand;
	}
	public void setZustand(FieldZustand _zustand) {
		this._zustand = _zustand;
	}

	private boolean _bombe;
	public boolean isBombe() {
		return _bombe;
	}
	public void setBombe(boolean _bombe) {
		this._bombe = _bombe;
	}
	
	public String toString(boolean showBombs) {
		if(showBombs) {
			if(_bombe) { return "X"; }
		}
		switch(_zustand) {
		case Markiert:
			return "M";
		case Offen:
			return Integer.toString(_proximity);
		default:
			return " ";
		}
	}
}
