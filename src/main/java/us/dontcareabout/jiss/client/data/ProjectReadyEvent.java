package us.dontcareabout.jiss.client.data;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import us.dontcareabout.jiss.client.data.ProjectReadyEvent.ProjectReadyHandler;

public class ProjectReadyEvent extends GwtEvent<ProjectReadyHandler> {
	public static final Type<ProjectReadyHandler> TYPE = new Type<ProjectReadyHandler>();

	@Override
	public Type<ProjectReadyHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ProjectReadyHandler handler) {
		handler.onProjectReady(this);
	}

	public interface ProjectReadyHandler extends EventHandler{
		public void onProjectReady(ProjectReadyEvent event);
	}
}
