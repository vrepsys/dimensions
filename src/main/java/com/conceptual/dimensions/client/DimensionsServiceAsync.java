package com.conceptual.dimensions.client;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DimensionsServiceAsync {

	void getMyDimensions(Date startDate, Date endDate, List<String> dimensions,
			AsyncCallback<String> callback);

	void getDefaultEndDate(AsyncCallback<Date> asyncCallback);
}
