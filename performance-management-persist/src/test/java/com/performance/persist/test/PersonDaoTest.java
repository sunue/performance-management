package com.performance.persist.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.performance.persist.dao.PersonDao;
import com.performance.persist.domain.Person;

import junit.framework.TestCase;

@ContextConfiguration({ "classpath:spring-mybatis.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
//@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
//@Transactional(rollbackFor = Exception.class)
public class PersonDaoTest extends TestCase {

    @Resource
    private PersonDao personDao;

    @Test
    public void testInsertSelective() {
        Person p1 = new Person();
        p1.setId(1L);
        p1.setName("张明");
        p1.setPassword("123456");
        p1.setAge(2);
        personDao.insertSelective(p1);
    }

}
