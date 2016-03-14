package alphabreakout;

public class Brick {
	private int posy, posx;
	private int leny, lenx;
	private int integrity;//how often can the brick be hitten before he breaks?
	

	public Brick(int py, int px, int ly,int lx,int i) {//object representing the Breakout-Bricks
		posy=py;
		posx=px;
		leny=ly;
		lenx=lx;
		integrity=i;
	}
public void setInt (int int2){
	integrity=int2;
}
public int getInt (){
	return integrity;
}
public void setPosx(int p){
	posx=p;
}
public int getPosx(){
	return posx;
}
public void setPosy(int p){
	posy=p;
}
public int getPosy(){
	return posy;
}
public void setLenx(int p){
	lenx=p;
}
public int getLenx(){
	return lenx;
}
public void setLeny(int p){
	leny=p;
}
public int getLeny(){
	return leny;
}
public boolean contact(int posbx,int posby, int rad){//true means that the circle touches the brick
	if((posbx+2*rad)>posx&&posbx<(posx+lenx)&&posby<posy+leny&&(posby+2*rad)>posy){
		integrity=integrity-1;
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
}//(posby+rad>posy||posby+rad<posy+leny)&&!(posbx+rad>posx||posbx+rad<posx+lenx)
