package com.conceptual.dimensions.server;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.conceptual.dimensions.client.DimensionsService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class DimensionsServiceImpl extends RemoteServiceServlet implements DimensionsService {
	
	@Override
	public String getMyDimensions() {
		File dimensionsDesign = new File(this.getClass().getResource("timedimensions-design.html").getPath());				
		String html = null;
		try {
			html = FileUtils.readFileToString(dimensionsDesign, "UTF-8");
		}
		catch (Exception e) {
			e.printStackTrace();
			return "Woops, errors..";
		}
		return html;		
	}

}
