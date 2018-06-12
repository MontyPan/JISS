package us.dontcareabout.jiss.client.gf;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.HandlerRegistration;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.event.StoreAddEvent;
import com.sencha.gxt.data.shared.event.StoreClearEvent;
import com.sencha.gxt.data.shared.event.StoreDataChangeEvent;
import com.sencha.gxt.data.shared.event.StoreFilterEvent;
import com.sencha.gxt.data.shared.event.StoreHandlers;
import com.sencha.gxt.data.shared.event.StoreRecordChangeEvent;
import com.sencha.gxt.data.shared.event.StoreRemoveEvent;
import com.sencha.gxt.data.shared.event.StoreSortEvent;
import com.sencha.gxt.data.shared.event.StoreUpdateEvent;

import us.dontcareabout.gxt.client.draw.LayerSprite;

//TODO 搬去 GF
public abstract class StoreLayerSprite<T> extends LayerSprite {
	private ListStore<T> store;
	private boolean deferred = false;
	private HandlerRegistration storeHR;
	private StoreHandlers<T> storeHandlers = new StoreHandlers<T>() {
		@Override
		public void onAdd(StoreAddEvent<T> event) {
			_render();
		}

		@Override
		public void onRemove(StoreRemoveEvent<T> event) {
			_render();
		}

		@Override
		public void onFilter(StoreFilterEvent<T> event) {
			_render();
		}

		@Override
		public void onClear(StoreClearEvent<T> event) {
			_render();
		}

		@Override
		public void onUpdate(StoreUpdateEvent<T> event) {
			_render();
		}

		@Override
		public void onDataChange(StoreDataChangeEvent<T> event) {
			_render();
		}

		@Override
		public void onRecordChange(StoreRecordChangeEvent<T> event) {
			_render();
		}

		@Override
		public void onSort(StoreSortEvent<T> event) {
			_render();
		}
	};

	public StoreLayerSprite(ListStore<T> store) {
		setStore(store);
	}

	public void setStore(ListStore<T> store) {
		this.store = store;
		if (storeHR != null) {
			storeHR.removeHandler();
		}
		storeHR = store.addStoreHandlers(storeHandlers);
	}

	public ListStore<T> getStore() {
		return store;
	}

	//Refactory 取個比較直覺的名字 [淚目]
	protected abstract void render();

	private void _render() {
		//參考 Chart.redrawChart()
		if (!deferred) {
			deferred = true;

			//Chart.redrawChart() 是用 scheduleDeferred
			//但是這裡還是得用 finally 才能正常...... Orz
			Scheduler.get().scheduleFinally(new ScheduledCommand() {
				@Override
				public void execute() {
					clear();
					render();
					redeploy();
					redraw(true);
					adjustMember();
					deferred = false;
				}
			});
		}
	}
}