package com.conceptual.dimensions.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DimensionsServiceAsync {

	void getMyDimensions(Date startDate, Date endDate, AsyncCallback<String> callback);
}
