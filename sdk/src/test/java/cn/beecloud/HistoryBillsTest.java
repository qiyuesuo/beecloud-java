package cn.beecloud;

import cn.beecloud.bean.BCException;
import cn.beecloud.bean.BCHistoryBills;
import cn.beecloud.bean.BCOrder;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sy on 2017/6/23.
 */
public class HistoryBillsTest {
    // 历史数据补全接口测试
    static void testHistoryBills() {
        final Map<String, List<String>> billInfoMap = new HashMap<String, List<String>>();
        List billInfo1 = new ArrayList();
        billInfo1.add("e0d6f71791a645b5a9fe46671a7cbeb2");
        List billInfo2 = new ArrayList();
        billInfo2.add("f0d7f4755a1311e7931468f728d24cd8");
        billInfo2.add("e5831c4eb9d342a19bcab3c628a177d9");
        billInfoMap.put("aa10@bc.com", billInfo1);
        billInfoMap.put("aa20@bc.com", billInfo2);
        BCHistoryBills param = new BCHistoryBills();
        param.setAppId(TestConstant.MOCK_WXJSAPI_APPID);
        param.setTimeStamp(String.valueOf(System.currentTimeMillis()));
        param.setBillInfo(billInfoMap);

        /*-------------------------start pay param test------------------------*/
        try {
            Map<String, Object> result = BCPay.historyBills(param);
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
}
