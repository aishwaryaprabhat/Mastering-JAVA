package ElementsOfSoftwareConstruction;


public class CohortExercise5 {

    private final double a;

    CohortExercise5(double n){

        this.a = n;
    }

    public int getint(){
        if(this.a<0){return -1;}
        else if(this.a>0){return 1;}
        else{return 0;}
    }

}


