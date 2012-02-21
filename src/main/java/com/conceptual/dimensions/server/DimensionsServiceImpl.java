package com.conceptual.dimensions.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;

import com.conceptual.dimensions.client.DimensionsService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class DimensionsServiceImpl extends RemoteServiceServlet implements DimensionsService {
	
	@Override
	public String getMyDimensions(Date startDate, Date endDate) {
		List<String> goals = new ArrayList<String>();
		goals.add("m&i");
		goals.add("h");
		goals.add("az");
		goals.add("exp");
		goals.add("soc");
		goals.add("proj");		
		
		LocalDate start = LocalDate.fromDateFields(startDate);
		LocalDate end = LocalDate.fromDateFields(endDate);
		
		if (end.isBefore(start)) {
			throw new RuntimeException("Invalid dates");
		}
		
		DimensionsBuilder builder = new DimensionsBuilder(goals, start, end, 3);
		
		builder.print();
		
		return builder.getText();
	}

}
