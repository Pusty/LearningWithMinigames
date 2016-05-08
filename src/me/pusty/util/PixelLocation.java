package me.pusty.util;

/**Pixel perfect Location*/
public class PixelLocation {
	//x position
	public int x;
	//y position
	public int y;
	/**A new PixelLocation*/
	public PixelLocation(int x,int y){
		this.x=x;
		this.y=y;
	}
	/**Returns X Position*/
	public int getX(){return x;}
	/**Returns Y Position*/
	public int getY(){return y;}
	/**Sets X value*/
	public void setX(int x){this.x=x;}
	/**Sets Y value*/
	public void setY(int y){this.y=y;}
	/**Returns the distance from l to l2*/
    public static int getDistance(PixelLocation l,PixelLocation l2){
    	return (int)Math.sqrt(((l2.getX()-l.getX())*(l2.getX()-l.getX()))+((l2.getY()-l.getY())*(l2.getY()-l.getY())));
    }
    
    /**Returns this+a as a PixelLocation*/
	public PixelLocation add(PixelLocation a) {
		int cx = x + a.x;
		int cy = y + a.y;
		return new PixelLocation(cx,cy);
	}
	/**Sets this location to l*/
	public void set(PixelLocation l) {
		this.setX(l.getX());
		this.setY(l.getY());
	}
	

}
