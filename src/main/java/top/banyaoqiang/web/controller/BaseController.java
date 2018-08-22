package top.banyaoqiang.web.controller;

import org.omg.CORBA.INTERNAL;

import javax.servlet.http.HttpServletRequest;

/**
 * 包含一些基本公用算法
 * Created by 班耀强 on 2018/8/19
 */
public abstract class BaseController {

    protected String getStringParam(HttpServletRequest request, String key) {
        return request.getParameter(key);
    }

    protected int getIntParam(HttpServletRequest request, String key) {
        return Integer.parseInt(request.getParameter(key));
    }

    protected Double getDoubleParam(HttpServletRequest request, String key) {
        return Double.parseDouble(request.getParameter(key));
    }

    protected Float getFloatParam(HttpServletRequest request, String key) {
        return Float.parseFloat(request.getParameter(key));
    }

    protected String[] getStringArrayParam(HttpServletRequest request, String key) {
        return request.getParameterValues(key + "[]");
    }

    protected int[] getIntArrayParam(HttpServletRequest request, String key) {
        String[] sp = getStringArrayParam(request, key);
        int[] result = new int[sp.length];
        for (int i = 0; i < sp.length; i++) {
            result[i] = Integer.parseInt(sp[i]);
        }
        return result;
    }

    protected double[] getDoubleArrayParam(HttpServletRequest request, String key) {
        String[] sp = getStringArrayParam(request, key);
        double[] result = new double[sp.length];
        for (int i = 0; i < sp.length; i++) {
            result[i] = Double.parseDouble(sp[i]);
        }
        return result;
    }

    protected float[] getFloatArrayParam(HttpServletRequest request, String key) {
        String[] sp = getStringArrayParam(request, key);
        float[] result = new float[sp.length];
        for (int i = 0; i < sp.length; i++) {
            result[i] = Float.parseFloat(sp[i]);
        }
        return result;
    }
}
