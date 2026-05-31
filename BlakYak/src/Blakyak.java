import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;

public class Blakyak {
	private ArrayList<Cards> cardDeck = new ArrayList<Cards>();
	private ArrayList<Cards> p1Deck = new ArrayList<Cards>();
	private ArrayList<Cards> p2Deck = new ArrayList<Cards>();
	private int p1count;
	private int p2count;
	private boolean enemHeld;
	private boolean containFace;

	public Blakyak() {
		for (int i = 0; i < 4; i++) {
			for (int j = 2; j <= 14; j++) {
				Cards temp = new Cards(j, i);
				cardDeck.add(temp);
			}
		}
		Collections.shuffle(cardDeck);
		this.hit(p1Deck, true);
		this.hit(p1Deck, true);
		this.hit(p2Deck, false);
		this.hit(p2Deck, false);
		enemHeld = false;
		while (p1count > 21) {
			clear();
			this.hit(p1Deck, true);
			this.hit(p1Deck, true);
		}
		while (p2count > 21) {
			clear();
			this.hit(p2Deck, false);
			this.hit(p2Deck, false);
		}
	}

	public void makePcount() {
		for (int i = 0; i < p1Deck.size(); i++) {
			boolean switcher = false;
			if (p1Deck.get(i).getValue() > 10 && p1Deck.get(i).getValue() < 14) {
				p1count += 10;
				switcher = true;
				containFace = true;
			}
			if (switcher == false) {
				p1count += p1Deck.get(i).getValue();
			}
		}
		for (int i = 0; i < p2Deck.size(); i++) {
			boolean switcher = false;
			if (p2Deck.get(i).getValue() > 10 && p2Deck.get(i).getValue() < 14) {
				p2count += 10;
				switcher = true;
				containFace = true;
			}
			if (switcher == false) {
				p2count += p2Deck.get(i).getValue();
			}
		}
	}

	public int trueValue(int val, ArrayList<Cards> deck) {
		int p1c = val;
		int counter = checkForAce(deck);
		while (p1c > 21 && counter != 0) {
			p1c = p1c - 10;
			counter--;
		}
		return p1c;
	}

	public int checkForAce(boolean isP1) {
		int counter = 0;
		if (isP1) {
			for (int i = 0; i < getP1Deck().size(); i++) {
				if (p1Deck.get(i).getValue() == 14) {
					counter++;
				}
			}
			return counter;
		} else {
			for (int i = 0; i < getP2Deck().size(); i++) {
				if (p2Deck.get(i).getValue() == 14) {
					counter++;
				}
			}
			return counter;
		}
	}

	public int checkForAce(ArrayList<Cards> deck) {
		int counter = 0;

		for (int i = 0; i < deck.size(); i++) {
			if (deck.get(i).getValue() == 14) {
				counter++;
			}
		}
		return counter;

	}

	public void clear() {
		// set them all manually
		this.cardDeck = new ArrayList<Cards>(0);
		this.p1Deck = new ArrayList<Cards>(0);
		this.p2Deck = new ArrayList<Cards>(0);
		this.p1count = 0;
		this.p2count = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 2; j <= 14; j++) {
				Cards c1 = new Cards(j, i);
				cardDeck.add(c1);
			}
		}
		Collections.shuffle(cardDeck);
		this.hit(p1Deck, true);
		this.hit(p1Deck, true);
		this.hit(p2Deck, false);
		this.hit(p2Deck, false);
		enemHeld = false;
	}

	public boolean isDone() {
		int sub = 0;
		int enemSub = 0;
		if (checkForAce(true) != 0) {
			sub = 10;
		}
		if (checkForAce(false) != 0) {
			enemSub = 10;
		}
		if (this.getP1count() - sub > 21) {
			return true;
		}
		if (this.getP2count() - enemSub > 21) {
			return true;
		}
		if (this.getP1count() == 21 || this.getP2count() == 21) {
			return true;
		}
		return false;
	}

	public void doubleDown() {
		// you hit but then your done
		hit(p1Deck, true);
	}

	public void hit(ArrayList<Cards> pdeck, boolean isP1) {
		Cards addCard = cardDeck.get(0);
		pdeck.add(addCard);

		if (isP1) {
			if (addCard.getValue() > 10 && addCard.getValue() < 14) {
				this.p1count += 10;
			} else if (addCard.getValue() == 14) {
				this.p1count += 11;
			} else if (addCard.getValue() <= 10) {
				this.p1count += addCard.getValue();
			}
		}
		if (isP1 == false) {
			if (addCard.getValue() > 10 && addCard.getValue() < 14) {
				this.p2count += 10;
			} else if (addCard.getValue() <= 10) {
				this.p2count += addCard.getValue();
			} else if (addCard.getValue() == 14) {
				this.p2count += 11;
			}
		}
		this.cardDeck.remove(0);

	}

	public int hit(ArrayList<Cards> pdeck, int count) {
		Cards addCard = cardDeck.get(0);
		pdeck.add(addCard);
		if (addCard.getValue() > 10 && addCard.getValue() < 14) {
			count += 10;
		} else if (addCard.getValue() == 14) {
			count += 11;
		} else if (addCard.getValue() <= 10) {
			count += addCard.getValue();
		}

		this.cardDeck.remove(0);
		return count;
	}

//draws cards so that when method is called, in color panel, it draws the cards.
	public void drawCards(Graphics g, int x, int y, int arrPos, ArrayList<Cards> deck, boolean isBack, boolean isDeck,
			boolean isVis) throws IOException {
		if (isVis == false) {
			return;
		}
		BufferedImage Card;
		int num = deck.get(arrPos).getValue();
		String s = "card" + num + deck.get(arrPos).getSuite() + ".png";
		if (isBack) {
			Card = ImageIO.read(new File("cardBacks.png"));
		} else if (isDeck) {
			Card = ImageIO.read(new File("deckBack.png"));
		} else if (num == 14) {
			Card = ImageIO.read(new File("cardAce" + deck.get(arrPos).getSuite() + ".png"));
		} else if (num == 11) {
			Card = ImageIO.read(new File("cardJack" + deck.get(arrPos).getSuite() + ".png"));
		} else if (num == 12) {
			Card = ImageIO.read(new File("cardQueen" + deck.get(arrPos).getSuite() + ".png"));
		} else if (num == 13) {
			Card = ImageIO.read(new File("cardKing" + deck.get(arrPos).getSuite() + ".png"));
		} else {
			Card = ImageIO.read(new File(s));
		}
		g.drawImage(Card, x, y, null);
	}
	public int splitEnd(int p1count, int p2count) {
		if ((p1count > p2count && p1count <= 21) || p2count > 21) {
			return 2;
		}
		if (p1count == p2count && p1count <= 21 && p2count <= 21) {
			return 1;
		}
		if ((p1count < p2count && p2count <= 21) || p1count > 21) {
			return 0;
		}
		return -1;
	}

	public void AIRun(int counter, int enemSize) {
		int temp = trueValue(p2count, p2Deck);
		if (temp >= 17 || enemSize > 21) {
			if (counter == 0) {
				enemHeld = true;
			}
			return;
		}
		hit(p2Deck, false);
		counter++;
		AIRun(counter, enemSize);
	}

	public void AIRun(int counter, int enemSize, Integer enemSize2) {
		int temp = trueValue(p2count, p2Deck);
		if (temp >= 17 || enemSize > 21 && enemSize2 > 21) {
			if (counter == 0) {
				enemHeld = true;
			}
			return;
		}
		hit(p2Deck, false);
		counter++;
		AIRun(counter, enemSize);
	}

	public void AIRun(ArrayList<Cards> e1) {
		if (p2count < 17) {
			hit(e1, false);
			return;
		}

	}

	public boolean isContainFace() {
		return containFace;
	}

	public void setContainFace(boolean containFace) {
		this.containFace = containFace;
	}

	public ArrayList<Cards> getCardDeck() {
		return cardDeck;
	}

	public void setCardDeck(ArrayList<Cards> cardDeck) {
		this.cardDeck = cardDeck;
	}

	public ArrayList<Cards> getP1Deck() {
		return p1Deck;
	}

	public void setP1Deck(ArrayList<Cards> p1Deck) {
		this.p1Deck = p1Deck;
	}

	public ArrayList<Cards> getP2Deck() {
		return p2Deck;
	}

	public void setP2Deck(ArrayList<Cards> p2Deck) {
		this.p2Deck = p2Deck;
	}

	public int getP1count() {
		return p1count;
	}

	public void setP1count(int p1count) {
		this.p1count = p1count;
	}

	public int getP2count() {
		return p2count;
	}

	public void setP2count(int p2count) {
		this.p2count = p2count;
	}

	public boolean isEnemHeld() {
		return enemHeld;
	}

	public void setEnemHeld(boolean enemHeld) {
		this.enemHeld = enemHeld;
	}

}