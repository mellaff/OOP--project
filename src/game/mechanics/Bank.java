package game.mechanics;
import java.util.*;

/**
 * The Bank class handles all the financial operations in the game,
 * such as managing player balances, property transactions, rent payments,
 * mortgages, and auctions.
 */
public class Bank {
    	// Keeps track of each player's balance
    	private Map<Player, Integer> balances = new HashMap<>();

    	// Keeps track of properties that are mortgaged and their mortgage values
    	private Map<Property, Integer> mortgages = new HashMap<>();

    	
    	//Registers a new player in the game with a starting balance.
    	public void registerPlayer(Player player, int startingMoney) {
        	balances.put(player, startingMoney);
    	}

    	
      //Returns the current balance of the player.
    	public int getBalance(Player player) {
        	return balances.getOrDefault(player, 0);
    	}

    	
     	//Adds money to a player's balance.
    	public void credit(Player player, int amount) {
        	balances.put(player, getBalance(player) + amount);
        	System.out.println("Bank credited " + amount + " to " + player.getName());
    	}

    	/**
     	* Deducts money from a player's balance, if they have enough funds.
     	* Returns true if the transaction succeeded.
     	*/
    	public boolean debit(Player player, int amount) {
        	int current = getBalance(player);
        	if (current >= amount) {
            		balances.put(player, current - amount);
            		System.out.println("Bank debited " + amount + " from " + player.getName());
            		return true;
        	} else {
            		System.out.println(player.getName() + " has insufficient funds!");
            		return false;
        	}
    	}

    	/**
     	* Transfers money from one player to another.
     	* Returns true if successful.
     	*/
    	public boolean transfer(Player from, Player to, int amount) {
        	if (debit(from, amount)) {
            		credit(to, amount);
            		System.out.println("Transferred " + amount + " from " + from.getName() + " to " + to.getName());
            		return true;
        	}
        	return false;
    	}

    	/**
     	* Handles property purchasing by checking ownership and player funds.
     	* Deducts cost and updates property ownership if successful.
     	*/
    	public boolean purchaseProperty(Player player, Property property) {
        	if (property.isOwned()) {
            		System.out.println("Property already owned!");
            		return false;
        	}
        	int price = property.getPrice();
        	if (debit(player, price)) {
            		property.setOwner(player);
            		System.out.println(player.getName() + " bought " + property.getName() + " for $" + price);
            		return true;
        	}
        	return false;
    	}

    	/**
    	 * Pays rent to a property’s owner, if applicable.
     	* Skips if player owns the property.
     	*/
    	public boolean payRent(Player player, Property property) {
        	Player owner = property.getOwner();
        	if (owner == null || owner == player) return false;

        	int rent = property.getRent();
        	if (transfer(player, owner, rent)) {
            		System.out.println(player.getName() + " paid $" + rent + " rent to " + owner.getName());
            		return true;
        	}
        	return false;
    	}

    	// ----------------- MORTGAGE SYSTEM -----------------

    	/**
     	* Mortgages a property: gives the player money, marks property as mortgaged.
     	*/
    	public boolean mortgageProperty(Player player, Property property) {
        	if (!property.isOwned() || property.isMortgaged()) {
            		System.out.println("Property cannot be mortgaged!");
            		return false;
        	}
        	int mortgageValue = property.getMortgageValue();
        	credit(player, mortgageValue);
        	mortgages.put(property, mortgageValue);
        	property.setMortgaged(true);
        	System.out.println(player.getName() + " mortgaged " + property.getName() + " for $" + mortgageValue);
        	return true;
    	}

    	/**
     	* Unmortgages a property by paying back the mortgage + interest (10%).
     	*/
    	public boolean unmortgageProperty(Player player, Property property) {
        	if (!property.isMortgaged()) {
            		System.out.println("Property is not mortgaged!");
            		return false;
        	}
        	int mortgageValue = mortgages.get(property);
        	int unmortgageCost = (int) (mortgageValue * 1.1); // 10% interest
        	if (debit(player, unmortgageCost)) {
            		property.setMortgaged(false);
            		mortgages.remove(property);
            		System.out.println(player.getName() + " unmortgaged " + property.getName() + " for $" + unmortgageCost);
            		return true;
        	}
        	return false;
    	}

    	// ----------------- AUCTION SYSTEM -----------------

    	/**
     	* Starts an auction for a property where all players place a bid.
     	* Highest bidder wins and pays the bid amount.
     	*/
    	public void auctionProperty(Property property, List<Player> players) {
        	System.out.println("Auction starts for " + property.getName() + "!");
        	int highestBid = 0;
        	Player highestBidder = null;

        	for (Player player : players) {
            		int bid = player.makeBid(); // Each player decides their own bid
            		System.out.println(player.getName() + " bids $" + bid);
            		if (bid > highestBid) {
                		highestBid = bid;
                		highestBidder = player;
            		}
       	 	}

        	if (highestBidder != null && highestBid > 0) {
            		System.out.println(highestBidder.getName() + " wins the auction for $" + highestBid);
            		if (debit(highestBidder, highestBid)) {
                		property.setOwner(highestBidder);
                		System.out.println(highestBidder.getName() + " now owns " + property.getName());
            		}
        		} else {
            			System.out.println("No bids placed! Auction failed.");
        		}
    		}

    	// ----------------- RECEIPT SYSTEM -----------------

    	/**
     	* Prints the current balance for a player in a receipt format.
     	*/
    	public void printReceipt(Player player) {
        	System.out.println("Bank Receipt for " + player.getName());
        	System.out.println("Current Balance: $" + getBalance(player));
        	System.out.println("************************************");
    	}
}
