/*
 * 
 */
	import java.io.*;

	// TODO: Auto-generated Javadoc
/**
	 * The Class Highscore.
	 */
	public class Highscore implements Serializable
	{
		
		/** The Constant serialVersionUID. */
		private static final long serialVersionUID = 1L;
		
		/** The _level. */
		private int _level;
		private int _size;
		
		//Konstruktor
		/**
		 * Instantiates a new highscore.
		 *
		 * @param _level the _level
		 */
		public Highscore(int _level, int _size)
		{
			this._level = _level;
			this._size = _size;
			getHighscores();
			
		}
		
		//Wird aufgerufen wenn das File leer ist um exceptions vorzubeugen
		/**
		 * Initialize file.
		 */
		private void initializeFile()
		{
			HighscoreEntry[] h={new HighscoreEntry(99999," "),new HighscoreEntry(99999," "),new HighscoreEntry(99999," "),
					new HighscoreEntry(99999," "),new HighscoreEntry(99999," "),new HighscoreEntry(99999," "),
					new HighscoreEntry(99999," "),new HighscoreEntry(99999," "),new HighscoreEntry(99999," "),
					new HighscoreEntry(99999," ")};
			try 
			{
				System.out.println("Hi1");
				ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream("Highscores"+"_"+_level+"_"+_size+".dat"));
				o.writeObject(h);
				o.close();
			} catch (FileNotFoundException e) {e.printStackTrace();}
			catch (IOException e) {e.printStackTrace();}
		}
		
		//Liest die .dat Datei und gib die Konstante zurück
		/**
		 * Gets the highscores.
		 *
		 * @return the highscores
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
		
		//Adds a new Highscore to the .dat file and maintains the proper order
		/**
		 * Adds the highscore.
		 *
		 * @param h the h
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

		public void zeichnen() {
			HighscoreEntry[] entries = this.getHighscores();
			
		  ConsoleHelper.writeLine("         _       _                            ");
	      ConsoleHelper.writeLine("  /\\  /(_) __ _| |__  ___  ___ ___  _ __ ___"); 
	      ConsoleHelper.writeLine(" / /_/ / |/ _` | '_ \\/ __|/ __/ _ \\| '__/ _ \\");
	      ConsoleHelper.writeLine("/ __  /| | (_| | | | \\__ \\ (_| (_) | | |  __/");
	      ConsoleHelper.writeLine("\\/ /_/ |_|\\__, |_| |_|___/\\___\\___/|_|  \\___|");
	      ConsoleHelper.writeLine("          |___/                              ");

			
			int i = 1;
			for(HighscoreEntry e : entries)
			{
				if(e.getDateTime() != 99999)
				{
					if(i < 10)
						ConsoleHelper.writeLine(" " + i++ + ". " + e.getName() + " ---------- " + e.getDateTime());
					else
						ConsoleHelper.writeLine(i++ + ". " + e.getName() + " ---------- " + e.getDateTime());
				}
			}
			
		}
	}