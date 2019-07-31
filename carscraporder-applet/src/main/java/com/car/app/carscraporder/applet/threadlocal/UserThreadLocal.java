package com.car.app.carscraporder.applet.threadlocal;

import com.car.app.carscraporder.web.bean.ClientUser;





public class UserThreadLocal {

    private static final ThreadLocal<ClientUser> USER = new ThreadLocal<ClientUser>();

    public static void set(ClientUser user) {
        USER.set(user);
    }

    public static ClientUser get() {
        return USER.get();
    }
    
    public static void clear(){
        set(null);
    }

}
