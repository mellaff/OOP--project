package cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class CommunityChestCards {
    private String description;
    private String effect;

    // Static list shared by all players
    private static ArrayList<CommunityChestCards> cards = new ArrayList<>(Arrays.asList(
            new CommunityChestCards("Your aunt invited you to a khorovats picnic.", "Miss 1 turn to help prepare."),
            new CommunityChestCards("You helped your grandma make cherry preserves.", "Collect $25 from each player for your kindness."),
            new CommunityChestCards("You donated books to a rural school.", "Collect $100 as a thank-you grant."),
            new CommunityChestCards("You opened a tarmarzakan (tailor shop).", "Advance to the nearest commercial property."),
            new CommunityChestCards("Blessing from a village grandmother.", "Remove one negative card effect."),
            new CommunityChestCards("Your neighbor’s chickens broke into your garden.", "Pay $40 for damage repair."),
            new CommunityChestCards("You overspent at Yerevan Mall.", "Pay $100 for impulse buys."),
            new CommunityChestCards("You graduated from AUA with honors!", "Collect $200 scholarship bonus."),
            new CommunityChestCards("You hosted a charity khorovats fundraiser.", "Everyone donates $25 to you."),
            new CommunityChestCards("You started a tan business at Vernissage.", "Collect $75."),
            new CommunityChestCards("You accidentally spilled homemade soap at GUM Market.", "Pay $30 in clean-up costs."),
            new CommunityChestCards("You helped renovate your family dacha.", "Pay $50 for supplies."),
            new CommunityChestCards("You received lavash and cheese from your uncle in the village.", "Enjoy the snack. Collect $20."),
            new CommunityChestCards("You won first place in a patriotic poetry contest.", "Collect $150."),
            new CommunityChestCards("You planted trees on Tree Planting Day.", "Advance to the nearest park or garden space."),
            new CommunityChestCards("Lost wallet found at Cascade.", "Return it. Gain $10 in gratitude from the owner.")
    ));

    public CommunityChestCards(String description, String effect) {
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
    public static CommunityChestCards drawCard() {
        if (cards.isEmpty()) {
            System.out.println("No more Community Chest cards left.");
            return null;
        }

        Random rand = new Random();
        int index = rand.nextInt(cards.size());
        return cards.remove(index); // Remove and return the drawn card
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
            case "Advance to the nearest commercial property.":
                player.moveTo(3); // You need to define this method
                break;
            case "Remove one negative card effect.":
                player.getOutOfJail(); // Assume this clears jail or similar penalty
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
            case "Advance to the nearest park or garden space.":
                player.moveTo(13); // You'll define this based on your board layout
                break;
            case "Return it. Gain $10 in gratitude from the owner.":
                player.receive(10);
                break;
            default:
                System.out.println("Unknown effect: " + effect);
                break;
        }
    }
}
