package com.pms.restful.home_communication;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.dms.base.db.MySQL;
import com.pms.base.routing.API;
import com.pms.base.routing.REST;
import com.pms.base.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "가정통신문", summary = "가정통신문 리스트 조회")
@REST(responseBody = "idx: int, title: String, summary: String, date: String", successCode = 200,  failureCode = 204, etc = "가정통신문이 없을 경우 status 204")
@Route(uri = "/homecommu", method = HttpMethod.GET)
public class GetHomeCommuList implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		ResultSet rs = MySQL.executeQuery("SELECT * FROM home_comm");
		JSONArray response = new JSONArray();
		try {
			while (rs.next()) {
				JSONObject homeCommu = new JSONObject();
				homeCommu.put("idx", rs.getInt("idx"));
				homeCommu.put("title", rs.getString("title"));
				homeCommu.put("summary", rs.getString("summary"));
				homeCommu.put("date", rs.getString("date"));
				
				response.put(homeCommu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(response.length() > 0) {
			ctx.response().setStatusCode(200);
			ctx.response().end(response.toString());
			ctx.response().close();
		} else {
			ctx.response().setStatusCode(204).end();
			ctx.response().close();
		}
	}
}
