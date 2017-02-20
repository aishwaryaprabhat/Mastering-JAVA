package ElementsOfSoftwareConstruction;


import java.util.ArrayList;

public class SubmarineRYAN{

    private final int xmax = 100;
    private final int ymax = 100;

    final Submarine player1, player2;

    SubmarineRYAN(Submarine player1, Submarine player2){

        Timer t = new Timer();
        this.player1 = player1;
        this.player2 = player2;
    }

    public double[] moveplayer1(double x, double y){
        //check if outside gameplay area
        if(x<0 | y<0 | x>xmax | y>ymax ){return null;}
        else{this.player1.setCoordinates(x,y);
        return this.player1.coordinates;}
    }

    public double[] moveplayer2(double x, double y){
        //check if outside gameplay area
        if(x<0 | y<0 | x>xmax | y>ymax ){return null;}
        else{this.player2.setCoordinates(x,y);
           return this.player2.coordinates;}
    }

    public boolean collissiondetected(){
        if(player1.coordinates.equals(player2.coordinates)){
            return true;
        }else{return false;}
    }








}

class Submarine{
    public double[] coordinates;
    private String name;

    Submarine(String name){
        this.name = name;
    }

    public void setCoordinates(double x, double y){
        coordinates[0] = x;
        coordinates[1] = y;
    }
}

class Timer{
    long startTime;
    long endTime;
    long elapsedTime;
    public void startTimer(){
        startTime = System.currentTimeMillis();
    }

    public void stopTimer(){
        endTime=System.currentTimeMillis();
        elapsedTime = endTime-startTime;
    }

    public long getElapsedTime(){
        return elapsedTime;
    }
}
