import java.io.*;

	/**
	 * @author: Elias 
	 * The Class Highscore. Maintains the highscore order and saves it to a file
	 */
	public class Highscore implements Serializable
	{
		private static final long serialVersionUID = 1L;

		private int _level;
		private int _size;

		/**
		 * Instantiates a new highscore table.
		 *
		 * @param level the difficulty level
		 * @param size the size of the board
		 */
		public Highscore(int level, int size)
		{
			this._level = level;
			this._size = size;
			getHighscores();

		}

		/**
		 * Initializes the highscore file.
		 */
		private void initializeFile()
		{
			HighscoreEntry[] h={new HighscoreEntry(99999," "),new HighscoreEntry(99999," "),new HighscoreEntry(99999," "),
					new HighscoreEntry(99999," "),new HighscoreEntry(99999," "),new HighscoreEntry(99999," "),
					new HighscoreEntry(99999," "),new HighscoreEntry(99999," "),new HighscoreEntry(99999," "),
					new HighscoreEntry(99999," ")};
			try
			{
				ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream("Highscores"+"_"+_level+"_"+_size+".dat"));
				o.writeObject(h);
				o.close();
			} catch (FileNotFoundException e) {e.printStackTrace();}
			catch (IOException e) {e.printStackTrace();}
		}

		/**
		 * Gets the highscore entries.
		 *
		 * @return the highscore entries
		 */
		public HighscoreEntry[] getHighscores()
		{
			if (!new File("Highscores"+"_"+_level+"_"+_size+".dat").exists())
				initializeFile();
			try
			{
				ObjectInputStream o=new ObjectInputStream(new FileInputStream("Highscores"+"_"+_level+"_"+_size+".dat"));
				HighscoreEntry[] h=(HighscoreEntry[]) o.readObject();
				o.close();
				return h;
			} catch (IOException e) {e.printStackTrace();}
			catch (ClassNotFoundException e) {e.printStackTrace();}
			return null;
		}

		/**
		 * tries to add a new highscore entry
		 *
		 * @param h the highscore entry to add
		 * @return true, if successful
		 */
		public boolean addHighscore(HighscoreEntry h)
		{
			boolean _success = false;

			HighscoreEntry[] HighscoresEntry=getHighscores();
			for (int i=0; i<HighscoresEntry.length-1; i++)
			{
				if (h.compareTo(HighscoresEntry[i])<0)
				{
					for(int j=HighscoresEntry.length-1;j > i;j--) {
						HighscoresEntry[j]=HighscoresEntry[j-1];
					}
					HighscoresEntry[i] = h;
					_success = true;
					break;
				}
			}
			try
			{
				ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream("Highscores"+"_"+_level+"_"+_size+".dat"));
				o.writeObject(HighscoresEntry);
				o.close();
			} catch (FileNotFoundException e) {e.printStackTrace();}
			catch (IOException e) {e.printStackTrace();}

			return _success;
		}

		/**
		 * draws the highscores into the console
		 * 
		 * @param blinkingEntry the entry that should be displayed blinking 
		 */
		public void zeichnen(HighscoreEntry blinkingEntry) {
			HighscoreEntry[] entries = this.getHighscores();
			Console c = Console.getInstance();
			boolean hasHighscores = false;

			for(HighscoreEntry e : entries)
			{
				if(e.getDateTime() != 99999)
				{
					hasHighscores = true;
					break;
				}
			}
			
			if(hasHighscores)
			{
				c.writeLine("        _       _                            ");
				c.writeLine("  /\\  /(_) __ _| |__  ___  ___ ___  _ __ ___");
				c.writeLine(" / /_/ / |/ _` | '_ \\/ __|/ __/ _ \\| '__/ _ \\");
				c.writeLine("/ __  /| | (_| | | | \\__ \\ (_| (_) | | |  __/");
				c.writeLine("\\/ /_/ |_|\\__, |_| |_|___/\\___\\___/|_|  \\___|");
				c.writeLine("          |___/                              ");
				c.writeLine("");
			}
			
			int i = 1;
			for(HighscoreEntry e : entries)
			{
				if(e.getDateTime() != 99999)
				{
					if(i < 10)
						c.writeLine(" " + i++ + ". " + e.getName() + " ---------- " + e.getDateTime());
					else
						c.writeLine(i++ + ". " + e.getName() + " ---------- " + e.getDateTime());
				}
			}

		}
	}
