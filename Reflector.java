package alphabreakout;

public class Reflector {
int lenx;
int leny;
int posx;
int posxp;
int posy;
int speed;
int savex;
	public Reflector() {
		leny=20;
		lenx=180;
		posx=410;
		posy=890;
		posxp=410;//saves the last position for getting the value for speed
		speed=0;
	}
public int getSpeed(){
	return speed;
}
public void setSpeed(int s){
	speed=s;
}
public int getPosx(){
	return posx;
}
public void setPosx(int px){
	posx=px;
}
public int getPosxp(){
	return posxp;
}
public void setPosxp(int pxp){
	posxp=pxp;
}
public int getPosy(){
	return posy;
}
public void setPosy(int py){
	posx=py;
}
public int getLenx(){
	return lenx;
}
public int getLeny(){
	return leny;
}
public boolean contactRefl(int posbx,int posby, int rad){//checks if it was touched by the ball
		if(((posbx+2*rad)>posx&&(posbx)<(posx+lenx))&&((posby+2*rad)>posy&&(posby)<(posy+leny))){	
			return true;
		}else
			return false;

	}
}
