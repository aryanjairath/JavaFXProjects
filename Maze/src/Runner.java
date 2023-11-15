import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Color;

public class Runner {
	private Location loc;
	private int size;
	private Color color;
	private int dir;
	private int count;
	
		public Runner(Location loc, int dir, int size, Color color) {
			this.loc = loc;
			this.dir = dir;
			this.size = size;
			this.color = color;
			count = 0;
		}
		public Color getColor() {
			return color;
			
		}
		public Location getLoc() {
			return loc;
			
		}
		public int getDir() {
			return dir;
		}
		public int getCount() {
			return count;
		
		}
		public void move(int key, char[][] maze) {
			int r= getLoc().getR();
			int c=getLoc().getC();
			if(key ==38) {
				if(dir == 0) {  //up
					if(r>0&&maze[r-1][c]=='1') {
							getLoc().setR(-1);
							count++;
					}
				}if(dir == 1) {//right
					if(c<maze[0].length-1 && maze[r][c+1] == '1')
						getLoc().setC(+1);
					count++;
				}if(dir == 2) {
					if(r<maze.length-1 && maze[r+1][c] == '1')
						getLoc().setR(+1);
					count++;
				}if(dir == 3) {
					if(c>0 && maze[r][c-1] == '1')
						getLoc().setC(-1);
					count++;
				}
			}if(key == 37) {
				dir--;
				if(dir<0)
					dir=3;
			}if(key == 39) {
				dir++;
				if(dir>3)
					dir=0;
			}
		}
		public Rectangle getRect() {
			int r = getLoc().getR();
			int c = getLoc().getC();
			return new Rectangle(c*size+size, r*size+size, size, size);
		}
}
