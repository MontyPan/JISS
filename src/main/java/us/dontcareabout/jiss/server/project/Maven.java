package us.dontcareabout.jiss.server.project;

import java.io.File;
import java.io.IOException;

import com.google.common.io.ByteStreams;

public class Maven {
	//用 File.separator 有點無聊，因為不是 Windows 上也沒辦法用 bat 吧 XDDD
	private static final String MVN = System.getenv("MAVEN_HOME") + File.separator + "bin" + File.separator + "mvn.bat";
	private static final String[] INSTALL = {MVN, "install"};

	public static String install(File folder) throws IOException {
		return run(folder, INSTALL);
	}

	private static String run(File folder, String[] cmd) throws IOException {
		Process p = new ProcessBuilder(cmd).directory(folder).start();
		return new String(ByteStreams.toByteArray(p.getInputStream()));
	}
}