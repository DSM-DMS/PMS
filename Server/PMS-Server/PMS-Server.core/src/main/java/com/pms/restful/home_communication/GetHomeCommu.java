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

@API(functionCategory = "가정통신문", summary = "가정통신문 질문 조회")
@REST(requestParams = "idx: int", responseBody = "idx: int, title: String, type: int, content: String, or, choices: String, (JSONArray)", successCode = 200, failureCode = 204, etc = "찾을 수 없는 가정통신문의 경우 204")
@Route(uri = "/homecommu/question", method = HttpMethod.GET)
public class GetHomeCommu implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		int idx = Integer.parseInt(ctx.request().getParam("idx"));

		ResultSet rs = MySQL.executeQuery("SELECT * FROM home_comm_question WHERE comm_idx=?", idx);
		JSONArray response = new JSONArray();
		try {
			while (rs.next()) {
				JSONObject question = new JSONObject();
				question.put("idx", rs.getInt("idx"));
				question.put("title", rs.getString("title"));
				question.put("type", rs.getInt("type"));
				if(question.getInt("type") == 1) {
					question.put("content", rs.getString("content"));
				} else {
					question.put("choices", rs.getString("choices"));
				}
				
				response.put(question);
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
