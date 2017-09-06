package com.pms.parser;

import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;

import com.dms.base.db.MySQL;
import com.pms.parser.support.school_api.School;
import com.pms.parser.support.school_api.SchoolException;
import com.pms.parser.support.school_api.SchoolMenu;

public class MealParser extends Parser {
	@Override
	public void parse() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int date = cal.get(Calendar.DATE);
		// 오늘은 몇날몇일일까

		parse(year, month);

		if (date >= 27) {
			// 매달 27일에는 다음달 메뉴를 파싱
			cal.set(year, month + 1, 1);
			parse(year, month);
		}
	}

	private void parse(int year, int month) {
		try {
			List<SchoolMenu> monthlyMenus = School.getMonthlyMenu(year, month);
			for (int i = 0; i < monthlyMenus.size(); i++) {
				SchoolMenu dayMenu = monthlyMenus.get(i);
				String[] strMenus = new String[3];
				// SchoolMenu 객체에서 급식 정보를 문자열 형태로 얻어오기 위한 배열

				strMenus[0] = dayMenu.breakfast;
				strMenus[1] = dayMenu.lunch;
				strMenus[2] = dayMenu.dinner;
				JSONArray[] menuArrays = new JSONArray[3];
				// 문자열 형태의 메뉴에서 알러지 정보를 정규식으로 빼낸 이후 관리하기 위한 Json array
				// 위와 같이 각 인덱스는 아침, 점심, 저녁을 나타냄

				for (int j = 0; j < 3; j++) {
					// 각각 아침, 점심, 저녁 인덱스
					menuArrays[j] = new JSONArray();
					// 인스턴스화
					for (String menu : strMenus[j].split("\n")) {
						// 메뉴를 개행을 기준을 잘라내서 메뉴 array를 획득
						menuArrays[j].put(menu.split("\\*|\\w+")[0]);
						// 메뉴만 얻어올 수 있도록 잘라냄(원래 메뉴에 알러지 정보가 포함되어 있음)
					}
				}
				
				String today = String.format("%04d-%02d-%02d", year, month, i + 1).toString();
				MySQL.executeUpdate("DELETE FROM meal WHERE date=?", today);
				MySQL.executeUpdate("INSERT INTO meal VALUES(?, ?, ?, ?)", today, menuArrays[0].toString(), menuArrays[1].toString(), menuArrays[2].toString());
			}
		} catch (SchoolException e) {
			e.printStackTrace();
		}
	}
}
