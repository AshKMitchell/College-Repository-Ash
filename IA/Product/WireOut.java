import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.nio.file.*;;
import java.io.*;
import java.lang.*;
import java.util.*;

public class WireOut {
	
	/*
	 * This class is to get the wireframe points, and draw them within 
	 * specified boundries which can be set.
	 */
	static int x1=0;
	static int x2=100;
	static int y1=0;
	static int y2=100;
	private static double SizeX;
	private static double SizeY;
	private static double[][] PointData = {{0,0,0,0,0,0,0}};
	private static double Pi = 3.14159;
	private static int totalPoints=0;
	//camera information
	private static double camX;
	private static double camY;
	private static double camZ;
	//which direction the camera is facing see modelInstructions for more
	//details
	private static double camFace;
	//scale
	private static double scaleX=100;
	private static double scaleY=100;
	//fov used in camera calculation, which is locked at 70.
	private static double fov=70;
	//a nice function for simplicity when using radians.
	private static double rad90=Math.toRadians(90);
	//a result from a calculation that must be used in multiple functions.
	private static double fovDistanceX;
	private static double fovDistanceY;
	//and the points at which the model will rotate around.
	private static double CenterX;
	private static double CenterY;
	//the speed is actually the fps cap, 60fps is 16, so that's what it
	//the default it, rotRad is the amount the model rotates per frame, 1
	//looks good for small models, feel free to change though.
	private static int Speed=40;
	private static double rotRad=Math.toRadians(3);
	private static double selectedPoint=0;
	
	//setters:
	
 public static void setBoundries(int ex1, int ex2, int ey1, int ey2){
		x1=ex1;
		x2=ex2;
		y1=ey1;
		y2=ey2;
}
	
	//getters:
	
	public static int getPointAmount(){
		return PointData.length;
	}
	
	public static double getSelectedPoint(){
		return selectedPoint;
	}
	
	public static double getPersPointX(int i){
		//gets the point on the screen in a perspective
		if(x1!=0){
			return (LineEqX(PointData[i][0], PointData[i][1])*SizeX)+x1;
		}else {
			return (LineEqX(PointData[i][0], PointData[i][1]));
		}
	}
	
	public static double getPersPointY(int i){
		if(y1!=0){
			return (LineEqZ(PointData[i][0], PointData[i][1], PointData[i][2])*SizeY)+y1;
		} else {
			return (LineEqZ(PointData[i][0], PointData[i][1], PointData[i][2]));
		}
	}
	
	public static double[] getPointConnections(int i){
		double [] out = {PointData[i][3],PointData[i][4],PointData[i][5]}; 
		return out;
	}
	
	public static double getLineProperties(int i){
		return PointData[i][6];
	}
	
	public static double getPointX(int i){
		return PointData[i][0];
	}
	
	public static double getPointY(int i){
		return PointData[i][1];
	}
	
	public static double getPointZ(int i){
		return PointData[i][2];
	}
	
	public static double getCenterX(){
		return PointData[0][3];
	}

	public static double getCenterY(){
		return PointData[0][4];
	}
	
	public static double getCenterZ(){
		return PointData[0][5];
	}
	
	//setters:
	
	public static void setSelectedPoint(double x){
		selectedPoint=x;
	}
	
	//tools:
	
	private static double CirPointX(double angle, double radius, double Origin) throws InterruptedException {
		//finds a point on a circle at the angle specified, then outputs
		//it's X value.
		angle=Math.cos(angle);
		double out=Origin+radius*angle;
		return out;
	}
	
	private static double CirPointY(double angle, double radius, double Origin) throws InterruptedException {
		//finds a point on a circle at the angle specified, then outputs
		//it's Y value.
		angle=Math.sin(angle);
		double out=Origin+radius*angle;
		return out;
	}
	
	private static void MovePoint(double X, double Y,int i) throws InterruptedException {
		double Xd=X-CenterX;
		double Yd=Y-CenterY;
		//A simple program to take the X and Y coordinates and rotate them
		//along a circle.
		//find the point relative to the center
		int Quadrant=1;
		int is90=0;
		//find the distance to the point from the camera
		double hyp=Math.pow(Xd,2)+Math.pow(Yd,2);
		hyp=Math.sqrt(hyp);
		//find the angle of that point to the camera
		double angle=Math.atan2(Yd,Xd);
		//rotate the point on a circle with the radius of the distance.
		PointData[i][0]=CirPointX(angle+rotRad, hyp, CenterX);
		PointData[i][1]=CirPointY(angle+rotRad, hyp, CenterY);
	}
	
	public static double LineEqX(double X, double Y){
	 /*A function which is essentially raycasting in 2D space, grab the X
	  * and Y coordinates of the point you want to calculate, and have 
	  * them act as a representation of the slice of 3d space you want,
	  * then draw a line to athe camera and find the point that it
	  * intersects with a line, which will then output to a x coordinate
	  * on the screen.
	  */
	  //getting the relative X points to the camera
	  double Xc=X-camX;
	  double Yc=Y-camY;
	  /*a multiplication to make the point on the same grid as the fov
	   *camera, based on the distance at 90, which is 50.
	   *if the number is greater (or less than based upon the camera) then
	   *it outputs an error, 1234567, which is interpereted as nothing.
	   */
	  Xc=Xc*50;
	  Yc=Yc*50;
	  double tempCamX=camX*50;
	  double tempCamY=camY*50;
	  if (camFace==4 && tempCamY<Yc){
			return 1234567;
		} else if (camFace==2 && tempCamX<Xc){
			return 1234567;
		}	else if (camFace==1 && tempCamX>Xc){
			return 1234567;
		} else if (camFace==3 && tempCamY>Yc){
			return 1234567;
		}
	  //then we find the slope (rise over run), however, if it is facing
	  // the y coordinate, they are swapped. Same for Y intercept.
	  double Rise=0;
	  double Run=0;
	  double Inter=0;
	  if (camFace==4){
			Run=Yc;
			Rise=Xc+fovDistanceX;
			Inter=-fovDistanceX;
		} else if (camFace==2){
			Run=Yc;
			Rise=Xc+fovDistanceX;
			Inter=-fovDistanceX;
		}	else if (camFace==1){
			Run=Xc;
			Rise=Yc-fovDistanceX;
			Inter=fovDistanceX;
		} else if (camFace==3){
			Run=Yc;
			Rise=Xc-fovDistanceX;
			Inter=fovDistanceX;
		}
		//then we find the x intercept with the equation x=-c/m, and shift
		// it over so it displays on the screen.
		double out=-Inter/(Rise/Run);
		out=out+(scaleX/2);
		return out;
	}
	
	public static double LineEqZ(double X, double Y, double Z){
		//basically the same as X, but slightly more complex as X and Y are
		//required, so refer to LineEqX for the explaination.
	  double Zc=Z-camZ;
	  double Yc=Y-camY;
	  double Xc=X-camX;
	  Zc=Zc*50;
	  Yc=Yc*50;
	  Xc=Xc*50;
	  double tempCamZ=camZ*50;
	  double tempCamY=camY*50;
	  double tempCamX=camX*50;
	  if (camFace==4 && tempCamY<Yc){
			return 1234567;
		} else if (camFace==2 && tempCamX<Xc){
			return 1234567;
		}	else if (camFace==1 && tempCamX>Xc){
			return 1234567;
		} else if (camFace==3 && tempCamY>Yc){
			return 1234567;
		}
	  double Rise=0;
	  double Run=0;
	  double Inter=0;
	  //The main difference is in these, where the rise over run are
	  //swapped between X and Y.
	  if (camFace==4){
			Run=Zc;
			Rise=Yc+fovDistanceY;
			Inter=-fovDistanceY;
		} else if (camFace==2){
			Run=Zc;
			Rise=Xc+fovDistanceY;
			Inter=-fovDistanceY;
		}	else if (camFace==1){
			Run=Zc;
			Rise=Xc-fovDistanceY;
			Inter=fovDistanceY;
		} else if (camFace==3){
			Run=Zc;
			Rise=Yc-fovDistanceY;
			Inter=fovDistanceY;
		}
		double out=Inter/(Rise/Run);
		out=out+(scaleY/2);
		return out;
	}
	
	public static void MoveAll()throws InterruptedException {
		//moves all points
		for(int i=1;i<PointData.length;i++){
			double X=PointData[i][0];
			double Y=PointData[i][1];
			MovePoint(X,Y,i);
		}
	}
	
	public static void Init3d() throws InterruptedException, Exception{
		//initialize stdDraw, enabling double buffering for smooth motion.
		PointData=DataUnpack.getPointData();
		StdDraw.setPenRadius(0.005);
		StdDraw.setXscale(0, scaleX);
		StdDraw.setYscale(0, scaleY);
		StdDraw.setPenColor(255,255,255);
		StdDraw.clear(StdDraw.BLACK);
		StdDraw.enableDoubleBuffering();
		//allocate the information in index 0.
		camX=PointData[0][0];
		camY=PointData[0][1];
		camZ=PointData[0][2];
		camFace=PointData[0][6];
		CenterX=PointData[0][3];
		CenterY=PointData[0][4];
		//for scaling the points later on when being output into points on the screen:
		if (x1!=0||y1!=0){
		SizeX=(x2-x1);
		SizeY=(y2-y1);
		SizeX=SizeX/100;
		SizeY=SizeY/100;
		}
		//find the the height of the fov triangle, which will determine how
		//much of the screen is able to be seen.
		fovDistanceX=(scaleX/2)*Math.sin(rad90)/Math.sin(fov*(Pi/180));
		fovDistanceY=(scaleY/2)*Math.sin(rad90)/Math.sin(fov*(Pi/180));
	}
}

