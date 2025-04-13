package board;

import board.tiles.*;

public class Board {
    private final Tile[] tiles;
    //Total number of tiles
    private final int SIZE;

    public Board(int size) {
        this.SIZE = size;
        this.tiles = new Tile[SIZE];
        initialise();
    }

    //Mutator for a tile at a specific position
    public void setTile(int position, Tile tile) {
        if (position >= 0 && position < SIZE) {
            tiles[position] = tile;
        } else {
            System.out.println("Invalid position.");
        }
    }

    //Accessor for the Tile at a specific position
    public Tile getTile(int position) {
        if (position >= 0 && position < SIZE) {
            return tiles[position];
        }
        return null;
    }

    // Initializes the board with special tiles at specific positions
    public void initialise() {
        setTile(0, new GoTile());
        setTile((SIZE/4), new JailTile());
        setTile(2*(SIZE/4), new FreeParkingTile());
        setTile(3*(SIZE/4), new GoToJailTile((SIZE/4)));

        for (int i = 1; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {

            }
        }
    }

    // Prints the board
    public void printBoard() {

    }
}