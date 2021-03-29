package banking;

public class BankInterface {
    private int count;
    JDBC jdbc;

    void printMenu() {
        System.out.println("1. Create account\n" +
                "2. Log into account\n" +
                "0. Exit");
    }
    void createAccount() {
        CreditCard tempCard = new CreditCard(count);
        System.out.println("Your card have been created\n" +
                            "Your card number:\n" +
                            tempCard.getCardNumber() + "\n" +
                            "Your card PIN:\n" +
                            tempCard.getPin() + "\n");
        jdbc.insertCard(count, tempCard); //INSERTION TO DATABASE
        count++;


    }
    boolean logIntoAccount(long cardNumber, int pin) {
        CreditCard inputCard = jdbc.checkCard(Long.toString(cardNumber));
        if (inputCard.getCardNumber() == cardNumber  && inputCard.getPin() == pin) {
            System.out.println("You have successfully logged in!\n");
            inputCard.setExit();
            return inputCard.cardMenu(jdbc);
        } else {
              System.out.println("Wrong card number or PIN!");
          }
        return false;
    }
    BankInterface(String dbName) {
        jdbc = new JDBC(dbName);
        count = 1;
    }


    int getCount() {
        return count;
    }
}
