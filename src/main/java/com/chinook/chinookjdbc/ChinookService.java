package com.chinook.chinookjdbc;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.chinook.chinookjdbc.models.Customer;
import com.chinook.chinookjdbc.repository.CustomerRepositoryImplementation;

@Component
public class ChinookService implements ApplicationRunner {

    private final CustomerRepositoryImplementation cri;

    public ChinookService(CustomerRepositoryImplementation cri) {
        this.cri = cri;
    }
        
    @Override
    public void run(ApplicationArguments args) throws Exception {
        

        //--Gets customer with id 5 and prints it out --
        System.out.println(cri.findById(5));

        //--Gets all customers, puts them in a a list, each customer in the returned
        //list is printed --
        for (Customer customer : cri.findAll()) {
        System.out.println(customer);
        }

        //--Print all customers with "na" in there name--
        for (Customer customer : cri.findCustomerByName("na")) {
        System.out.println(customer);
        }

        System.out.println(cri.findMostOccurringCountry());

        //--Insert customer --
        Customer linus = new Customer(0, "Linus", "Täreby", "Sweden", "39400", "0733211434", "test@test.com");
        cri.createEntry(linus);

        //--Updates customer with id "61"--
        Customer updatedLinus = new Customer(61, "Linus", "Tåreby", "Sweden", "39400", "0733211434", "test@test.com");
        cri.updateEntry(updatedLinus);

        //--Gets the country that most customers have and the number of customers who
        //have the country--
        System.out.println("The country occurring most times is: " + cri.findMostOccurringCountry().country()
               + "\n It occurs " + cri.findMostOccurringCountry().noOfCustomers() + " times!");
        System.out.println(cri.findHighestSpendingCustomer());

        //--Gets the most popular genre/s by one customer
        System.out.println(cri.findCustomersMostPopularGenre(12));
        

        // --Gets 20 customers in order starting from id 5--
        System.out.println(cri.findCustomersPagination(5, 20));
    }
}
