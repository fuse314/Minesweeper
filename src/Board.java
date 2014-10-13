
public class Board {
	
	public Board(int size, int difficulty)
	{
		setSize(size);
		setDifficulty(difficulty);
		_fields = new Field[_size][_size];
		for(int _i=0;_i<_size;_i++) {
			for(int _j=0;_j<_size;_j++) {
				_fields[_i][_j] = new Field();
			}
		}
		
		this._anzahlMinen = (int)Math.round(((double)_size * (double)_size / 100.0) * _difficulty);
		ConsoleHelper.writeLine("" + _anzahlMinen);
		for(int k=0;k<this._anzahlMinen;k++) {
			boolean _success = false;
			do {
				int _x = (int)(Math.random() * _size);
				int _y = (int)(Math.random() * _size);
				_x = Helper.constrain(_x,0,_size-1);
				_y = Helper.constrain(_y, 0, _size-1);
				if(!_fields[_x][_y].istMine()) {
					_fields[_x][_y].setMine(true);
					_success = true;
				}
			}
			while(_success==false);
		}
		for(int i=1;i<=_size;i++) {
			for(int j=1;j<=_size;j++) {
				_fields[i-1][j-1].setProximity(calcProximity(i,j));
			}
		}
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
	private int _anzahlMinen;
	public int getAnzahlMinen() {
		return this._anzahlMinen;
	}
	

	private Field[][] _fields;
	
	public boolean alleFelderAufgedeckt() {
		for(int _x=0;_x<_size;_x++) {
			for(int _y=0;_y<_size;_y++) {
				if(!_fields[_x][_y].istAufgedeckt())
					return false;
			}
		}
		return true;
	}

	public void markieren(int x, int y) {
		x = Helper.constrain(x,1,_size);
		y = Helper.constrain(y,1,_size);
		FieldZustand _aktZustand = _fields[x-1][y-1].getZustand();
		if(_aktZustand != FieldZustand.Offen) {
			if(_aktZustand == FieldZustand.Verdeckt) {
				_fields[x-1][y-1].setZustand(FieldZustand.Markiert);
			} else {
				_fields[x-1][y-1].setZustand(FieldZustand.Verdeckt);
			}
		}
	}
	
	// returns true if successful, false if GAME OVER
	public boolean aufdecken(int x, int y) {
		x = Helper.constrain(x,1,_size);
		y = Helper.constrain(y, 1, _size);
		Field _aktField = _fields[x-1][y-1];
		if(_aktField.getZustand() != FieldZustand.Offen) {
			if(_aktField.istMine()) {
				_aktField.setZustand(FieldZustand.Offen);
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
	
	public void zeichnen(boolean showMines) {
		String _line;
		if(_size < 10) {
			_line = " ";
		} else {
			_line = "  ";
		}
		char _alph = 65;
		for(int i=0;i<_size;i++) {
			_line += " | " + (char)(_alph+i);
		}
		ConsoleHelper.writeLine(_line);
		for(int _y=0;_y<_size;_y++) {
			if(_y < 9 && _size >= 10) {
				_line = " ";
			} else {
				_line = "";
			}
			_line += "" + (_y+1);
			for(int _x=0;_x<_size;_x++) {
				_line += " | " + _fields[_x][_y].toString(showMines);
			}
			ConsoleHelper.writeLine(_line);
		}
	}
	
	
	private int calcProximity(int x, int y) {
		return calcProximity(x,y,false);
	}
	
	private int calcProximity(int x, int y, boolean stop) {
		if(x < 1 || x > _size || y < 1 || y > _size)
			return 0;
		if(_fields[x-1][y-1].istMine())
			return 1;
		if(stop)
			return 0;
		int _prox = 0;
		_prox += calcProximity(x-1,y,true);   // left
		_prox += calcProximity(x-1,y-1,true); // top left
		_prox += calcProximity(x,y-1,true);   // top
		_prox += calcProximity(x+1,y-1,true); // top right
		_prox += calcProximity(x+1,y,true);   // right
		_prox += calcProximity(x+1,y+1,true); // bottom right
		_prox += calcProximity(x,y+1,true);   // bottom
		_prox += calcProximity(x-1,y+1,true); // bottom left
		return _prox;
	}
}
