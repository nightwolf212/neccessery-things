package com.example.polygon;

import java.util.ArrayList;

public class Polygon {
	private ArrayList<Point> verts;
	public Polygon()
	{
		verts=new ArrayList<Point>();
	}
	public Polygon(ArrayList<Point> v)
	{
		verts=v;
	}
	public void addPoint(Point t)
	{
		verts.add(t);
	}
	public void erasePoint(int ind)
	{
		verts.remove(ind);
	}
	public Point getPoint(int ind)
	{
		return verts.get(ind);
	}
	public int getVertexNum()
	{
		return verts.size();
	}
	public ArrayList<Point> getRotatedPoints(float rot, float centrX, float centrY)
	{
		ArrayList<Point> newPoints=new ArrayList<Point>();
		Point mid=new Point(centrX,centrY);
		for(int i=0; i<verts.size(); i++)
		{
			float dist=getDistBetweenPoints(mid,verts.get(i));
			float relX=0;
			relX=verts.get(i).getX()-mid.getX();
			float relY=0;
			relY=mid.getY()-verts.get(i).getY();
			double angl=0;
			if(relX<0 && relY>0)
				angl=Math.acos((relY-mid.getY())/dist)*(-1);
			if(relX<0&&relY<0)
				angl=Math.acos((relY-mid.getY())/dist)*(-1);
			if(relX>0&&relY<0)
				angl=Math.acos((relY-mid.getY())/dist);
			if(relX>0 && relY>0)
				angl=Math.acos((relY-mid.getY())/dist);
			angl-=rot;
			float newX=(float) (dist*Math.cos(angl));
			float newY=(float) (dist*Math.sin(angl));
			
			newPoints.add(new Point(newX,newY));
			
			
		}
		return newPoints;
	}
	private float getDistBetweenPoints(Point a, Point b)
	{
		return (float)Math.sqrt((a.getX()-b.getX())*(a.getX()-b.getX())+(a.getY()-b.getY())*(a.getY()-b.getY()));
	}
	public boolean intersectWith(float mRot, float pRot, float mCentrX, float mCentrY,float pCentrX, float pCentrY, Polygon t)
	{
		ArrayList<Point> p1=this.getRotatedPoints(mRot, mCentrX, mCentrY);
		ArrayList<Point> p2=t.getRotatedPoints(pRot, pCentrX, pCentrY);
		p1.add(p1.get(0));
		p2.add(p2.get(0));
		for(int i=0; i<p1.size()-1; i++)
			for(int j=0; j<p2.size()-1; j++)
			{
				if(intersect(p1.get(i),p1.get(i+1),p2.get(j),p2.get(j+1)))
				{
					return true;
				}
			}
	
		
		return false;
	}
	
	private float area (Point a, Point b, Point c) {
		return (b.getX() - a.getX()) * (c.getY() - a.getY()) - (b.getY() - a.getY()) * (c.getX() - a.getX());
	}
	 
	private boolean intersect_1 (float a, float b, float c, float d) {
		if (a > b){ float t=a; a=b; b=t;};
		if (c > d) { float t=c; c=d; d=t;};
		return Math.max(a,c) <= Math.min(b,d);
	}
	 
	private boolean intersect (Point a, Point b, Point c, Point d) {
		return intersect_1 (a.getX(), b.getX(), c.getX(), d.getX())
			&& intersect_1 (a.getY(), b.getY(), c.getY(), d.getY())
			&& area(a,b,c) * area(a,b,d) <= 0
			&& area(c,d,a) * area(c,d,b) <= 0;}
}
