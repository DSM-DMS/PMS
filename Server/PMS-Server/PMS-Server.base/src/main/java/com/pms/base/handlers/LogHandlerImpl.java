package com.pms.base.handlers;

import com.pms.base.util.Log;

import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

public class LogHandlerImpl implements LogHandler {
	@Override
	public void handle(RoutingContext ctx) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(ctx.request().host()).append(" : ");
		sb.append(ctx.request().method()).append(" ");
		sb.append(ctx.request().uri()).append("\n");
		
		if(ctx.request().method() != HttpMethod.GET) {
			// Parameters show in Request URI
			sb.append("Body - ").append(ctx.request().formAttributes());
		}
		
		Log.request(sb.toString());
		
		ctx.next();
	}
}
