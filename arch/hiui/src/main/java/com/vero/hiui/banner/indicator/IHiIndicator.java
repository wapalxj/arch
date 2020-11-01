package com.vero.hiui.banner.indicator;

import android.view.View;

/**
 * 泛型表示所在容器
 * @param <T>
 */
public interface IHiIndicator<T extends View> {

    T get();

    /**
     * 初始化Indicator
     *
     * @param count 幻灯片数量
     */
    void onInflate(int count);

    /**
     * 幻灯片切换回调
     *
     * @param current 切换到的幻灯片位置
     * @param count   幻灯片数量
     */
    void onPointChange(int current, int count);

}
