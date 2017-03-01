package ElementsOfSoftwareConstruction;
import java.util.Scanner;

public class TicTacTo {

    public static void main(String[] args) {
        char player='o';
        int row,column;
        Scanner k =  new Scanner (System.in);
        Game g = new Game ();

        g.setup_game();
        g.showBoard();
        System.out.println("Game ready !\n");

        while(true)
        {
            player=g.switch_players(player);
            System.out.print("\n"+player +" Enter Row: ");

            row=k.nextInt();
            System.out.print("\n"+player +" Enter Column: ");
            column=k.nextInt();

            while (g.slot_already_taken(row,column))
            {
                System.out.println("That slot is already taken or out of bounds, please try again (row, column).");
                g.showBoard();
                row=k.nextInt();
                column=k.nextInt();
            }
            g.updateboard(player,row,column );
            g.showBoard();

            if(g.check_for_winner())
            {
                System.out.println("\nThe winner is "+ player);
                break;

            }
            if(g.check_if_tie())
            {
                System.out.println("\nThe game is a tie");
                break;
            }
        }


    }

}
public class Game {

    char [][] grid = new char[3][3];



    public void setup_game()
    {
        for (int i = 0; i < 3; i++)
            for (int p=0; p < 3; p++)
                grid [i][p]= ' ';
    }


    public boolean slot_already_taken(int row, int column)
    {
        if( (row>2 || column>2) || (row<0 || column <0) )
            return true;

        else if(grid[row][column]=='x' || grid[row][column]=='o')
            return true;

        return false;
    }


    public void updateboard(char player, int row, int column)
    {
        grid[row][column]=player;

    }


    public void showBoard()
    {

        System.out.println("  0  " + grid[0][0] + "|" + grid[0][1] + "|" + grid[0][2]);
        System.out.println("    -------");
        System.out.println("  1  " + grid[1][0] + "|" + grid[1][1] + "|" + grid[1][2]);
        System.out.println("    -------");
        System.out.println("  2  " + grid[2][0] + "|" + grid[2][1] + "|" + grid[2][2]);
        System.out.println("     0 1 2 ");
    }


    public char switch_players(char player) {
        char newTurn='e';
        if (player == 'o')
            newTurn='x';
        if (player == 'x')
            newTurn='o';
        return newTurn;
    }


    public boolean check_for_winner() {
        if( grid [0][0]==grid[1][0] && grid[1][0] == grid[2][0] && (grid [0][0]=='x' || grid [0][0]=='o'))
            return true;
        else if( grid [0][1]==grid[1][1] && grid[1][1] == grid[2][1] && (grid [0][1]=='x' || grid [0][1]=='o'))
            return true;
        else if( grid [0][2]==grid[1][2] && grid[1][2] == grid[2][2] && (grid [0][2]=='x' || grid [0][2]=='o'))
            return true;
        else if( grid [0][0]==grid[0][1] && grid[0][1] == grid[0][2] && (grid [0][0]=='x' || grid [0][0]=='o'))
            return true;
        else if( grid [1][0]==grid[1][1] && grid[1][1] == grid[1][2] && (grid [1][0]=='x' || grid [1][0]=='o'))
            return true;
        else if( grid [2][0]==grid[2][1] && grid[2][1] == grid[2][2] && (grid [2][0]=='x' || grid [2][0]=='o'))
            return true;
        else if( grid [0][0]==grid[1][1] && grid[1][1] == grid[2][2] && (grid [0][0]=='x' || grid [0][0]=='o'))
            return true;
        else if( grid [2][0]==grid[1][1] && grid[1][1] == grid[0][2] && (grid [2][0]=='x' || grid [2][0]=='o'))
            return true;
        else
            return false;
    }


    public boolean check_if_tie() {
        for (int i = 0; i < 3; i++)
            for (int p=0; p < 3; p++)
                if(grid [i][p]==' ')
                    return false;

        return true;
    }



}