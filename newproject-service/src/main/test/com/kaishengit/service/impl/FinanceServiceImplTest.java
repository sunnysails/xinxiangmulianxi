package com.kaishengit.service.impl;

import com.kaishengit.service.FinanceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by sunny on 2017/3/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class FinanceServiceImplTest {
    @Autowired
    private FinanceService financeService;

    @Test
    public void count() throws Exception {
        Long a = financeService.count();
        System.out.println(a);
        assertNotNull(a);
    }

}