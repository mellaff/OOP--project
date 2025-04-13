package game.mechanics;
import java.util.Random;

public class Dice {

	/*DiceEventListener is an interface used to notify other classes
 	 when a dice roll has occurred.
 
 	 This allows us to separate the logic of the dice from the logic
 	 of what happens *after* a roll — for example, updating the player’s position,
 	 checking for doubles, or triggering tile actions.*/
	public interface DiceEventListener {
    		void onDiceRolled(int die1, int die2);
	}
    	// Variables to store the two dice values
    	private int die1;
    	private int die2;

    	// Random number generator for rolling dice
    	private Random random;

    	// Listener to notify when dice are rolled
    	private DiceEventListener listener;

    	// Constructor: initializes the random number generator
    	public Dice() {
        	random = new Random();
    	}

    	// Sets the event listener (used to notify other parts of the game about dice results to furtherly use those results for following actions)
    	public void setDiceEventListener(DiceEventListener listener) {
        	this.listener = listener;
    	}

    	// Rolls the dice with a fake animation and prints dices
    	public void rollWithAnimation() {
        	System.out.print("Rolling the dice...");

        	try {
            		// Show fake loading dots for suspense
            		for (int i = 0; i < 5; i++) {
                		Thread.sleep(200);
                		System.out.print(".");
            		}
            		System.out.println();
        	} catch (InterruptedException e) {
            		e.printStackTrace();
        	}

        	// Rolls the dice: random numbers between 1 and 6
        	die1 = random.nextInt(6) + 1;
        	die2 = random.nextInt(6) + 1;

        	// Notifies listener that dice have been rolled (if set)
        	if (listener != null) {
            		listener.onDiceRolled(die1, die2);
        	}

        	// Prints out the dice using PrintDice
        	printDice(die1, die2);
    	}
    	// Accessor for die1
    	public int getDie1() {
        	return die1;
    	}
    	// Accessor for die2
    	public int getDie2() {
        	return die2;
    	}
    	// Accessor total of both dice
    	public int getTotal() {
        	return die1 + die2;
    	}
    	// Checks if the player rolled a double
    	public boolean isDouble() {
        	return die1 == die2;
    	}
    	// String representation for dice result
    	@Override
    	public String toString() {
        	return "Dice rolled: " + die1 + " and " + die2 + "\nTotal steps: " + getTotal() +
            	(isDouble() ? "\nDoubles! You get to roll again!" : "");
    	}
    	// Prints dice visually side by side
    	private void printDice(int d1, int d2) {
        	String[] die1Art = getDieFace(d1);
        	String[] die2Art = getDieFace(d2);

        	System.out.println("You rolled:");
        	for (int i = 0; i < die1Art.length; i++) {
            		System.out.println(die1Art[i] + "   " + die2Art[i]);
        	}
        	System.out.println(toString());
    	}
    	// Returns pattern for a given die face value (1–6)
	private String[] getDieFace(int value) {
    		// Grid positions (3x3): 1-9
    		boolean[] dots = new boolean[9];

    		switch (value) {
        		case 1:
            			dots[4] = true; break; // center
        		case 2:
        		    dots[0] = dots[8] = true; break; // top-left and bottom-right
        		case 3:
            			dots[0] = dots[4] = dots[8] = true; break; // top-left, center, bottom-right
       			case 4:
            			dots[0] = dots[2] = dots[6] = dots[8] = true; break; // four corners
        		case 5:
            			dots[0] = dots[2] = dots[4] = dots[6] = dots[8] = true; break; // four corners + center
        		case 6:
            			dots[0] = dots[2] = dots[3] = dots[5] = dots[6] = dots[8] = true; break; // 2 rows of 3 dots
        	default:
            		break;
    		}
    		// Constructs the die face line by line
    		return new String[]{
        		"---------",
        		"| " + (dots[0] ? "*" : " ") + " " + (dots[1] ? "*" : " ") + " " + (dots[2] ? "*" : " ") + " |",
        		"| " + (dots[3] ? "*" : " ") + " " + (dots[4] ? "*" : " ") + " " + (dots[5] ? "*" : " ") + " |",
       			"| " + (dots[6] ? "*" : " ") + " " + (dots[7] ? "*" : " ") + " " + (dots[8] ? "*" : " ") + " |",
        		"---------"
    		};
	}
    	// Main method for quick testing
    	public static void main(String[] args) {
        	Dice dice = new Dice();

        	// Rolls the dice with visual animation
        	dice.rollWithAnimation();
    	}
}
