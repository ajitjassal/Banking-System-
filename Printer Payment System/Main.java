package banking;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        BankInterface bankInterface = new BankInterface(args[1]);

        boolean exit = false;
        byte input;
        long tempNumber;
        int tempPin;

        while (!exit) {
            bankInterface.printMenu();
            input = scan.nextByte();
           if (input == 1) {
               bankInterface.createAccount();
           } else if (input == 2) {
               System.out.println("Enter your card number:");
               tempNumber = scan.nextLong();
               System.out.println("Enter your card PIN:");
               tempPin =scan.nextShort();
               if (bankInterface.logIntoAccount(tempNumber, tempPin))
                   break;
           } else if (input == 0) {
               exit = true;
               System.out.println("\nBye!");
           }
        }
    }
}
