package cards;

/**
 * Represents a generic base class for all card types in the game.
 * Specific types like ChanceCard and CommunityChestCard should extend this class.
 */
public class Card {

    /**
     * Returns the type of the card.
     * Subclasses can override this method to return more specific types.
     *
     * @return the string "Card"
     */
    public String getType() {
        return "Card";
    }
}
