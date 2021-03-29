import java.util.Random;
import java.util.Scanner;

public class CreditCard {
    private long cardNumber;
    private int balance;
    private int pin;
    private boolean exit = false;


    Scanner scanner = new Scanner(System.in);
    Random random =new Random();
    byte input;



    boolean cardMenu(JDBC jdbc) {
        while (!exit) {
            System.out.println("1. Balance\n" +
                    "2. Add income\n" +
                    "3. Do transfer\n" +
                    "4. Close account\n" +
                    "5. Log out\n" +
                    "0. Exit");
            input = scanner.nextByte();
            if (input == 1) {
                System.out.println("Balance: " + balance);
            } else if (input == 2) {
                System.out.println("Enter income");
                this.balance += scanner.nextInt();
                System.out.println("Income was added!");
            } else if (input == 3) {
                transfer(jdbc);
            } else if (input == 4) {
                closeAccount(jdbc);
            }else if (input == 5) {
                System.out.println("You have successfully logged out!");
            } else if (input == 0) {
                this.exit = true;
                System.out.println("\nBye!");
                return true;
            }
        }
        return false;
    }

    CreditCard() {
        balance = 0;
        cardNumber = 400_000_000_000_000_0L;
        pin = 1000; // 0000 to 9999
    }
    CreditCard(int index) {
        balance = 0;
        cardNumber = 400_000_000_000_000_0L + index * 10; // CheckSum requires this line
        cardNumber += getCheckSum(cardNumber);
        pin = random.nextInt(9000) + 1000; // 0000 to 9999

    }
    void transfer(JDBC jdbc) {
        System.out.println("Transfer");
        System.out.println("Enter card number:");
        long temp = scanner.nextLong();
        CreditCard receiver = jdbc.checkCard(Long.toString(temp));
        if (luhnCheck(temp)) {
            if (this.cardNumber == temp) {
                System.out.println("You can't transfer money to the same account!");
            } else if (receiver.getCardNumber() == temp) {
                System.out.println("Enter how much money you want to transfer:");
                int transfer = scanner.nextInt();
                if (this.balance >= transfer) {
                    this.balance -= transfer;
                    receiver.addBalance(transfer);
                    System.out.println("Success");
                } else {
                    System.out.println("Not enough money!");
                }
            } else {
                System.out.println("Such a card does not exist");
            }
        } else {
            System.out.println("Probably you made mistake in the card number. Please try again!");
        }
    }

    byte getCheckSum(long inputCard) { // Luhn Algorithm
        String tempcard = Long.toString(inputCard);
        byte checkSum = 0;
        byte temp;
        for (int i = 0; i < 15; i++) {
            temp = (byte)(tempcard.charAt(i) - 48);
            if ( i % 2 != 0) {
                checkSum += temp;
            } else {
                temp *= 2;
                temp = (byte) (temp > 9 ? (temp - 9) : temp);
                checkSum += temp;
            }
        }
        checkSum %= 10;
        checkSum = (byte) (checkSum != 0 ? 10 - checkSum: 0);
        return checkSum;
    }

    boolean luhnCheck(long inputCard) {
        byte lastDigit = getCheckSum(inputCard);
        inputCard = inputCard % 10;
        return inputCard == lastDigit ? true : false;
    }
    void closeAccount(JDBC jdbc) {
        jdbc.deleteCard(this.cardNumber);
    }
    long getCardNumber() {
        return cardNumber;
    }
    int getBalance() {
        return balance;
    }
    int getPin() {
        return pin;
    }

    void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }
    void setPin(int pin) {
        this.pin = pin;
    }
    void setBalance(int balance) {
        this.balance = balance;
    }
    void addBalance(int transfer1) {
        this.balance += transfer1;
    }
    void setExit() {
        this.exit =false;
    }
}
