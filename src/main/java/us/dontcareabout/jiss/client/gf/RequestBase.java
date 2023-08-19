package us.dontcareabout.jiss.client.gf;

import com.github.nmorel.gwtjackson.client.ObjectMapper;
import com.github.nmorel.gwtjackson.client.ObjectReader;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

import us.dontcareabout.gwt.client.data.Callback;

public abstract class RequestBase<T> {
	//XXX 不知道塞哪裡，所以暫時放在這裡 XD
	public interface StringMapper extends ObjectMapper<String> {};
	public static StringMapper stringMapper = GWT.create(StringMapper.class);

	private String path;
	private ObjectReader<T> reader;
	private Callback<T> callback;
	private String data;

	protected abstract RequestBuilder build();

	public void send() {
		RequestBuilder builder = build();
		try {
			builder.sendRequest(data, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					callback.onSuccess(reader.read(response.getText()));
				}

				@Override
				public void onError(Request request, Throwable exception) {
					callback.onError(exception);
				}
			});
		} catch (RequestException exception) {
			callback.onError(exception);
		}
	}

	String getPath() {
		return path;
	}

	void setPath(String path) {
		this.path = path;
	}

	ObjectReader<T> getReader() {
		return reader;
	}

	void setReader(ObjectReader<T> reader) {
		this.reader = reader;
	}

	Callback<T> getCallback() {
		return callback;
	}

	void setCallback(Callback<T> callback) {
		this.callback = callback;
	}

	String getData() {
		return data;
	}

	void setData(String data) {
		this.data = data;
	}
}
