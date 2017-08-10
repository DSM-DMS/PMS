package com.pms.restful.account;

import com.pms.base.routing.API;
import com.pms.base.routing.REST;
import com.pms.base.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "학부모", summary = "회원가입 ID 중복체크")
@REST(requestBody = "id: String", successCode = 200, failureCode = 204, etc = "중복된 id일 경우 204")
@Route(uri = "/idcheck/parent", method = HttpMethod.POST)
public class IDCheck_Parent implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		
	}
}
