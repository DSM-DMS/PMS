package com.pms.base.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.dms.base.db.MySQL;
import com.pms.base.crypto.SHA256;

import io.vertx.ext.web.RoutingContext;

public class CookieManager {
	public static String getParentIdFromCookie(RoutingContext ctx) {
		String cookieValue = SessionUtil.getSessionId(ctx, "signin_keeper");
		if(cookieValue == null) {
			return null;
		}
		
		ResultSet rs = MySQL.executeQuery("SELECT id FROM account_parent WHERE cookie=?", SHA256.encrypt(cookieValue));
		try {
			rs.next();
			return rs.getString("id");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
