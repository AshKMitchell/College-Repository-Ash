
public class NovTest {
	
	public static void main (String[] args) {
		IBIO.out("Ash M");
		var never=1;
			do{
				double base=IBIO.inputInt("\nEnter the base: ");
				while (base>=10){
					IBIO.out("please enter a number below 10. ");
					base=IBIO.inputInt("Enter the base: ");
				}
				
				if (base==0){
					IBIO.out("Error, base is 0");
					System.exit(0);
				}
				
				double expo=IBIO.inputInt("Enter the Exponent: ");
				while (expo>=10){
					IBIO.out("please enter a number below 10. ");
					expo=IBIO.inputInt("Enter the exponent: ");
				}
				
				var negative = 0;
				if(expo<0){
					//IBIO.out("Error, exponent is below 0");
					//System.exit(0);
					negative=1;
				}
					
				
				double expoCount=(Math.abs(expo));
				double Solu=base;
				
				do{			
					Solu=Solu*base;
					expoCount=expoCount-1;
				}while(expoCount>1);
				
				var Denom=Solu;
				
				if (negative==1){
					Solu=1/Solu;
				}
				
				if (expo==0){
					Solu=1;
				}
				
				if (negative==0){
					IBIO.out(base+" to the power of "+expo+" = "+Solu);
				} else {
					//IBIO.out(base+" to the power of "+expo+" = "+Solu+" or, 1/"+Denom);
					IBIO.out("\n  1\n----- = "+Solu+" or, 1/"+Denom+"\n"+base+"^"+expo+"\n");
				}
			}while(never==1);
		}
	}

