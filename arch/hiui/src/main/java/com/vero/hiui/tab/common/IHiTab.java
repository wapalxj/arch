package com.vero.hiui.tab.common;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

/**
 * HiTab对外接口
 *
 * @param <D>
 */
public interface IHiTab<D> extends IHiTabLayout.OnTabSelectedListener<D> {
    void setHiTabInfo(@NonNull D data);

    /**
     * 动态设置某个Item的大小
     *
     * @param height
     */
    void resetHeight(@Px int height);


}
