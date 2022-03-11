package com.bluemsun.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class HttpRequestUtil {
    public static int getInt(Map<String,String> request, String key) {
        try {
            return Integer.decode(request.get(key));
        } catch (Exception e) {
            return -1;
        }
    }

    public static long getLong(HttpServletRequest request, String key) {
        try {
            return Long.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1;
        }
    }

    public static Float getFloat(Map<String,String> request, String key) {
        try {
            return Float.valueOf(request.get(key));
        } catch (Exception e) {
            return null;
        }
    }

    public static Boolean getBoolean(Map<String,String> request, String key) {
        try {
            if (getString(request,key)==null) {
                return null;
            }else {
                return Boolean.parseBoolean(request.get(key));
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static String getString(Map<String,String> request, String key) {
        try {
            String result = request.get(key);
            if (result != null) {
                result = result.trim();
            }
            if ("".equals(result)) {
                result = null;
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
