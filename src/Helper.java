/**
 * @author Gottfried Mayer
 * The Helper class with helper functions
 */
public class Helper {

	/// returns integer value within min and max values
	/**
	 * Constrain a number within min and max numbers
	 *
	 * @param val the value
	 * @param min the minimum value
	 * @param max the maximum value
	 * @return the return value
	 */
	public static int constrain(int val,int min,int max) {
		if(val < min) { return min; }
		if(val > max) { return max; }
		return val;
	}
}
