package cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ChanceCards {
    private String description;
    private String effect;

   
    private static ArrayList<ChanceCards> cards = new ArrayList<>(Arrays.asList(
            new ChanceCards("You visited Tatev Monastery.", "Gain spiritual bonus. Collect $100."),
            new ChanceCards("You took the Wings of Tatev and dropped your wallet.", "Pay $50 for a replacement ID."),
            new ChanceCards("You organized a jazz night in Yerevan.", "All players pay you $50 each."),
            new ChanceCards("You helped with the grape harvest in Areni.", "Collect $75 for your contribution."),
            new ChanceCards("You got on a marshrutka and forgot your stop.", "Miss 1 turn finding your way back."),
            new ChanceCards("Your art exhibit opened at Cafesjian Center.", "Advance to the nearest Cultural Center property. If unowned, you may buy it."),
            new ChanceCards("You hit the jackpot at the Sevan Casino!", "Collect $150."),
            new ChanceCards("You saw a bear in Dilijan.", "Retreat to the nearest safe zone (Go back 3 spaces)."),
            new ChanceCards("Tourist season is wild. Sell photos of Mount Ararat.", "Collect $200."),
            new ChanceCards("You hosted a hiking tour in Lori.", "Collect $100 from each player."),
            new ChanceCards("Unexpected roadwork on Baghramyan Street.", "Pay $50 for tire repairs."),
            new ChanceCards("You bought too much tan and gata at Vernissage.", "Lose $20 in snacks."),
            new ChanceCards("You received a wedding invitation in Etchmiadzin.", "Advance to Etchmiadzin. If you pass GO, collect $200."),
            new ChanceCards("Real estate boom in Gyumri!", "Your next property purchase costs $100 less."),
            new ChanceCards("You danced too hard at a shurjpar.", "Miss 1 turn to recover."),
            new ChanceCards("You argued with a traffic cop over a no-parking sign.", "Go directly to Jail. Do not pass GO. Do not collect $200.")
    ));

    public ChanceCards(String description, String effect) {
        this.description = description;
        this.effect = effect;
    }

    public String getDescription() {
        return description;
    }

    public String getEffect() {
        return effect;
    }

   
    public static ChanceCards drawCard() {
        if (cards.isEmpty()) {
            System.out.println("No more chance cards left.");
            return null;
        }

        Random rand = new Random();
        int index = rand.nextInt(cards.size());
        return cards.remove(index); // Remove and return
    }

    // Apply effect to the player
    public void applyChanceEffect(Player player, Player[] players) {
        switch (this.effect) {
            case "Gain spiritual bonus. Collect $100.":
                player.receive(100);
                break;
            case "Pay $50 for a replacement ID.":
                player.pay(50);
                break;
            case "All players pay you $50 each.":
                for (Player p : players) {
                    if (!p.equals(player)) {
                        p.pay(50);
                        player.receive(50);
                    }
                }
                break;
            case "Collect $75 for your contribution.":
                player.receive(75);
                break;
            case "Miss 1 turn finding your way back.":
                player.setMissTurn(true);
                break;
            case "Advance to the nearest Cultural Center property. If unowned, you may buy it.":
                player.moveToNearestCultureCenter();
                break;
            case "Collect $150.":
                player.receive(150);
                break;
            case "Retreat to the nearest safe zone (Go back 3 spaces).":
                player.move(-3);
                break;
            case "Collect $200.":
                player.receive(200);
                break;
            case "Collect $100 from each player.":
                for (Player p : players) {
                    if (!p.equals(player)) {
                        p.pay(100);
                        player.receive(100);
                    }
                }
                break;
            case "Pay $50 for tire repairs.":
                player.pay(50);
                break;
            case "Lose $20 in snacks.":
                player.pay(20);
                break;
            case "Advance to Etchmiadzin. If you pass GO, collect $200.":
                player.moveTo(12); // Adjust as needed
                break;
            case "Your next property purchase costs $100 less.":
                player.setNextDiscount();
                break;
            case "Miss 1 turn to recover.":
                player.setMissTurn(true);
                break;
            case "Go directly to Jail. Do not pass GO. Do not collect $200.":
                player.goToJail();
                break;
            default:
                System.out.println("Unknown effect: " + this.effect);
        }
    }
}
