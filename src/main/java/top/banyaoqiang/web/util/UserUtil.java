package top.banyaoqiang.web.util;

import top.banyaoqiang.RPCApi.entity.User;

/**
 * Created by 班耀强 on 2018/8/26
 */
public final class UserUtil {

    public static boolean isVisitor(User user) {
        return user.getId() == null;
    }
}
