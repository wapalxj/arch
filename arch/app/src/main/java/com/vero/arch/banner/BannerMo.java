package com.vero.arch.banner;

import android.Manifest;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.vero.hiui.banner.core.HiBannerMo;

public class BannerMo extends HiBannerMo {
    public BannerMo(String url) {
        this.url = url;
    }
}
