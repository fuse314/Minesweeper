import com.googlecode.lanterna.terminal.Terminal;

/**
 * contains all necessary information to draw a single field
 * @author Maurus KŸhne
 *
 */
public class FieldDrawingInformation {

	private String _character;
	private Terminal.Color _color;
	
	public FieldDrawingInformation(String character, Terminal.Color color)
	{
		_character = character;
		_color = color;
	}

	/**
	 * @return the color
	 */
	public Terminal.Color getColor() {
		return _color;
	}

	/**
	 * @return the character
	 */
	public String getCharacter() {
		return _character;
	}
}
