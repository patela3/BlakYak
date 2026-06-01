# BlakYak

> An 8-bit Blackjack variant built in Java, with all art designed from scratch.

---

## About

BlakYak is a retro-styled Blackjack game written in Java, featuring original 8-bit pixel art created by the developer. You start by entering how much money you're bringing to the table, place your bet, and play against an AI dealer — complete with looping background music, funny loss quotes, and a full split hand mechanic.

---

## Features

- Full Blackjack gameplay: Hit, Stand, Split, and Double Down
- AI dealer that follows standard Blackjack soft-17 rules (hits until 17+)
- Split — play two hands simultaneously when your first two cards match
- Double Down — double your bet and take exactly one more card
- Persistent money tracking across rounds; game ends when you go broke
- Full 52-card deck with all four suits (Spades, Diamonds, Clubs, Hearts)
- Looping background music (`gameSong.wav`) via Java Sound API
- Original 8-bit pixel art for all cards, the board, and UI elements
- Randomized funny loss quotes on defeat

---

## Getting Started

### Prerequisites

- Java 8 or higher
- All asset files (`.png` card images, `gameSong.wav`, `Start.png`, `Board.png`, `splitBoard.png`, etc.) must be in the same directory as the compiled classes

### Running the Game

**Option 1 — From your IDE (recommended):**

1. Clone the repository:
   ```bash
   git clone https://github.com/patela3/BlakYak.git
   ```
2. Open the project in IntelliJ IDEA, Eclipse, or similar.
3. Run `GameSim.java` to launch a standard game, or `DemoSim.java` to launch with a preset starting hand.

**Option 2 — From the command line:**

```bash
git clone https://github.com/patela3/BlakYak.git
cd BlakYak/BlakYak/src
javac *.java
java GameSim
```

> Make sure all image and audio assets are present in the working directory before running.

---

## How to Play

1. **Enter your bankroll** — a dialog asks how much money you're coming in with.
2. **Place your bet** — choose a bet amount (must be at or below your current balance).
3. **You and the dealer are each dealt two cards.** The dealer's first card is face-down.
4. Choose your action:

| Action | Description |
|---|---|
| Hit | Draw another card from the deck |
| Stand | End your turn; the dealer then plays |
| Split | If your first two cards have equal value, split into two separate hands |
| Double Down | Double your bet and receive exactly one more card |

5. The dealer reveals their hidden card and hits until reaching 17 or higher.
6. Closest to 21 without busting wins. Busting (going over 21) is an automatic loss.

**Card Values:**
- Number cards (2–10): face value
- Jack, Queen, King: 10
- Ace: 11 (automatically counted as 1 if your hand would otherwise bust)

---

## Project Structure

```
BlakYak/
├── BlakYak/
│   └── src/
│       ├── GameSim.java      # Main entry point — launches the game window
│       ├── DemoSim.java      # Alternate launcher with a preset starting hand
│       ├── Blakyak.java      # Core game logic: deck, dealing, hit, AI, win detection
│       ├── ColorPanel.java   # Swing panel: rendering, mouse input, UI flow, betting
│       ├── Cards.java        # Card model — value (2–14) and suit
│       ├── Button.java       # Clickable region helper with enable/disable toggle
│       └── Sounds.java       # Audio engine: play, loop, stop WAV files
└── README.md
```

---

## Asset Files Required

The following files must be present in the working directory at runtime:

| File | Description |
|---|---|
| `Start.png` | Title screen background |
| `Board.png` | Main game board |
| `splitBoard.png` | Board layout for split hands |
| `cardBacks.png` | Face-down card image |
| `deckBack.png` | Deck image |
| `cardAce[Suit].png` | Ace cards (one per suit) |
| `cardJack[Suit].png` | Jack cards |
| `cardQueen[Suit].png` | Queen cards |
| `cardKing[Suit].png` | King cards |
| `card[N][Suit].png` | Number cards 2–10 per suit |
| `potHeader.png` | Bet display header |
| `moneyBag.png` | Money display icon |
| `gameSong.wav` | Background music (loops continuously) |

---

## Art & Design

All visual assets were designed by the developer. The 8-bit pixel art style gives the game a retro arcade feel, covering every card face, board layout, and UI element.

---

## Built With

- Java — core language
- Java Swing / AWT — windowing and rendering
- Java Sound API (`javax.sound.sampled`) — audio playback

---

## Author

**patela3** — [GitHub](https://github.com/patela3)

---

## License

This project is open source. *(Add a LICENSE file to specify terms, e.g. MIT.)*
