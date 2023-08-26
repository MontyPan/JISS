package us.dontcareabout.jiss.client.data.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import us.dontcareabout.jiss.client.data.event.DataUriImageReadyEvent.DataUriImageReadyHandler;

public class DataUriImageReadyEvent extends GwtEvent<DataUriImageReadyHandler> {
	public static final Type<DataUriImageReadyHandler> TYPE = new Type<DataUriImageReadyHandler>();

	public final String data;

	public DataUriImageReadyEvent(String img) {
		this.data = img;
	}

	@Override
	public Type<DataUriImageReadyHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(DataUriImageReadyHandler handler) {
		handler.onAlbumImageReady(this);
	}

	public interface DataUriImageReadyHandler extends EventHandler{
		public void onAlbumImageReady(DataUriImageReadyEvent event);
	}
}
