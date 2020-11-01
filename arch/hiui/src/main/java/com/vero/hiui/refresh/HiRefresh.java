package com.vero.hiui.refresh;

public interface HiRefresh {
    /**
     * 刷新时禁止滚动
     *
     * @param disableRefreshScroll
     */
    void setDisableRefreshScroll(boolean disableRefreshScroll);

    /**
     * 刷新完成
     */

    void refreshFinished();

    void setRefreshOverView(HiOverView hiOverView);

    void setRefreshListener(HiRefreshListener hiRefreshListener);

    interface HiRefreshListener {
        void onRefresh();

        boolean enableRefresh();
    }
}
