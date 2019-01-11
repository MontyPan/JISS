package us.dontcareabout.jiss.server.project;

import java.io.File;

import com.google.common.base.Preconditions;

import org.apache.maven.model.Model;

import us.dontcareabout.java.common.Paths;
import us.dontcareabout.jiss.shared.Project;

public class PathHelper {
	private static final String[] JAVA_SRC_ROOT = {"src", "main", "java"};
	private static final String[] WEBAPP = {"src", "main", "webapp"};

	public static File packageFolder(Project project, String subPackage) {
		return javaBasePath(project)
			.append(pkgToStr(subPackage))
			.existFolder()
			.toFile();
	}

	public static Paths webappPaths(Project project) {
		return new Paths(project.getPath()).append(WEBAPP).existFolder();
	}

	public static File webappFolder(Project project) {
		return webappPaths(project).toFile();
	}

	public static File targetWarFolder(Project project) {
		Model model = Maven.pom(project);
		Preconditions.checkNotNull(model);
		return new Paths(project.getPath()).append("target")
			.append(model.getArtifactId() + "-" + model.getVersion()).toFile();
	}

	public static File javaFile(Project project, String subPackage, String className) {
		return new File(PathHelper.packageFolder(project, subPackage), className + ".java");
	}

	public static File gwtXml(Project project) {
		//過去是找出 javaBasePath() 底下的所有 gwt.xml
		//後來認為沒必要，因為都傳 project 進來就表示要找的是符合命名慣例
		return new File(javaBasePath(project).existFolder().toFile(), project.getName() + ".gwt.xml");
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