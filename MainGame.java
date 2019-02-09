/**
 * Name: Abdul Moiz Munir Sohu
 * Recitation no.: 216
 * PennKey: sohu
 * Implementation: This is the MainGame.java class, and this is where we run 
 * the entire the game of connect 4.
 */

//We import the color class to later use it & the Color object for our discs
import java.awt.Color;

//The MainGame function that runs the game
public class MainGame {    
    private static String [][] discArray;
    private static String colorTurn = "";
    private static boolean winCheck;
    private static boolean drawCheck;
    
    /**
     * This is our main function, it presents starting screen for the very
     * first time to the user
     * Input: Takes in string arguments, but we don't really use that
     * Output: none, the first occurence of starting screen.
     */
    
    public static void main(String[] args) {
        //Set scale so it's easy to navigate board when we need to draw discs
        PennDraw.setXscale(0, 8);
        PennDraw.setYscale(0, 7);
        startingScreen();
    }
    
    /**
     * This is our menu function that uses the menu-drawing and clicks to begin 
     * the game
     * Input: none, just making sure the game goes ahead and begins
     * Output: None, only letting the game begin.
     */
    
    public static void startingScreen() {
        drawMenu();
        
        //keep checking for mouse click with 'while true'
        while (true && !winCheck) {
            // if the mouse is clicked
            if (PennDraw.mousePressed()) {
                // get the coordinates of the mouse cursor
                double x = PennDraw.mouseX();
                double y = PennDraw.mouseY();
                
                /**check which region the mouse click was in and proceed
                  * accordingly
                  */
                if (y >= 2.5 && y <= 4.5 && 
                    x >= 1.0 && x <= 3.0) {
                    /**calling single player because that region has been 
                      * clicked into
                      */
                    singlePlayer(); 
                    
                    
                } else if (y >= 2.5 && y <= 4.5 && 
                           x >= 5.0 && x <= 7.0) {
                    doublePlayer();
                }
            }
        }
    }        
    
    /**
     * This is our function that draws the Connect4 menu using PennDraw
     * Input: None, just draws the board
     * Output: Side effect of this is the menu is represented on the screen
     */
    
    public static void drawMenu() {  
        //use PennDraw to draw the menu
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.filledRectangle(4.0, 3.5, 4, 3.5);
        PennDraw.setPenColor(PennDraw.WHITE);
        PennDraw.square(2.0, 3.5, 1.0);
        PennDraw.square(6.0, 3.5, 1.0);
        PennDraw.text(2.0, 3.5, "1 Player");
        PennDraw.text(6.0, 3.5, "2 Player");
    }
    
    /**
     * This is our function that draws the Connect4 board using PennDraw
     * Input: None, just draws the board
     * Output: Side effect of this is the board is represented on the screen
     */
    
    public static void drawBoard() {
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.filledRectangle(4, 3.5, 4, 3.5);
        PennDraw.setPenColor(PennDraw.WHITE);
        //Loop to create vertical lines i.e. 7 divisions 
        for (int i = 1; i <= 7; i++) {
            for (int j = 1; j <= 6; j++) {
                PennDraw.filledCircle(i, j, 0.3);
            }
        }
    }
    
    /**
     * This is our function that changes the color after every turn, the 
     * function is incorporated into our game player functions to alternate
     * turns
     * Input: none
     * Output: none, just does the process of changing turns
     */
    
    public static void changeColorTurn() {
        if (colorTurn.equals("red")) {
            colorTurn = "blue";
        } else {
            colorTurn = "red";
        }
    }
    
    /**
     * This is our function for the single player mode
     * Input: None
     * Output: Plays the single player version of the game, i.e. user against
     * computer
     */
    
    public static void singlePlayer() {
        /**
         * make a 2D array for the discs such that they're represented on the 
         * scope of this board
         */
        winCheck = false;
        drawCheck = false;
        discArray = new String[8][7];
        colorTurn = "red";
        int xRounded = 0;
        int yRounded = 0;
        boolean valid = false;
        int lowest = -1;
        String winner = "";
        
        
        //make sure all holes are empty before the game begins
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 6; j++) {
                discArray[i][j] = "empty";
            }
        }
        
        boolean next = true;
        
        //game begins by drawing the board
        drawBoard();
        
        
        //Now we check for mouse-clicks and draw a disc where mouse is clicked
        while (true && !winCheck) {
            
            double x = 0.0;
            double y = 0.0;
            // if the mouse is clicked
            if (PennDraw.mousePressed() & !next) {
                // get the coordinates of the mouse cursor
                x = PennDraw.mouseX();
                xRounded = (int) (double) Math.round(x);
                y = PennDraw.mouseY();
                yRounded = (int) (double) Math.round(y);
            }
            next = PennDraw.mousePressed();
            
            /**check if the region mouse click happened in was a valid region
              * to be clicked in i.e. there exists an empty hole 
              */
            if (isValidClick(x, y)) {
                valid = true;
                lowest = getLowestHole(xRounded);
                //player turn
                Disc disc = new Disc(xRounded, lowest, 
                                     getColor(colorTurn));
                disc.drawDisc();
                discArray[xRounded][lowest] = colorTurn;
                valid = false;
                changeColorTurn();
                
                
                //do computer random turn
                discArray[0][0] = "full";
                int xRandom = 0;
                int yRandom = 0; 
                
                while (!isHoleEmpty(xRandom, yRandom)) {
                    xRandom = (int) Math.ceil(Math.random() * 7);
                    yRandom =  getLowestHole(xRandom); 
                }
                
                Disc randomDisc = new Disc(xRandom, yRandom, 
                                           getColor(colorTurn));
                randomDisc.drawDisc();
                discArray[xRandom][yRandom] = colorTurn;
                changeColorTurn();
            }
            
            
            
            winner = winCheck();
            if (winner.equals("blue")) {
                winner = "computer";
            }
            else if (winner.equals("red")) {
                winner = "you";
            }
            
            boolean filled = checkForFilled();
            
            if (filled && !winCheck) {
                System.out.println("draw");
                drawCheck = true;
                break;
            }
            
            if (winCheck) {
                break;
            }
            
        }
        
        if (drawCheck) {
            showDraw();
        } else if (winCheck) {
            showWinner(winner);
        }
        
        //returning to menu after the game has concluded
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.filledRectangle(4, 6.6, 0.7, 0.25);
        PennDraw.setPenColor(PennDraw.WHITE);
        PennDraw.text(4, 6.6, "Press 'F' for menu");
        
        while (true && (winCheck || drawCheck)) {
            if (PennDraw.hasNextKeyTyped()) {
                char k = PennDraw.nextKeyTyped();
                if (k == 'f') {
                    winCheck = false;
                    drawCheck = false;
                    break; 
                }
            }
        }
        
        startingScreen();
        
    }
    
    /**
     * This is our function for the double player mode
     * Input: None
     * Output: Plays the double player version of the game
     */
    
    public static void doublePlayer() {
        /**
         * make a 2D array for the discs such that they're represented on the 
         * scope of this board
         */
        discArray = new String[8][7];
        colorTurn = "red";
        int xRounded = 0;
        int yRounded = 0;
        boolean valid = false;
        int lowest = -1;
        String winner = "";
        winCheck = false;
        drawCheck = false;
        
        //make sure all holes are empty before the game begins
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 6; j++) {
                discArray[i][j] = "empty";
            }
        }
        
        boolean next = true;
        
        //game begins by drawing the board
        drawBoard();
        PennDraw.setPenColor(PennDraw.WHITE);
        PennDraw.text(4, 6.6, colorTurn + "'s turn");
        
        //Now we check for mouse-clicks and draw a disc where mouse is clicked
        while (true && !winCheck) {
            double x = 0.0;
            double y = 0.0;
            // if the mouse is clicked
            if (PennDraw.mousePressed() & !next) {
                // get the coordinates of the mouse cursor
                x = PennDraw.mouseX();
                xRounded = (int) (double) Math.round(x);
                y = PennDraw.mouseY();
                yRounded = (int) (double) Math.round(y);
            }
            next = PennDraw.mousePressed();
            
            /**check if the region mouse click happened in was a valid region to
              * be clicked in 
              */
            if (isValidClick(x, y)) {
                valid = true;
                lowest = getLowestHole(xRounded);
            }
            
            //if region is valid, put a disc there!
            if (valid == true) {
                Disc d = new Disc(xRounded, lowest, 
                                  getColor(colorTurn));
                d.drawDisc();
                discArray[xRounded][lowest] = 
                    colorTurn;
                valid = false;
                changeColorTurn();
                PennDraw.setPenColor(PennDraw.BLACK);
                PennDraw.filledRectangle(4, 6.6, 0.7, 0.25);
                PennDraw.setPenColor(PennDraw.WHITE);
                PennDraw.text(4, 6.6, colorTurn + "'s turn");
            }
            
            winner = winCheck();
            boolean filled = checkForFilled();
            
            if (filled && !winCheck) {
                drawCheck = true;                
                break;
            }
            
            if (winCheck) {
                break;
            }
            
        }
        
        if (drawCheck) {
            showDraw();
        } else if (winCheck) {
            showWinner(winner);
        }
        
        //returning to menu after the game has concluded
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.filledRectangle(4, 6.6, 0.7, 0.25);
        PennDraw.setPenColor(PennDraw.WHITE);
        PennDraw.text(4, 6.6, "Press 'F' for menu");
        
        while (true && (winCheck || drawCheck)) {
            if (PennDraw.hasNextKeyTyped()) {
                char k = PennDraw.nextKeyTyped();
                if (k == 'f') {
                    winCheck = false;
                    drawCheck = false;
                    break; 
                }
            }
        }
        
        startingScreen();
        
    }    
    
    /**
     * This is our function that puts on screen the fact that a draw has been
     * reached
     * Input: Nothing, just puts on screen, using PennDraw that there has been
     * a case of draw
     * Output: puts on screen, using PennDraw that there has been
     * a case of draw
     */
    
    public static void showDraw() {
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.filledRectangle(4, 3.5, 1, .5);
        PennDraw.setPenColor(PennDraw.WHITE);
        PennDraw.text(4, 3.5, "It's a draw");
    }
    
    /**
     * This is our function that puts on screen the winner 
     * Input: The winner's color, or winner, basically, as a string
     * Output: none, uses PennDraw to put winner on screen
     */
    
    public static void showWinner(String w) {
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.filledRectangle(4, 3.5, 1, 0.5);
        PennDraw.setPenColor(PennDraw.WHITE);
        PennDraw.text(4, 3.5, w + " won");
    }
    
    /**
     * This is our function that checks if the hole is filled (does so by
     * going through the entire grid)
     * Input: none
     * Output: yes or no (boolean return, depending on whether hole is filled
     * or not)
     */
    
    public static boolean checkForFilled() {
        boolean filled = true; 
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 7; j++) {
                if (discArray[i][j].equals("empty")) {
                    filled = false;
                }     
            }
        }
        return filled; 
    }
    
    /**
     * This is our function to get color of the current player's disc
     * Input: takes in a string, as we have encoded the color of our discs
     * as strings
     * Output: compares strings to colors, and output corresponding COLOR
     */
    
    public static Color getColor(String c) {
        Color color = null;
        if (c.equals("red")) {
            color = Color.RED;
        } else if (c.equals("blue")) {
            color = Color.BLUE; 
        }
        return color;            
    }
    
    /**
     * This is our function that checks if the hole has a disc or not, returns
     * true if hole is empty
     * Input: the x and y coordinates of the user's click
     * Output: yes or no (a boolean return, true or false), depending on whether
     * hole is empty or not
     */
    
    public static boolean isHoleEmpty(int x, int y) {
        return discArray[x][y].equals("empty");
    }
    
    /**
     * This is our function that checks if the user-click is in a 
     * clickable region i.e a circle area
     * Input: the x and y coordinates of the user's click
     * Output: yes or no (a boolean return, true or false), depending on whether
     * click is valid or not
     */
    
    public static boolean isValidClick(double x, double y) {
        boolean canPlace = false;
        
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 7; j++) {
                
                if ((y <= j + 0.3) && (x <= i + 0.3) && 
                    (y >= j - 0.3) && (x >= i - 0.3)) {
                    for (int b = 1; b < 7; b++) {
                        if (isHoleEmpty(i, b)) {
                            canPlace = true;
                        }
                    }
                }
            }
        }
        return canPlace;
    }
    
    /**
     * This is our function that gets us the lowest hole that is empty
     * Input: takes in the integer value of x-cooridnate of column
     * Output: gives us the integer value for y coordinate of the lowest
     * hole that is empty
     */
    
    public static int getLowestHole(int x) {
        int y = 1;
        for (int b = 1; b < 7; b++) {
            if (isHoleEmpty(x, b) == true) {
                y = b;
                break;
            }
        }        
        return y;
    }
    
    /**
     * This is our win-checker function, and checks for any winning conditions
     * Input: none, just the process for checking four-in-a-row
     * Output: outputs the winner string i.e. "blue" or "red"
     */
    
    public static String winCheck() {
        String winner = "none";
        
        //Checking for horizontal winner case
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j < 7; j++) {
                if (discArray[i][j].equals("red")) {
                    if (discArray[i + 1][j].equals("red")) {
                        if (discArray[i + 2][j].equals("red")) {
                            if (discArray[i + 3][j].equals("red")) {
                                winner = "red";
                                winCheck = true;
                            }
                        }
                    }
                } else if (discArray[i][j].equals("blue")) {
                    if (discArray[i + 1][j].equals("blue")) {
                        if (discArray[i + 2][j].equals("blue")) {
                            if (discArray[i + 3][j].equals("blue")) {
                                winner = "blue";
                                winCheck = true;
                            }
                        }
                    }
                } 
            }           
        }
        
        //Checking for vertical winner case
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 4; j++) {
                if (discArray[i][j].equals("red")) {
                    if (discArray[i][j + 1].equals("red")) {
                        if (discArray[i][j + 2].equals("red")) {
                            if (discArray[i][j + 3].equals("red")) {
                                winner = "red";
                                winCheck = true;
                            }
                        }
                    }
                }
                else if (discArray[i][j].equals("blue")) {
                    if (discArray[i][j + 1].equals("blue")) {
                        if (discArray[i][j + 2].equals("blue")) {
                            if (discArray[i][j + 3].equals("blue")) {
                                winner = "blue";
                                winCheck = true;
                            }
                        }
                    }
                }
            }     
        }
        
        //Check for diagnol cases- both directions, this is the +ve direction:
        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 3; j++) {
                if (discArray[i][j].equals("red")) {
                    if (discArray[i + 1][j + 1].equals("red")) {
                        if (discArray[i + 2][j + 2].equals("red")) {
                            if (discArray[i + 3][j + 3].equals("red")) {
                                winner = "red";
                                winCheck = true;
                            }
                        }
                    }
                } else if (discArray[i][j].equals("blue")) {
                    if (discArray[i + 1][j + 1].equals("blue")) {
                        if (discArray[i + 2][j + 2].equals("blue")) {
                            if (discArray[i + 3][j + 3].equals("blue")) {
                                winner = "blue";
                                winCheck = true;
                            }
                        }
                    }
                }
            }       
        }
        
        //Check for diagnol cases- both directions, this is the -ve direction:
        for (int i = 7; i > 3; i--) {
            for (int j = 1; j <= 3; j++) {
                if (discArray[i][j].equals("red")) {
                    if (discArray[i - 1][j + 1].equals("red")) {
                        if (discArray[i - 2][j + 2].equals("red")) {
                            if (discArray[i - 3][j + 3].equals("red")) {
                                winner = "red";
                                winCheck = true;
                            }
                        }
                    }
                } else if (discArray[i][j].equals("blue")) {
                    if (discArray[i - 1][j + 1].equals("blue")) {
                        if (discArray[i - 2][j + 2].equals("blue")) {
                            if (discArray[i - 3][j + 3].equals("blue")) {
                                winner = "blue";
                                winCheck = true;
                            }
                        }
                    }
                }
            }       
        }
        return winner;
    }
}