import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.nio.file.*;;
import java.io.*;
import java.lang.*;
import java.util.*;

public class WireFrame {
	//pi is always useful
	static double Pi = 3.14159;
	//The point data.
	static double[][] PointData = {{0,0,0,0,0,0,0}};
	//a variable used to avoid overflows
	static int totalPoints=0;
	//camera information
	static double camX=0;
	static double camY=0;
	static double camZ=0;
	//which direction the camera is facing see modelInstructions for more
	//details
	static double camFace=0;
	//scale
	static double scaleX=100;
	static double scaleY=100;
	//fov used in camera calculation, which is locked at 70.
	static double fov=70;
	//a nice function for simplicity when using radians.
	static double rad90=Math.toRadians(90);
	//a result from a calculation that must be used in multiple functions.
	static double fovDistanceX=0;
	static double fovDistanceY=0;
	//and the points at which the model will rotate around.
	static double CenterX=0;
	static double CenterY=0;
	//the speed is actually the fps cap, 60fps is 16, so that's what it
	//the default it, rotRad is the amount the model rotates per frame, 1
	//looks good for small models, feel free to change though.
	static int Speed=30;
	static double rotRad=Math.toRadians(3);
		
	public static void DrawAll() throws InterruptedException {
		//simple for loop to draw every line in the data set, then rotate
		//them for the next frame.
		for(int i=1;i<PointData.length;i++){
			DrawLine(i);
		}
		for(int i=1;i<PointData.length;i++){
			double X=PointData[i][0];
			double Y=PointData[i][1];
			MovePoint(X,Y,i);
		}
	}
	
	public static void MovePoint(double X, double Y,int i) throws InterruptedException {
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
	
	public static void DrawLine(int i){
		//Draws a line and all specified connections on the screen.
		if (PointData[i][6]==1){
		//reads the last number in a given point, and sets the colour
		//based on predetermined variables.
			StdDraw.setPenColor(0,235,255);
		} else if (PointData[i][6]==2) {
			StdDraw.setPenColor(255,151,0);
		} else if (PointData[i][6]==3) {
			StdDraw.setPenColor(0,255,0);
		} else if (PointData[i][6]==4) {
			StdDraw.setPenColor(255,135,242);
		} else {
			StdDraw.setPenColor(255,255,255);
		}
		//X and Y being mapped to their point on the screen.
		double X= LineEqX(PointData[i][0], PointData[i][1]);
		double Y= LineEqZ(PointData[i][0], PointData[i][1], PointData[i][2]);
		if (PointData[i][6]==10&&PointData[i][3]==0&&PointData[i][4]==0&&PointData[i][5]==0) {
		//if the point has a colour of 10, and no connections, it is a point
		//and is rendered as such.
			StdDraw.setPenRadius(0.01);
			StdDraw.point(Math.abs(X),Math.abs(Y));
			StdDraw.setPenRadius(0.005);
		} else {
			for(int j=3;j<6;j++){
				//checks the point for connections, if there is one, it then
				//locates that point in the array, and maps it to the screen,
				//then drawing a line from it to regular X.
				int P=(int)PointData[i][j];
				if(P!=0){
					double Xp= LineEqX(PointData[P][0], PointData[P][1]);
					double Yp= LineEqZ(PointData[P][0], PointData[P][1], PointData[P][2]);
					if(X==1234567||Y==1234567||X==1234567||Yp==1234567){
						//error message
						System.out.println("a line is unable to be rendered in line "+i+" to "+P+".");
					} else {
					StdDraw.line(Math.abs(X),Math.abs(Y),Math.abs(Xp),Math.abs(Yp));
			}
		}}}}
		
	public static void Init3d() throws InterruptedException{
		//initialize std.draw
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
		//find the the height of the fov triangle, which will determine how
		//much of the screen is able to be seen.
		fovDistanceX=(scaleX/2)*Math.sin(rad90)/Math.sin(fov*(Pi/180));
		fovDistanceY=(scaleY/2)*Math.sin(rad90)/Math.sin(fov*(Pi/180));
		//draw the wireframe for the initial
		DrawAll();
		//start the rotation animation
		Animate();
	}
	
	public static double CirPointX(double angle, double radius, double Origin) throws InterruptedException
	{
		//finds a point on a circle at the angle specified, then outputs
		//it's X value.
		angle=Math.cos(angle);
		double out=Origin+radius*angle;
		return out;
	}
	
	public static double CirPointY(double angle, double radius, double Origin) throws InterruptedException
	{
		//finds a point on a circle at the angle specified, then outputs
		//it's Y value.
		angle=Math.sin(angle);
		double out=Origin+radius*angle;
		return out;
	}

	public static void Animate() throws InterruptedException{
		boolean Anim=true;
		//this will loop forever, clearing, drawing, then waiting.
		while (Anim==true){
			StdDraw.clear(StdDraw.BLACK);
			DrawAll();
			StdDraw.show();
			Thread.sleep(Speed);
		}
	}

	/*
	 * ==================================================================
	 * Everything below this line is for the file system and data unpack.
	 * ==================================================================
	 */
	
	static double[] addElement(double[] a, double e) {
	//adds an element to an array.
    a  = Arrays.copyOf(a, a.length + 1);
    a[a.length - 1] = e;
    return a;
	}

	public static double[][] To2d(double[] array, int Size){
		//makes the really long array, and sorts it into a 2d array with
		//the individual lengths specified.
		int arrSize=array.length/7;
		double out[][]=new double[arrSize][Size];
		int max=out.length;
		int i=0;
		int total=0;
		while (i<arrSize){
			for(int j=0;j<Size;j++){
				out[i][j]=array[total];
				total++;
			}
			i++;
		}
		return out;
	}
	
	public static boolean FileCallCheck(String name){
		//just checks that the file exists and outputs some basic stuff 
		//about it, taken partially from W3schools as I was learning about
		//file systems.
    File myObj = new File("C:\\Program Files (x86)\\AshPgms\\Wireframes\\"+name);
    if (myObj.exists()) {
      System.out.println("File name: " + myObj.getName());
      System.out.println("Absolute path: " + myObj.getAbsolutePath());
      System.out.println("Writeable: " + myObj.canWrite());
      System.out.println("Readable " + myObj.canRead());
      System.out.println("File size in bytes " + myObj.length());
      return true;
    } else {
      System.out.println("The file does not exist.");
      return false;
    }
}

	public static String FileReadToString (String name) throws Exception {
		//reads the file specified and converts it to a long string
		 String data = "";
    data = new String(Files.readAllBytes(Paths.get("C:\\Program Files (x86)\\AshPgms\\Wireframes\\"+name)));
    return data;
	}
	
	public static void FileCallMenu() throws Exception{
		//a little menu for the fanciness, and to allow file selection.
		System.out.println("select one from the following:\n1>Testing Square\n2>Utah SugarBowl\n3>Cobra from Elite\n");
		int input=inputInt(">");
		String name="n/a";
		if (input==1){
			name="TestingCube.txt";
		} else if (input==2){
			name="SugarBowl.txt";
		} else if (input==3){
			name="EliteCobra.txt";
		}
		if (FileCallCheck(name)==true){
			//if the file is good, tons of functions to adapt it to a readable
			//multidimensional array. Then start the rendering.
			String data=FileReadToString(name);
			System.out.println(data);
			DataInterpereter(data);
			System.out.println("Unpack successful.");
			Init3d();
		}
	}
	
	public static void DataInterpereter (String data){
	//a program to take the string data from the file and adapt it to a 
	//useable miltidimensional array. Uses a pointer system inspired by 
	//brainf*ck to read the information letter by letter.
	char[] arr=data.toCharArray();
	int tempInt=0;
	double[] tempArr={};
	int StartPoint=0;
	int Point=StartPoint+1;
	char pointFind='n';
	int currentEnd=0;
	boolean EndFound=false;
	boolean ReadNum=false;
	boolean Completed=false;
	boolean end=false;
	while (Completed==false) {
	//^This loop is to loop all of the code for multiple data sets.
	while (EndFound==false&&Completed==false){
	//^This loop is to find the next start of a data set.
		end=false;
		if (arr.length-1<=Point+1||arr.length-1<=Point||arr[Point]=='!'){
			/*This will detect when the pointer has reached it's end, and will 
			*shut down everything and Change the PointData to a useable
			*multidimensional array. And I'm leaving the system out because I 
			*think it looks like the matrix, which is cool.
			*/
			System.out.println("End");
			PointData=To2d(tempArr,7);
			System.out.println(Arrays.deepToString(PointData));
			Completed=true;
		}
		if(arr[Point]=='}'&&Completed==false){
		//flips the switch to read the data in the points
			EndFound=true;
		}
		if(Completed==false){
		//progresses the pointer
			Point=Point+1;
		}
	}
	Point=StartPoint;
	while (EndFound==true&&Completed==false){
	//This loop reads all of the data points.
		int i=0;
		double NumRead=0;
		double numbers=0;
		while(ReadNum==true){
		/*This loop will read 1 point of data, and adds it to the M.D. array
		 * once it's done adding the points to it's own array.
		 */
			Point=Point-1;
			i=i+1;
			numbers++;
			if(Character.isDigit(arr[Point])){
				//calculates the value of the number it is reading. While
				//accounting for modifiers like '.' and '-'
				double num=Character.getNumericValue(arr[Point])*(Math.pow(10,numbers)/10);
				NumRead=NumRead + num;
			} else if (arr[Point]=='.') {
				NumRead=NumRead/Math.pow(10,numbers)*10;
				numbers=0;
			} else if (arr[Point]=='-') {
				NumRead=NumRead-(NumRead*2);
			} else {
				//adds the number to the array.
				tempArr=addElement(tempArr, NumRead);
				Point=Point+i+2;
				ReadNum=false;
				if (end==true){
					EndFound=false;
				}
			}
		}
		//checks if the end of a data set, file, or number is found, if not,
		//simply progresses the pointer to check again.
		if(arr[Point]=='}'&&EndFound==true){
			StartPoint=Point+1;
			ReadNum=true;
			end=true;
		} else if(arr[Point]==','&&EndFound==true){
			ReadNum=true;
		} else if (arr[Point]=='!'&&EndFound==true) {
			end=true;
			i=-2;
		} else if (EndFound==true) {
		Point=Point+1;
		}
		}
	}
	}
	
	public static void main(String[] args) throws Exception
	{
		//just to run my first function when the program starts
		FileCallMenu();
	}
	
	//input stolen from IBIO bc I'm too lazy to write it myself.
	
		static String input(String prompt)
	{	String	inputLine = "";
		System.out.print(prompt);
		try
		{	inputLine = (new java.io.BufferedReader(
							new java.io.InputStreamReader(System.in))).readLine();}
		catch (Exception e)
		{	String	err = e.toString();
			System.out.println(err);
			inputLine = "";
		}
		return	inputLine;
	}
	
	static char inputChar(String prompt)
	{	char	result = (char) 0;
		try
		{	result = input(prompt).charAt(0);}
		catch (Exception e)	{	result = (char) 0;}
		return	result;
	}
	
	static int inputInt(String prompt)
	{	int	result = 0;
		try
		{	result = Integer.valueOf(input(prompt).trim()).intValue() ;	}
		catch (Exception e)	{	System.out.println(">>> error in input"); result = 0;}
		return	result;
	}
	
	static String inputString(String prompt)
	{	return	input(prompt);	}
	
}
