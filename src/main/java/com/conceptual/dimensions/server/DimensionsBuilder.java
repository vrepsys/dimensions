package com.conceptual.dimensions.server;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;

public class DimensionsBuilder {

	private PrintWriter html;
	private StringWriter str = new StringWriter();
	final int totalDays;
	final int weeksInBlock;
	final int totalBlocks;
	final LocalDate trackingStart;
	final LocalDate trackingEnd;
	final LocalDate calendarStart;
	final LocalDate calendarEnd;
	final List<String> goals;
	
	
	public DimensionsBuilder(List<String> goals, LocalDate start, LocalDate end, int weeksInBlock) {
		this.html = new PrintWriter(str);
		this.trackingStart = start;
		this.trackingEnd = end;
		this.totalDays = Days.daysBetween(trackingStart, trackingEnd).getDays();
		this.calendarStart = trackingStart.withDayOfWeek(1);
		int daysBetweenCSandTE = Days.daysBetween(calendarStart, trackingEnd).getDays()+1;
		int daysInBlock = 7*weeksInBlock;
		totalBlocks = (daysBetweenCSandTE / daysInBlock) + ((daysBetweenCSandTE % daysInBlock == 0) ? 0 : 1);
		System.out.println(totalBlocks);		
		int greyDaysAtTheEnd = daysInBlock - (daysBetweenCSandTE % daysInBlock);
		this.calendarEnd = trackingEnd.plusDays(greyDaysAtTheEnd);
		this.goals = goals;
		this.weeksInBlock = weeksInBlock;
	}
	
	public void addRowStart() {
		html.println("<tr>");
	}
	
	public void addGreyCell() {
		addCell("", "grey-area");		
	}
	
	public void addHeaderCell(String text) {
		addCell(text, "header");
	}
	
	public void addSideHeaderCell(String text) {
		addCell(text, "side-header");
	}	
	
	public void addCell(String text, String styles) {
		html.print("   <td class='");
		html.print(styles);
		html.print("'>");
		html.print(text);
		html.println("</td>");
	}
	
	public void addRowEnd() {
		html.println("</tr>");
	}
	
	
	public String getText() {
		return str.toString();
	}
	
	public String getCellStyles(LocalDate day, int row) {
		StringBuilder styles = new StringBuilder();
		int week = day.getWeekOfWeekyear();	
		if (week == 1) {
			styles.append("week-odd");
		}
		else if (week % 2 == 0) {
			styles.append("week-even");
		}
		else {
			styles.append("week-odd");
		}
		if (day.getDayOfWeek() == 6) {
			styles.append(" saturday");
			if (row == 1) {
				styles.append(" saturday-top");
			}
			else if (row == goals.size()) {
				styles.append(" saturday-bottom");
			}
		}
		if (day.getDayOfWeek() == 7) {
			styles.append(" sunday");
			if (row == 1) {
				styles.append(" sunday-top");
			}
			else if (row == goals.size()) {
				styles.append(" sunday-bottom");
			}			
		}
		return styles.toString();		
	}
	
	public void print() {
		for (int i=0;i<totalBlocks;i++) {
			printBlock(calendarStart.plusWeeks(weeksInBlock*i));
		}		
	}

	public void printBlock(LocalDate blockStart) {
		html.println("<table class='main-table' cellspacing='0' cellpadding='0'>");
		addRowStart();
		int leftSplice = 0;
		if (blockStart.equals(calendarStart) && !calendarStart.equals(trackingStart)) {
			leftSplice = Days.daysBetween(calendarStart, trackingStart).getDays();
		}
		int daysInBlock = weeksInBlock*7;
		int rightSplice = daysInBlock;
		if (blockStart.plusWeeks(weeksInBlock).minusDays(1).isAfter(trackingEnd)) {
			rightSplice = Days.daysBetween(blockStart, trackingEnd).getDays()+1;
		}
		// Header row
		addHeaderCell("");
		for (int i=0; i<leftSplice;i++) {			
			addHeaderCell("");			
		}
		for (int i=leftSplice; i<rightSplice; i++) {
			LocalDate today = blockStart.plusDays(i);
			addHeaderCell(getFillingText(today, 0));			
		}
		for (int i=rightSplice; i<daysInBlock; i++) {				
			addHeaderCell("");			
		}					
		addRowEnd();

		// A row for each goal
		for (int row = 1; row <= goals.size(); row++) {
			addRowStart();
			addSideHeaderCell(goals.get(row-1));
			for (int i=0; i<leftSplice;i++) {			
				addGreyCell();			
			}		
			for (int i=leftSplice; i<rightSplice; i++) {
				LocalDate today = blockStart.plusDays(i);
				addCell(getFillingText(today, row), getCellStyles(today, row));			
			}
			for (int i=rightSplice; i<daysInBlock; i++) {				
				addGreyCell();			
			}			
			addRowEnd();				
		}
		
		
		
		html.println("<tr class='emptyrow'>");
		for (int i=0; i<daysInBlock;i++) {
			html.print("<td></td>");
		}
		html.println("</tr>");
	}
	
	private String getFillingText(LocalDate today, int row) {		
		if (row==0) {
			int daysLeft = Days.daysBetween(today, trackingEnd).getDays()+1;
			return String.valueOf(daysLeft); 
		}
		else if (row == 1) {
			return DateTimeFormat.forPattern("E").print(today);
		}
		else if (row == 2) {
			return DateTimeFormat.forPattern("MMM d").print(today);
		}
		else if (row == 3) {
			int daysPassed = Days.daysBetween(trackingStart, today).getDays()+1;
			return String.valueOf(daysPassed);
		}
		return "";
	}
	
	public static void main(String[] args) throws IOException {
		List<String> goals = new ArrayList<String>();
		goals.add("m&i");
		goals.add("h");
		goals.add("az");
		goals.add("exp");
		goals.add("soc");
		goals.add("proj");
				
		LocalDate trackingStart = new LocalDate(2012, 01, 01);		
		LocalDate trackingEnd = new LocalDate(2013, 01, 01).minusDays(1);		
		System.out.println("trackingStart: " + trackingStart);
		System.out.println("trackingEnd: " + trackingEnd);
		
		DimensionsBuilder b = new DimensionsBuilder(goals, trackingStart, trackingEnd, 3);

		b.print();				
		
		File dimensionsDesign = new File("c:/programs/workspace/dimensions/dimensions.html");
		FileUtils.writeStringToFile(dimensionsDesign, b.getText());
				
	}
	
}
