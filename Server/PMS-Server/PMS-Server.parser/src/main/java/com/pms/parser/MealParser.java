package com.pms.parser;

import java.util.Calendar;
import java.util.List;

import com.dms.parser.support.school_api.School;
import com.dms.parser.support.school_api.SchoolException;
import com.dms.parser.support.school_api.SchoolMenu;

public class MealParser extends Parser {
	@Override
	public void parse() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int maxDate = cal.getActualMaximum(Calendar.DATE);
		int date = cal.get(Calendar.DATE);

		parse(year, month, maxDate, date);

		if (date >= 27) {
			// 매달 27일에는 다음달 메뉴를 파싱
			cal.set(year, month, 1);
			// zero-based numbering이므로 위에서 이미 더해줬으니 그대로 set
			parse(year, month, maxDate, date);
		}
	}

	private void parse(int year, int month, int maxDate, int date) {
		try {
			List<SchoolMenu> monthlyMenus = School.getMonthlyMenu(year, month);
			System.out.println(monthlyMenus);
		} catch (SchoolException e) {
			e.printStackTrace();
		}
	}
}
