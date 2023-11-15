import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class GUITask extends JPanel implements ActionListener {
	JFrame frame;
	JPanel buttonPanel,bigPanel;
	GridLayout buttonlayout,biglayout;
	JMenuBar menu;
	JButton north,south,east, west,reset;
	JMenu fontoptions;
	JMenu fonty;
	JMenu tcol;
	JMenuItem[] fontoption, sizeoption, tcolors;
	String[] fontnames,bgcolornames,textcolorname,outcolorname;
	JTextArea area;
	Font currentfont;
	int currentsize;
	Font[] allfonts, allsizes, allcolors;
	int[] fontsizes;
	Color[] bordercolor,textcolor,outlinecolor,textbackgroundcolor;
	public GUITask() {
		frame = new JFrame("GUI Task");
		frame.setLayout(new BorderLayout());
		frame.add(this);
		
		menu = new JMenuBar();
		
		fontoptions = new JMenu("Font");
		fonty = new JMenu("Font Sizes");
		tcol = new JMenu("Text Color");
		fontoption = new JMenuItem[3];
		sizeoption = new JMenuItem[3];
		tcolors = new JMenuItem[3];
		menu.setLayout(new GridLayout(1,5));
		menu.add(fontoptions);
		menu.add(fonty);
		menu.add(tcol);
		
		fontnames = new String[]{"Times New Roman","Arial","Consolas"};
		fontsizes = new int[] {18,24,50};
		textcolorname = new String[] {"Green","Blue","Orange"};
		textcolor = new Color[] {Color.GREEN, Color.BLUE, Color.ORANGE};
		allfonts = new Font[fontnames.length];
		allsizes = new Font[fontnames.length];
		allcolors = new Font[fontnames.length];

		north = new JButton("North");
		south = new JButton("South");
		east = new JButton("East");
		west = new JButton("West");
		reset = new JButton("Reset");

		for(int i = 0; i < fontnames.length; i++) {
			allfonts[i] = new Font(fontnames[i], Font.PLAIN,fontsizes[0]);
			fontoption[i] = new JMenuItem(fontnames[i]);
			fontoption[i].setFont(allfonts[i]);
			fontoption[i].addActionListener(this);
			fontoptions.add(fontoption[i]);
			
			allsizes[i] = new Font(fontnames[0], Font.PLAIN,fontsizes[0]);
			sizeoption[i] = new JMenuItem(fontsizes[i]+"");
			sizeoption[i].addActionListener(this);
			fonty.add(fontsizes[i]+"");
			
			allcolors[i] = new Font(fontnames[0], Font.PLAIN,fontsizes[0]);
			tcolors[i] = new JMenuItem(textcolorname[i]+"");
			tcolors[i].addActionListener(this);
			tcol.add(textcolorname[i]+"");
		}
		currentfont = allfonts[0];
		outlinecolor = new Color[] {Color.MAGENTA};
		
		bordercolor = new Color[1];
		bordercolor[0] = Color.WHITE;
		textcolor = new Color[1];
		textcolor[0] = Color.ORANGE;
		textbackgroundcolor = new Color[1];
		textbackgroundcolor[0] = Color.BLACK;
		reset.addActionListener(this);
		
		menu.add(reset);
		
		biglayout = new GridLayout(1,4);
		bigPanel = new JPanel();
		bigPanel.setLayout(biglayout);
		
		buttonPanel = new JPanel();
		buttonlayout = new GridLayout(1,4);
		buttonPanel.setLayout(buttonlayout);
		buttonPanel.add(north);
		buttonPanel.add(east);
		buttonPanel.add(south);
		buttonPanel.add(west);

		north.addActionListener(this);
		east.addActionListener(this);
		west.addActionListener(this);
		south.addActionListener(this);
		south.addActionListener(this);

		north.setBorder(new LineBorder(outlinecolor[0]));
		east.setBorder(new LineBorder(outlinecolor[0]));
		west.setBorder(new LineBorder(outlinecolor[0]));
		south.setBorder(new LineBorder(outlinecolor[0]));
		reset.setBorder(new LineBorder(outlinecolor[0]));

		area = new JTextArea();
		area.setBackground(Color.WHITE);
		area.setForeground(Color.BLACK);
		

		biglayout = new GridLayout(1,4);
		bigPanel = new JPanel();
		bigPanel.setLayout(biglayout);
		bigPanel.add(buttonPanel);
		bigPanel.add(menu);
		
		frame.add(bigPanel, BorderLayout.NORTH);
		frame.add(area,BorderLayout.CENTER);
		frame.setSize(1000,600);
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==north) {
			frame.remove(bigPanel);
			buttonlayout=new GridLayout(1,4);
			biglayout = new GridLayout(1,4);
			buttonPanel.setLayout(buttonlayout);
			bigPanel.setLayout(biglayout);
			bigPanel.remove(menu);
			bigPanel.remove(buttonPanel);
			menu.setLayout(new GridLayout(1,4));
			menu.remove(fontoptions);
			menu.remove(reset);
			
			menu.add(fontoptions);
			menu.add(reset);
			bigPanel.add(buttonPanel);
			bigPanel.add(menu);

			frame.add(bigPanel,BorderLayout.NORTH);

		}
		if(e.getSource()==south) {
			frame.remove(bigPanel);
			buttonlayout=new GridLayout(1,4);
			biglayout = new GridLayout(1,4);
			buttonPanel.setLayout(buttonlayout);
			bigPanel.setLayout(biglayout);
			bigPanel.remove(menu);
			bigPanel.remove(buttonPanel);
			menu.setLayout(new GridLayout(1,4));
			menu.remove(fontoptions);
			menu.remove(reset);
			
			menu.add(fontoptions);
			menu.add(reset);
			bigPanel.add(buttonPanel);
			bigPanel.add(menu);

			frame.add(bigPanel,BorderLayout.SOUTH);

		}
		if(e.getSource()==east) {
			frame.remove(bigPanel);
			buttonlayout=new GridLayout(4,1);
			biglayout = new GridLayout(4,1);
			buttonPanel.setLayout(buttonlayout);
			bigPanel.setLayout(biglayout);
			bigPanel.remove(menu);
			bigPanel.remove(buttonPanel);
			menu.setLayout(new GridLayout(4,1));
			menu.remove(fontoptions);
			menu.remove(reset);
			
			menu.add(fontoptions);
			menu.add(reset);
			bigPanel.add(buttonPanel);
			bigPanel.add(menu);

			frame.add(bigPanel,BorderLayout.EAST);

		}
		if(e.getSource()==west) {
			frame.remove(bigPanel);
			buttonlayout=new GridLayout(4,1);
			biglayout = new GridLayout(4,1);
			buttonPanel.setLayout(buttonlayout);
			bigPanel.setLayout(biglayout);
			bigPanel.remove(menu);
			bigPanel.remove(buttonPanel);
			menu.setLayout(new GridLayout(4,1));
			menu.remove(fontoptions);
			menu.remove(reset);
			
			menu.add(fontoptions);
			menu.add(reset);
			bigPanel.add(buttonPanel);
			bigPanel.add(menu);

			frame.add(bigPanel,BorderLayout.WEST);

		}/*
		if(e.getSource()==reset) {
			frame.remove(bigPanel);
			buttonlayout=new GridLayout(1,4);
			biglayout = new GridLayout(1,4);
			buttonPanel.setLayout(buttonlayout);
			bigPanel.setLayout(biglayout);
			bigPanel.remove(menu);
			bigPanel.remove(buttonPanel);
			menu.setLayout(new GridLayout(1,4));
			menu.remove(fontoptions);
			menu.remove(reset);
			
			menu.add(fontoptions);
			menu.add(reset);
			bigPanel.add(buttonPanel);
			bigPanel.add(menu);

			frame.add(bigPanel,BorderLayout.NORTH);
			currentsize = 18;

		}*/
	currentsize = fontsizes[0];

		for(int x = 0; x < 3; x++) {
			if(e.getSource()==sizeoption[x]) {
				currentsize = fontsizes[x];
				System.out.println("yes");
				currentfont = new Font(allfonts[x].getName(), Font.PLAIN, currentsize);
				area.setFont(currentfont);

			}
		}
		System.out.println(currentsize);
		for(int x = 0; x <3; x++) {
			if(e.getSource()==fontoption[x]) {
				currentfont = new Font(allfonts[x].getName(), Font.PLAIN, currentsize);
				area.setFont(currentfont);
			}
				
		}
		for(int x = 0; x <3; x++) {
			System.out.println("OO");
			if(e.getSource()==tcolors[x]) {
				System.out.println("HOOO");
				area.setForeground(textcolor[x]);
			}
				
		}
		
		
		
			
	
		frame.revalidate();
	}
	
	
	public static void main(String[] args) {
		GUITask t = new GUITask();
	}

	

}
