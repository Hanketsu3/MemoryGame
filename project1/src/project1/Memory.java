package project1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Memory{
	public static void main(String[] args) {
		new Frame();
	}
}
class Frame extends JFrame implements MouseListener {
	Panel panel;
	private int[][] Array = randomizer();//This is the color array and we make our work on this.
	private int counter = -1;//This is the click counter
	private int[] x = new int[2], y = new int[2];//These are the click recorders.
	private int[][] found = new int[4][4];//This array is the found tracker if you find the colors this array will track it.
	private int[][] foundArray = new int[4][4];//This array is full of ones. And we compare this with found array to know when to finish
	private int[] timeStart = new int[2];
	private int[] timeEnd = new int[2];
	private int response;//Joptionpane 
	private int difficulty;//Joptionpane
	private String[] options = { "Easy", "Medium", "Hard" };
	private String[] options2 = { "Continiue", "Exit" };
	Frame() {
		response = JOptionPane.showOptionDialog(this.getContentPane(),
				"Welcome To The Memory Game!\nAt your first click you will see the colors\nMemorise them and find the pairs to finish the game",
				"Guidelines", 0, JOptionPane.INFORMATION_MESSAGE, null, options2, null);//This shows a message that guides the user
		if (response == JOptionPane.NO_OPTION || response == JOptionPane.CLOSED_OPTION) {
			System.exit(0);
		}
		difficulty = JOptionPane.showOptionDialog(this.getContentPane(), "Please select a difficulty", "Difficulty", 0,
				JOptionPane.INFORMATION_MESSAGE, null, options, null);//This is the difficulty message
		if (difficulty== JOptionPane.CLOSED_OPTION)
			System.exit(0);
		panel = new Panel();
		//These are frame settings
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.add(panel);
		this.setBackground(Color.black);
		this.pack();
		this.setLocationRelativeTo(null);
		this.addMouseListener(this);
		this.setVisible(true);
		this.setResizable(false);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++)
				System.out.print(Array[i][j] + " ");
			System.out.println();
		}
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				foundArray[i][j] = 1;
	}

	@SuppressWarnings("deprecation")
	//This is the method that reads mouse clicks and does all the work.
	public void mouseClicked(MouseEvent e) {
		if (counter == -1) {//At the first click this part shows the circles and starts the timer
			panel.showCircle(Array, difficulty);
			Date d1 = new Date();
			timeStart[0] = d1.getMinutes();
			timeStart[1] = d1.getSeconds();
			counter++;
		} else {//This part actually starts the game
			x[counter] = e.getX() - 5;
			y[counter] = e.getY() - 30;
			Date d2 = new Date();
			System.out.println(x[counter] + "  " + y[counter]);
			if ((found[y[counter] / 100][x[counter] / 100] != 1)) {//If the clicked points are not found this part works.
				panel.drawCircle(getGraphics(), e.getX() - 5, e.getY() - 30, Array);
				counter++;
				if (counter == 2 && ((x[0] / 100) * 10 + (y[0] / 100)) == ((x[1] / 100) * 10 + (y[1] / 100))) {//If you click 2 times and the clicked places are the same this part works.
					counter = 0;
					panel.eraseCircle(getGraphics(), x[0], y[0]);
				} else if (counter == 2 && ((x[0] / 100) * 10 + (y[0] / 100)) != ((x[1] / 100) * 10 + (y[1] / 100))) {//If you click 2 times and the clicked places arent the same this part works.
					if (Array[y[0] / 100][x[0] / 100] == Array[y[1] / 100][x[1] / 100]) {
						found[y[0] / 100][x[0] / 100] = 1;
						found[y[1] / 100][x[1] / 100] = 1;
						for (int i = 0; i < 4; i++) {//This part just prints the found array.
							for (int j = 0; j < 4; j++)
								System.out.print(found[i][j] + " ");
							System.out.println();
						}
						counter = 0;
					} else {//If the clicked places are not equal this part deletes them.
						panel.eraseCircle(getGraphics(), x[0], y[0]);
						panel.eraseCircle(getGraphics(), x[1], y[1]);
						counter = 0;
					}
				}
			} else if (found[y[counter] / 100][x[counter] / 100] == 1) {//If you click at a place you already found this prints "Problem?"
				System.out.println("Problem?");
			}
			if (equal(found, foundArray)) {//This part ends the game and lets you know your time.
				timeEnd[0] = d2.getMinutes();
				timeEnd[1] = d2.getSeconds();
				int seconds = (timeEnd[0] * 60 + timeEnd[1]) - (timeStart[0] * 60 + timeStart[1]);
				JOptionPane.showMessageDialog(null, "Finished in :" + seconds + "Seconds");
				System.exit(0);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	//This is a method that randomizes a 4x4 Array
	public static int[][] randomizer() {
		int num[] = { 1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8 };
		int Array[][] = new int[4][4];
		Random random = new Random();
		int temp, t;
		for (int j = 0; j <= 20; j++) {
			for (int x = 0; x < 16; x++) {
				t = random.nextInt(1000) % 15;
				temp = num[x];
				num[x] = num[t];
				num[t] = temp;
			}
			t = 0;
			for (int r = 0; r < 4; r++) {
				for (int s = 0; s < 4; s++) {
					Array[r][s] = num[t];
					t = t + 1;
				}
			}
		}
		return Array;
	}
	//This method checs if any 2 dimensional arrays are equal
	public static boolean equal(final int[][] arr1, final int[][] arr2) {
		if (arr1 == null) {
			return (arr2 == null);
		}
		if (arr2 == null) {
			return false;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (!Arrays.equals(arr1[i], arr2[i])) {
				return false;
			}
		}
		return true;
	}
}
 class Panel extends JPanel {
	Panel() {
		this.setPreferredSize(new Dimension(400, 400));
	}
	//This paint method just paints the bounds
	public void paint(Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setPaint(Color.white);
		g2D.drawLine(0, 100, 400, 100);
		g2D.drawLine(0, 200, 400, 200);
		g2D.drawLine(0, 300, 400, 300);
		g2D.drawLine(100, 0, 100, 400);
		g2D.drawLine(200, 0, 200, 400);
		g2D.drawLine(300, 0, 300, 400);
	}
	//This is a method to show the circles for a time period.
	public void showCircle(int[][] Array, int diff) {
		try {
			for (int i = 0; i < 4; i++)
				for (int j = 0; j < 4; j++) {
					this.drawCircleFirst(getGraphics(), j * 100, i * 100, Array);
				}
			if (diff == JOptionPane.YES_OPTION)
				Thread.sleep(10000);
			if (diff == JOptionPane.NO_OPTION)
				Thread.sleep(5000);
			if (diff == JOptionPane.CANCEL_OPTION)
				Thread.sleep(2000);
			this.repaint();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//This is a method to draw the circes in the right place.
	public void drawCircle(Graphics g, int x, int y, int[][] Array) {
		Graphics2D g2D = (Graphics2D) g;
		switch (Array[y / 100][x / 100]) {
		case 1:
			g2D.setColor(Color.MAGENTA);
			g2D.fillOval((x / 100 * 100) + 32, (y / 100 * 100) + 55, 50, 50);
			break;
		case 2:
			g2D.setColor(Color.blue);
			g2D.fillOval((x / 100 * 100) + 32, (y / 100 * 100) + 55, 50, 50);
			break;
		case 3:
			g2D.setColor(Color.green);
			g2D.fillOval((x / 100 * 100) + 32, (y / 100 * 100) + 55, 50, 50);
			break;
		case 4:
			g2D.setColor(Color.RED);
			g2D.fillOval((x / 100 * 100) + 32, (y / 100 * 100) + 55, 50, 50);
			break;
		case 5:
			g2D.setColor(Color.gray);
			g2D.fillOval((x / 100 * 100) + 32, (y / 100 * 100) + 55, 50, 50);
			break;
		case 6:
			g2D.setColor(Color.YELLOW);
			g2D.fillOval((x / 100 * 100) + 32, (y / 100 * 100) + 55, 50, 50);
			break;
		case 7:
			g2D.setColor(Color.cyan);
			g2D.fillOval((x / 100 * 100) + 32, (y / 100 * 100) + 55, 50, 50);
			break;
		case 8:
			g2D.setColor(Color.PINK);
			g2D.fillOval((x / 100 * 100) + 32, (y / 100 * 100) + 55, 50, 50);
			break;
		}

	}
	//This is a method to show the circles the first time. The other method doesnt show correctly so we made this.
	public void drawCircleFirst(Graphics g, int x, int y, int[][] Array) {
		Graphics2D g2D = (Graphics2D) g;
		switch (Array[y / 100][x / 100]) {
		case 1:
			g2D.setColor(Color.MAGENTA);
			g2D.fillOval((x / 100 * 100) + 25, (y / 100 * 100) + 25, 50, 50);
			break;
		case 2:
			g2D.setColor(Color.blue);
			g2D.fillOval((x / 100 * 100) + 25, (y / 100 * 100) + 25, 50, 50);
			break;
		case 3:
			g2D.setColor(Color.green);
			g2D.fillOval((x / 100 * 100) + 25, (y / 100 * 100) + 25, 50, 50);
			break;
		case 4:
			g2D.setColor(Color.RED);
			g2D.fillOval((x / 100 * 100) + 25, (y / 100 * 100) + 25, 50, 50);
			break;
		case 5:
			g2D.setColor(Color.gray);
			g2D.fillOval((x / 100 * 100) + 25, (y / 100 * 100) + 25, 50, 50);
			break;
		case 6:
			g2D.setColor(Color.YELLOW);
			g2D.fillOval((x / 100 * 100) + 25, (y / 100 * 100) + 25, 50, 50);
			break;
		case 7:
			g2D.setColor(Color.cyan);
			g2D.fillOval((x / 100 * 100) + 25, (y / 100 * 100) + 25, 50, 50);
			break;
		case 8:
			g2D.setColor(Color.PINK);
			g2D.fillOval((x / 100 * 100) + 25, (y / 100 * 100) + 25, 50, 50);
			break;
		}

	}
	//This is a method to erase the circles we have drawn.
	public void eraseCircle(Graphics g, int x, int y) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setColor(Color.black);
		g2D.fillOval((x / 100 * 100) + 32, (y / 100 * 100) + 55, 50, 50);
	}
}