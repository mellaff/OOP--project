package cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import players.Player;
import javax.swing.*;

/**
 * Represents a Community Chest card with a description and effect.
 * Cards can be drawn randomly from a shared deck and applied to players.
 */
public class CommunityChestCard {
    private String description;
    private String effect;

    /**
     * Static list of Community Chest cards used in the game.
     * Cards are drawn from this shared deck.
     */
    public static ArrayList<CommunityChestCard> cardsCommunity = new ArrayList<>(Arrays.asList(
            new CommunityChestCard("Your aunt invited you to a khorovats picnic.", "Miss 1 turn to help prepare."),
            new CommunityChestCard("You helped your grandma make cherry preserves.", "Collect $25 from each player for your kindness."),
            new CommunityChestCard("You donated books to a rural school.", "Collect $100 as a thank-you grant."),
            new CommunityChestCard("You opened a tarmarzakan (tailor shop).", "Advance to the next city."),
            new CommunityChestCard("Blessing from a village grandmother.", "Remove one negative card effect."),
            new CommunityChestCard("Your neighbor’s chickens broke into your garden.", "Pay $40 for damage repair."),
            new CommunityChestCard("You overspent at Yerevan Mall.", "Pay $100 for impulse buys."),
            new CommunityChestCard("You graduated from AUA with honors!", "Collect $200 scholarship bonus."),
            new CommunityChestCard("You hosted a charity khorovats fundraiser.", "Everyone donates $25 to you."),
            new CommunityChestCard("You started a tan business at Vernissage.", "Collect $75."),
            new CommunityChestCard("You accidentally spilled homemade soap at GUM Market.", "Pay $30 in clean-up costs."),
            new CommunityChestCard("You helped renovate your family dacha.", "Pay $50 for supplies."),
            new CommunityChestCard("You received lavash and cheese from your uncle in the village.", "Enjoy the snack. Collect $20."),
            new CommunityChestCard("You won first place in a patriotic poetry contest.", "Collect $150."),
            new CommunityChestCard("You planted trees on Tree Planting Day.", "Advance to the next city."),
            new CommunityChestCard("Lost wallet found at Cascade.", "Return it. Gain $10 in gratitude from the owner.")
    ));

    /**
     * Constructs a Community Chest card with a description and effect.
     *
     * @param description the text shown to the player
     * @param effect      the logic to be executed when the card is used
     */
    public CommunityChestCard(String description, String effect) {
        this.description = description;
        this.effect = effect;
    }

    /**
     * Returns the description of the card.
     *
     * @return the descriptive text
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the effect text of the card.
     *
     * @return the effect description
     */
    public String getEffect() {
        return effect;
    }

    /**
     * Draws a random Community Chest card from the deck.
     * The drawn card is removed from the list to avoid reuse.
     *
     * @return the drawn CommunityChestCard, or null if deck is empty
     */
    public static CommunityChestCard drawCard() {
        if (cardsCommunity.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No more Community Chest cards left.");
            return null;
        }

        Random rand = new Random();
        int index = rand.nextInt(cardsCommunity.size());
        return cardsCommunity.remove(index);
    }

    /**
     * Applies the effect of the card to the given player.
     * Supports interactions with other players as well.
     *
     * @param player  the player who drew the card
     * @param players all players in the game (for effects that involve others)
     */
    public void applyChestEffect(Player player, Player[] players) {
        switch (effect) {
            case "Miss 1 turn to help prepare.":
                player.setMissTurn(true);
                break;
            case "Collect $25 from each player for your kindness.":
                for (Player p : players) {
                    if (!p.equals(player)) {
                        p.pay(25);
                        player.receive(25);
                    }
                }
                break;
            case "Collect $100 as a thank-you grant.":
                player.receive(100);
                break;
            case "Advance to the next city.":
                player.setMoveToNearestCity(true);
                break;
            case "Remove one negative card effect.":
                player.addGetOutOfJailFreeCard();
                break;
            case "Pay $40 for damage repair.":
                player.pay(40);
                break;
            case "Pay $100 for impulse buys.":
                player.pay(100);
                break;
            case "Collect $200 scholarship bonus.":
                player.receive(200);
                break;
            case "Everyone donates $25 to you.":
                for (Player p : players) {
                    if (!p.equals(player)) {
                        p.pay(25);
                        player.receive(25);
                    }
                }
                break;
            case "Collect $75.":
                player.receive(75);
                break;
            case "Pay $30 in clean-up costs.":
                player.pay(30);
                break;
            case "Pay $50 for supplies.":
                player.pay(50);
                break;
            case "Enjoy the snack. Collect $20.":
                player.receive(20);
                break;
            case "Collect $150.":
                player.receive(150);
                break;
            case "Advance to the next station.":
                player.setMoveToNearestStation(true);
                break;
            case "Return it. Gain $10 in gratitude from the owner.":
                player.receive(10);
                break;
            default:
                break;
        }
    }
}
