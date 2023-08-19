package us.dontcareabout.jiss.client.gf;

import com.github.nmorel.gwtjackson.client.ObjectReader;
import com.google.gwt.http.client.RequestBuilder;

import us.dontcareabout.gwt.client.data.Callback;

//Refactory GF
//XXX 考慮提供方便組 query string 的 method？
public class GetRequest<T> extends RequestBase<T> {
	public GetRequest<T> path(String path) {
		setPath(path);
		return this;
	}

	public GetRequest<T> reader(ObjectReader<T> reader) {
		setReader(reader);
		return this;
	}

	public GetRequest<T> callback(Callback<T> callback) {
		setCallback(callback);
		return this;
	}

	@Override
	protected RequestBuilder build() {
		return new RequestBuilder(RequestBuilder.GET, getPath());
	}
}