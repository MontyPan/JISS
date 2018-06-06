package us.dontcareabout.jiss.server.project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;

import freemarker.template.Configuration;
import us.dontcareabout.java.common.Paths;
import us.dontcareabout.jiss.server.Setting;
import us.dontcareabout.jiss.server.Util;
import us.dontcareabout.jiss.shared.Project;

public class ProjectCenter {
	private static final Gson gson = new Gson();
	private static final Setting SETTING = new Setting();
	private static final File PROJECT_DIR;
	static {
		PROJECT_DIR = new Paths(SETTING.workspace()).append("project").existFolder().toFile();
	}

	public static ArrayList<Project> getProjects() {
		ArrayList<Project> result = new ArrayList<>();

		for (File file : PROJECT_DIR.listFiles()) {
			result.add(Util.parseJson(file, Project.class));
		}

		return result;
	}

	public static void save(Project project) {
		File file = new File(PROJECT_DIR, project.getName());

		try {
			Files.write(file.toPath(), gson.toJson(project).getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// ==== 自動化區 ==== //
	public static void build(Project project) throws IOException {
		String gwtModule = GwtHelper.moduleName(project);
		File jsFolder = new File(PathHelper.webappFolder(project), gwtModule);
		ArrayList<File> error = new ArrayList<>();

		//先刪除 webapp 下的 GWT compile 結果
		Util.deleteFolder(jsFolder, error);
		Maven.install(new File(project.getPath()));

		//把 GWT compile 結果塞回 webapp 底下
		Util.copyFolder(
			new File(PathHelper.targetWarFolder(project), gwtModule),
			jsFolder,
			error
		);

		//目前不打算理會 error... XD
	}
	// ======== //

	// ==== FreeMarker 區 ==== //
	private static final Configuration ftlConfig = new Configuration();

	static {
		ftlConfig.setClassForTemplateLoading(ProjectCenter.class, "");
	}

	public static void genDataEvent(Project project, String eventName) throws Exception {
		final String subPackage = "client.data";
		HashMap<String, Object> data = new HashMap<>();
		data.put("project", project);
		data.put("subPackage", subPackage);
		data.put("eventName", eventName);
		File packageFolder = PathHelper.packageFolder(project, subPackage);

		gen(
			"DataEvent.ftl",
			data,
			new File(packageFolder, eventName + "Event.java")
		);
	}

	private static void gen(String ftlName, HashMap<String, Object> data, File target) throws Exception {
		ftlConfig.getTemplate(ftlName).process(data, new FileWriter(target));
	}
	// ======== //
}
