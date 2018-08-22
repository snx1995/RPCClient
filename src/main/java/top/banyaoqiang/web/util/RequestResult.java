package top.banyaoqiang.web.util;

import com.google.gson.Gson;
import top.banyaoqiang.RPCApi.entity.OperationResult;

/**
 * Created by 班耀强 on 2018/8/19
 */
public final class RequestResult {
    private static final Gson JSON = new Gson();

    public static String newResponse(int status, Object data) {
        return JSON.toJson(new Response(status, data));
    }

    public static String success(Object result) {
        if (result instanceof OperationResult) {
            OperationResult result1 = (OperationResult) result;
            return JSON.toJson(new Response(result1.getStatus(), result1.getResult()));
        }
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
