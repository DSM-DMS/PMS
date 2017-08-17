package com.pms.restful.home_communication;

import com.dms.base.db.MySQL;
import com.pms.base.routing.API;
import com.pms.base.routing.REST;
import com.pms.base.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "가정통신문", summary = "가정통신문에 질문 추가")
@REST(requestBody = "idx: int, title: String, type: int, content: String", successCode = 201, etc = "idx는 가정통신문 인덱스입니다")
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
