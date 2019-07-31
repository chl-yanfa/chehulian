package com.car.app.carscraporder.app.util;

import java.util.Locale;

import org.slf4j.MDC;




import com.car.app.carscraporder.app.bean.UserAppBO;
import com.car.app.carscraporder.app.exception.UnloginException;
import com.car.app.carscraporder.pojo.User;

public class UserUtil {
	

	private final static ThreadLocal<UserAppBO> tlUser = new ThreadLocal<UserAppBO>();
	
	private final static ThreadLocal<Locale> tlLocale = new ThreadLocal<Locale>() {
		protected Locale initialValue() {
			// 语言的默认值
			return Locale.CHINESE;
		};
	};
	



	public static final String KEY_USER = "user";
	public static final String KEY_LANG = "lang";

	public static void setUser(UserAppBO user) {
		tlUser.set(user);
		// 把用户信息放到log4j
		MDC.put(KEY_USER, user.getId());
	}



	/**
	 * 如果没有登录会抛出异常
	 * 
	 * @return
	 * @throws UnloginException 
	 */
	public static UserAppBO getUser() throws UnloginException {
		UserAppBO user = tlUser.get();
		if (user == null) {
			throw new UnloginException("没有登录");
		}
		return user;
	}
	
	
	
	public static void setLocale(String locale) {
		setLocale(new Locale(locale));
	}

	public static void setLocale(Locale locale) {
		tlLocale.set(locale);
	}

	public static Locale getLocale() {
		return tlLocale.get();
	}



	public static void clearAllUserInfo() {
		tlUser.remove();
	

		MDC.remove(KEY_USER);
	}

}
