package com.pms.restful.account;

import com.pms.base.routing.API;
import com.pms.base.routing.REST;
import com.pms.base.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "학부모", summary = "학생 추가")
@REST(requestParams = "uid: String", successCode = 201, failureCode = 204, etc = "uid가 존재하지 않을 경우 204")
@Route(uri = "/add_student", method = HttpMethod.POST)
public class AddStudent implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		
	}
}
