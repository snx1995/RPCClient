package top.banyaoqiang.web.util;

import com.google.gson.Gson;

/**
 * Created by 班耀强 on 2018/8/19
 */
public final class RequestResult {
    private static final Gson JSON = new Gson();

    public static String success(Object result) {
        return JSON.toJson(new Response(200, result));
    }

    public static String error(Object result) {
        return JSON.toJson(new Response(500, null));
    }

    public static String notFound() {
        return JSON.toJson(new Response(404, "not found"));
    }

    private static class Response {
        int status;
        Object data;

        public Response(int status, Object data) {
            this.status = status;
            this.data = data;
        }
    }
}
