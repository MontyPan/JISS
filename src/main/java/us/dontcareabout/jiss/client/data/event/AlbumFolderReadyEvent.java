package us.dontcareabout.jiss.client.data.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import us.dontcareabout.jiss.client.data.event.AlbumFolderReadyEvent.AlbumFolderReadyHandler;
import us.dontcareabout.jiss.shared.AlbumFolder;

public class AlbumFolderReadyEvent extends GwtEvent<AlbumFolderReadyHandler> {
	public static final Type<AlbumFolderReadyHandler> TYPE = new Type<AlbumFolderReadyHandler>();

	public final AlbumFolder data;

	public AlbumFolderReadyEvent(AlbumFolder data) {
		this.data = data;
	}

	@Override
	public Type<AlbumFolderReadyHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(AlbumFolderReadyHandler handler) {
		handler.onAlbumFolderReady(this);
	}

	public interface AlbumFolderReadyHandler extends EventHandler{
		public void onAlbumFolderReady(AlbumFolderReadyEvent event);
	}
}
