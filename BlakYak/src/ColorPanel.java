
//sat make all cards + all suites so 50 cards exist, and update methods accordingly.
import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ColorPanel extends JPanel {
	private BufferedImage img;
	private String currentFile;
	private Sounds music;
	private Blakyak game;
	private Button hit = new Button(57, 521, 197, 612, true);
	private Button hold = new Button(204, 521, 344, 612, true);
	private Button split = new Button(352, 521, 491, 612, true);
	private Button doubleDown = new Button(499, 521, 639, 612, true);
	private Button splitHit = new Button(57, 521, 344, 612, false);
	private Button splitHold = new Button(352, 521, 499, 612, false);
	private boolean justHit = false;
	private boolean justHeld = false;
	private boolean justSplit = false;
	private boolean justDD = false;
	private int betNum;
	private Boolean endCard = true;
	private int moneyAmt;
	private boolean didSplit = false;
	private BufferedImage betHeader = ImageIO.read(new File("potHeader.png"));
	private BufferedImage moneyBag = ImageIO.read(new File("moneyBag.png"));
	private ArrayList<Cards> ghostDeck = new ArrayList<Cards>();
	private int whichHand = 1;
	private Integer ghostcount = 0;
	private Integer[] arr = new Integer[100000];

	// AryasButtons start;
	public ColorPanel(Color background) throws IOException {
		setBackground(background);
		music = new Sounds("gameSong.wav");
		img = ImageIO.read(new File("Start.png"));
		currentFile = "Start.png";
		game = new Blakyak();

		addMouseListener(new PanelListener());
		setFocusable(true);
		for (int i = 10; i < 100010; i++) {
			arr[i - 10] = i;
		}
		moneyAmt = (int) JOptionPane.showInputDialog(null, "How much are you coming in with?", "Amount of Money",
				JOptionPane.PLAIN_MESSAGE, null, arr, arr[0]);
		betValAmt();
//		JOptionPane.showInputDialog("What is your Credit-Card Information?");
//		JOptionPane.showInputDialog("Whats is your Social Security Information");
//		JOptionPane.showInputDialog("What is your Zip Code");
//		JOptionPane.showInputDialog("Whats is your Birthday");
	}

	public void betValAmt() {
		betNum = (int) JOptionPane.showInputDialog(null, "Whats your bet?", "Amount of Money Being Bet",
				JOptionPane.PLAIN_MESSAGE, null, arr, arr[0]);

		while (moneyAmt < betNum) {
			betNum = (int) JOptionPane.showInputDialog(null, "Please give a valid bet.", "Amount of Money Being Bet",
					JOptionPane.PLAIN_MESSAGE, null, arr, arr[0]);
		}
	}

	public void end() {
		PanelListener p1 = new PanelListener();
		p1.end();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
		int splitX = 150;
		try {

			if (!currentFile.equals("Start.png")) {
				for (int i = 0; i < game.getP2Deck().size(); i++) {
					if (i == 0) {
						game.drawCards(g, (50 + (50 * (i + 1))), 70, i, game.getP2Deck(), endCard, false, true);
					} else
						game.drawCards(g, (50 + 50 * (i + 1)), 70, i, game.getP2Deck(), false, false, true);
				}
				for (int i = 0; i < game.getP1Deck().size(); i++) {
					game.drawCards(g, (50 + 50 * (i + 1)), 270, i, game.getP1Deck(), false, false, true);
				}
				for (int i = 0; i < ghostDeck.size(); i++) {
					game.drawCards(g, (50 + 50 * (i + 1)) + splitX, 360, i, ghostDeck, false, false, true);
				}
				g.drawImage(betHeader, 200, 10, null);
				g.drawImage(moneyBag, 15, 180, null);
				String temp = "" + betNum;
				g.drawString(temp, 340, 100);
				String money = "" + moneyAmt;
				g.drawString(money, 45, 270);

				// what runs if you hit
				if (justHit) {
					justHit = false;
					// what runs when you hold
				} else if (justHeld) {

					// repaint();
					justHeld = false;
					// what runs when you split
				} else if (justSplit) {

					if (game.getP1Deck().size() > 2 || game.getP2Deck().size() > 2) {
						justSplit = false;
					}
					if (didSplit) {
						justSplit = false;
					}
					if (game.getP1Deck().get(0).getValue() == game.getP1Deck().get(1).getValue()
							&& game.getP1Deck().size() == 2) {
						img = ImageIO.read(new File("splitBoard.png"));
						hit.setOn(false);
						hold.setOn(false);
						split.setOn(false);
						doubleDown.setOn(false);
						splitHit.setOn(true);
						splitHold.setOn(true);
						currentFile = "splitBoard.png";
						repaint();
						ghostDeck.add(game.getP1Deck().get(1));
						game.hit(ghostDeck, ghostcount);
						Cards gc1 = ghostDeck.get(0);
						Cards gc2 = ghostDeck.get(1);
						if (gc1.getValue() > 10 && gc1.getValue() < 14) {
							ghostcount += 10;
						} else if (gc1.getValue() <= 10) {
							ghostcount += gc1.getValue();
						} else if (gc1.getValue() == 14) {
							ghostcount += 11;
						}
						if (gc2.getValue() > 10 && gc2.getValue() < 14) {
							ghostcount += 10;
						} else if (gc2.getValue() <= 10) {
							ghostcount += gc2.getValue();
						} else if (gc2.getValue() == 14) {
							ghostcount += 11;
						}
						game.trueValue(ghostcount, ghostDeck);

						//////////////////////////////////////////////////////////
						Cards secCard = game.getP1Deck().remove(1);
						if (secCard.getValue() > 10 && secCard.getValue() < 14) {
							game.setP1count(game.getP1count() - 10);
						} else if (secCard.getValue() <= 10) {
							game.setP1count(game.getP1count() - secCard.getValue());
						} else if (secCard.getValue() == 14) {
							game.setP1count(game.getP1count() - 1);
						}
						game.hit(game.getP1Deck(), true);
						didSplit = true;
						System.out.println("P1 Deck Size:" + game.getP1Deck().size());
						justSplit = false;
						// make it so that you draw two ghost cards a bit further from your og 2, then
						// have the dealer AIRun(), then compare with each hand(make a splitEnd()
						// method).

					}
					// what runs when you double down
				} else if (justDD) {
					betNum = betNum * 2;
					// repaint();
					// end();
					justDD = false;

				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	public boolean isClicked(MouseEvent me) {
		int clickX = me.getX();
		int clickY = me.getY();
		if (clickX >= 176 && clickX <= 487 && clickY >= 528 && clickY <= 607) {
			return true;
		}

		return false;
	}

	public String getCurrentFile() {
		return currentFile;
	}

	public void setCurrentFile(String currentFile) {
		this.currentFile = currentFile;
	}

	public boolean isTitleScreen() throws IOException {
		if (img == ImageIO.read(new File("Start.png"))) {
			return true;
		}
		return false;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(String path) throws IOException {
		currentFile = path;
		img = ImageIO.read(new File(currentFile));
	}

	public Sounds getMusic() {
		return music;
	}

	public void setMusic(Sounds music) {
		this.music = music;
	}

//re read on rules of blackjack and add the new ifs for it.
	private class PanelListener extends MouseAdapter {
		public void mouseClicked(MouseEvent me) {
			int clickX = me.getX();
			int clickY = me.getY();
			try {// startButton
				if (currentFile.equals("Start.png") && clickX >= 176 && clickX <= 487 && clickY >= 528
						&& clickY <= 607) {
					img = null;
					Thread.sleep(10);
					setImg("Board.png");
					setCurrentFile("Board.png");
					repaint();
					end();
					return;
				}
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (currentFile.equals("Board.png")) {
				System.out.println("P1 Card Values: " + game.getP1count() + " " + "P2 Card Values: " + game.getP2count()
						+ " Cards: " + (game.getP2Deck().get(0).getValue()) + " " + (game.getP2Deck().get(1)).getValue()
						+ "P1 2nd hand: " + ghostcount);
				if (end() == -1) {
					repaint();
					return;
				}
				// clicked game.AIRun button
				if (hit.isClicked(me, clickX, clickY)) {// hitbox
					game.hit(game.getP1Deck(), true);
					// repaint();
					justHit = true;
					repaint();
					if (end() == -1) {
						repaint();
						return;
					}
					repaint();
				}

				// clicked hold button
				else if (hold.isClicked(me, clickX, clickY)) {
					// make hold method to continue the loop
					// ask mouradov for help tmrw.
					game.AIRun(0, game.getP1count());
					justHeld = true;
//					repaint();
					quickEnd();
				}

				// clicked split button
				else if (split.isClicked(me, clickX, clickY)) {
					if (!didSplit) {
						justSplit = true;
					}

				}
				// clicked double down.
				else if (doubleDown.isClicked(me, clickX, clickY)) {
					game.doubleDown();
					justDD = true;
					if (quickEnd() == -1) {
						game.clear();
						repaint();
						return;
					}
				}
				// checks for game over

			}
			//////////////////////////////////////////////////////////

			if (currentFile.equals("splitBoard.png")) {

				if (splitHit.isClicked(me, clickX, clickY)) {
					if (whichHand == 1) {
						game.hit(game.getP1Deck(), true);
						game.trueValue(game.getP1count(), game.getP1Deck());
						justHit = true;
						int p1c = game.trueValue(game.getP1count(), game.getP1Deck());
						if (p1c >= 21) {
							whichHand++;
						}
					} else if (whichHand == 2) {
						ghostcount = game.hit(ghostDeck, ghostcount);
						game.trueValue(ghostcount, ghostDeck);
						justHit = true;
						if (ghostcount >= 21) {
							whichHand++;
						}
					}

					if (whichHand > 2) {
						System.out.println("ahaha");
						game.AIRun(0, game.getP1count(), ghostcount);
						System.out.println("hsda");
						try {
							splitEnd();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				} else if (splitHold.isClicked(me, clickX, clickY)) {
					if (whichHand < 3) {
						whichHand++;
					}
					if (whichHand > 2) {
						System.out.println("ahaha");
						try {
							splitEnd();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						game.AIRun(0, game.getP1count(), ghostcount);
						System.out.println("hsda");
						try {
							splitEnd();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				System.out.println(whichHand);

			}
			System.out.println(clickX + " " + clickY);
			repaint();

		}

		public int winMessage() {
			System.out.println("in winmessage");
			int choice = -1;
			endCard = false;
			repaint();
			choice = JOptionPane.showConfirmDialog(null, "You won the hand! Go again?");
			moneyAmt += betNum;
			if (choice == 0) {
				game.clear();
				ghostDeck.clear();
				endCard = true;
				betNum = (int) JOptionPane.showInputDialog(null, "Whats your bet?", "Amount of Money Being Bet",
						JOptionPane.PLAIN_MESSAGE, null, arr, arr[0]);
				return -1;
			} else if (choice == 1 || choice == 2) {
//				System.exit(0);
				try {
					img = ImageIO.read(new File("Start.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				currentFile = "Start.png";
				repaint();
			}
			return -1;
		}

		public int loseMessage() {
			System.out.println("in losemessage");
			repaint();
			endCard = false;
			int temp = (int) (Math.random() * 4);
			int choice = -1;
			moneyAmt -= betNum;
			if (moneyAmt <= 0) {
				JOptionPane.showMessageDialog(null, "Goodbye. Get money and try again.");
//				System.exit(0);
				try {
					img = ImageIO.read(new File("Start.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				currentFile = "Start.png";
				repaint();
			}
			if (temp == 0) {
				choice = JOptionPane.showConfirmDialog(null,
						"Ayo fogetta' 'bout it- Alex Wong Probably" + "\n" + "So, are you gonna play again?");
			} else if (temp == 1) {
				choice = JOptionPane.showConfirmDialog(null,
						"A foolish man will patch the hole in his pants. But a wiser one will use it to pee - Sun Tzu"
								+ "\n" + "So, are you gonna play again?");
			} else if (temp == 2) {
				choice = JOptionPane.showConfirmDialog(null,
						"You donkey - Gordan Ramsey" + "\n" + "So, are you gonna play again?");
			} else if (temp == 3) {
				choice = JOptionPane.showConfirmDialog(null,
						"Jack Black was playing some Blakyak and bet his back in a shack and lost to the Yak - Barack Obama"
								+ "\n" + "So, are you gonna play again?");
			}
			if (choice == 0) {
				game.clear();
				endCard = true;
				ghostDeck.clear();

				betNum = (int) JOptionPane.showInputDialog(null, "Whats your bet?", "Amount of Money Being Bet",
						JOptionPane.PLAIN_MESSAGE, null, arr, arr[0]);
				return -1;
			}
			if (choice == 1 || choice == 2) {
//				System.exit(0);
				try {
					img = ImageIO.read(new File("Start.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				currentFile = "Start.png";
				repaint();
			}
			return 0;
		}

		public int tieMessage() {
			System.out.println("in tiemessage");
			repaint();
			int choice = -1;
			System.out.println(justHeld);
			endCard = false;
			choice = JOptionPane.showConfirmDialog(null, "We tied, so you wanna go again?");
			if (choice == 0) {
				game.clear();
				endCard = true;
				ghostDeck.clear();
				betNum = (int) JOptionPane.showInputDialog(null, "Whats your bet?", "Amount of Money Being Bet",
						JOptionPane.PLAIN_MESSAGE, null, arr, arr[0]);
				// repaint();
				return -1;
			}
			if (choice == 1 || choice == 2) {
//				System.exit(0);
				try {
					img = ImageIO.read(new File("Start.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				currentFile = "Start.png";
				repaint();
			}
			return -1;
		}

		private boolean isWin(int playerHandVal, int dealerHandVal) {
			return playerHandVal <= 21 && (dealerHandVal > 21 || playerHandVal > dealerHandVal);
		}

		private boolean isTie(int playerHandVal, int dealerHandVal) {
			return (playerHandVal > 21 && dealerHandVal > 21) || playerHandVal == dealerHandVal;
		}

		private boolean isLoss(int playerHandVal, int dealerHandVal) {
			return dealerHandVal <= 21 && (playerHandVal > 21 || playerHandVal < dealerHandVal);
		}

		public int splitEnd() throws IOException {
			int p1c = game.trueValue(game.getP1count(), game.getP1Deck());
			int p2c = game.trueValue(game.getP2count(), game.getP2Deck());
			int g = game.trueValue(ghostcount, ghostDeck);
			didSplit = false;

			System.out.println("P1 Card Values: " + p1c + " " + "P2 Card Values: " + p2c + " Cards: "
					+ (game.getP2Deck().get(0).getValue()) + " " + (game.getP2Deck().get(1)).getValue()
					+ "P1 2nd hand: " + g);

			if ((isTie(p1c, p2c) && isTie(g, p2c)) || (isWin(p1c, p2c) && isLoss(g, p2c))
					|| (isWin(g, p2c) && isLoss(p1c, p2c))) {

				System.out.println("I am in tie");
				ghostcount = 0;
				whichHand = 1;
				img = ImageIO.read(new File("Board.png"));
				hit.setOn(true);
				hold.setOn(true);
				split.setOn(true);
				doubleDown.setOn(true);
				splitHit.setOn(false);
				splitHit.setOn(false);
				currentFile = "Board.png";
				return tieMessage();
			}
			if ((isWin(p1c, p2c) && isWin(g, p2c)) || (isWin(p1c, p2c) && isTie(g, p2c))
					|| (isWin(g, p2c) && isTie(p1c, p2c))) {

				System.out.println("I am in win");
				ghostcount = 0;
				whichHand = 1;
				img = ImageIO.read(new File("Board.png"));
				hit.setOn(true);
				hold.setOn(true);
				split.setOn(true);
				doubleDown.setOn(true);
				splitHit.setOn(false);
				splitHit.setOn(false);
				currentFile = "Board.png";

				return winMessage();
			}
			if ((isLoss(p1c, p2c) && isLoss(g, p2c)) || (isLoss(p1c, p2c) && isTie(g, p2c))
					|| (isLoss(g, p2c) && isTie(p1c, p2c))) {

				System.out.println("I am in lose");
				ghostcount = 0;
				whichHand = 1;
				img = ImageIO.read(new File("Board.png"));
				hit.setOn(true);
				hold.setOn(true);
				split.setOn(true);
				doubleDown.setOn(true);
				splitHit.setOn(false);
				splitHit.setOn(false);
				currentFile = "Board.png";
				return loseMessage();
			}
			return 0;
		}

		// make a splitend method that passes in an arraylist. it will run this specific
		// arraylist through its specific commands that leads us to our answer of if you
		// won or not.
		public int quickEnd() {
			System.out.println("in quickEnd");
			int p1c = game.trueValue(game.getP1count(), game.getP1Deck());
			int p2c = game.trueValue(game.getP2count(), game.getP2Deck());
			System.out.println("P1 Card Values: " + p1c + " " + "P2 Card Values: " + p2c + " Cards: "
					+ (game.getP2Deck().get(0).getValue()) + " " + (game.getP2Deck().get(1)).getValue());
			// did you tie
			if (isTie(p1c, p2c)) {
				return tieMessage();
			}
			// did you win
			if (isWin(p1c, p2c)) {
				// code an if for all ends so that ablack jack gives 1.5 bet;
				return winMessage();
			}

			// did you lose
			if (isLoss(p1c, p2c)) {
				return loseMessage();
			}
			return 0;
		}

		public int end() {
			// checks for aces
			// did you tie
			System.out.println();
			int p1c = game.trueValue(game.getP1count(), game.getP1Deck());
			int p2c = game.trueValue(game.getP2count(), game.getP2Deck());
			if (p1c == 21 && p2c == 21) {
				return tieMessage();
			}
			// did you win
			if (p1c == 21 && p2c != 21) {
				endCard = false;
				return winMessage();
			}

			// did you lose
			if ((p2c == 21 && p1c != 21) || p1c > 21) {
				return loseMessage();
			}
			return 0;
		}

	}

	public Blakyak getGame() {
		return game;
	}

	public void setGame(Blakyak game) {
		this.game = game;
	}

	
	
}