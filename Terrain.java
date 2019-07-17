/**
 * Created by Jeff Hejna on 3/31/2015.
 * -----------------------------------------------------------------
 * This program creates a grid of cells to represent a forest. It
 * can start a fire in a cell and update it to the appropriate color.
 * Green - forest
 * Red - on fire
 * Black - empty
 * -----------------------------------------------------------------
 */
import java.awt.*;

public class Terrain {

    private int W;  // Width of Grid
    private int H;  // Height of Grid
    public int[][] state; //0 - empty, 1 - burning, 2- forest
    public int[][] nextState; //0 - empty, 1 - burning, 2- forest

    /**
     * constructor for Terrain Class
     * @param width width of the grid
     * @param height height of the grid
     */
    public Terrain (int width, int height){
        this.W = width;
        this.H = height;

        this.state = new int[W][H];
        this.nextState = new int[W][H];

        //initializes all cells in grid to forest
        for (int i = 0; i<W; i++){
            for (int j=0; j <H; j++){
                this.state[i][j] = 2;
                this.nextState[i][j] = 2;
            }
        }

        StdDraw.setCanvasSize(500,500);
        StdDraw.setXscale(0, W);
        StdDraw.setYscale(0, H);
        this.update();
    }

    /**
     * updates terrain by swapping next state as current state and redrawing
     */
    public void update(){

        for (int i = 0; i<W; i++){
            for (int j=0; j <H; j++){
                this.state[i][j] = this.nextState[i][j];

                Color c = new Color(30,30,30);
                if (this.state[i][j] == 1){ c = new Color(255,30,30);}
                else if (this.state[i][j] == 2){ c = new Color(30,200,30);}
                StdDraw.setPenColor(c);
                StdDraw.filledSquare(i+.45, j+.45, 0.45);
            }
        }
    }

    /**
     * This method sets a cell to burning.
     * @param x x coordinate of cell
     * @param y y corrdinate of cell
     */
    public void setBurning(int x,int y){
        this.nextState[x][y]=1;
    }

    /**
     * This method sets a cell to empty.
     * @param x x coordinate of cell
     * @param y y coordinate of cell
     */
    public void setEmpty(int x,int y){
        this.nextState[x][y]=0;
    }

    /**
     * Sample main method that tests Terrain Class -
     * @param args
     */
    public static void main(String[] args) {
        Terrain t = new Terrain(8,10);
        //t.update();
        t.setBurning(3,5);
        t.setEmpty(1,2);
        t.update();
    }
}