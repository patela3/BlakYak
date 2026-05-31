import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

public class DemoSIm {
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		ColorPanel myPanel = new ColorPanel(Color.blue);
		ArrayList<Cards> temp = new ArrayList<Cards>();
		temp.add(new Cards(5, "Spade"));
		temp.add(new Cards(5, "Club"));
		myPanel.getGame().setP1Deck(temp);
		myPanel.getGame().setP1count(10);
		JFrame theOnlyFrame = new JFrame("Blakyak");
		theOnlyFrame.setSize(716, 738);
		theOnlyFrame.setVisible(true);
		theOnlyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		theOnlyFrame.getContentPane().add(myPanel);
		theOnlyFrame.setVisible(true);
		Sounds musOb = new Sounds("gameSong.wav");
		musOb.playMusic();
		musOb.loop();
		
	}
}
