package main;

public class Camera {
	private float x, y;	
	public Camera(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(GameObject object) {
		x += ((object.getX() - x) - 1280/2) * 0.05f;
		y += ((object.getY() - y) - 720/2) * 0.05f;
		
		if(x <= 0) x = 0;
		if(x >= 925) x = 925;
		if(y <= 0) y = 0;
		if(y >=1500) y = 1500;		
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
}
