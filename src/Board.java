
public class Board {
	
	public Board(int size, int difficulty)
	{
		setSize(size);
		setDifficulty(difficulty);
		_fields = new Field[_size][_size];
		
	}
	
	private int _size;
	public int getSize() {
		return this._size;
	}
	private void setSize(int size) {
		this._size = Helper.constrain(size,5,10);
	}

	private int _difficulty;
	public int getDifficulty() {
		return this._difficulty;
	}
	private void setDifficulty(int difficulty) {
		this._difficulty = Helper.constrain(difficulty,1,99);
	}

	private Field[][] _fields;
	
	
	public void markieren(int x, int y) {
		x = Helper.constrain(x,1,_size);
		y = Helper.constrain(y,1,_size);
		FieldZustand _aktZustand = _fields[x][y].getZustand();
		if(_aktZustand != FieldZustand.Offen) {
			if(_aktZustand == FieldZustand.Verdeckt) {
				_fields[x][y].setZustand(FieldZustand.Markiert);
			} else {
				_fields[x][y].setZustand(FieldZustand.Verdeckt);
			}
		}
	}
	
	// returns true if successful, false if GAME OVER
	public boolean aufdecken(int x, int y) {
		x = Helper.constrain(x,1,_size);
		y = Helper.constrain(y, 1, _size);
		Field _aktField = _fields[x-1][y-1];
		if(_aktField.getZustand() != FieldZustand.Offen) {
			if(_aktField.isBombe()) {
				return false;   // game over. you are dead.
			} else {
				_aktField.setZustand(FieldZustand.Offen);
				if(_aktField.getProximity() == 0) {  // keine Zahl, somit rekursive Suche bis Rand oder Zahl
					if(x > 1) { aufdecken(x-1,y); }      // nach links aufdecken
					if(x < _size) { aufdecken(x+1,y); }  // nach rechts aufdecken
					if(y > 1) { aufdecken(x,y-1); }      // nach oben aufdecken
					if(y < _size) { aufdecken(x,y+1); }  // nach unten aufdecken
				}
			}
		}
		return true;
	}

}
