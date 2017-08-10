package com.pms.restful.account;

import com.pms.base.routing.API;
import com.pms.base.routing.REST;
import com.pms.base.routing.Route;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "학부모", summary = "회원가입")
@REST(requestBody = "uid: String, id: String, pw: String", successCode = 201, failureCode = 204, etc = "uid가 존재하지 않을 경우 204")
@Route(uri = "/signup/parent", method = HttpMethod.POST)
public class Signup_Parent implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		
	}
}
