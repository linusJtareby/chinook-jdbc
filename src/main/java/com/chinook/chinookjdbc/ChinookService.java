package com.chinook.chinookjdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.chinook.chinookjdbc.dao.ChinookDAO;

@Component
public class ChinookService implements ApplicationRunner {

    private final ChinookDAO chiDao;

    @Autowired
    public ChinookService(ChinookDAO chiDao) {
        this.chiDao = chiDao;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        chiDao.testDataBaseConnectionI();
        // chiDao.printAllCustomers();
        // chiDao.getCustomerById(5);
        // System.out.println(chiDao.getAllCustomersToList());
        // chiDao.getMostOccuringCountry();
        // chiDao.insertCustomer("Linus", "Täreby", "Sweden", "39400", "test@test.com",
        // "0733211434");
        // chiDao.updateCustomer("Linus", "Täreby", "Sweden", "39400", "test@test.com",
        // "0733211434", 61);
        // chiDao.printCustomerByName("Linus");

    }
}
