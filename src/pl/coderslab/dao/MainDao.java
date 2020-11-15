package pl.coderslab.dao;

import pl.coderslab.tables.User;

import java.util.Arrays;

public class MainDao {
    public static void main(String[] args) {
        User user = new User();
        UserDao userDao = new UserDao();
//        user.setEmail("wolodyjowski55@gmail.com");
//        user.setUsername("Pan Wołodyjowski");
//        user.setPassword("Wolodyjowski34");
//        userDao.create(user);
//        User userToUpdate = userDao.read(1);
//        userToUpdate.setUsername("Mickiewicz");
//        userToUpdate.setEmail("mickiewicz@wp.pl");
//        userToUpdate.setPassword("slowacki12");
//        userToUpdate.setId(10);
//        userDao.delete(1);
//        System.out.println(userDao.read(1));
        System.out.println(Arrays.toString(userDao.findAll()));


    }
}
