package com.pms.restful.home_communication;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONObject;

import com.dms.base.db.MySQL;
import com.pms.base.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/homecommu", method = HttpMethod.POST)
public class AddHomeCommu implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String title = ctx.request().getFormAttribute("title");
		String summary = ctx.request().getFormAttribute("summary");
		String date = ctx.request().getFormAttribute("date");
		
		MySQL.executeUpdate("INSERT INTO home_comm(title, summary, date) VALUES(?, ?, ?)", title, summary, date);
		ResultSet rs = MySQL.executeQuery("SELECT comm_idx FROM home_comm ORDER BY comm_idx DESC LIMIT 1");
		try {
			JSONObject response = new JSONObject();
			rs.next();
			response.put("idx", rs.getInt("comm_idx"));
			
			ctx.response().setStatusCode(201);
			ctx.response().end(response.toString());
			ctx.response().close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
