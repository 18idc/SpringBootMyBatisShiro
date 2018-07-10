package com.q18idc.sbms.demo;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void password() {
        //所需加密的参数  即  密码
        String source = "123456";
        //[盐] 一般为用户名 或 随机数
        String salt = "q18idc.com";
        //加密次数
        int hashIterations = 1024;

        SimpleHash hash = new SimpleHash("MD5", source, salt, hashIterations);
        System.err.println(hash);
    }

}
