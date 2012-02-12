package com.conceptual.dimensions.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DimensionsServiceAsync {

	void getMyDimensions(AsyncCallback<String> callback);
}
