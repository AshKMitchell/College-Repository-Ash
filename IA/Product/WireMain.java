public class WireMain {
	private static boolean editMode;
	private static int EditScale=5;
	private static double selectedPoint=1;
	
	private static void DrawLine(int i){
		//Draws a line and all specified connections on the screen.
		double properties=WireOut.getLineProperties(i);
		if (properties==1){
		//reads the last number in a given point, and sets the colour
		//based on predetermined variables.
			StdDraw.setPenColor(0,235,255);
		} else if (properties==2) {
			StdDraw.setPenColor(255,151,0);
		} else if (properties==3) {
			StdDraw.setPenColor(0,255,0);
		} else if (properties==4) {
			StdDraw.setPenColor(255,135,242);
		} else if(i==selectedPoint&&editMode==true) {
			StdDraw.setPenColor(255,0,255);
		} else {
			StdDraw.setPenColor(255,255,255);
		}
		//X and Y being mapped to their point on the screen.
		double X= WireOut.getPersPointX(i);
		double Y= WireOut.getPersPointY(i);
		if(i==selectedPoint&&editMode==true){
			StdDraw.circle(X,Y,5);
		}
		if (properties==10) {
		//if the point has a colour of 10, it is a point
		//and is rendered as such.
			StdDraw.setPenRadius(0.01);
			StdDraw.point(Math.abs(X),Math.abs(Y));
			StdDraw.setPenRadius(0.005);
		} else {
			double[] Connections=WireOut.getPointConnections(i);
			for(int j=0;j<3;j++){
				//checks the point for connections, if there is one, it then
				//locates that point in the array, and maps it to the screen,
				//then drawing a line from it to regular X.
				int P=(int)Connections[j];
				if(P!=0){
					double Xp= WireOut.getPersPointX(P);
					double Yp= WireOut.getPersPointY(P);
					if(X==1234567||Y==1234567||X==1234567||Yp==1234567){
						//error message
						System.out.println("a line is unable to be rendered in line "+i+" to "+P+".");
					} else {
					StdDraw.line(Math.abs(X),Math.abs(Y),Math.abs(Xp),Math.abs(Yp));
			}
		}}}}
	
	private static void DrawPlane(int xOffSet, int yOffSet, String plane){
		//will automatically scale down to 1 quarter of the screen
			int num=WireOut.getPointAmount();
			for(int i=1;i<num;i++){
				double properties=WireOut.getLineProperties(i);
			if (properties==1){
			//reads the last number in a given point, and sets the colour
			//based on predetermined variables.
				StdDraw.setPenColor(0,235,255);
			} else if (properties==2) {
				StdDraw.setPenColor(255,151,0);
			} else if (properties==3) {
				StdDraw.setPenColor(0,255,0);
			} else if (properties==4) {
				StdDraw.setPenColor(255,135,242);
			} else if(i==selectedPoint) {
				StdDraw.setPenColor(255,0,255);
			} else {
				StdDraw.setPenColor(255,255,255);
			}
			if(plane=="Top"){
				double X=((WireOut.getPointX(i)-WireOut.getCenterX())*EditScale)+xOffSet;
				double Y=((WireOut.getPointY(i)-WireOut.getCenterY())*EditScale)+yOffSet;
				if(i==selectedPoint){
					StdDraw.circle(X,Y,5);
				}
				double[] Connections=WireOut.getPointConnections(i);
				for(int j=0;j<3;j++){
					int P=(int)Connections[j];
					if(P!=0){
						double Xp=((WireOut.getPointX(P)-WireOut.getCenterX())*EditScale)+xOffSet;
						double Yp=((WireOut.getPointY(P)-WireOut.getCenterY())*EditScale)+yOffSet;
						//System.out.println("Drawing line from: "+X+", "+Y+" to "+Xp+", "+Yp);
						StdDraw.line(Math.abs(X),Math.abs(Y),Math.abs(Xp),Math.abs(Yp));
						StdDraw.setPenColor(255,255,255);
						StdDraw.textRight(xOffSet*2,yOffSet*2,plane);
				}
			}
			}
			if(plane=="Front"){
				double Y=((WireOut.getPointY(i)-WireOut.getCenterY())*EditScale)+xOffSet;
				double Z=((WireOut.getPointZ(i)-WireOut.getCenterZ())*EditScale)+yOffSet;
				if(i==selectedPoint){
					StdDraw.circle(Y,Z,5);
				}
				double[] Connections=WireOut.getPointConnections(i);
				for(int j=0;j<3;j++){
					int P=(int)Connections[j];
					if(P!=0){
						double Yp=((WireOut.getPointY(P)-WireOut.getCenterY())*EditScale)+xOffSet;
						double Zp=((WireOut.getPointZ(P)-WireOut.getCenterZ())*EditScale)+yOffSet;
						//System.out.println("Drawing line from: "+X+", "+Y+" to "+Xp+", "+Yp);
						StdDraw.line(Math.abs(Y),Math.abs(Z),Math.abs(Yp),Math.abs(Zp));
						StdDraw.setPenColor(255,255,255);
						StdDraw.text(95,50,plane);
					}
				}
			}
			if(plane=="Side"){
				double X=((WireOut.getPointX(i)-WireOut.getCenterX())*EditScale)+xOffSet;
				double Z=((WireOut.getPointZ(i)-WireOut.getCenterZ())*EditScale)+yOffSet;
				if(i==selectedPoint){
					StdDraw.circle(X,Z,5);
				}
				double[] Connections=WireOut.getPointConnections(i);
				for(int j=0;j<3;j++){
					int P=(int)Connections[j];
					if(P!=0){
						double Xp=((WireOut.getPointX(P)-WireOut.getCenterX())*EditScale)+xOffSet;
						double Zp=((WireOut.getPointZ(P)-WireOut.getCenterZ())*EditScale)+yOffSet;
						//System.out.println("Drawing line from: "+X+", "+Y+" to "+Xp+", "+Yp);
						StdDraw.line(Math.abs(X),Math.abs(Z),Math.abs(Xp),Math.abs(Zp));
						StdDraw.setPenColor(255,255,255);
						StdDraw.textRight(xOffSet*2,yOffSet*2-5,plane);
					}
				}
			}
		}
	}
	
	private static void DrawAll() throws InterruptedException {
		//simple for loop to draw every line in the data set, then rotate
		//them for the next frame.
		int num=WireOut.getPointAmount();
		for(int i=1;i<num;i++){
			DrawLine(i);
		}
	}
	
	public static void main (String[] args) throws Exception{
		//gets the filename via an input, initiates 3d, then animates the model.
		String iname=inputString("please input filename, files are located in windows devices in: C:/Program files (x86)/AshPgms/Wireframes, you do not have to append .txt at the end of the filename\n>");
		int iedit=inputInt("Would you like to (1)edit or (2)view?\n>");
		if (iedit==1){
			WireOut.setBoundries(50,100,50,100);
			editMode=true;
		}
		//WireOut.setBoundries(50,100,50,100);
		DataUnpack.UnpackFile(iname+".txt");
		if (DataUnpack.FileCallCheck(iname+".txt")==true){
			StdDraw.setPenRadius(0.005);
			WireOut.Init3d();
			if (editMode==false){AnimateView();} else {AnimateEditor();}
		}
	}
	
	private static void AnimateEditor() throws InterruptedException{
		boolean Anim=true;
		//this will loop forever, clearing, drawing, then waiting.
		while (Anim==true){
			StdDraw.clear(StdDraw.BLACK);
			DrawAll();
			DrawPlane(25,25,"Top");
			DrawPlane(25,50,"Side");
			DrawPlane(75,0,"Front");
			KeyCheck();
			StdDraw.text(50,5,"Selected Point: "+(int)selectedPoint+", coords: "+WireOut.getPointX((int)selectedPoint)+", "+WireOut.getPointY((int)selectedPoint)+", "+WireOut.getPointZ((int)selectedPoint));
			StdDraw.show();
			Thread.sleep(40);
		}
	}
	
	private static void AnimateView() throws InterruptedException{
		boolean Anim=true;
		//this will loop forever, clearing, drawing, moving, then waiting.
		while (Anim==true){
			StdDraw.clear(StdDraw.BLACK);
			DrawAll();
			StdDraw.show();
			WireOut.MoveAll();
			Thread.sleep(40);
		}
	}
	
	//Controls for the Editor
	private static boolean spacePressed=false;
	private static boolean upPressed=false;
	private static boolean downPressed=false;
	private static boolean leftPressed=false;
	private static boolean rightPressed=false;
	private static boolean ZPressed=false;
	private static boolean XPressed=false;
	//cooldown makes sure that inputs are only executed once when pressed
	private static boolean cooldown=false;
	
	private static void KeyCheck ()throws InterruptedException{
		//space bar
		if (StdDraw.isKeyPressed(32)==true){
			spacePressed=true;
		} else {
			spacePressed=false;
		}
		//up
		if (StdDraw.isKeyPressed(38)==true){
			upPressed=true;
		} else {
			upPressed=false;
		}
		//down
		if (StdDraw.isKeyPressed(40)==true){
			downPressed=true;
		} else {
			downPressed=false;
		}
		//left
		if (StdDraw.isKeyPressed(37)==true){
			leftPressed=true;
		} else {
			leftPressed=false;
		}
		//right
		if (StdDraw.isKeyPressed(39)==true){
			rightPressed=true;
		} else {
			rightPressed=false;
		}
		//Z
		if (StdDraw.isKeyPressed(90)==true){
			ZPressed=true;
		} else {
			ZPressed=false;
		}
		//X
		if (StdDraw.isKeyPressed(88)==true){
			XPressed=true;
		} else {
			XPressed=false;
		}
		if(cooldown==false){
			if (rightPressed==true){
				cooldown=true;
				WireOut.setSelectedPoint(WireOut.getSelectedPoint()+1);
				selectedPoint=WireOut.getSelectedPoint();
			}
			if (ZPressed==true){
				cooldown=true;
				WireOut.MoveAll();
			}
			if (leftPressed==true){
				cooldown=true;
				WireOut.setSelectedPoint(WireOut.getSelectedPoint()-1);
				selectedPoint=WireOut.getSelectedPoint();
			}
		}
		if(cooldown==true&&leftPressed==false&&rightPressed==false){
			cooldown=false;
		}
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

