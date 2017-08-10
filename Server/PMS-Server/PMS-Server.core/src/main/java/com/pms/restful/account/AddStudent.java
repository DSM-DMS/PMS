package com.pms.restful.account;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.dms.base.db.MySQL;
import com.dms.base.db.MySQL_DMS;
import com.pms.base.crypto.SHA256;
import com.pms.base.routing.API;
import com.pms.base.routing.REST;
import com.pms.base.routing.Route;
import com.pms.base.util.CookieManager;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.RoutingContext;

@API(functionCategory = "학부모", summary = "학생 추가")
@REST(requestParams = "uid: String", successCode = 201, failureCode = 204, etc = "uid가 존재하지 않을 경우 204")
@Route(uri = "/add_student", method = HttpMethod.POST)
public class AddStudent implements Handler<RoutingContext> {
	@Override
	public void handle(RoutingContext ctx) {
		String uid = SHA256.encrypt(ctx.request().getFormAttribute("uid"));
		String id = CookieManager.getParentIdFromCookie(ctx);
		
		ResultSet rs = MySQL_DMS.executeQuery("SELECT uid FROM student_data WHERE uid=?", uid);
		try {
			if(rs.next()) {
				// 학생 uid 존재
				MySQL.executeUpdate("INSERT INTO student_links VALUES(?, ?)", id, uid);
				
				ctx.response().setStatusCode(201).end();
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
