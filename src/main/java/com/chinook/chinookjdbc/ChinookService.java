package com.chinook.chinookjdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.chinook.chinookjdbc.dao.ChinookDAO;

@Component
public class ChinookService implements ApplicationRunner {

    @Autowired
    private ChinookDAO chiDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        chiDao.testDataBaseConnectionI();
    }
}
