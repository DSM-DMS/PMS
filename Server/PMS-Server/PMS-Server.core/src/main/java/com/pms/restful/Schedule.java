package com.pms.restful;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.dms.base.db.MySQL;
import com.pms.base.routing.API;
import com.pms.base.routing.REST;
import com.pms.base.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "파싱된 학교 정보", summary = "학사일정 조회")
@REST(requestParams = "year: int, month: int, day: int", responseBody = "value: String", successCode = 200)
@Route(uri = "/schedule", method = HttpMethod.GET)
public class Schedule implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		try {
			JSONObject response = getSchedule(Integer.parseInt(ctx.request().getParam("year")), Integer.parseInt(ctx.request().getParam("month")), Integer.parseInt(ctx.request().getParam("day")));
			// 스케줄 get
			
			ctx.response().setStatusCode(200);
			ctx.response().end(response.toString());
			ctx.response().close();
		} catch (NumberFormatException | JSONException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private JSONObject getSchedule(int year, int month, int day) throws JSONException, SQLException {
		JSONObject obj = new JSONObject();
		
		String date = String.format("%04d-%02d-%02d", year, month, day);
		// 포매팅
		ResultSet rs = MySQL.executeQuery("SELECT value FROM schedule WHERE date=?", date);
		if(rs.next()) {
			// row가 있으면(파싱되어 있으면)
			obj.put("value", rs.getString("value"));
		} else {
			// 파싱되어 있지 않으면
			obj.put("value", "");
		}
		
		return obj;
	}
}
