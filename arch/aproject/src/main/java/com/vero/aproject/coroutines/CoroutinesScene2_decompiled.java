package com.vero.aproject.coroutines;


import android.util.Log;

import org.jetbrains.annotations.NotNull;

import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.Dispatchers;

/**
 * 还原协程的挂起与恢复
 */
public class CoroutinesScene2_decompiled {

    public static final String TAG = "CoroutinesScene2";

    public static final Object request1(Continuation preCallback) {
        ContinuationImpl request1Callback;
        if (!(preCallback instanceof ContinuationImpl) || (((ContinuationImpl) preCallback).label & Integer.MIN_VALUE) == 0) {

            //包装入参为ContinuationImpl
            request1Callback = new ContinuationImpl(preCallback) {
                @Override
                Object invokeSuspend(@NotNull Object resumeResult) {
                    this.label |= Integer.MIN_VALUE;
                    this.result = resumeResult;
                    Log.e(TAG, "恢复request1111");
                    return request1(this);
                }
            };

        } else {
            request1Callback = (ContinuationImpl) preCallback;
        }

        switch (request1Callback.label) {
            case 0:
                //首次进来，
//                Object delay = DelayKt.delay(2000, request1Callback);
                Object res2 = request2(request1Callback);
                if (res2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    //需要挂起
                    Log.e(TAG, "开始挂起request1");
                    return res2;
                }

                break;
            case 1:
                break;
        }

        return "request1==" + request1Callback.result;
    }

    public static final Object request2(Continuation preCallback) {
        ContinuationImpl request2Callback;
        if (!(preCallback instanceof ContinuationImpl) || (((ContinuationImpl) preCallback).label & Integer.MIN_VALUE) == 0) {

            //包装入参为ContinuationImpl
            request2Callback = new ContinuationImpl(preCallback) {
                @Override
                Object invokeSuspend(@NotNull Object resumeResult) {
                    this.label |= Integer.MIN_VALUE;
                    this.result = resumeResult;
                    Log.e(TAG, "恢复request2222");
                    return request2(this);
                }
            };

        } else {
            request2Callback = (ContinuationImpl) preCallback;
        }

        switch (request2Callback.label) {
            case 0:
                //首次进来，
                Object delay = DelayKt.delay(2000, request2Callback);
                if (delay == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    //需要挂起
                    Log.e(TAG, "开始挂起request2");
                    return delay;
                }

                break;
            case 1:
                break;
        }

        return "request2" + request2Callback.result;
    }

    static abstract class ContinuationImpl<T> implements Continuation<T> {

        public Continuation preCallback;
        int label;
        Object result;

        public ContinuationImpl(Continuation preCallback) {
            this.preCallback = preCallback;
        }

        @NotNull
        @Override
        public CoroutineContext getContext() {
            return preCallback.getContext();
        }

        @Override
        public void resumeWith(@NotNull Object resumeResult) {
            Object suspend = invokeSuspend(resumeResult);
            if (suspend == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                return;
            }
            preCallback.resumeWith(suspend);
        }


        abstract Object invokeSuspend(@NotNull Object resumeResult);
    }
}
