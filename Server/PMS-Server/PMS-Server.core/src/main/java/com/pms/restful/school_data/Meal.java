package com.pms.restful.school_data;

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

@API(functionCategory = "파싱된 학교 정보", summary = "급식 정보 조회")
@REST(requestParams = "year: int, month: int, day: int", responseBody = "breakfast: JsonArray, lunch: JsonArray, dinner: JsonArray", successCode = 200)
@Route(uri = "/meal", method = HttpMethod.GET)
public class Meal implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		try {
			JSONObject response = getMeal(Integer.parseInt(ctx.request().getParam("year")), Integer.parseInt(ctx.request().getParam("month")), Integer.parseInt(ctx.request().getParam("day")));
			// 급식 get
			
			ctx.response().setStatusCode(200);
			ctx.response().end(response.toString());
			ctx.response().close();
		} catch (NumberFormatException | JSONException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	private JSONObject getMeal(int year, int month, int day) throws JSONException, SQLException {
		JSONObject obj = new JSONObject();
		
		String date = String.format("%04d-%02d-%02d", year, month, day);
		// 포매팅
		ResultSet rs = MySQL.executeQuery("SELECT * FROM meal WHERE date=?", date);
		if(rs.next()) {
			// row가 있으면(파싱되어 있으면)
			obj.put("breakfast", rs.getString("breakfast"));
			obj.put("lunch", rs.getString("lunch"));
			obj.put("dinner", rs.getString("dinner"));
		} else {
			// 파싱되어 있지 않으면
			obj.put("breakfast", "");
			obj.put("lunch", "");
			obj.put("dinner", "");
		}
		
		return obj;
	}
}
