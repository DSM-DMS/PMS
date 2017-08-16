package com.pms.restful.home_communication;

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
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
