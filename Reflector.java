package alphabreakout;

public class Reflector {
int lenx;
int leny;
int posx;
int posxp;
int posy;
int speed;
int savex;
	public Reflector(int py) {
		leny=8;
		lenx=180;
		posx=410;
		posy=py;
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
/*public boolean contactRefl(int posbx,int posby, int rad){//checks if it was touched by the ball
		if(((posbx+2*rad)>posx&&(posbx)<(posx+lenx))&&((posby+2*rad)>posy&&(posby)<(posy+leny))){	
			return true;
		}else
			return false;

	}*/
public boolean contact(int posbx,int posby, int rad){//true means that the circle touches the brick
	if((posbx+2*rad)>posx&&posbx<(posx+lenx)&&posby<posy+leny&&(posby+2*rad)>posy){
		
		return true;
	}else
		return false;

}
public int collisionOnTop(int posbx,int posby, int rad){//0<=>no touch 1<=>touch on top or bottom 2<=>touch on sides
	if(contact(posbx, posby, rad)){
		if(((posbx+rad<posx)||posbx+rad>posx+lenx)&&(posy+leny>posby+rad||posby+rad>posy)) //check kind of collision; if coordinates are in the choosen range, the collision has to be taken place on top of the brick
			return 1;
		else
			return 2;
		
	}else
		return 0;
}
}
