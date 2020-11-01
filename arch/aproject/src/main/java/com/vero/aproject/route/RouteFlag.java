package com.vero.aproject.route;

public interface RouteFlag {
    int FLAG_LOGIN = 0X01;
    int FLAG_AUTH = FLAG_LOGIN << 1;
    int FLAG_VIP = FLAG_AUTH << 1;
}
