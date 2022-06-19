import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Polygon;

public class Wall {
	private int[] rows;
	private int[] cols;
	private int r;
	private int g;
	private int b;
	String type;
	int size;
	public Wall(int[] rows, int[]cols, int r, int g, int b, String type, int size) {
		this.rows = rows;
		this.cols = cols;
		this.r = r;
		this.g = g;
		this.b = b;
		this.type = type;
		this.size = size;
		
	}
	public GradientPaint getPaint() {
		int red = this.r-size;
		if(red<0)
			red=0;
		int green = this.g-size;
		if(green<0)
			green = 0;
		int blue = this.b-size;
		if(blue<0)
			blue=0;
		if(type.equals("Left"))
			return new GradientPaint(cols[0],rows[1],new Color(r,g,b), cols[1],rows[1], new Color(red,green,blue));
		else if(type.equals("Right"))
			return new GradientPaint(cols[1],rows[1],new Color(r,g,b), cols[0],rows[0], new Color(red,green,blue));
		else if(type.equals("Cieling"))
			return new GradientPaint(cols[0],rows[0],new Color(r,g,b), cols[0],rows[2], new Color(red,green,blue));
		else if(type.equals("Floor"))
			return new GradientPaint(cols[2],rows[2],new Color(r,g,b), cols[0],rows[0], new Color(red,green,blue));
		else if(type.equals("Forward"))
			return new GradientPaint(cols[0],rows[0],new Color(r,g,b), cols[0],rows[0], new Color(red,green,blue));
		return new GradientPaint(cols[0],rows[1],new Color(r,g,b), cols[1],rows[1], new Color(red,green,blue));
		}
	public String getType() {
		return type;
	}
	public Polygon getPoly() {
		return new Polygon(cols,rows,cols.length);
	}
}
