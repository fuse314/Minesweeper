// helper functions
public class Helper {

	/// returns integer value within min and max values
	public static int constrain(int val,int min,int max) {
		if(val < min) { return min; }
		if(val > max) { return max; }
		return val;
	}
}
