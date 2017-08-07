package com.pms.core;

import com.pms.base.handlers.CORSHandler;
import com.pms.base.handlers.LogHandler;
import com.pms.base.routing.Routing;
import com.pms.base.util.Config;
import com.pms.base.util.Log;
import com.pms.parser.Parser;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;

public class Verticle extends AbstractVerticle {
	@Override
	public void start() throws Exception {
		Router router = Router.router(vertx);
		int serverPort = Config.getIntValue("serverPort");
		
		router.route().handler(BodyHandler.create().setUploadsDirectory("files"));
		router.route().handler(CookieHandler.create());
		router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));
		router.route().handler(CORSHandler.create());
		router.route().handler(LogHandler.create());
		Routing.route(router, "com.dms.restful");
		router.route().handler(StaticHandler.create());
		
		Thread parser = new Parser();
		parser.start();
		
		Log.info("Server Started At : " + serverPort);
		vertx.createHttpServer().requestHandler(router::accept).listen(serverPort);
	}
	
	@Override
	public void stop(@SuppressWarnings("rawtypes") Future stopFuture) throws Exception {
		Log.info("Server Stopped");
	}
}
