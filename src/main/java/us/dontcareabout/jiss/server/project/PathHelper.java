package us.dontcareabout.jiss.server.project;

import java.io.File;
import java.io.FileNotFoundException;

import us.dontcareabout.java.common.Paths;
import us.dontcareabout.jiss.shared.Project;

public class PathHelper {
	private static final String[] JAVA_SRC_ROOT = {"src", "main", "java"};

	public static File packageFolder(Project project, String subPackage) {
		return javaBasePath(project)
			.append(pkgToStr(subPackage))
			.existFolder()
			.toFile();
	}

	//XXX 前提假設：需要 compile 的 gwt.xml 會在 project 的 root package 下
	public static File gwtXml(Project project) throws FileNotFoundException {
		File javaBase = javaBasePath(project).toFile();

		for (File file : javaBase.listFiles()) {
			if (file.isDirectory()) { continue; }

			if (file.getName().endsWith(".gwt.xml")) {
				return file;
			}
		}

		throw new FileNotFoundException();
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