package ElementsOfSoftwareConstruction;

public class PizzaStore {

    public Pizza orderPizza (String type) {
        return new Pizzafactory().producepizza(type);
    }
}

class Pizza {

    public void prepare() {
    }

    public void box() {
    }

    public void cut() {
    }

    public void bake() {
    }
}

class Pizzafactory{


    public Pizza producepizza(String type){
        if (type.equals("cheese")) {
            return new CheesePizza();
        }
        if (type.equals("greek")) {
            return new GreekPizza();
        }
        if (type.equals("pepperoni")) {
            return new PepperoniPizza();
        }
        else{return null;}


    }
}

class CheesePizza extends Pizza {}
class GreekPizza extends Pizza {}
class PepperoniPizza extends Pizza {}

