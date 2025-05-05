package board.tiles.general;

import cards.CommunityChestCard;
import players.Player;

public class CommunityChestTile extends Tile {

    public CommunityChestTile(String name) {
        super(name);
    }

    @Override
    public String getType() {
        return "Community Chest";
    }

    @Override
    public void tileAction(Player player) {

    }
    public void tileAction(Player player, Player[] allPlayers) {
        System.out.println(player.getName() + " landed on Community Chest! Drawing a card...");
        CommunityChestCard card = CommunityChestCard.drawCard();

        if (card != null) {
            System.out.println("Card drawn: " + card.getDescription());
            card.applyChestEffect(player, allPlayers);
        } else {
            System.out.println("No Community Chest cards remaining.");
        }
    }
}
