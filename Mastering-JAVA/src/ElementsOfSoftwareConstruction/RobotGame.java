package ElementsOfSoftwareConstruction;

class Robot {
    String name;
    public IBehaviour behave = new NormalBehaviour();

    public Robot (String name)
    {
        this.name = name;
    }

    public void behave ()
    {
     System.out.println("I am "+this.name+". "+ behave.moveCommand());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBehavior(int n) {
        if(n==1) {
            this.behave = new AggressiveBehaviour();
        }
        else if (n==2){
            this.behave = new DefensiveBehaviour();
        }
        else{
            this.behave = new NormalBehaviour();
        }
    }
}

interface IBehaviour {
    public String moveCommand();
}
class AggressiveBehaviour implements IBehaviour
{

    @Override
    public String moveCommand() {
        return "I am aggressive";
    }
}
class DefensiveBehaviour implements IBehaviour
{

    @Override
    public String moveCommand() {
        return "I am defensive";
    }
}
class NormalBehaviour implements IBehaviour
{

    @Override
    public String moveCommand() {
        return "I am normal";
    }
}

public class RobotGame {

    public static void main(String[] args) {

        Robot r1 = new Robot("Big ElementsOfSoftwareConstruction.Robot");
        Robot r2 = new Robot("George v.2.1");
        Robot r3 = new Robot("R2");

        r1.setBehavior(1);
        r2.setBehavior(1);
        r3.setBehavior(1);

        r1.behave();
        r2.behave();
        r3.behave();
        System.out.println();
        //change the behaviors of each robot.
        r1.setBehavior(2);
        r2.setBehavior(2);
        r3.setBehavior(2);

        r1.behave();
        r2.behave();
        r3.behave();
    }
}