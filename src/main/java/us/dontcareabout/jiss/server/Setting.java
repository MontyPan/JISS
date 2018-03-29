package us.dontcareabout.jiss.server;

import java.io.File;

import us.dontcareabout.java.common.DoubleProperties;

public class Setting extends DoubleProperties {
	private static File workspace;

	public Setting() {
		super("dev-setting.xml", "JIZZ.xml");
	}

	public File workspace() {
		if (workspace == null) {
			workspace = new File(getProperty("workspace"));
		}

		return workspace;
	}
}
