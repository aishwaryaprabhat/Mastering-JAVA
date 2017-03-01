package ElementsOfSoftwareConstruction;

//The Observers update method is called when the Subject changes

import java.util.ArrayList;
import java.util.HashMap;

public interface Observer {
    public void update(Company c);
    ArrayList get_interested_companies();
}

//Represents each Observer that is monitoring changes in the subject


class StockObserver implements Observer {

    private double ibmPrice;
    private double aaplPrice;
    private double googPrice;

    // Static used as a counter

    private static int observerIDTracker = 0;

    // Used to track the observers

    private int observerID;

    // Will hold reference to the StockGrabber object

    private iSubject stockGrabber;

    public StockObserver(iSubject stockGrabber){

        // Store the reference to the stockGrabber object so
        // I can make calls to its methods

        this.stockGrabber = stockGrabber;

        // Assign an observer ID and increment the static counter

        this.observerID = ++observerIDTracker;

        // Message notifies user of new observer

        System.out.println("New Observer " + this.observerID);

        // Add the observer to the Subjects ArrayList

        stockGrabber.register(this);

    }

    // Called to update all observers
    HashMap<Company,Double> hasmap_of_prices = new HashMap<>();

    public void update(Company c) {

        hasmap_of_prices.replace(c,c.getPrice());
        printThePrices(c,c.getPrice());

    }

    public void printThePrices(Company c, double p){

        System.out.println(observerID + "\nName: "+c.getName()+hasmap_of_prices.get(c));

    }

    public ArrayList get_interested_companies(){
        ArrayList companies_observer_interested_in = new ArrayList();
        companies_observer_interested_in.addAll(hasmap_of_prices.keySet());
        return get_interested_companies();
    }

}

interface iSubject {
    public void register(Observer o);
    public void unregister(Observer o);
    public void notifyObserver();
}
//Uses the Subject interface to update all Observers

class StockGrabber implements iSubject {

    private ArrayList<Observer> observers;

    public StockGrabber(){

        // Creates an ArrayList to hold all observers

        observers = new ArrayList<Observer>();
    }

    public void register(Observer newObserver) {


        observers.add(newObserver);

    }

    public void unregister(Observer deleteObserver) {

        // Get the index of the observer to delete

        int observerIndex = observers.indexOf(deleteObserver);

        // Print out message (Have to increment index to match)

        System.out.println("Observer " + (observerIndex+1) + " deleted");

        // Removes observer from the ArrayList

        observers.remove(observerIndex);

    }

    public void notifyObserver() {

        // Cycle through all observers and notifies them of
        // price changes

        for(Observer observer : observers){
            ArrayList<Company> interested_companies = observer.get_interested_companies();
            for(Company c:interested_companies){
                observer.update(c);
            }


        }
    }


}

class Company{
    private double price;
    private final String name;

    Company(double price, String name){
        this.price = price;
        this.name = name;
    }

    public double getPrice(){
        return this.price;

    }

    public void setPrice(double price) {
        this.price = price;
        new StockGrabber().notifyObserver();
    }

    public String getName() {
        return name;
    }
}

class Goog extends Company{
//    private final String name = "Goog";
    Goog(double price) {
        super(price, "Goog");
    }
}
class Aapl extends Company{

    Aapl(double price,String name) {
        super(price, "Aapl");
    }
}
class IBM extends Company{
    IBM(double price,String name) {
        super(price, "IBM");
    }
}

