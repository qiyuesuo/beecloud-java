package cn.beecloud;

import cn.beecloud.bean.*;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sy on 2017/6/23.
 */
public class UserTest {
    private static String MAIN_EMAIL = "zhihaoq@beecloud.cn";

    // 单个用户注册接口测试
    static void testUserRegister() {
        BCUserInfo user = new BCUserInfo();
        user.setBuyerId(String.valueOf(System.currentTimeMillis())+"-test");
        user.setTimeStamp(String.valueOf(System.currentTimeMillis()));
        try {
            Map<String, Object> result = BCUserManager.userRegister(user);
            Assert.assertEquals("", 0,
                    result.get("result_code"));
            Assert.assertEquals("", "OK",
                    result.get("result_msg"));
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(BCEumeration.RESULT_TYPE.PARAM_INVALID.name()));
        }

    }

    // 批量用户导入接口测试
    static void testUserBatchImport() {
        BCUsers user = new BCUsers();
        user.setEmail(MAIN_EMAIL);
        List list = new ArrayList();
        list.add("aa10@bc.com");
        list.add("aa20@bc.com");
        user.setBuyerIds(list);
        user.setTimeStamp(String.valueOf(System.currentTimeMillis()));
        try {
            Map<String, Object> result = BCUserManager.userBatchImport(user);
            Assert.assertEquals("", 0,
                    result.get("result_code"));
            Assert.assertEquals("", "OK",
                    result.get("result_msg"));
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(BCEumeration.RESULT_TYPE.PARAM_INVALID.name()));
        }

    }

    // 商户用户批量查询接口测试
    static void testUserBatchQuery() {
        BCUsersQuery user = new BCUsersQuery();
        user.setEmail(MAIN_EMAIL);
        user.setBuyerType("1");
        user.setTimeStamp(String.valueOf(System.currentTimeMillis()));
        try {
            Map<String, Object> result = BCUserManager.userBatchQuery(user);
            Assert.assertEquals("", 0,
                    result.get("result_code"));
            Assert.assertEquals("", "OK",
                    result.get("result_msg"));
            Assert.assertNotNull(result.get("users"));
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage(), e instanceof BCException);
            Assert.assertTrue(e.getMessage(),
                    e.getMessage().contains(BCEumeration.RESULT_TYPE.PARAM_INVALID.name()));
        }

    }
}
