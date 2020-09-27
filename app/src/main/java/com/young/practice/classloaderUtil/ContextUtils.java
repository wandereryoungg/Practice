package com.young.practice.classloaderUtil;

import android.content.Context;

import java.lang.reflect.Method;

/**
 * Function:
 * Project:MyHookModule
 * Date:2020/9/7
 * Created by xiaojun .
 */

public class ContextUtils {
    /**
     * Context对象
     */
    private static Context CONTEXT_INSTANCE;

    /**
     * 取得Context对象
     * PS:必须在主线程调用
     * @return Context
     */
    public static Context getContext() {
        if (CONTEXT_INSTANCE == null) {
            synchronized (ContextUtils.class) {
                if (CONTEXT_INSTANCE == null) {
                    try {
                        Class<?> ActivityThread = Class.forName("android.app.ActivityThread");

                        Method method = ActivityThread.getMethod("currentActivityThread");
                        Object currentActivityThread = method.invoke(ActivityThread);//获取currentActivityThread 对象

                        Method method2 = currentActivityThread.getClass().getMethod("getApplication");
                        CONTEXT_INSTANCE =(Context)method2.invoke(currentActivityThread);//获取 Context对象

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return CONTEXT_INSTANCE;
    }
}
