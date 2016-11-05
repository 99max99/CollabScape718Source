package org.dawn.tools.map.viewer.util;

import com.thoughtworks.xstream.XStream;

public class XStreamUtil {

	private static XStream xstream = new XStream() {{
		alias("selection", org.dawn.tools.map.viewer.Selection.class);
	}};

	public static XStream getXStream() {
		return xstream;
	}
	
}
