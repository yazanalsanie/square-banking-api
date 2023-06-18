package com.square.www.platform;

import com.square.www.platform.service.api.Depositor;
import com.square.www.platform.service.api.Inquirer;
import com.square.www.platform.service.api.Withdrawal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Starter.class)
public class StarterIntegrationTest {

    @Autowired
    private Withdrawal withdrawal;
    @Autowired
    private Inquirer inquirer;
    @Autowired
    private Depositor depositor;

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(withdrawal);
        Assertions.assertNotNull(inquirer);
        Assertions.assertNotNull(depositor);
    }
}
