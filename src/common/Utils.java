package common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Utils {
	public static Set<String> parseSet(String str) {
	    if (str == null || str.isEmpty()) return new HashSet<>();
	    return new HashSet<>(Arrays.asList(str.split(",")));
	}
}
