package DesignPatterns;

import java.util.ArrayList;

public class InheritanceDemo{

    public static void main(String[] args) {
        Dog doggo = new Dog("Doggo");
        System.out.println(doggo.move());

        Animal pupper = new Dog("Pupper");
        System.out.println(pupper.move());

        ArrayList<Animal> animals = new ArrayList<>();
        animals.add(pupper);
        animals.add(new Cat("Puss in boots"));

        for(Animal animal:animals){
            System.out.println(animal.move());
        }
    }

}

class Animal{

    private double weight;
    private String name;


    Animal(String name){
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String move(){return "Moves like jagger";}
}

class Dog extends Animal{
    private String name;
    Dog(String name){
        super(name);
        this.name = name;

    }

    public String move(){
        return "Moves like a doggo";
    }

}

class Cat extends Animal{
//    private String name;
    Cat(String name) {
        super(name);
//        this.name = name;
    }

    public String move(){return "Licks pussy";}
}