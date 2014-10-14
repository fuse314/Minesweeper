// TODO: Auto-generated Javadoc
// helper functions
/**
 * The Class Helper.
 */
public class Helper {

	/// returns integer value within min and max values
	/**
	 * Constrain.
	 *
	 * @param val the val
	 * @param min the min
	 * @param max the max
	 * @return the int
	 */
	public static int constrain(int val,int min,int max) {
		if(val < min) { return min; }
		if(val > max) { return max; }
		return val;
	}
}
