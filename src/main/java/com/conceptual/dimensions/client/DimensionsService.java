package com.conceptual.dimensions.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("main")
public interface DimensionsService extends RemoteService {
	
	String getMyDimensions();
	
}
