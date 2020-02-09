package us.dontcareabout.jiss.server.project;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import com.google.common.io.ByteStreams;

import us.dontcareabout.java.common.Paths;
import us.dontcareabout.jiss.shared.Project;

public class Maven {
	//用 File.separator 有點無聊，因為不是 Windows 上也沒辦法用 bat 吧 XDDD
	private static final String MVN = System.getenv("MAVEN_HOME") + File.separator + "bin" + File.separator + "mvn.cmd";
	private static final String[] INSTALL = {MVN, "install"};

	public static Model pom(Project project) {
		try {
			return new MavenXpp3Reader().read(
				new FileReader(new Paths(project.getPath()).append("pom.xml").toFile())
			);
		} catch (Exception e) {
			return null;
		}
	}

	public static String install(File folder) throws IOException {
		return run(folder, INSTALL);
	}

	private static String run(File folder, String[] cmd) throws IOException {
		Process p = new ProcessBuilder(cmd).directory(folder).start();
		return new String(ByteStreams.toByteArray(p.getInputStream()));
	}
}
