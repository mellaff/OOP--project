package cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import players.Player;
import javax.swing.*;  // Add this for GUI dialogs

public class CommunityChestCard {
    private String description;
    private String effect;

    // Static list shared by all players
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
            new CommunityChestCard("You planted trees on Tree Planting Day.", "Advance to the next station."),
            new CommunityChestCard("Lost wallet found at Cascade.", "Return it. Gain $10 in gratitude from the owner.")
    ));

    public CommunityChestCard(String description, String effect) {
        this.description = description;
        this.effect = effect;
    }

    public String getDescription() {
        return description;
    }

    public String getEffect() {
        return effect;
    }

    // Draw a random card and remove it from the list
    public static CommunityChestCard drawCard() {
        if (cardsCommunity.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No more Community Chest cards left.");
            return null;
        }

        Random rand = new Random();
        int index = rand.nextInt(cardsCommunity.size());
        return cardsCommunity.get(index); // Remove and return the drawn card
    }

    // Apply the effect of the card to the player
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
                player.setMoveToNearestCity(true); // You need to define this method
                break;
            case "Remove one negative card effect.":
                player.addGetOutOfJailFreeCard();// Assume this clears jail or similar penalty
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
                player.setMoveToNearestStation(true); // You'll define this based on your board layout
                break;
            case "Return it. Gain $10 in gratitude from the owner.":
                player.receive(10);
                break;
            default:
                break;
        }
    }
}
