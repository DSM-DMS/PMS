package com.pms.restful.home_communication;

import com.dms.base.db.MySQL;
import com.pms.base.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@Route(uri = "/homecommu/question", method = HttpMethod.POST)
public class AddQuestion implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		int commIdx = Integer.parseInt(ctx.request().getFormAttribute("idx"));
		String title = ctx.request().getFormAttribute("title");
		int type = Integer.parseInt(ctx.request().getFormAttribute("type"));
		String content = ctx.request().getFormAttribute("content");
		
		if(type == 1) {
			MySQL.executeUpdate("INSERT INTO home_comm_question(comm_idx, question_title, type, question_content) VALUES(?, ?, ?, ?)", commIdx, title, type, content);
		} else {
			MySQL.executeUpdate("INSERT INTO home_comm_question(comm_idx, question_title, type, question_choices) VALUES(?, ?, ?, ?)", commIdx, title, type, content);
		}
		
		ctx.response().setStatusCode(201).end();
		ctx.response().close();
	}
}
