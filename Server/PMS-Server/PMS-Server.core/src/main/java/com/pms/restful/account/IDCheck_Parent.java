package com.pms.restful.account;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.dms.base.db.MySQL;
import com.pms.base.crypto.AES256;
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
		String id = AES256.encrypt(ctx.request().getFormAttribute("id"));
		
		ResultSet rs = MySQL.executeQuery("SELECT * FROM account_parent WHERE id=?", id);
		try {
			if(rs.next()) {
				ctx.response().setStatusCode(200).end();
				ctx.response().close();
			} else {
				ctx.response().setStatusCode(204).end();
				ctx.response().close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
