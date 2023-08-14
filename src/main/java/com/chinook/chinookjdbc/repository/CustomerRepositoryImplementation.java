package com.chinook.chinookjdbc.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.chinook.chinookjdbc.models.Customer;
import com.chinook.chinookjdbc.models.CustomerCountry;
import com.chinook.chinookjdbc.models.CustomerGenre;
import com.chinook.chinookjdbc.models.CustomerSpender;

@Repository
public class CustomerRepositoryImplementation implements CustomerRepository{
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Override
    public List<Customer> findAll() {
        String sql = "SELECT * FROM customer";
        List<Customer> customerList = new ArrayList<Customer>();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(8), resultSet.getString(9), resultSet.getString(12),
                        resultSet.getString(10));
                customerList.add(customer);
            }
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
        return customerList;
    }

    @Override
    public Customer findById(Integer id) {
        String sql = "SELECT * FROM customer WHERE customer_id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(8), resultSet.getString(9), resultSet.getString(12),
                        resultSet.getString(10));
                return customer;
            }
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Customer> findCustomerByName(String name) {
        String sql = "SELECT * FROM customer WHERE first_name LIKE ?";

        List<Customer> customerList = new ArrayList<Customer>();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, "%" + name + "%");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(8), resultSet.getString(9), resultSet.getString(12),
                        resultSet.getString(10));
                customerList.add(customer);
            }
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
        return customerList;
    }

    @Override
    public void createEntry(Customer customer) {
        String sql = "insert into customer (first_name, last_name, country, postal_code, phone, email) values (?,?,?,?,?,?)";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, customer.firstName());
            statement.setString(2, customer.lastName());
            statement.setString(3, customer.country());
            statement.setString(4, customer.postalCode());
            statement.setString(5, customer.phoneNo());
            statement.setString(6, customer.phoneNo());

            statement.executeUpdate();
            statement.close();

        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }

    @Override
    public void updateEntry(Customer customer) {
        String sql = "update customer set first_name = ?, last_name = ?, country = ?, postal_code = ?, phone = ?, email = ? where customer_id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, customer.firstName());
            statement.setString(2, customer.lastName());
            statement.setString(3, customer.country());
            statement.setString(4, customer.postalCode());
            statement.setString(5, customer.email());
            statement.setString(6, customer.phoneNo());
            statement.setInt(7, customer.id());

            statement.executeUpdate();

        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }

    @Override
    public void deleteEntry(Integer id) {
        String sql = "delete customer where customer_id = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password)){
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.executeUpdate();
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }

    @Override
    public List<Customer> findCustomersPagination(int offset, int limit) {
        String sql = "select * from customer offset ? limit ?";
        List<Customer> results = new ArrayList<Customer>();

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, offset - 1);
            statement.setInt(2, limit);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Customer customer = new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                        resultSet.getString(8), resultSet.getString(9), resultSet.getString(12),
                        resultSet.getString(10));
                results.add(customer);
            }

        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
        return results;
    }

    @Override
    public CustomerCountry findMostOccurringCountry() {
        String sql = "select country, count (country) as Occurrence from customer group by country order by Occurrence desc limit 1";

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CustomerCountry country = new CustomerCountry(resultSet.getString(1), resultSet.getInt(2));
                return country;
            }
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public CustomerSpender findHighestSpendingCustomer() {
        String sql = "select first_name, last_name, x.sum from customer join (select customer_id, SUM(total) sum from invoice group by customer_id order by sum desc limit 1) as x on x.customer_id = customer.customer_id";

        try(Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CustomerSpender spender = new CustomerSpender(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3));
                return spender;
            }
            
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
        return null;
    }

    @Override
    public List<CustomerGenre> findCustomersMostPopularGenre(int id) {
        List<CustomerGenre> mostpopularGenres = new ArrayList<CustomerGenre>();

        String sql = "select citgrf.first_name, citgrf.last_name, citgrf.name from "
                .concat("(select citgr.first_name, citgr.last_name, citgr.name, rank() over (order by citgr.sum desc) as rn from ")
                .concat("(select citg.first_name, citg.last_name, name, count(name) as sum ")
                .concat("from genre join ")
                .concat("(select cit.first_name, cit.last_name, cit.invoice_id, cit.track_id, genre_id ")
                .concat("from track join ")
                .concat("(select ci.first_name, ci.last_name, ci.invoice_id, track_id ") 
                .concat("from invoice_line join ")
                .concat("(select first_name, last_name, invoice_id from invoice join customer on customer.customer_id = invoice.customer_id ")
                .concat("where customer.customer_id = ?) as ci on ci.invoice_id = invoice_line.invoice_id) as cit on cit.track_id = track.track_id) ")
                .concat("as citg on citg.genre_id = genre.genre_id group by citg.first_name, citg.last_name, name) as citgr) as citgrf where citgrf.rn = 1");

        try(Connection conn = DriverManager.getConnection(url, username, password)) {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                CustomerGenre genres = new CustomerGenre(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                mostpopularGenres.add(genres);
            }
            return mostpopularGenres;
        } catch (Exception sqle) {
            sqle.printStackTrace();
        }
        return null;
    }


}
