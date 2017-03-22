package com.performance.service;

import com.performance.persist.domain.Person;

public interface PersonService {

    /**
     * 保存用户注册信息
     * */
    public Boolean saveRegisterPerson(Person person);
}
