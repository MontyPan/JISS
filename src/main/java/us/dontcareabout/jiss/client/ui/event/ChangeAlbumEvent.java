package us.dontcareabout.jiss.client.ui.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import us.dontcareabout.jiss.client.ui.event.ChangeAlbumEvent.ChangeAlbumHandler;

public class ChangeAlbumEvent extends GwtEvent<ChangeAlbumHandler> {
	public static final Type<ChangeAlbumHandler> TYPE = new Type<ChangeAlbumHandler>();

	public final String data;

	public ChangeAlbumEvent(String data) {
		this.data = data;
	}

	@Override
	public Type<ChangeAlbumHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ChangeAlbumHandler handler) {
		handler.onChangeAlbum(this);
	}

	public interface ChangeAlbumHandler extends EventHandler{
		public void onChangeAlbum(ChangeAlbumEvent event);
	}
}
