package com.fzy.fzprj.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fzy
 * @version 1.0
 * 创建时间：2024-09-30 13:10
 */

public class BaseContext {
    private static final ThreadLocal<Map<String, Long>> threadLocal = new ThreadLocal<>();
    private BaseContext(){}

    public static void saveLoginId(Long id){
        if (threadLocal.get() == null) {
            Map<String, Long> map = new HashMap<>();
            threadLocal.set(map);
        }
        threadLocal.get().put(CommonValues.LOGIN_ID, id);
    }

}
