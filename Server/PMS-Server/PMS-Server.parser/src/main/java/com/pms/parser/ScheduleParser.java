package com.pms.parser;

import java.util.Calendar;
import java.util.List;

import com.dms.base.db.MySQL;
import com.pms.parser.support.school_api.School;
import com.pms.parser.support.school_api.SchoolException;
import com.pms.parser.support.school_api.SchoolSchedule;

public class ScheduleParser extends Parser {
	@Override
	public void parse() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int date = cal.get(Calendar.DATE);
		
		parse(year, month);
		
		if (date >= 27) {
			// 매달 27일에는 다음달 학사일정을 파싱
			cal.set(year, month + 1, 1);
			parse(year, month);
		}
	}
	
	private void parse(int year, int month) {
		try {
			List<SchoolSchedule> monthlySchedules = School.getMonthlySchedule(year, month);
			for (int i = 0; i < monthlySchedules.size(); i++) {
				String daySchedule = monthlySchedules.get(i).schedule;
				String today = String.format("%04d-%02d-%02d", year, month, i + 1).toString();
				MySQL.executeUpdate("DELETE FROM schedule WHERE date=?", today);
				MySQL.executeUpdate("INSERT INTO schedule VALUES(?, ?)", today, daySchedule);
			}
		} catch (SchoolException e) {
			e.printStackTrace();
		}
	}
}
