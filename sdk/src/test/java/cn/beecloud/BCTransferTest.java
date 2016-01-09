package cn.beecloud;

import mockit.integration.junit4.JMockit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Client;


/**
 * Created by bianjianjun on 16/1/9.
 */
@RunWith(JMockit.class)
public class BCTransferTest {
    protected Client client;

    @Before
    public void setUp() throws Exception {
        BeeCloud.registerApp(TestConstant.KTestAppID, null, TestConstant.kTestAppSecret,
                TestConstant.kTestMasterSecret);
    }

    @Test
    public void javaSDKTest() {
        TransferTest.testBCTransfer();
    }
}
