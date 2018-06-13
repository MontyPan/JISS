package us.dontcareabout.jiss.server;

import java.io.IOException;
import java.util.ArrayList;

import us.dontcareabout.gwt.server.GFServiceServlet;
import us.dontcareabout.jiss.client.RpcService;
import us.dontcareabout.jiss.server.project.ProjectCenter;
import us.dontcareabout.jiss.shared.Project;

public class RpcServiceImpl extends GFServiceServlet implements RpcService {
	private static final long serialVersionUID = 1L;

	@Override
	public void convertNewline(String path) throws Exception {
		Converter.newline(path);
	}

	@Override
	public ArrayList<Project> getProjects() {
		return ProjectCenter.getProjects();
	}

	@Override
	public void build(Project p) throws Exception {
		try {
			ProjectCenter.build(p);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
}
