	import java.io.*;

	public class Highscore implements Serializable
	{
		private static final long serialVersionUID = 1L;
		private int _level;
		
		//Konstruktor
		public Highscore(int _level)
		{
			this._level = _level;
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
			HighscoreEntry[] h={new HighscoreEntry(0," "),new HighscoreEntry(0," "),new HighscoreEntry(0," "),
					new HighscoreEntry(0," "),new HighscoreEntry(0," "),new HighscoreEntry(0," "),
					new HighscoreEntry(0," "),new HighscoreEntry(0," "),new HighscoreEntry(0," "),
					new HighscoreEntry(0," ")};
			try 
			{
				System.out.println("Hi1");
				ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream("Highscores.dat"));
				o.writeObject(h);
				o.close();
			} catch (FileNotFoundException e) {e.printStackTrace();}
			catch (IOException e) {e.printStackTrace();}
		}
		
		//Liest die .dat Datei und gib die Konstante zurŸck
		public static HighscoreEntry[] getHighscores()
		{
			if (!new File("Highscores.dat").exists())
				initializeFile();
			try 
			{
				ObjectInputStream o=new ObjectInputStream(new FileInputStream("Highscores.dat"));
				HighscoreEntry[] h=(HighscoreEntry[]) o.readObject();
				o.close();
				return h;
			} catch (IOException e) {e.printStackTrace();} 
			catch (ClassNotFoundException e) {e.printStackTrace();}
			return null;
		}
		
		//Adds a new Highscore to the .dat file and maintains the proper order
		public static void addHighscore(HighscoreEntry h)
		{
			HighscoreEntry[] HighscoresEntry=getHighscores();
			HighscoresEntry[HighscoresEntry.length-1]=h;
			for (int i=HighscoresEntry.length-2; i>=0; i--)
			{
				if (HighscoresEntry[i+1].compareTo(HighscoresEntry[i])>0)
				{
					HighscoreEntry temp=HighscoresEntry[i];
					HighscoresEntry[i]=HighscoresEntry[i+1];
					HighscoresEntry[i+1]=temp;
				}
			}
			try 
			{
				ObjectOutputStream o=new ObjectOutputStream(new FileOutputStream("Highscores.dat"));
				o.writeObject(HighscoresEntry);
				o.close();
			} catch (FileNotFoundException e) {e.printStackTrace();} 
			catch (IOException e) {e.printStackTrace();}
		}
	}