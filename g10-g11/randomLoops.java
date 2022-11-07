import java.util.Scanner;
public class randomLoops {
	
	public static void main (String[] args) {
		System.out.println("enter your name");
		Scanner myObj = new Scanner(System.in);
		String Name = myObj.nextLine();
		for (int i=0; i < 20; i++) {
			System.out.println("hiiiii "+Name+"!");
		}
	}
}

