package us.dontcareabout.jiss.client.data;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;

import us.dontcareabout.jiss.client.RpcService;
import us.dontcareabout.jiss.client.RpcServiceAsync;
import us.dontcareabout.jiss.client.data.ProjectReadyEvent.ProjectReadyHandler;
import us.dontcareabout.jiss.shared.Project;

public class DataCenter {
	private final static SimpleEventBus eventBus = new SimpleEventBus();
	private final static RpcServiceAsync rpc = GWT.create(RpcService.class);

	private static ArrayList<Project> projects;
	public static ArrayList<Project> getProjects() {
		return projects;
	}

	public static void wantProject() {
		rpc.getProjects(new AsyncCallback<ArrayList<Project>>() {
			@Override
			public void onSuccess(ArrayList<Project> result) {
				projects = result;
				eventBus.fireEvent(new ProjectReadyEvent());
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		});
	}

	public static HandlerRegistration addProjectReady(ProjectReadyHandler handler) {
		return eventBus.addHandler(ProjectReadyEvent.TYPE, handler);
	}
}
