package ${project.rootPackage}.${subPackage};

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

import ${project.rootPackage}.${subPackage}.${eventName}Event.${eventName}Handler;

public class ${eventName}Event extends GwtEvent<${eventName}Handler> {
	public static final Type<${eventName}Handler> TYPE = new Type<${eventName}Handler>();

	@Override
	public Type<${eventName}Handler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(${eventName}Handler handler) {
		handler.on${eventName}(this);
	}

	public interface ${eventName}Handler extends EventHandler{
		public void on${eventName}(${eventName}Event event);
	}
}