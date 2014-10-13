	import java.io.*;

	public class Highscore implements Serializable
	{
		private static final long serialVersionUID = 1L;
		private int _level;
		
		//Konstruktor
		public Highscore(int _level)
		{
			this._level = _level;
			getHighscores();
		}
		
		//Die setters und getters
		public void setLevel(int _level) 
		{
			this._level = _level;
		}

		public int getLevel() 
		{
			return _level;
		}

		
		//Wird aufgerufen wenn das File leer ist um exceptions vorzubeugen
		private static void initializeFile()
		{
			HighscoreEntry[] h={new HighscoreEntry(99999," "),new HighscoreEntry(99999," "),new HighscoreEntry(99999," "),
					new HighscoreEntry(99999," "),new HighscoreEntry(99999," "),new HighscoreEntry(99999," "),
					new HighscoreEntry(99999," "),new HighscoreEntry(99999," "),new HighscoreEntry(99999," "),
					new HighscoreEntry(99999," ")};
			try 
			{
				System.out.println("Hi1");
				ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream("Highscores.dat"));
				o.writeObject(h);
				o.close();
			} catch (FileNotFoundException e) {e.printStackTrace();}
			catch (IOException e) {e.printStackTrace();}
		}
		
		//Liest die .dat Datei und gib die Konstante zur�ck
		public HighscoreEntry[] getHighscores()
		{
			if (!new File("Highscores"+_level+".dat").exists())
				initializeFile();
			try 
			{
				ObjectInputStream o=new ObjectInputStream(new FileInputStream("Highscores"+_level+".dat"));
				HighscoreEntry[] h=(HighscoreEntry[]) o.readObject();
				o.close();
				return h;
			} catch (IOException e) {e.printStackTrace();} 
			catch (ClassNotFoundException e) {e.printStackTrace();}
			return null;
		}
		
		//Adds a new Highscore to the .dat file and maintains the proper order
		public boolean addHighscore(HighscoreEntry h)
		{
			boolean _success = false;
			
			HighscoreEntry[] HighscoresEntry=getHighscores();
			//HighscoresEntry[HighscoresEntry.length-1]=h;
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
				ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream("Highscores"+_level+".dat"));
				o.writeObject(HighscoresEntry);
				o.close();
			} catch (FileNotFoundException e) {e.printStackTrace();} 
			catch (IOException e) {e.printStackTrace();}
			
			return _success;
		}
	}