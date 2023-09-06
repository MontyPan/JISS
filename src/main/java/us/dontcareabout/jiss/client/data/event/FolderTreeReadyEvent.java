package us.dontcareabout.jiss.client.data.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import us.dontcareabout.jiss.client.data.event.FolderTreeReadyEvent.FolderTreeReadyHandler;
import us.dontcareabout.jiss.shared.FolderTree;

public class FolderTreeReadyEvent extends GwtEvent<FolderTreeReadyHandler> {
	public static final Type<FolderTreeReadyHandler> TYPE = new Type<FolderTreeReadyHandler>();

	public final FolderTree data;

	public FolderTreeReadyEvent(FolderTree data) {
		this.data = data;
	}

	@Override
	public Type<FolderTreeReadyHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(FolderTreeReadyHandler handler) {
		handler.onFolderTreeReady(this);
	}

	public interface FolderTreeReadyHandler extends EventHandler{
		public void onFolderTreeReady(FolderTreeReadyEvent event);
	}
}
