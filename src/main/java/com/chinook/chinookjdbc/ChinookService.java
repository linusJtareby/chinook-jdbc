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

        // --Gets customer with id 5 and prints it out --
        // System.out.println(chiDao.customerStringBuilder(chiDao.getCustomerById(5)));

        // --Gets all customers, puts them in a a list, each customer in the returned
        // list is printed --
        // for (Customer customer : chiDao.getAllCustomersToList()) {
        // System.out.println(chiDao.customerStringBuilder(customer));
        // }

        // --Print all customers with "na" in there name--
        // for (Customer customer : chiDao.getCustomerByName("na")) {
        // System.out.println(chiDao.customerStringBuilder(customer));
        // }

        // chiDao.getMostOccuringCountry();

        // --Insert customer --
        // chiDao.insertCustomer("Linus", "Täreby", "Sweden", "39400", "test@test.com",
        // "0733211434");

        // --Updates customer with id "61"--
        // chiDao.updateCustomer("Linus", "Täreby", "Sweden", "39400", "test@test.com",
        // "0733211434", 61);

        // --Gets the country that most customers have and the number of customers who
        // have the country--
        // System.out.println("The country occurring most times is: " +
        // chiDao.getMostOccurringCountry().country()
        // + "\n It occurs " + chiDao.getMostOccurringCountry().noOfCustomers() + "
        // times!");

        System.out.println(chiDao.getCustomersPegenation(5, 20));
    }
}
