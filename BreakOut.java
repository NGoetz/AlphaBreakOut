package alphabreakout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

//seitliche reflektion paddle

public class BreakOut extends JFrame {//main-class, containing the whole game environment
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Vector<Brick> bricks;//vector of all bricks
	Reflector bar;
	Ball ball;
	boolean clicked;
	Timer timer;
	long time1;
	long time2;
	int level;
	int posbar;
	

	public BreakOut() {
		Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
		if(d.height>1000){
		setPreferredSize(new Dimension(1000,1000));
		posbar=890;
		setLocation((d.width-1000)/2,(d.height-1000)/2-25);
		}else{
			setPreferredSize(new Dimension(1000,d.height-45));
			posbar=d.height-100;
			
			setLocation((d.width-1000)/2,0);
		}
		setTitle("AlphaBreakOut");
		clicked=false;
		level=1;//start with level 1
		time1=System.currentTimeMillis();;
		time2=System.currentTimeMillis();;
		
		
		addWindowListener(new MyWindowListener());
		addMouseListener(new MouseHelper());
		addMouseMotionListener(new MouseMotionHelper());
		bricks=brickcreator();
		bar=new Reflector(posbar);
		ball=new Ball(posbar-24);
		Gamesuit Game=new Gamesuit();
		add(Game);
		pack();
		setVisible(true);
		timer=new Timer(10, new ActionListener (){//main timer, coordinating the animiation
			public void actionPerformed(ActionEvent ae){
				ball.setPosx((int) (ball.getPosx()+timer.getDelay()*level*0.2*ball.getSpeedx()));//get new place of ball
				ball.setPosy((int) (ball.getPosy()+timer.getDelay()*level*0.2*ball.getSpeedy()));
				
				ball.ReflectAtWalls();//check for reflection at all objects
				ball.ReflectAtRefl(bar);
				
				for(int i=0; i<bricks.size();i++){
					if(ball.ReflectAtBrick(bricks.elementAt(i))){
						if(bricks.elementAt(i).getInt()==0){
							bricks.removeElementAt(i);
						}
						timer.setDelay(timer.getDelay()*3);//prevent reflection on other bricks
						break;
					}else
						timer.setDelay(10);
					
				}
				if(ball.Gameover()){//if out of bounds, start new game
					timer.stop();
					clicked=false;
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
					bricks=brickcreator();
					bar=new Reflector(posbar);
					ball=new Ball(posbar-24);
					Gamesuit Game=new Gamesuit();
					level=1;
					
				}
				if(bricks.size()==0){//if all bricks are broken -> LEVEL UP!
					timer.stop();
					clicked=false;
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						
						e.printStackTrace();
					}
					bricks=brickcreator();
					bar=new Reflector(posbar);
					ball=new Ball(posbar-24);
					Gamesuit Game=new Gamesuit();
					level++;
					
				}
				repaint();
			}
			});
	
		
	}
	public class Gamesuit extends JPanel{//place for painting
	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		setBackground(Color.BLACK);
		g.setColor(Color.ORANGE);
		g.drawString("Welcome to AlphaBreakOut! Start by clicking. You can move the paddle with your mouse. The speed of the paddle ist physically correct transferred to the ball."
				, 20, 500);
		g.drawString("Some bricks have multiple lifes, but you have only one. If you succeed, your level ist raised. Current level: "+level+". By Niklas Götz", 20, 530);
		for(int i=0;i<bricks.size(); i++){
			g.setColor(Color.BLACK);
			g.draw3DRect(bricks.elementAt(i).getPosx(), bricks.elementAt(i).getPosy(), bricks.elementAt(i).getLenx(), bricks.elementAt(i).getLeny(),false);
			if(bricks.elementAt(i).getInt()==1){//color shows integrity
				g.setColor(Color.GREEN);
				g.fill3DRect(bricks.elementAt(i).getPosx(), bricks.elementAt(i).getPosy(), bricks.elementAt(i).getLenx(), bricks.elementAt(i).getLeny(),true);}
			else if(bricks.elementAt(i).getInt()==2){
				g.setColor(Color.RED);
				g.fill3DRect(bricks.elementAt(i).getPosx(), bricks.elementAt(i).getPosy(), bricks.elementAt(i).getLenx(), bricks.elementAt(i).getLeny(),true);
			}else if(bricks.elementAt(i).getInt()==3){
				g.setColor(Color.BLUE);
				g.fill3DRect(bricks.elementAt(i).getPosx(), bricks.elementAt(i).getPosy(), bricks.elementAt(i).getLenx(), bricks.elementAt(i).getLeny(),true);
			}
		}
		g.setColor(Color.BLACK);
		g.drawOval(ball.getPosx(), ball.getPosy(), 2*ball.getRad(), 2*ball.getRad());
		g.setColor(Color.CYAN);
		g.fillOval(ball.getPosx(), ball.getPosy(), 2*ball.getRad(), 2*ball.getRad());
		g.setColor(Color.BLACK);
		g.drawRoundRect(bar.getPosx(), bar.getPosy(), bar.getLenx(), bar.getLeny(), 15, 15);
		g.setColor(Color.WHITE);
		g.fillRoundRect(bar.getPosx(), bar.getPosy(), bar.getLenx(), bar.getLeny(), 15, 15);
		
		
	}
	}
	class MouseHelper extends MouseAdapter{
		public void mousePressed(MouseEvent e){//player has to give okay for starting
			timer.start();
			clicked=true;
			
		}
	}
	class MouseMotionHelper extends MouseMotionAdapter{
		public void mouseMoved(MouseEvent e){//moves the paddle and gets new speed if enough time passed
			time1=System.currentTimeMillis();
			if(clicked==true){
			int pos=e.getX();
			if(time1-time2>timer.getDelay()){
			bar.setSpeed((int)Math.ceil((3*(pos-bar.getPosxp())/(time1-time2))));
			bar.setPosxp(pos);
			time2=time1;
			}
			if(pos<1000){
			bar.setPosx(pos);
			repaint(0, 880, 1000,40);
			
			}
			
			}
			
		}
	}
		
	
	class MyWindowListener extends WindowAdapter{
		public void windowClosing(WindowEvent event){//enable window-closing
			
			System.exit(0);
		}
	}
	private Vector<Brick>brickcreator(){//creates a minimum amount of bricks by randomness
		Vector<Brick>returner=new Vector<Brick>();
		Random r=new Random();
		int counter=0;
		for(int i=0;i<17;i++){
		if(counter>=50)
			break;
			
				int xspace=50+r.nextInt(3)*10;
				while(xspace<870){
					int lenx=60+r.nextInt(3)*10;
					int integ=r.nextInt(10);
					if(integ==0)
						xspace=xspace+lenx+1;
					else{
						if(integ<7){
							returner.add(new Brick(50+i*51,xspace,50,lenx,1));
							counter++;
							xspace=xspace+lenx+1;
						}else if(integ<9){
							returner.add(new Brick(50+i*51,xspace,50,lenx,2));
							counter++;
							xspace=xspace+lenx+1;
						}else{
							returner.add(new Brick(50+i*51,xspace,50,lenx,3));
							counter++;
							xspace=xspace+lenx+1;
						}
					}
				}
			
		}
		return returner;
	}
	public static void main (String[] args){
		new BreakOut();
	}
}
//by Niklas Götz