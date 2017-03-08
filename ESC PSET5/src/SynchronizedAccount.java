/**
 * Created by Lenovo on 3/8/2017.
 */
public class SynchronizedAccount {
    private static double balance=1000;

    public static double getBalance() {
        return balance;
    }

    public static void setBalance(double balance) {
        SynchronizedAccount.balance = balance;
    }

    public synchronized void deposit(double amount){
        balance = balance+amount;
        System.out.println("Deposited $"+amount+". Current balance is"+getBalance());

    }


    public synchronized void withdraw(double amount){
        balance = balance-amount;
        System.out.println("Withdrew $"+amount+". Current balance is"+getBalance());
    }

    public double checkbalance(){
        return getBalance();
    }
}
