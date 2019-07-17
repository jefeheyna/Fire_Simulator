/**
 * Created by Jeff Hejna on 4/13/2015.
 * -----------------------------------------------------------------------------------------
 * The purpose of this program is to create a fire simulator that creates a terrain from the
 * Terrain() class and allows fires to spread based on probability.
 * -----------------------------------------------------------------------------------------
 */

public class FireSimulator {
    private int W; //Width
    private int H; //Height
    private double P; //Probability
    private Terrain T; //Terrain
    private int steps=0; //number of steps that the fire spreads

    /**
     * Constructor for the FireSimulator Class which utilizes the Terrain Class.
     * @param width width of the grid
     * @param height height of the grid
     * @param probability probability of cell to catch fire
     */
    public FireSimulator(int width,int height,double probability){
        this.W=width; //Width of grid
        this.H=height; //Height of grid
        this.P=probability; //probability to catch fire
        this.T=new Terrain(width, height); //Terrain object
    }

    /**
     * Starts a fire in a random location.
     */
    public void startFire(){
        int x=(int)(Math.random()*(this.W));
        int y=(int)(Math.random()*(this.H));
        this.T.setBurning(x,y);
        this.T.update();
    }

    /**
     * Calls spread() and itself if there are still cells that have fire. Otherwise, it calls report().
     */
    public void spreadFire(){
        int n=0;
        for (int i = 0; i < this.W; i++) {
            for (int j = 0; j < this.H; j++) {
                if(this.T.state[i][j]==1){
                    n++;
                }
            }
        }
        if(n!=0){
            spread();
            try {Thread.sleep(1000);} catch (InterruptedException ie) {}
            this.steps++;
            spreadFire();
        }
        else{
            System.out.println(report(this.steps));
        }
    }

    /**
     * This method prints out the report of how many cells were burned and how many steps it took to burn out.
     * @param numSteps The number of steps it took for the fire to burn out.
     * @return A string that states the percentage of cells burned and the number of steps for the fire to burn out.
     */
    public String report(int numSteps){
        double empty=0;
        double total=0;
        for (int i = 0; i < this.W; i++) {
            for (int j = 0; j < this.H; j++) {
                if(this.T.state[i][j]==0){
                    empty++;
                }
                total++;
            }
        }
        double percentage=(empty/total)*100;
        return("The percentage of cells burned was: "+percentage+"%. The number of steps it took for the fire to burn out was: "+numSteps+".");
    }

    /**
     * Spread the fire to other cells based on the probability of the fire spreading. It can spread north,south,east, or west. The fire will burn out after it is on fire.
     */
    public void spread(){
        //creating random numbers for different probabilities to spread to adjacent cells.
        double randomNum1=Math.random();
        double randomNum2=Math.random();
        double randomNum3=Math.random();
        double randomNum4=Math.random();

        //loops through all the cells in the grid.
        for (int i = 0; i < this.W; i++) {
            for (int j = 0; j <this.H ; j++) {
                //these statements check the different direction to see if the fire can spread to these locations.
                if(this.T.state[i][j]==1) {
                    if (i + 1 < this.W && this.T.state[i + 1][j] == 2 && randomNum1 < this.P) {
                        this.T.setBurning(i + 1, j);
                        if (i - 1 >= 0 && this.T.state[i - 1][j] == 2 && randomNum2 < this.P) {
                            this.T.setBurning(i - 1, j);
                            if (j + 1 < this.H && this.T.state[i][j + 1] == 2 && randomNum3 < this.P) {
                                this.T.setBurning(i, j + 1);
                                if (j - 1 >= 0 && this.T.state[i][j - 1] == 2 && randomNum4 < this.P) {
                                    this.T.setBurning(i, j - 1);
                                }
                            }
                        }
                    }else if (i - 1 >= 0 && this.T.state[i - 1][j] == 2 && randomNum2 < this.P) {
                        this.T.setBurning(i - 1, j);
                        if (j + 1 < this.H && this.T.state[i][j + 1] == 2 && randomNum3 < this.P) {
                            this.T.setBurning(i, j + 1);
                            if (j - 1 >= 0 && this.T.state[i][j - 1] == 2 && randomNum4 < this.P) {
                                this.T.setBurning(i, j - 1);
                            }
                        }
                    }else if (j + 1 < this.H && this.T.state[i][j + 1] == 2 && randomNum3 < this.P) {
                        this.T.setBurning(i, j + 1);
                        if (j - 1 >= 0 && this.T.state[i][j - 1] == 2 && randomNum4 < this.P) {
                            this.T.setBurning(i, j - 1);
                        }
                    }else if (j - 1 >= 0 && this.T.state[i][j - 1] == 2 && randomNum4 < this.P) {
                        this.T.setBurning(i, j - 1);
                    }
                    this.T.nextState[i][j]=0; //sets the cells that were on fire to empty
                    //creates new random numbers for the other fires.
                    randomNum1=Math.random();
                    randomNum2=Math.random();
                    randomNum3=Math.random();
                    randomNum4=Math.random();
                }
            }
        }
        this.T.update();
    }

    /**
     * The main method which allows the user to create the grid size, choose a probability of the fire spreading, and number of fires to start.
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("What do you want the height to be? : ");
        int H = StdIn.readInt();
        System.out.println("What do you want the width to be? : ");
        int W = StdIn.readInt();
        System.out.println("What do you want the probability to start a fire to be? : ");
        double P=StdIn.readDouble();
        System.out.println("How many fires do you want to light? : ");
        int numFires=StdIn.readInt();

        //creating different probabilities for the fire to spread based on weather conditions that can randomly happen.
        double weather=Math.random();
        if(weather<=0.1){
            System.out.println("OH NO! IT IS SO HOT AND DRY THAT THE FIRE IS MORE LIKELY TO SPREAD!!");
            System.out.println();
            if(P<=.25){
                P=P+.70;
            }else if(P<=.50){
                P=P+.45;
            }else if(P<=.75){
                P=P+.20;
            }else if(P<=.90){
                P=P+.10;
            }else{P=P+.05;}
        }else if(weather<=0.2){
            System.out.println("HURRAY! IT STARTED TO RAIN SO THE FIRE IS LESS LIKELY TO SPREAD!!");
            System.out.println();
            if(P>=.90){
                P=P-.70;
            }else if(P>=.75){
                P=P-.60;
            }else if(P>=.50){
                P=P-.40;
            }else if(P>=.25){
                P=P-.20;
            }else{P=P-P/2;}
        }else if(weather<=0.3){
            System.out.println("THERE WAS A LIGHTNING STRIKE! ANOTHER FIRE HAS STARTED!");
            System.out.println();
            numFires++;
        }

        FireSimulator fs=new FireSimulator(W,H,P);
        for (int i = 0; i < numFires; i++) {
            fs.startFire();
        }
        fs.spreadFire();
    }
}
