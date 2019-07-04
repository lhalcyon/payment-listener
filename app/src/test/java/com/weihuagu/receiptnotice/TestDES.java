package com.weihuagu.receiptnotice;
import junit.framework.TestCase;

import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
public class TestDES extends TestCase{
    DES des=null;
    protected void setUp() throws Exception {
        super.setUp();
        des =new DES("abcd12345678");
    }

    @Test
    public void testTransferMapValue() throws Exception{
        Map<String,String> postmap=new HashMap<String,String>();
                                postmap.put("time","2090 12 04");
                                postmap.put("title","支付宝支付");
                                postmap.put("money","345.56");
                                postmap.put("content","测试des伪造的");
    
    des.transferMapValue(postmap);


}




}
