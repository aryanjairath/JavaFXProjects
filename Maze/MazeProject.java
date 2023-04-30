import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
public class MazeProject extends JPanel implements KeyListener{

	JFrame frame;
	boolean distracted = false;
	int r=2;
	int count;
	int c=0;
	int dir= 1;
	int size = 10;
	int shrink = 50;
	int renderd;
	boolean render;
	Runner hero;
	boolean winner = false;
	boolean winagain = false;
	ArrayList<Wall> walls;
	Image wallImg;
	boolean threed = false;
	char[][] maze = new char[11][74];
	Scanner reader = new Scanner(System.in);
	public MazeProject(){
		hero = new Runner(new Location(r,c), dir, size, Color.RED);
		setBoard();
		frame = new JFrame("A-Mazing Program");
		frame.add(this);
		frame.setSize(frame.getWidth(),frame.getHeight());
//initializing of variables and arrays etc.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.addKeyListener(this);
	}
	
	public void setBoard() {
		String ans;
		File file;
		ans = JOptionPane.showInputDialog("Would you like the easy or hard map?");
		if(ans.contentEquals("easy")|| ans.contentEquals("Easy")) 
			file = new File("/Users/AryanJairath/Desktop/DataStructures/Lucas/Maze.txt");
		else
			file = new File("/Users/AryanJairath/Desktop/DataStructures/Lucas/MazeTwo.txt");
		
		try {
			String text;
			int row = 0;
			BufferedReader input = new BufferedReader(new FileReader(file));
		while((text = input.readLine())!= null) {
			
			for(int col = 0; col < text.length(); col++) {
				maze [row][col] = text.charAt(col);
			 	System.out.print(maze[row][col]);
			}
				row++;
				System.out.println();

		}
				}catch(IOException io){
			System.out.print("file not found");
		}
			if(threed)
				createWalls();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);      //giant erase!!!
		Graphics2D g2 = (Graphics2D)g;
		g2.fillRect(0, 0, 1200, 900);
		
		g2.setColor(Color.BLACK);
		g2.fillRect(10, 75, size*maze[0].length, 25*maze.length);
		g2.setColor(Color.WHITE);
		Font font=new Font("Futura", Font.BOLD, 20);
		Font fontey = new Font("Garamond", Font.BOLD, 15);

		if(!threed) {
			g2.setFont(font);
			g2.drawString("Welcome to the maze game!", 30, 300);
			g2.drawString("In order to play, you will need to use the four directional arrow keys which will alter the direction you face!", 30, 335);
			g2.drawString("Press the spacebar to get inside of the maze (3D directions are located there)", 30, 370);

			for(int c = 0; c <maze[0].length; c++) 
				for(int r = 0; r <maze.length; r++) {
					g2.setColor(Color.GRAY);
					if(maze[r][c] == '1') {
						g2.fillRect(c*size+size, r*size+size, size, size);
						}else {
						g2.drawRect(c*size+size, r*size+size, size, size);
						}
					}
				g2.setColor(hero.getColor());
				g2.fill(hero.getRect());
				if(hero.getLoc().getR() == 6 && hero.getLoc().getC() == 73 && winagain == false){
					g2.drawString("WINNER! CONGRATULATIONS! Number of moves you took to win: "+hero.getCount(), 400, 500);
				}
				g2.setColor(hero.getColor());
				g2.fill(hero.getRect());
				while(hero.getLoc().getR() == 6 && hero.getLoc().getC() == 73 && winagain == false){
				
					g2.drawString("WINNER! CONGRATULATIONS! Number of moves you took to escape: "+hero.getCount(), 400, 500);
					winagain = true;
				}
			}else {
				g2.setFont(fontey);
				g2.drawString("Congratulations! You have arrived at the 3D portion of the maze ", 370, 25);
				g2.drawString("Navigate through the inside using the arrow keys ", 370, 50);
				g2.drawString("If you are facing a wall, a square will be in front of you: use an arrow to get in direction to move again", 370, 75);
				if(winner == true && winagain == false ){
					g2.drawString("  WINNER! Number of moves you took to win: "+hero.getCount(), 700, 350);
					winagain = true;
				}
				for(Wall wall: walls) {
					g2.setPaint( wall.getPaint());
					g2.fillPolygon(wall.getPoly());
					g2.setColor(Color.BLACK);
     				g2.drawPolygon(wall.getPoly());///
					
				}
			
			}
		
	}
	public void keyPressed(KeyEvent e) {
		hero.move(e.getKeyCode(),maze);
		if(e.getKeyCode() == 32&&distracted==false) {
			threed=!threed;
		}
		if(threed&&distracted==false)
			createWalls();
		repaint();
	}
	public void keyReleased(KeyEvent e) {
		
	}
	public void keyTyped(KeyEvent e) {
	}
	public Wall getLeft(int n) {
		int[] rLocs = new int[] {100+shrink*n, 150+shrink*n, 650-shrink*n, 700-shrink*n};
		int[] cLocs = new int[] {100+shrink*n, 150+shrink*n, 150+shrink*n, 100+shrink*n};
		return new Wall(rLocs, cLocs, 255-shrink*n, 255-shrink*n, 255-shrink*n, "Left", shrink);


	}
	public Wall getLeftPath(int n) {
		int[] rLocs = new int[] {150+shrink*n, 150+shrink*n, 650-shrink*n, 650-shrink*n};
		int[] cLocs = new int[] {100+shrink*n, 150+shrink*n, 150+shrink*n, 100+shrink*n};
		return new Wall(rLocs, cLocs, 255-shrink*n, 255-shrink*n, 255-shrink*n, "LeftPath", shrink);

	}
	public Wall getRightPath(int n) {
		int[] rLocs = new int[] {150+shrink*n, 150+shrink*n, 650-shrink*n, 650-shrink*n};
		int[] cLocs = new int[] {650-shrink*n, 700-shrink*n, 700-shrink*n, 650-shrink*n};
		return new Wall(rLocs, cLocs, 255-shrink*n, 255-shrink*n, 255-shrink*n, "RightPath", shrink);


	}
	public Wall getRight(int n) {
		int[] rLocs = new int[] {150+shrink*n, 100+shrink*n, 700-shrink*n, 650-shrink*n};
		int[] cLocs = new int[] {650-shrink*n, 700-shrink*n, 700-shrink*n, 650-shrink*n};
		return new Wall(rLocs, cLocs, 255-shrink*n, 255-shrink*n, 255-shrink*n, "Right", shrink);
	}
	public Wall getFloor(int n) {
		int[] rLocs = new int[] {650-shrink*n, 650-shrink*n, 700-shrink*n, 700-shrink*n};
		int[] cLocs = new int[] {150+shrink*n, 650-shrink*n, 700-shrink*n, 100+shrink*n};
		return new Wall(rLocs, cLocs, 255-shrink*n, 255-shrink*n, 255-shrink*n, "Floor", shrink);
	}
	public Wall getCieling(int n) {
		int[] rLocs = new int[] {100+shrink*n, 100+shrink*n, 150+shrink*n, 150+shrink*n};
		int[] cLocs = new int[] {100+shrink*n, 700-shrink*n, 650-shrink*n, 150+shrink*n};
		return new Wall(rLocs, cLocs, 255-shrink*n, 255-shrink*n, 255-shrink*n, "Cieling", shrink);
	}
	public Wall findFront(int n) {
		int[] rLocs = new int[] {150+shrink*n, 150+shrink*n, 650-shrink*n, 650-shrink*n};
		int[] cLocs = new int[] {150+shrink*n, 650-shrink*n, 650-shrink*n, 150+shrink*n};
		return new Wall(rLocs, cLocs, 255-shrink*n, 255-shrink*n, 255-shrink*n, "Forward", shrink);
	}
	public Wall getFloorTriL(int n) {
			int[] rLocs = new int[] {650-shrink*n, 700-shrink*n, 650-shrink*n};
			int[] cLocs = new int[] {100+shrink*n, 100+shrink*n, 150+shrink*n};
			return new Wall(rLocs, cLocs, 255-shrink*n, 255-shrink*n, 255-shrink*n, "FloorLeft", shrink);
	}
	public Wall getCeilTri(int n) {
		int[] rLocs = new int[] {100+shrink*n, 150+shrink*n, 150+shrink*n};
		int[] cLocs = new int[] {100+shrink*n, 100+shrink*n, 150+shrink*n};
		return new Wall(rLocs, cLocs, 255-shrink*n, 255-shrink*n, 255-shrink*n, "CeilLeft", shrink);
}
	public Wall getFloorTriR(int n) {
		int[] rLocs = new int[] {650-shrink*n, 650-shrink*n, 700-shrink*n};
		int[] cLocs = new int[] {650-shrink*n, 700-shrink*n, 700-shrink*n};
		return new Wall(rLocs, cLocs, 255-shrink*n, 255-shrink*n, 255-shrink*n, "FloorRight", shrink);
}
	public Wall getCeilTriR(int n) {
		int[] rLocs = new int[] {100+shrink*n, 150+shrink*n, 150+shrink*n};
		int[] cLocs = new int[] {700-shrink*n, 700-shrink*n, 650-shrink*n};
		return new Wall(rLocs, cLocs, 255-shrink*n, 255-shrink*n, 255-shrink*n, "CeilRight", shrink);
}

	public void createWalls() {
		walls = new ArrayList<Wall>();
		//floors and ceilings
			
			int rr = hero.getLoc().getR();
			int cc = hero.getLoc().getC();
			int dir = hero.getDir();
			if(!render) {
				String ans;
				ans=JOptionPane.showInputDialog("Before you begin: would you like far render distance or short?");
				render = true;
				if(ans.equals("Far") ||ans.equals("far"))
					renderd = 6;
				if(ans.equals("Short") || ans.equals("short"))
					renderd = 3;
							}
			if(rr==6 && cc == 73&&(winner == false)) {
				winner = true;
			}
			
				
			
				/*winner = true;
			if(winner) {
				System.out.println("Winner winner chicken dinner");
				System.out.println(count);*/

			//}
			for(int n =0; n < renderd; n++) {
				walls.add(getFloor(n));
				walls.add(getCieling(n));
			}
			
			switch(dir) {
			case 0: //up
				for(int n = 0; n < renderd; n++) {
					try {
						if(maze[rr-n][cc-1] == '*') {
							walls.add(getLeft(n));
						}else {
							walls.add(getLeftPath(n));
							walls.add(getFloorTriR(n));
							walls.add(getCeilTriR(n));
						}
							
					}catch(ArrayIndexOutOfBoundsException e) {
						
					}
						try {
							if(maze[rr-n][cc+1] == '*') {
								walls.add(getRight(n));
							}else {
								walls.add(getRightPath(n));
								walls.add(getFloorTriR(n));
								walls.add(getCeilTri(n));
							}
						}
						catch(ArrayIndexOutOfBoundsException e) {
					}		
					
				}
			
				for(int y = 0; y <=renderd; y++) {
					try {
						if(maze[rr-y][cc]=='*') {
							walls.add(findFront(y-1));
							y=renderd;
						
						}
						
					}catch(ArrayIndexOutOfBoundsException e) {
					}
				}
				break;

			case 1:
				
				for(int n = 0;n < renderd; n++) {						
				
					try {
						if(maze[rr-1][cc+n]=='*') {
							walls.add(getLeft(n));
						}else {
							walls.add(getLeftPath(n));
							walls.add(getFloorTriL(n));
							walls.add(getCeilTri(n));
							}
					
						if(maze[rr+1][cc+n] == '*') {
							walls.add(getRight(n));
						}else {
							walls.add(getRightPath(n));
							walls.add(getFloorTriR(n));
							walls.add(getCeilTriR(n));
						}
						

					}catch(ArrayIndexOutOfBoundsException e) {
					}				
				
				for(int y = 0; y <=renderd; y++) {
					try {
						if(maze[rr][cc+y]=='*') {
							walls.add(findFront(y-1));
							y=renderd;
						}
						
					}catch(ArrayIndexOutOfBoundsException e) {
					}
				
				}}
				break;
			case 2:
			
				for(int n = 0; n < renderd; n++) {
					try {
						if(maze[rr+n][cc+1]=='*') {
							walls.add(getLeft(n));

						}
						
						else {
							walls.add(getLeftPath(n));
							walls.add(getFloorTriL(n));
							walls.add(getCeilTri(n));
						}
							
						if(maze[rr+n][cc-1] == '*') {
							walls.add(getRight(n));
						
						}else {
							walls.add(getRightPath(n));
							walls.add(getFloorTriR(n));
							walls.add(getCeilTriR(n));
						}
						
					}catch(ArrayIndexOutOfBoundsException e) {
				}			
				}
				for(int y = 0; y <=renderd; y++) {
					try {
						if(maze[rr+y][cc]=='*') {
							walls.add(findFront(y-1));
							y=renderd;
						}
						
					}catch(ArrayIndexOutOfBoundsException e) {
					}
				}
				break;
			case 3:
								
				for(int n = 0; n < renderd; n++) {
					try {
						if(maze[rr+1][cc-n]=='*') { 
							walls.add(getLeft(n));
						}else {
							walls.add(getLeftPath(n));
							walls.add(getFloorTriL(n));
							walls.add(getCeilTri(n));
						}
						
						if(maze[rr-1][cc-n] == '*') {
							walls.add(getRight(n));
						}else {
							walls.add(getRightPath(n));
							walls.add(getFloorTriR(n));
							walls.add(getCeilTriR(n));					
						}
						
					}catch(ArrayIndexOutOfBoundsException e) {
				}		
				}
				for(int y = 0; y <=renderd; y++) {
					try {
						if(maze[rr][cc-y]=='*') {
							walls.add(findFront(y-1));
							y=renderd;
						}
						
					}catch(ArrayIndexOutOfBoundsException e) {
					}
				}
				break;
			
			}
				
				
	}
	
	
	public static void main(String[] args) {
		
		MazeProject app = new MazeProject();
	}

}
