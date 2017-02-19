package ElementsOfSoftwareConstruction;

import java.util.ArrayList;
import java.util.Scanner;

public class Election {
    private static int Acount;
    private static int Bcount;

    public static void main(String[] args) {
        ArrayList<Electorate> electoratelist = new ArrayList<>();
        Scanner input = new Scanner(System.in);
        System.out.println("ElementsOfSoftwareConstruction.Electorate 1 enter your vote either A or B: ");
        String electorate1vote = input.next();
        electoratelist.add(Electorate1.getinstance(electorate1vote));

        System.out.println("ElementsOfSoftwareConstruction.Electorate 2 enter your vote either A or B: ");
        String electorate2vote = input.next();
        electoratelist.add(Electorate2.getinstance(electorate2vote));

        System.out.println("ElementsOfSoftwareConstruction.Electorate 3 enter your vote either A or B: ");
        String electorate3vote = input.next();
        electoratelist.add(Electorate3.getinstance(electorate3vote));

        System.out.println("ElementsOfSoftwareConstruction.Electorate 4 enter your vote either A or B: ");
        String electorate4vote = input.next();
        electoratelist.add(Electorate2.getinstance(electorate4vote));

        System.out.println("ElementsOfSoftwareConstruction.Electorate 5 enter your vote either A or B: ");
        String electorate5vote = input.next();
        electoratelist.add(Electorate5.getinstance(electorate5vote));

        System.out.println(countvotes(electoratelist));


    }

    public static String countvotes(ArrayList<Electorate> a){
        for (Electorate i:a){
            if(i.getvote().equals("A")){
                Acount++;
            }else if(i.getvote().equals("B")){
                Bcount++;
            }
        }
        if (Acount>Bcount){
            return "A won the election";
        }else{return "B won the election";}
    }

}
interface Electorate{
    String getvote();

}
class Electorate1 implements Electorate{
    public final String vote;
    private Electorate1(String vote){
        this.vote = vote;
    }

    public static Electorate1 getinstance(String vote){
        Electorate1 instance = new Electorate1(vote);
        return instance;
    }

    @Override
    public String getvote() {
        return this.vote;
    }
}
class Electorate2 implements Electorate{
    public final String vote;
    private Electorate2(String vote){
        this.vote = vote;
    }

    public static Electorate2 getinstance(String vote){
        Electorate2 instance = new Electorate2(vote);
        return instance;
    }
    public String getvote() {
        return this.vote;
    }
}
class Electorate3 implements Electorate{
    public final String vote;
    private Electorate3(String vote){
        this.vote = vote;
    }

    public static Electorate3 getinstance(String vote){
        Electorate3 instance = new Electorate3(vote);
        return instance;
    }
    public String getvote() {
        return this.vote;
    }
}
class Electorate4 implements Electorate{
    public final String vote;
    private Electorate4(String vote){
        this.vote = vote;
    }

    public static Electorate4 getinstance(String vote){
        Electorate4 instance = new Electorate4(vote);
        return instance;
    }
    public String getvote() {
        return this.vote;
    }
}
class Electorate5 implements Electorate{
    public final String vote;
    private Electorate5(String vote){
        this.vote = vote;
    }

    public static Electorate5 getinstance(String vote){
        Electorate5 instance = new Electorate5(vote);
        return instance;
    }
    public String getvote() {
        return this.vote;
    }
}