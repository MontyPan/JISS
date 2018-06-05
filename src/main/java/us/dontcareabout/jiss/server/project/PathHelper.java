package us.dontcareabout.jiss.server.project;

import java.io.File;

import us.dontcareabout.java.common.Paths;
import us.dontcareabout.jiss.shared.Project;

public class PathHelper {
	private static final String[] JAVA_SRC_ROOT = {"src", "main", "java"};

	public static File packageFolder(Project project, String subPackage) {
		return javaBasePath(project)
			.append(subPackage.split("\\."))
			.existFolder()
			.toFile();
	}

	private static Paths javaBasePath(Project project) {
		return new Paths(project.getPath())
			.append(JAVA_SRC_ROOT)
			.append(pkgToStr(project.getRootPackage()));
	}

	private static String[] pkgToStr(String pkg) {
		return pkg.split("\\.");
	}
}