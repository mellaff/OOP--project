# Monopoly Game

## Project Overview
This project implements a simplified version of the Monopoly board game using Object-Oriented Programming (OOP) principles in Java. The game models core Monopoly mechanics, including player turns, property transactions, special board spaces, and card effects.

## Project Structure
```
├── MonopolyGame.java       // Main class managing game flow
├── Player.java             // Stores player information
├── Board.java              // Holds board tiles and tracks player positions
├── Tile.java               // Base class for all board spaces
│     ├── Property.java         // Purchasable tiles
│     ├── ChanceTile.java       // Triggers chance card draws
│     ├── CommunityChestTile.java // Triggers community chest card draws
│     ├── GoTile.java           // Start tile (passing earns money)
│     ├── JailTile.java         // Jail space
│     ├── FreeParkingTile.java  // Free Parking space
│     └── GoToJailTile.java     // Sends player to Jail
├── Card.java               // Base class for Chance/Community Chest cards
│     ├── ChanceCard.java       // Implements chance card effects
│     └── CommunityChestCard.java // Implements community chest effects
├── Dice.java               // Simulates dice rolls
├── Bank.java               // Handles money, mortgages, auctions
└── GameRules.java          // Defines and enforces game rules
```
