/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        for(int i=0;i<4;i++)
        {
            turtle.forward(sideLength);
            turtle.turn(90);
        }
    }
    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        return (sides-2)*180.0/sides;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
    	return (int) Math.round((360.0 / (180 - angle)));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        double angle=180-calculateRegularPolygonAngle(sides);
        for(int i=0;i<sides;i++)
        {
            turtle.forward(sideLength);
            turtle.turn(angle);
        }
    }
    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {    
        double degree=Math.toDegrees(Math.atan2(targetY-currentY, targetX-currentX));
        degree = (90 - degree) - currentBearing;
        if(degree<0)
        {
        	degree+=360;
        }
        return degree;
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
    	List<Double> degrees = new ArrayList<Double>();
    	int x1,x2,y1,y2;
    	double degree1=0;
    	double degree2=0;
    	Iterator<Integer> x = xCoords.iterator();
        Iterator<Integer> y = yCoords.iterator();
        x1=x.next();
        y1=y.next();
        while(x.hasNext()&&y.hasNext())
        {
        	x2=x.next();
        	y2=y.next();
        	degree1=calculateBearingToPoint(degree2,x1,y1,x2,y2);
        	degree2+=degree1;
        	degree2=degree2%360;
        	x1=x2;
        	y1=y2;
        	degrees.add(degree1);
        }
        return degrees;
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
    	Iterator<Point> it = points.iterator();
    	List<Point>freePoint=new ArrayList<Point>();//未选择的点
    	List<Point>selectedPoint=new ArrayList<Point>();//已选择的点
    	freePoint.addAll(points);
    	//只有三个点或更少，直接返回
    	if(freePoint.size()<4)
    		return points;	
    	Point firstPoint=freePoint.get(0);
    	//寻找左下方的点
    	while (it.hasNext()) {
    		Point temp=it.next();
    		if(temp.y()<firstPoint.y())
    		{
    			firstPoint=temp;
    		}
    		else if(temp.y()==firstPoint.y()&&temp.x()<firstPoint.x())
    		{
    			firstPoint=temp;
    		}
    	}
    	selectedPoint.add(firstPoint);
    	freePoint.remove(firstPoint);
    	Point prePoint=firstPoint;
    	int i=0;
    	do
    	{
    		if(i==1)
    			freePoint.add(firstPoint);
    		Point nextPoint=null;
    		double minDegree=360;
    		double distance=0;
    		for(Point temp:freePoint)
    		{
    			double tempDegree=calculateBearingToPoint(0, (int) prePoint.x(), (int) prePoint.y(), (int) temp.x(),
						(int) temp.y());//计算转向角
    			double tempDis=Math.pow(prePoint.x() - temp.x(), 2) + Math.pow(prePoint.y() - temp.y(), 2);//计算距离
    			if(tempDegree==minDegree&&distance<tempDis)
    			{
    				nextPoint=temp;
    				distance=tempDis;
    			}
    			else if(tempDegree<minDegree)
    			{
    				nextPoint=temp;
    				distance=tempDis;
    				minDegree=tempDegree;
    			}	
    		}
    		selectedPoint.add(nextPoint);
    		freePoint.remove(nextPoint);
    		prePoint=nextPoint;
    		i++;
    	}while(selectedPoint.get(i)!=firstPoint);
    	Set<Point> result = new HashSet<Point>();
		result.addAll(selectedPoint);
		return result;
    }  
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
    	PenColor[] color = new PenColor[4];
    	color[0]=PenColor.BLACK;
    	color[1]=PenColor.BLUE;
    	color[2]=PenColor.RED;
    	color[3]=PenColor.GREEN;
        for(int i=0;i<100;i++)
        {
        	turtle.color(color[i%4]);
        	turtle.forward(100);
        	turtle.turn(95);
        }
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();
        //int sides=5;
        //drawSquare(turtle, 40);
        //drawRegularPolygon(turtle,sides,40);
        // draw the window
        drawPersonalArt(turtle);
        turtle.draw();
    }

}
