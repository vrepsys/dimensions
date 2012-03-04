package com.conceptual.dimensions.server;

import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;

import com.conceptual.dimensions.client.DimensionsService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class DimensionsServiceImpl extends RemoteServiceServlet implements DimensionsService {
	
	@Override
	public String getMyDimensions(Date startDate, Date endDate, List<String> dimensions) {	
		LocalDate start = LocalDate.fromDateFields(startDate);
		LocalDate end = LocalDate.fromDateFields(endDate);
		
		if (end.isBefore(start)) {
			throw new RuntimeException("Invalid dates");
		}
		
		DimensionsBuilder builder = new DimensionsBuilder(dimensions, start, end, 3);
		
		builder.print();
		
		return builder.getText();
	}
	
	@Override
	public Date getDefaultEndDate() {	
		return new LocalDate().dayOfYear().withMaximumValue().toDate();
	}

}
