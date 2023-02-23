import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.nio.file.*;;
import java.io.*;
import java.lang.*;
import java.util.*;

public class DataUnpack {
	
	/*
	 * The purpose of this class is to be able to take a file name and unpack
	 * the data into an array which is readable. The majority of this file
	 * was originally written when my first wireframe was created, but adapted
	 * to a more, OOP friendly format.
	 */
	
	static double[][] PointData = {{0,0,0,0,0,0,0}};
	
	// getters:
	
	public static double[][] getPointData(){
		return PointData;
	}
	
	// tools:
	
	public static void UnpackFile(String name) throws Exception{
		//unpacks the file and interpets it into an array
		if (FileCallCheck(name)==true){
			String data=FileReadToString(name);
			System.out.println(data);
			DataInterpereter(data);
		}
	}
	
	//Below this line is from the original wireframe.
	
	private static double[] addElement(double[] a, double e) {
	//adds an element to an array.
    a  = Arrays.copyOf(a, a.length + 1);
    a[a.length - 1] = e;
    return a;
	}

	private static double[][] To2d(double[] array, int Size){
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
      return true;
    } else {
      System.out.println("The file does not exist.");
      return false;
    }
	}

	private static String FileReadToString (String name) throws Exception {
		//reads the file specified and converts it to a long string
		 String data = "";
    data = new String(Files.readAllBytes(Paths.get("C:\\Program Files (x86)\\AshPgms\\Wireframes\\"+name)));
    return data;
	}
	
	private static void DataInterpereter (String data){
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
}
