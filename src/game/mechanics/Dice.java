package game.mechanics;

import java.util.Random;

/**
 * Represents a pair of six-sided dice used in the game.
 * Provides methods to roll the dice, access individual values,
 * check for doubles, and get the total.
 */
public class Dice {

	private int die1;
	private int die2;
	private Random random;

	/**
	 * Constructs a Dice object and initializes the random number generator.
	 */
	public Dice() {
		random = new Random();
	}

	/**
	 * Rolls both dice, generating random values between 1 and 6.
	 */
	public void rollWithAnimation() {
		die1 = random.nextInt(6) + 1;
		die2 = random.nextInt(6) + 1;
	}

	/**
	 * Returns the value of the first die.
	 *
	 * @return value of die1 (1–6)
	 */
	public int getDie1() {
		return die1;
	}

	/**
	 * Returns the value of the second die.
	 *
	 * @return value of die2 (1–6)
	 */
	public int getDie2() {
		return die2;
	}

	/**
	 * Returns the total of both dice.
	 *
	 * @return the sum of die1 and die2
	 */
	public int getTotal() {
		return die1 + die2;
	}

	/**
	 * Checks if both dice show the same value.
	 *
	 * @return true if a double is rolled, false otherwise
	 */
	public boolean isDouble() {
		return die1 == die2;
	}

	/**
	 * Returns a string representation of the current dice roll.
	 *
	 * @return a formatted summary of the dice roll
	 */
	@Override
	public String toString() {
		return "Dice rolled: " + die1 + " and " + die2 +
				"\nTotal steps: " + getTotal() +
				(isDouble() ? "\nDoubles! You get to roll again!" : "");
	}
}
