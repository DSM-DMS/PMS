package com.pms.restful.account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.dms.base.db.MySQL;
import com.pms.base.crypto.AES256;
import com.pms.base.crypto.SHA256;
import com.pms.base.routing.API;
import com.pms.base.routing.REST;
import com.pms.base.routing.Route;
import com.pms.base.util.SessionUtil;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "학부모", summary = "로그인")
@REST(requestBody = "id: String, pw: String", successCode = 200, failureCode = 204, etc = "로그인 실패 시 204")
@Route(uri = "/signin/parent", method = HttpMethod.POST)
public class Signin_Parent implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String id = AES256.encrypt(ctx.request().getFormAttribute("id"));
		String pw = SHA256.encrypt(ctx.request().getFormAttribute("pw"));
		
		ResultSet rs = MySQL.executeQuery("SELECT * FROM account_parent WHERE id=? AND pw=?", id, pw);
		try {
			if(rs.next()) {
				// 로그인 성공
				createCookie(ctx, id);
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
	
	private void createCookie(RoutingContext ctx, String id) throws SQLException {
		while(true) {
			String value = UUID.randomUUID().toString();
			ResultSet rs = MySQL.executeQuery("SELECT * FROM account_parent WHERE cookie=?", value);
			if(!rs.next()) {
				SessionUtil.createCookie(ctx, "signin_keeper", value);
				MySQL.executeUpdate("UPDATE account_parent SET cookie=?", SHA256.encrypt(value));
				
				break;
			}
		}
	}
}
