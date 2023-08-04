package service;

import model.User;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.Scanner;

public class UserService {

    private UserRepository userRepository = new UserRepository();

    private Scanner scanerNumbers = new Scanner(System.in);
    private Scanner scanerText = new Scanner(System.in);
    private Scanner scanerBoolean = new Scanner(System.in);


    public void readAll() {
        //return all users
        ArrayList<User> allUsers = userRepository.readAll();
        //print the users
        allUsers.forEach(System.out::println);
    }

    public void readById() {
        //enter the id of the desired user
        System.out.println("Enter the desired user id");
        int id = scanerNumbers.nextInt();

        //make a request to the repository
        User userCitit = userRepository.readById(id);

        //daca utilizatorul exista, atunci il printam
        if (userCitit == null) {
            System.out.println("There is no user with id " + id);
        } else {
            System.out.println(userCitit);
        }
    }

    public void create() {
        //if the user exists, then we print it
        System.out.println("Enter the last name");
        String lastName = scanerText.nextLine();
        System.out.println("Enter the first name");
        String firstName = scanerText.nextLine();
        System.out.println("Enter the email");
        String email = scanerText.nextLine();
        System.out.println("Enter the phone number");
        String phoneNumber = scanerText.nextLine();
        //send a request with the information to save the new user in the database
        boolean succes = userRepository.create(lastName, firstName, email, phoneNumber);
        if (succes) {
            System.out.println("User saved");
        } else {
            System.out.println("An error occurred");
        }
    }

    public void update() {
        //enter the user id
        System.out.println("Enter the user id");
        int id = scanerNumbers.nextInt();
        //check if the user exists in the database
        User userRead = userRepository.readById(id);
        if (userRead != null) {
            boolean changeLastName = changeProperty("firstName");
            boolean changeFirstName = changeProperty("lastName");
            boolean changeEmail = changeProperty("email");
            boolean changePhoneNumber = changeProperty("phoneNumber");

            if (changeLastName) {
                System.out.println("Enter the new last name");
                String newLastName = scanerText.nextLine();
                userRepository.modifyName(id, newLastName);
            }

            if (changeFirstName) {
                System.out.println("Enter the new first name");
                String newFirstName = scanerText.nextLine();
                userRepository.modifyPrenume(id, newFirstName);
            }

            if (changeEmail) {
                System.out.println("Enter the new email");
                String newEmail = scanerText.nextLine();
                userRepository.modifyEmail(id, newEmail);
            }

            if (changePhoneNumber) {
                System.out.println("Enter the new phone number");
                String newPhoneNumber = scanerText.nextLine();
                userRepository.modifyPhoneNumber(id, newPhoneNumber);
            }
        } else {
            System.out.println("There is no user with the id " + id);
        }

    }

    public boolean changeProperty(String property) {
        System.out.println("Do you want to modify the " + property + " ? (true / false)");
        return scanerBoolean.nextBoolean();
    }

    public void delete() {
        System.out.println("Enter the id of the user you want to delete");
        int id = scanerNumbers.nextInt();
        boolean succes = userRepository.delete(id);
        System.out.println(succes ? "The user has been deleted" : "The user has not been deleted");
    }


}
