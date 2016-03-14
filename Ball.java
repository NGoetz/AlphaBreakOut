package alphabreakout;

public class Ball {
	private int posy, posx;
	private int speedy, speedx;
	private static int rad;
	public Ball() {
		posx=485;
		posy=860;
		speedy=-1;//inital speed
		speedx=0;
		setRad(15);
	}
	public int getRad() {
		return rad;
	}
	public void setRad(int rad) {
		Ball.rad = rad;
	}
public void setPosy(int py){
	posy=py;
}
public int getPosy (){
	return posy;
}
public void setPosx(int px){
	posx=px;
}
public int getPosx (){
	return posx;
}
public void setSpeedy(int sy){
	speedy=sy;
}
public int getSpeedy (){
	return speedy;
}
public void setSpeedx(int sx){
	speedx=sx;
}
public int getSpeedx (){
	return speedx;
}
public void ElasticReflection (boolean top){//physically correct reflection at static objects
	if(top){
		speedy=-speedy;
	}else{
		speedx=-speedx;
	}
}
public void InelasticReflection(int speed){//reflection at the paddle, transfers its speed because of friction
	speedy=-speedy;
	speedx=speedx+speed;
}
public boolean Gameover(){//out of bounds?
	if(posy+getRad()>950)
		return true;
	else
		return false;
}
public void ReflectAtWalls(){//checks if the wall is touched, normalises the position and reflects
	if(posx<=0){
		posx=0;
		ElasticReflection(false);
	}else if((posx+2*getRad())>=1000){
		posx=1000-2*getRad();
		ElasticReflection(false);
	}else if((posy)<0){
		posy=0;
		ElasticReflection(true);
	}
}
public boolean ReflectAtBrick(Brick b){//same with bricks
	int determ=b.collisionOnTop(posx, posy, getRad());
	if(determ==1){
		ElasticReflection(false);
		return true;
	}
	else if(determ==2){
		ElasticReflection(true);
		return true;
	}
	return false;
		
}
public void ReflectAtRefl(Reflector r){//also with the paddle, but this time the reflector moves
	if(r.contactRefl(posx, posy, getRad())){
		InelasticReflection(r.getSpeed());
	}
}

}
