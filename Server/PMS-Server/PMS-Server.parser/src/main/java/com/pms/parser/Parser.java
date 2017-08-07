package com.pms.parser;

public class Parser extends Thread {
	protected void parse() { }
	// for override
	
	@Override
	public void run() {
		Parser mealParser = new MealParser();
		Parser scheduleParser = new ScheduleParser();
		
		try {
			mealParser.parse();
			scheduleParser.parse();
			
			Thread.sleep(1000 * 3600 * 12);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
