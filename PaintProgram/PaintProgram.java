import java.awt.*;

import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.Iterator;
import java.awt.geom.*;

public class PaintProgram  extends JPanel implements MouseMotionListener, ActionListener, MouseListener, AdjustmentListener, ChangeListener{
	
	JFrame frame;
	ArrayList<Point> points;
	Color currentColor, oldColor, background;
	Color[] colors;
	JMenuBar bar;
	Shape currShape;
	JMenu colorMenu, fileMenu;
	JMenuItem save, load, clear, exit;
	JMenuItem[] colorOptions;
	JButton freeLineButton, rectangleButton, ovalButton, eraser, undo, redo;
	Stack<Object> shapes, undoRedoStack; 
	boolean drawing, shiftPressed;
	boolean firstClick = true;
	boolean drawingFreeLine = true, drawingRectangle = false, drawingOval = false, eraserOn = false;
    int penWidth,currX,currY,currWidth,currHeight;
	JScrollBar penWidthBar;
	JColorChooser colorChooser;
	BufferedImage loadedImage;
	JFileChooser fileChooser;
	ImageIcon freeLineImg, rectImg, ovalImg, loadImg, saveImg, undoImg, redoImg, eraserImg;
	public PaintProgram() {
		frame = new JFrame("Paint program");
		frame.add(this);
		
		bar = new JMenuBar();
		colorMenu = new JMenu("Color Options");
		
		colors = new Color[] {Color.RED,Color.orange,Color.yellow,Color.GREEN,Color.blue,Color.CYAN,Color.magenta};
		
		colorOptions = new JMenuItem[colors.length];
		
		colorMenu.setLayout(new GridLayout(7,1));
		for(int x = 0; x < colorOptions.length;x++) {
			colorOptions[x] = new JMenuItem();
			colorOptions[x].putClientProperty("colorIndex",x);
			colorOptions[x].setBackground(colors[x]);
			colorOptions[x].addActionListener(this);
			colorOptions[x].setFocusable(false);
			colorMenu.add(colorOptions[x]);
		}
		
		
		
		
		currentColor=colors[0];
		
		colorChooser = new JColorChooser();
		colorChooser.getSelectionModel().addChangeListener(this);
		colorMenu.add(colorChooser);
				
		drawing = false;
		
		penWidthBar = new JScrollBar(JScrollBar.HORIZONTAL,1,0,1,40);
		penWidthBar.addAdjustmentListener(this);
		penWidthBar.setFocusable(false);
		penWidth = penWidthBar.getValue();
		
		points = new ArrayList<Point>();
		
		shapes=new Stack<Object>();
		
		undoRedoStack = new Stack<Object>();

		fileMenu = new JMenu("File");
		fileMenu.addActionListener(this);
		save = new JMenuItem("Save", KeyEvent.VK_S);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
		save.addActionListener(this);
		
		load = new JMenuItem("Load", KeyEvent.VK_L);
		load.addActionListener(this);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,ActionEvent.CTRL_MASK));

		clear = new JMenuItem("New");
		clear.addActionListener(this);
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);

		fileMenu.add(clear);
		fileMenu.add(load);
		fileMenu.add(save);
		fileMenu.add(exit);
		
		String currDir = System.getProperty("user.dir");
		fileChooser = new JFileChooser(currDir);
		
		saveImg = new ImageIcon("saveImg.png");
		saveImg = new ImageIcon(saveImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		
		loadImg = new ImageIcon("loadImg.png");
		loadImg = new ImageIcon(loadImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		
		save.setIcon(saveImg);
		load.setIcon(loadImg);
		
		freeLineImg = new ImageIcon("freeLineImg.png");
		freeLineImg = new ImageIcon(freeLineImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		freeLineButton = new JButton();
		freeLineButton.setIcon(freeLineImg);
		freeLineButton.setFocusPainted(false);
		freeLineButton.setBackground(Color.LIGHT_GRAY);
		freeLineButton.setOpaque(true);
		freeLineButton.setBorderPainted(false);
		freeLineButton.setFocusable(false);
		freeLineButton.addActionListener(this);
		
		rectImg = new ImageIcon("rectImg.png");
		rectImg = new ImageIcon(rectImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		rectangleButton = new JButton();
		rectangleButton.setIcon(rectImg);
		rectangleButton.setFocusPainted(false);
		rectangleButton.setBackground(Color.LIGHT_GRAY);
		rectangleButton.setOpaque(true);
		rectangleButton.setBorderPainted(false);
		rectangleButton.setFocusable(false);
		rectangleButton.addActionListener(this);
		
		ovalImg = new ImageIcon("ovalImg.png");
		ovalImg = new ImageIcon(ovalImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		ovalButton = new JButton();
		ovalButton.setIcon(ovalImg);
		ovalButton.setFocusPainted(false);
		ovalButton.setBackground(Color.LIGHT_GRAY);
		ovalButton.setOpaque(true);
		ovalButton.setFocusPainted(false);
		ovalButton.setFocusable(false);
		ovalButton.setBorderPainted(false);
		ovalButton.addActionListener(this);
		
		eraserImg = new ImageIcon("eraserImg.png");
		eraserImg = new ImageIcon(eraserImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		eraser = new JButton();
		eraser.setIcon(eraserImg);
		eraser.setFocusPainted(false);
		eraser.setBackground(Color.LIGHT_GRAY);
		eraser.setOpaque(true);
		eraser.setFocusPainted(false);
		eraser.setFocusable(false);
		eraser.setBorderPainted(false);
		eraser.addActionListener(this); 
		
		undoImg = new ImageIcon("undoImg.png");
		undoImg = new ImageIcon(undoImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		undo = new JButton();
		undo.setIcon(undoImg);
		undo.setFocusPainted(false);
		undo.setBackground(Color.LIGHT_GRAY);
		undo.setOpaque(true);
		undo.setFocusPainted(false);
		undo.setFocusable(false);
		undo.setBorderPainted(false);
		undo.addActionListener(this); 
		
		redoImg = new ImageIcon("redoImg.png");
		redoImg = new ImageIcon(redoImg.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
		redo = new JButton();
		redo.setIcon(redoImg);
		redo.setFocusable(false);
		redo.setBackground(Color.LIGHT_GRAY);
		redo.setOpaque(true);
		redo.setFocusPainted(false);
		redo.setFocusable(false);
		redo.setBorderPainted(false);
		redo.addActionListener(this); 


		
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		bar.add(fileMenu);
		bar.add(colorMenu);
		bar.add(penWidthBar);
		bar.add(freeLineButton);
		bar.add(eraser);
		bar.add(undo);
		bar.add(redo);
		bar.add(rectangleButton);
		bar.add(ovalButton);

		background = Color.WHITE;
		frame.add(bar, BorderLayout.NORTH);
		
		frame.setSize(1000,700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2=(Graphics2D)g;
        g2.setColor(background);
        g2.fillRect(0,0,frame.getWidth(),frame.getHeight());
        if(loadedImage!=null)
        {
            g2.drawImage(loadedImage,0,0,null);
        }
        Iterator<Object> it=shapes.iterator();

        while(it.hasNext())
        {
            Object next=it.next();
            if(next instanceof Rectangle)
            {
                Rectangle temp=(Rectangle)next;
                g2.setColor(temp.getColor());
                g2.setStroke(new BasicStroke(temp.getPenWidth()));
                g2.draw(temp.getShape());
            }
            else if(next instanceof Oval)
                {
                    Oval temp=(Oval)next;
                    g2.setColor(temp.getColor());
                    g2.setStroke(new BasicStroke(temp.getPenWidth()));
                    g2.draw(temp.getShape());
                }
                else
                {
                    ArrayList<?> temp=(ArrayList<?>)next;
                    if(temp.size()>0)
                    {
                        g2.setStroke(new BasicStroke(((Point)temp.get(0)).getPenWidth()));
                        for(int x=0;x<temp.size()-1;x++)
                        {
                            Point p1=(Point)temp.get(x);
                            Point p2=(Point)temp.get(x+1);
                            g2.setColor(p1.getColor());
                            g2.drawLine(p1.getX(),p1.getY(),p2.getX(),p2.getY());
                        }
                    }
                }
        }

        if(drawingFreeLine && points.size()>0)
        {
            g2.setStroke(new BasicStroke(points.get(0).getPenWidth()));
            g2.setColor(points.get(0).getColor());
            for(int x=0;x<points.size()-1;x++)
            {
                Point p1=points.get(x);
                Point p2=points.get(x+1);
                g2.setColor(p1.getColor());
                g2.drawLine(p1.getX(),p1.getY(),p2.getX(),p2.getY());
            }
        }



    }
	
	public BufferedImage createImage(){
		int width=this.getWidth();
		int height=this.getHeight();
		BufferedImage img=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
		Graphics2D g2=img.createGraphics();
		this.paint(g2);
		g2.dispose();
		return img;
	}
	@Override
	public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==save)
        {
            FileFilter filter=new FileNameExtensionFilter("*.png","png");
            fileChooser.setFileFilter(filter);
            if(fileChooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
            {
                File file=fileChooser.getSelectedFile();
                try
                {
                    String st=file.getAbsolutePath();
                    if(st.indexOf(".png")>0)
                        st=st.substring(0,st.length()-4);
                    ImageIO.write(createImage(),"png",new File(st+".png"));

                }catch(IOException ioe){}
                String st=file.getAbsolutePath();

            }
        }
        else if(e.getSource()==load)
            {
                fileChooser.showOpenDialog(null);
                File imgFile=fileChooser.getSelectedFile();
                if(imgFile!=null &&imgFile.toString().indexOf(".png")>=0)
                {
                    try
                    {
                        loadedImage=ImageIO.read(imgFile);
                    }catch(IOException ioe){}
                    shapes=new Stack<Object>();
                    repaint();
                }
                else
                {
                    if(imgFile!=null)
                        JOptionPane.showMessageDialog(null,"Wrong file type. Please select a PNG file.");
                }
            }
        else if(e.getSource()==clear)
            {
                shapes=new Stack<Object>();
                loadedImage=null;
                repaint();
            }
            else if(e.getSource()==exit)
                {
                    System.exit(0);
                }
                else if(e.getSource()==freeLineButton)
                    {
                        drawingFreeLine=true;
                        drawingRectangle=false;
                        drawingOval=false;
                        eraserOn=false;
                        freeLineButton.setBackground(Color.LIGHT_GRAY);
                        rectangleButton.setBackground(null);
                        ovalButton.setBackground(null);
                        eraser.setBackground(null);
                        currentColor = oldColor;
                    }
                    else if(e.getSource()==rectangleButton)
                        {
                            drawingFreeLine=false;
                            drawingRectangle=true;
                            drawingOval=false;
                            freeLineButton.setBackground(null);
                            rectangleButton.setBackground(Color.LIGHT_GRAY);
                            ovalButton.setBackground(null);
                            eraser.setBackground(null);
                            currentColor = oldColor;
                        }
                        else if(e.getSource()==ovalButton)
                        {
                            drawingFreeLine=false;
                            drawingRectangle=false;
                            drawingOval=true;
                            freeLineButton.setBackground(null);
                            rectangleButton.setBackground(null);
                            ovalButton.setBackground(Color.LIGHT_GRAY);
                            eraser.setBackground(null);
                            currentColor = oldColor;
                        }
                        else if(e.getSource()==eraser)
                        {
                        	drawing = true;
                        	drawingRectangle = false;
                        	drawingOval = false;
                        	eraserOn = true;
                        	freeLineButton.setBackground(null);
                        	rectangleButton.setBackground(null);
                        	ovalButton.setBackground(null);
                        	eraser.setBackground(Color.LIGHT_GRAY);
                        	oldColor = currentColor;
                        	currentColor = background;
                        }
                        else if (e.getSource()==undo)
                        {
                        	undo();
                        }
                        else if(e.getSource() == redo)
                        {
                        	redo();
                        }
                        else
                        {
                        	if(eraserOn)
                        	{
                        		drawing=true;
                        		drawingRectangle = false;
                            	drawingOval = false;
                            	eraserOn = true;
                            	freeLineButton.setBackground(Color.LIGHT_GRAY);
                            	rectangleButton.setBackground(null);
                            	ovalButton.setBackground(null);
                            	eraser.setBackground(null);
                            	
                        	}
                            int index=(int)((JMenuItem)e.getSource()).getClientProperty("colorIndex");
                            currentColor=colors[index];
                        }
    }
	public void mouseDragged(MouseEvent e)
    {
        if(drawingRectangle || drawingOval)
        {
            if(firstClick)
            {
                currX=e.getX();
                currY=e.getY();
                if(drawingRectangle)
                    currShape=new Rectangle(currX,currY,currentColor,penWidth,0,0);
                else currShape=new Oval(currX,currY,currentColor,penWidth,0,0);
                firstClick=false;
                shapes.push(currShape);
            }
            else
            {
                currWidth=Math.abs(e.getX()-currX);
                currHeight=Math.abs(e.getY()-currY);
                currShape.setWidth(currWidth);
                currShape.setHeight(currHeight);
                if(currX<=e.getX() && currY>=e.getY())
                {
                    currShape.setY(e.getY());
                }
                else if(currX>=e.getX() && currY<=e.getY())
                    {
                        currShape.setX(e.getX());
                    }
                    else if(currX>=e.getX() && currY>=e.getY())
                        {
                            currShape.setX(e.getX());
                            currShape.setY(e.getY());
                        }
            }
        }
        else
        {
            points.add(new Point(e.getX(),e.getY(),currentColor,penWidth));
        }
        repaint();
    }


	
	public static void main(String[] args) {
		PaintProgram app = new PaintProgram();	
	}
	
	
	public class Point{
		
		int x,y, size;
		Color color;
		public Point(int x, int y, Color color, int size) {
			this.x = x;
			this.y=y;
			this.color=color;
			this.size = size;
		}
		
		public int getX() {
			
			return x;
		}
		
		public int getY() {
			
			return y;
		}
		
		public void setX(int x) {
			this.x=x;
			
		}
		public void setY(int y) {
			this.y=y;
			
		}
		public Color getColor() {
			return color;
		}
		
		public int getPenWidth() {
			return size;
		}
		
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public void mouseMoved(MouseEvent e) {
		
	}
	

	
	public void mouseReleased(MouseEvent e){
        if(drawingRectangle || drawingOval){
            firstClick=true;
        }
        else{
            shapes.push(points);
            points=new ArrayList<Point>();
        }
        undoRedoStack = new Stack<Object>();
        repaint();
    
	}
	public void redo() {
		if(!undoRedoStack.isEmpty()) {
			shapes.push(undoRedoStack.pop());
			repaint();
		}
	}
	public void undo() {
		if(!shapes.isEmpty()) {
			undoRedoStack.push(shapes.pop());
			repaint();
		}
	}
	public void keyPressed(KeyEvent e) {
		if(e.isControlDown()) {
			if(e.getKeyCode()==KeyEvent.VK_Z) {
				undo();
			}
			if(e.getKeyCode()==KeyEvent.VK_Y) {
				redo();
			}
		}
	}
	
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

	
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		penWidth = penWidthBar.getValue();
		
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		currentColor = colorChooser.getColor();		
	}
	
	public class Shape extends Point{
		
		private int x,y,width,height,penWidth;
		Color color;
		
		public Shape(int x, int y, Color color, int size, int width, int height) {
			super(x, y, color, size);
			this.width = width;
			this.height = height;
			
		}
		public void setWidth(int width) {
			this.width = width;
			
		}
		public void setHeight(int height) {
			this.height = height;
			
		}
		public int getWidth()
		{
		      return width;
		}
		public int getHeight()
		{
		      return height;
		}
		
	}

	public class Rectangle extends Shape{
		
		public Rectangle(int x, int y, Color color, int penWidth, int width, int height) {
			super(x, y, color, penWidth, width, height);
		
		}
		public Rectangle2D.Double getShape(){
			return new Rectangle2D.Double(getX(),getY(),getWidth(),getHeight());
		
		}
		
	}
	public class Oval extends Shape{
		
		public Oval(int x, int y, Color color, int penWidth, int width, int height) {
			super(x, y, color, penWidth, width, height);
		
		}
		public Ellipse2D.Double getShape(){
			return new Ellipse2D.Double(getX(),getY(),getWidth(),getHeight());
		
		}
	}
}
