package com.chinook.chinookjdbc.repository;

import java.util.List;

import com.chinook.chinookjdbc.models.Customer;
import com.chinook.chinookjdbc.models.CustomerCountry;
import com.chinook.chinookjdbc.models.CustomerGenre;
import com.chinook.chinookjdbc.models.CustomerSpender;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{
    
    List<Customer> findCustomerByName(String name);

    List<Customer> findCustomersPagination(int offset, int limit);

    CustomerCountry findMostOccurringCountry();

    CustomerSpender findHighestSpendingCustomer();

    List<CustomerGenre> findCustomersMostPopularGenre(int id);

}
