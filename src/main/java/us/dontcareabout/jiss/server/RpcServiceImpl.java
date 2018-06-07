package us.dontcareabout.jiss.server;

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
}
