package board;

import board.tiles.general.*;
import board.tiles.property.Property;
import board.tiles.property.Station;
import cards.ChanceCard;
import cards.CommunityChestCard;
import players.Player;
import static cards.ChanceCard.cards;
import static cards.CommunityChestCard.cardsCommunity;

import java.util.ArrayList;

/**
 * Represents the main game board, containing an array of 40 tiles
 * and methods to initialize, manage, and display the board.
 */
public class Board {
    private static final Tile[] tiles = new Tile[40];
    private final int SIZE;
    private ArrayList<ChanceCard> deckChance;
    private ArrayList<CommunityChestCard> deckCommunityChest;

    /**
     * Constructs a Board object and initializes the tiles and card decks.
     */
    public Board() {
        SIZE = 40; // Standard Monopoly board size
        deckChance = cards;
        deckCommunityChest = cardsCommunity;
        initialise();
    }

    /**
     * Places a tile at a specified board position.
     *
     * @param position the index on the board (0–39)
     * @param tile     the tile to be set
     */
    public void setTile(int position, Tile tile) {
        if (position >= 0 && position < SIZE) {
            tiles[position] = tile;
        } else {
            System.out.println("Invalid position.");
        }
    }

    /**
     * Returns the tile at a given position.
     *
     * @param position the index on the board
     * @return the tile object at that index or null if invalid
     */
    public Tile getTile(int position) {
        if (position >= 0 && position < SIZE) {
            return tiles[position];
        }
        return null;
    }

    /**
     * Returns the index of a tile based on its name.
     *
     * @param tileName the name of the tile to find
     * @return the index of the tile, or -1 if not found
     */
    public static int getTilePosition(String tileName) {
        for (int i = 0; i < 40; i++) {
            if (tiles[i].getName().equals(tileName)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Initializes all tiles on the board, including properties,
     * stations, and special tiles like GO, Jail, Chance, etc.
     */
    public void initialise() {
        // Setting up important tiles
        setTile(0, new GoTile());
        setTile(SIZE / 4, new JailTile());
        setTile(2 * (SIZE / 4), new FreeParkingTile());
        setTile(3 * (SIZE / 4), new GoToJailTile(SIZE / 4));

        setTile(5, new Station("Zoravar Andranik"));
        setTile(15, new Station("Barekamutyun"));
        setTile(25, new Station("Sasuntsi Davit"));
        setTile(35, new Station("Marshal Baghramyan"));

        // Add Chance and Community Chest tiles at specific locations
        setTile(7, new ChanceTile("Chance"));
        setTile(17, new ChanceTile("Chance"));
        setTile(22, new ChanceTile("Chance"));
        setTile(38, new ChanceTile("Chance"));

        setTile(2, new CommunityChestTile("Community Chest"));
        setTile(12, new CommunityChestTile("Community Chest"));
        setTile(28, new CommunityChestTile("Community Chest"));
        setTile(31, new CommunityChestTile("Community Chest"));

        // Add properties with names and values for different regions
        int i = 1;
        while (i < 41) {
            switch (i) {
                case 1:
                    setTile(i, new Property("Vayq", 50, 10, "Vayoc Dzor"));
                    break;
                case 3:
                    setTile(i, new Property("Jermuk", 60, 15, "Vayoc Dzor"));
                    break;
                case 4:
                    setTile(i, new Property("Areni", 70, 20, "Vayoc Dzor"));
                    break;
                case 6:
                    setTile(i, new Property("Artashat", 100, 30, "Ararat"));
                    break;
                case 8:
                    setTile(i, new Property("Masis", 110, 35, "Ararat"));
                    break;
                case 9:
                    setTile(i, new Property("Vedi", 120, 40, "Ararat"));
                    break;
                case 11:
                    setTile(i, new Property("Gavar", 150, 50, "Gegharkunik"));
                    break;
                case 13:
                    setTile(i, new Property("Sevan", 160, 60, "Gegharkunik"));
                    break;
                case 14:
                    setTile(i, new Property("Vardenis", 170, 70, "Gegharkunik"));
                    break;
                case 16:
                    setTile(i, new Property("Abovyan", 200, 80, "Kotayk"));
                    break;
                case 18:
                    setTile(i, new Property("Hrazdan", 210, 85, "Kotayk"));
                    break;
                case 19:
                    setTile(i, new Property("Tsaghkadzor", 220, 90, "Kotayk"));
                    break;
                case 21:
                    setTile(i, new Property("Kapan", 250, 100, "Syunik"));
                    break;
                case 23:
                    setTile(i, new Property("Goris", 260, 110, "Syunik"));
                    break;
                case 24:
                    setTile(i, new Property("Sisian", 270, 120, "Syunik"));
                    break;
                case 26:
                    setTile(i, new Property("Dilijan", 300, 130, "Tavush"));
                    break;
                case 27:
                    setTile(i, new Property("Ijevan", 310, 135, "Tavush"));
                    break;
                case 29:
                    setTile(i, new Property("Berd", 320, 140, "Tavush"));
                    break;
                case 32:
                    setTile(i, new Property("Stepanavan", 350, 150, "Lori"));
                    break;
                case 33:
                    setTile(i, new Property("Vanadzor", 360, 160, "Lori"));
                    break;
                case 34:
                    setTile(i, new Property("Spitak", 370, 170, "Lori"));
                    break;
                case 36:
                    setTile(i, new Property("Artik", 500, 200, "Shirak"));
                    break;
                case 37:
                    setTile(i, new Property("Maralik", 510, 300, "Shirak"));
                    break;
                case 39:
                    setTile(i, new Property("Gyumri", 520, 400, "Shirak"));
                    break;
            }
            i++;
        }
    }

    /**
     * Renders a simple ASCII-style grid representation of the board in the console.
     */
    public void printBoard() {
        String[][][] boardGrid = new String[11][11][8];

        // Initialize all tiles as empty strings to avoid null values
        for (int row = 0; row < 11; row++) {
            for (int col = 0; col < 11; col++) {
                for (int line = 0; line < 8; line++) {
                    boardGrid[row][col][line] = "        ";
                }
            }
        }

        // Helper to fill a tile
        for (int i = 0; i < SIZE; i++) {
            Tile tile = tiles[i];
            String[] content = new String[8];
            for (int j = 0; j < 8; j++) content[j] = "        ";

            if (tile != null) {
                content[0] = center("[" + i + "]", 8);
                content[1] = center(tile.getName(), 8);

                if (tile instanceof Property p) {
                    content[2] = center(p.getGroup(), 8);
                    content[3] = center("$" + p.getRent(), 8);
                } else if (tile instanceof ChanceTile) {
                    content[2] = center("CHANCE", 8);
                } else if (tile instanceof CommunityChestTile) {
                    content[2] = center("COMM", 8);
                    content[3] = center("CHEST", 8);
                } else if (tile instanceof JailTile) {
                    content[2] = center("JAIL", 8);
                } else if (tile instanceof GoToJailTile) {
                    content[2] = center("GO TO", 8);
                    content[3] = center("JAIL", 8);
                } else if (tile instanceof FreeParkingTile) {
                    content[2] = center("FREE", 8);
                    content[3] = center("PARK", 8);
                } else if (tile instanceof GoTile) {
                    content[2] = center("GO", 8);
                    content[3] = center("200$", 8);
                } else if (tile instanceof Station) {
                    content[2] = center("STATION", 8);
                }
            }

            int row = -1, col = -1;

            // Define the row and column for the tiles
            if (i <= 10) {
                row = 0; col = 10 - i;
            } else if (i <= 20) {
                row = i - 10; col = 0;
            } else if (i <= 30) {
                row = 10; col = i - 20;
            } else if (i <= 39) {
                row = 40 - i; col = 10;
            }

            if (row >= 0 && col >= 0) {
                boardGrid[row][col] = content;
            }
        }

        showGrid(boardGrid);
    }

    /**
     * Centers a given text within a fixed width.
     *
     * @param text  the text to center
     * @param width the total width for centering
     * @return the centered text string
     */
    private String center(String text, int width) {
        if (text.length() >= width) return text.substring(0, width);
        int padding = (width - text.length()) / 2;
        return " ".repeat(padding) + text + " ".repeat(width - text.length() - padding);
    }

    /**
     * Prints the final board grid row by row using box characters.
     *
     * @param board the 3D array representing the board layout
     */
    private void showGrid(String[][][] board) {
        String fullLine = "x".repeat(11 * 8 + 12); // Adjust for proper spacing
        for (int row = 0; row < 11; row++) {
            System.out.println(fullLine);
            for (int line = 0; line < 8; line++) {
                for (int col = 0; col < 11; col++) {
                    String content = board[row][col][line];
                    System.out.print("x" + content);
                }
                System.out.println("x");
            }
        }
        System.out.println(fullLine);
    }
}
