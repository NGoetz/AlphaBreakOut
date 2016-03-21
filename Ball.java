package alphabreakout;//ball caught in object


public class Ball {
	private int posy, posx, pystart;
	private int speedy, speedx;
	private static int rad;
	public Ball(int py) {
		posx=485;
		posy=py;
		pystart=py;
		speedy=-1;//inital speed
		speedx=0;
		setRad(12);
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
		if(speedx!=0){
		speedx=-speedx;
		}else{
			speedy=-speedy;
		}
	}
}
public void InelasticReflection(boolean top, int speed){//reflection at the paddle, transfers its speed because of friction
	if(top){
	speedy=-speedy;
	speedx=speedx+speed;
	}else{
		speedy=speedy;
		speedx=-speedx+speed;
	}
}
public boolean Gameover(){//out of bounds?
	if(posy>pystart+2*rad+15)
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
public boolean ReflectAtRefl(Reflector r){//also with the paddle, but this time the reflector moves
	/*if(r.contactRefl(posx, posy, getRad())){
		InelasticReflection(r.getSpeed());
	}*/
	int determ=r.collisionOnTop(posx, posy, getRad());
	if(determ==1){
		InelasticReflection(false, r.getSpeed());
		if(posy>r.getPosy()-2*rad-1&&posy<r.getPosy()+r.getLeny()){
			posy=r.getPosy()-2*rad-1;
		}else{
			posy=r.getPosy()+r.getLeny()+1;
		}
		return true;
	}
	else if(determ==2){
		InelasticReflection(true, r.getSpeed());
		posy=r.getPosy()-rad-1;
		if(posy>r.getPosy()-2*rad-1&&posy<r.getPosy()+r.getLeny()){
			posy=r.getPosy()-2*rad-1;
		}else{
			posy=r.getPosy()+r.getLeny()+1;
		}
		return true;
	}
	return false;
}

}
