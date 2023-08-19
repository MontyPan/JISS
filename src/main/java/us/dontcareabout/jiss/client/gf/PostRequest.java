package us.dontcareabout.jiss.client.gf;

import com.github.nmorel.gwtjackson.client.ObjectReader;
import com.google.gwt.http.client.RequestBuilder;

import us.dontcareabout.gwt.client.data.Callback;

//Refactory GF
/**
 * request 的 content type 會預設為 <code>application/json</code>
 *
 * @param <T> 回傳值的資料型態
 */
public class PostRequest<T> extends RequestBase<T> {
	public PostRequest<T> path(String path) {
		setPath(path);
		return this;
	}

	public PostRequest<T> reader(ObjectReader<T> reader) {
		setReader(reader);
		return this;
	}

	public PostRequest<T> callback(Callback<T> callback) {
		setCallback(callback);
		return this;
	}

	public PostRequest<T> data(String data) {
		setData(data);
		return this;
	}

	@Override
	protected RequestBuilder build() {
		RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, getPath());
		builder.setHeader("Content-Type", "application/json");
		return builder;
	}
}
