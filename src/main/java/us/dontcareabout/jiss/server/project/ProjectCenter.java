package us.dontcareabout.jiss.server.project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.Gson;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

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

	/**
	 * 執行 {@link #build(Project)}，並將 GWT 產出複製到 CDN 的目錄下，
	 * 然後 git push 到 remote repo 上。
	 */
	public static void deployGwtToCdn(Project project, File cdnRoot, String gitUser, String gitPW)
			throws IOException, CheckoutConflictException, GitAPIException {
		build(project);

		File cdnFolder = new File(cdnRoot, project.getName());
		String gwtModule = GwtHelper.moduleName(project);
		ArrayList<File> error = new ArrayList<>();
		Util.deleteFolder(cdnFolder, error);
		Util.copyFolder(
			new File(PathHelper.targetWarFolder(project), gwtModule),
			cdnFolder,
			error
		);

		CredentialsProvider cp = new UsernamePasswordCredentialsProvider(gitUser, gitPW);
		File local = new File(cdnRoot, ".git");
		Repository repo = new FileRepository(local);
		ObjectId oid = repo.resolve("HEAD^");
		String gitMessage = "[" + project.getName() + "] " + new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date());

		Git git = new Git(repo);
		git.reset().setRef(oid.getName()).call();
		git.add().addFilepattern(".").call();
		git.commit().setAll(true).setMessage(gitMessage).call();
		git.push().setCredentialsProvider(cp).setForce(true).call();
		git.close();
	}
	// ======== //

	// ==== FreeMarker 區 ==== //
	private static final Configuration ftlConfig = new Configuration();

	static {
		ftlConfig.setClassForTemplateLoading(ProjectCenter.class, "");
	}

	public static void init(Project project) throws Exception {
		HashMap<String, Object> data = new HashMap<>();
		data.put("project", project);
		data.put("groupName", "us.dontcareabout.app");	//XXX 抽出去？

		gen("gwt.xml.ftl", data, PathHelper.gwtXml(project));
		gen("EntryPoint.ftl", data, PathHelper.javaFile(project, "client", project.getName() + "EP"));
		gen("webapp/index.html.ftl", data, new File(PathHelper.webappFolder(project), "index.html"));
		gen("webapp/web.xml.ftl", data, new File(PathHelper.webappPaths(project).append("WEB-INF").toFile(), "web.xml"));
		//擺在最後面，因為前面會幫忙建立根目錄
		gen("pom.xml.ftl", data, new File(project.getPath(), "pom.xml"));
	}

	public static void genDataEvent(Project project, String eventName) throws Exception {
		final String subPackage = "client.data";
		HashMap<String, Object> data = new HashMap<>();
		data.put("project", project);
		data.put("subPackage", subPackage);
		data.put("eventName", eventName);

		gen(
			"DataEvent.ftl",
			data,
			PathHelper.javaFile(project, subPackage, eventName + "Event")
		);
	}

	private static void gen(String ftlName, HashMap<String, Object> data, File target) throws Exception {
		ftlConfig.getTemplate(ftlName).process(data, new FileWriter(target));
	}
	// ======== //
}
