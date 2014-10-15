/**
 * @author Gottfried Mayer
 * The game board class.
 */
public class Board {
	
	/**
	 * Instantiates a new board.
	 *
	 * @param size the board
	 * @param difficulty (percentage of mines in fields)
	 */
	public Board(int size, int difficulty)
	{
		setSize(size);
		setDifficulty(difficulty);
		// initialize all the fields
		_fields = new Field[_size][_size];
		for(int _i=0;_i<_size;_i++) {
			for(int _j=0;_j<_size;_j++) {
				_fields[_i][_j] = new Field();
			}
		}
		// calculate number of mines in field, make sure to have at least 1 mine and one free field
		this._anzahlMinen = Helper.constrain((int)Math.round(((double)_size * (double)_size / 100.0) * _difficulty),1,(_size * _size)-1);
		// place mines in random positions, make sure to not place two mines in same field
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
		// calculate proximity of game board
		for(int i=1;i<=_size;i++) {
			for(int j=1;j<=_size;j++) {
				_fields[i-1][j-1].setProximity(calcProximity(i,j));
			}
		}
	}
	
	/** The size. */
	private int _size;
	
	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize() {
		return this._size;
	}
	
	/**
	 * Sets the size.
	 *
	 * @param size the new size
	 */
	private void setSize(int size) {
		this._size = Helper.constrain(size,5,10);
	}

	/** The difficulty. */
	private int _difficulty;
	
	/**
	 * Gets the difficulty.
	 *
	 * @return the difficulty
	 */
	public int getDifficulty() {
		return this._difficulty;
	}
	
	/**
	 * Sets the difficulty.
	 *
	 * @param difficulty the new difficulty
	 */
	private void setDifficulty(int difficulty) {
		this._difficulty = Helper.constrain(difficulty,1,99);
	}
	
	/** The number of mines on the game board. */
	private int _anzahlMinen;
	
	/**
	 * Gets the number of mines.
	 *
	 * @return the number of mines
	 */
	public int getAnzahlMinen() {
		return this._anzahlMinen;
	}
	
	/**
	 * Gets the vertical field distance
	 *
	 * @return the distance between two neighbouring fields in the board (for drawing)
	 */
	public int getVerticalFieldDistance() {
		return 1;
	}
	
	/**
	 * Gets the horizontal field distance
	 *
	 * @return the distance between two neighbouring fields in the board (for drawing)
	 */
	public int getHorizontalFieldDistance() {
		return 4;
	}

	/**
	 * Gets horizontal starting position of field
	 * @return starting position of leftmost field
	 */
	public int getHorizontalFieldStart() {
		return (_size == 10) ? 5 : 4;
	}
	
	/** The fields of the board. */
	private Field[][] _fields;
	
	/**
	 * are all fields on the board uncovered?
	 *
	 * @return true, if all are uncovered (game end)
	 */
	public boolean alleFelderAufgedeckt() {
		for(int _x=0;_x<_size;_x++) {
			for(int _y=0;_y<_size;_y++) {
				if(!_fields[_x][_y].istAufgedeckt())
					return false;
			}
		}
		return true;
	}

	/**
	 * mark the field.
	 *
	 * @param x (1 to _size)
	 * @param y (1 to _size)
	 */
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
	
	/**
	 * uncover field.
	 *
	 * @param x (1 to _size)
	 * @param y (1 to _size)
	 * @return true, if successful, false if there was a mine
	 */
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
					if(x > 1) {
						aufdecken(x-1,y);               // nach links aufdecken
						if(y > 1) {
							aufdecken(x-1, y-1);		// nach links oben aufdecken
						}
						if(y < _size) {
							aufdecken(x-1, y+1);        // nach links unten aufdecken
						}
					}
					if(x < _size) {
						aufdecken(x+1,y);				// nach rechts aufdecken
						if(y > 1) {
							aufdecken(x+1,y-1);			// nach rechts oben aufdecken
						}
						if(y < _size) {
							aufdecken(x+1,y+1);			// nach rechts unten aufdecken
						}
					}
					if(y > 1) { aufdecken(x,y-1); }      // nach oben aufdecken
					if(y < _size) { aufdecken(x,y+1); }  // nach unten aufdecken
				}
			}
		}
		return true;
	}
	
	/**
	 * draw the game board.
	 *
	 * @param showMines to show all the mines (after game over)
	 */
	public void zeichnen(boolean showMines) {
		Console c = Console.getInstance();
		if(_size < 10) {  // set left border
			c.write(" ", 0, false);
		} else {
			c.write("  ", 0, false);
		}
		char _alph = 65;  // first line, draw A,B,C,etc...
		for(int i=0;i<_size;i++) {
			c.write(" | ", 0, false);
			c.write(""+(char)(_alph+i),12,false);
		}
		c.write("", 0, true);
		for(int _y=0;_y<_size;_y++) {  // write game board
			if(_y < 9 && _size >= 10) {  // left border
				c.write(" ",0,false);
			}
			c.write(""+(_y+1), 12, false);
			for(int _x=0;_x<_size;_x++) {
				c.write(" | ",0,false);
				c.write(_fields[_x][_y].toString(showMines), _fields[_x][_y].toInt(showMines), false);
			}

			c.write("", 0, true);
		}
	}
	
	
	/**
	 * Helper function to calc proximity.
	 *
	 * @param x (1 to _size)
	 * @param y (1 to _size)
	 * @return the number of mines in proximity
	 */
	private int calcProximity(int x, int y) {
		return calcProximity(x,y,false);
	}
	
	/**
	 * Helper function to calc proximity.
	 *
	 * @param x (1 to _size)
	 * @param y (1 to _size)
	 * @param stop do not call function recursively
	 * @return the number of mines in proximity
	 */
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
