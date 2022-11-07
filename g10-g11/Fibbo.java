public class Fibbo {
	
	public static void main (String[] args) {
		
		//Fibonachi sequence
		int Final=IBIO.inputInt("please enter the final term between 1 and 25");
		int go=0;
		int num=1;
		int firNum=0;
		int secNum=0;
		int out=1;
		do{
			IBIO.out("error, please try again");
			Final=IBIO.inputInt("please enter the final term between 1 and 25");
		}while (Final<1 && Final>25);

		
		while (go<=Final){
			out=num+firNum;
			IBIO.out(go+": "+num+" + "+firNum+" = ");
			secNum=firNum;
			firNum=num;
			num=out;
			IBIO.out(out+"\n");
			go++;
		}
	}
}

