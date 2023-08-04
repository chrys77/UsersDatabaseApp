import service.UserService;

import java.util.Scanner;

public class Controller {

    private static UserService userService = new UserService();

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        startUserFlow();
    }

    private static void startUserFlow() {
        while (true) {
            System.out.println("\n> Enter the desired operation:   RA - READ ALL,   RBI - READ BY ID,   C - CREATE,   U - UPDATE,   D - DELETE");
            String chosenOperation = sc.nextLine().toUpperCase();
            switch (chosenOperation) {
                case "RA":
                    userService.readAll();
                    break;
                case "RBI":
                    userService.readById();
                    break;
                case "C":
                    userService.create();
                    break;
                case "U":
                    userService.update();
                    break;
                case "D":
                    userService.delete();
                    break;
                default:
                    System.out.println("Invalid operation");
            }
        }
    }


}