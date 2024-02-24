package com.await.bdpw10.service;

import com.await.bdpw10.da.UserDao;
import com.await.bdpw10.rm.EmailValidation;
import com.await.bdpw10.rm.Handler;
import com.await.bdpw10.rm.PasswordValidation;
import com.await.bdpw10.ui.Menu;
import com.rickotb.bdpw7.da.*;
import com.await.bdpw10.da.entity.User;
import com.await.bdpw10.rm.NameValidation;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class UserService {
    private static UserDao userDao;

    boolean isValid = false;
    boolean isValidEmail = false;
    boolean isValidPassword = false;


    Handler nameHandler = new NameValidation();
    Handler emailHandler = new EmailValidation();
    Handler passwordHandler = new PasswordValidation();


    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void listUsers() throws SQLException {
        List<User> users = userDao.getAllUsers();
        if (users.size() > 0) {
            for (User user : users) {
                System.out.println("ID: " + user.getId() + ", Ім'я: "
                        + user.getName() + ", Емайл: " + user.getEmail());
            }
        } else {
            System.out.println("Користувачів не знайдено!");
            Menu.menu();
        }
    }

    public void addUser() throws SQLException {
        List<User> users = userDao.getAllUsers();
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введіть ім'я користувача");
            String name = scanner.nextLine();

            for(User userEquals : users){
                if(userEquals.getName().equals(name)){
                    System.out.println("Користувач вже інсує");
                    Menu.menu();
                }else {
                    System.out.println("Ведіть електронну пошту");
                    String email = scanner.nextLine();
                    System.out.println("Введіть пароль");
                    String password = scanner.nextLine();

                    nameHandler.setNextCHandler(nameHandler);
                    emailHandler.setNextCHandler(emailHandler);
                    passwordHandler.setNextCHandler(passwordHandler);

                    User user = new User(name, email, password);

                    isValid = nameHandler.validate(user);
                    isValidEmail = emailHandler.validate(user);
                    isValidPassword = passwordHandler.validate(user);

                    if (isValid && isValidEmail && isValidPassword) {
                        userDao.addUser(user);
                        System.out.println("Користувача додано!");
                        Menu.menu();
                    } else {
                        Menu.authorization();
                        for (String validMsg : user.getValidationMessage()) {
                            System.out.println(validMsg);
                        }
                    }
                }
            }
        }catch (Exception ignored){}
    }

    public void authorization(){
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.println("Ведіть логін");
            String name = scanner.nextLine();
            System.out.println("Ведіть пароль");
            String password = scanner.nextLine();
            User user = new User(name, password);

            if (isExistUser(user) == 1) {
                System.out.println("Авторизація успішна");
                Menu.menu();
            }else if(isExistUser(user) == 2){
                System.out.println("Не вірний логін або пароль");
                Menu.authorization();
            }

        }catch (Exception ex){
            System.out.println("Проблеми за авторизацією");
        }
    }

    public static int isExistUser(User user) throws SQLException{
        List<User> users = userDao.getAllUsers();
        for(User  userList: users){
            if(userList.getName().equals(user.getName()) && userList.getPassword().equals(user.getPassword())){
                return 1;
            }
        }
        return 2;
    }

    public void updateUser() throws SQLException{
        Scanner scanner = new Scanner(System.in);
        List<User> users = userDao.getAllUsers();
        listUsers();

        try {
            System.out.print("Введіть ID користувача: ");
            int id = scanner.nextInt();

            scanner.nextLine();
            System.out.println("Введіть нове ім'я користувача");
            String name = scanner.nextLine();

            for (User userEquals: users){
                if (userEquals.getName().equals(name)) {
                    System.out.println("Користувач вже інсує");
                } else {
                    System.out.println("Ведіть нову електронну пошту");
                    String email = scanner.nextLine();

                    System.out.println("Введіть новий пароль");
                    String password = scanner.nextLine();

                    nameHandler.setNextCHandler(nameHandler);
                    emailHandler.setNextCHandler(emailHandler);
                    passwordHandler.setNextCHandler(passwordHandler);

                    User user = new User(name, email, password);

                    isValid = nameHandler.validate(user);
                    isValid = emailHandler.validate(user);
                    isValid = passwordHandler.validate(user);

                    if (isValid) {
                        userDao.updateUser(user);
                        System.out.println("Користувача оновлено!");
                        Menu.menu();
                    } else {
                        Menu.menu();
                        for (String validMsg : user.getValidationMessage()) {
                            System.out.println(validMsg);
                        }
                    }
                }
            }
        }catch (Exception ignored){}
    }

    public void deleteUser() throws SQLException {
        listUsers();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть ID користувача: ");
        int id = scanner.nextInt();
        userDao.deleteUser(id);
        System.out.println("Користувача видалено!");
    }

    public void findUserId() throws SQLException{
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Введіть ID користувача: ");
            int id = scanner.nextInt();

            userDao.getUserById(id);
            System.out.println("Користувача знайдено Ім'я: " + userDao.getUserById(id).getName()
                    + " Емайл: " + userDao.getUserById(id).getEmail());
            Menu.menu();

        }catch (Exception e){
            System.out.println("Користувача не існує!");
            Menu.menu();
        }
    }
}
