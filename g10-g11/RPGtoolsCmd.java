public class RPGtoolsCmd {
	
	public static void MainMenu(){
		//simple menu for the different functions
		System.out.println("Welcome to RPGTools, please select an option\n1> Roll a die\n2> Attack roll\n3> Stat check\n4> HP calculator \nselect an option by entering the number next to it)");
		int in=inputInt("\n");
		if (in==1){
			DiceRoll();
		} else if (in==2){
			AttackRoll();
		} else if (in==3){
			StatCheck();
		} else if (in==4){
			LevelUp();
		} else if (secrets==true&&in==69) {
			DM();
		} else {
			System.out.println("error");
			MainMenu();
		}
	}
	
	public static void LevelUp(){
		//a tool to help players level up their characters
		//gathering the needed data for the level up
		System.out.println("Enter your class\n1>Sorcerer\n2>Wizard\n3>Warlock\n4>Cleric\n5>Bard\n6>Rogue\n7>Druid\n8>Monk\n9>Ranger\n10>Fighter\n11>Paladin\n12>Barbarian\n i.e 1");
		int Clas=inputInt("\n");
		int hitDice=6;
		if (Clas<=2){
			hitDice=6;
		} else if (Clas<=8){
			hitDice=8;
		} else if (Clas<=9){
			hitDice=10;
		} else if (Clas<=12){
			hitDice=12;
		}
		System.out.println("Enter your constitution stat with all of your bonuses\n i.e 1-20");
		int Con=inputInt("\n");
		Con=((Con/2)-5);
		System.out.println("Enter the level you are levelling up to\n i.e 1-20");
		int Level=inputInt("\n");
		int Base=RollDie(Level, hitDice, true);
		int HP=Base+(Con*Level);
		System.out.println("The hp is: "+HP+"!");
		System.out.println("If you're levelling up, don't forget to check your class in the player handbook, and ensure that you get your updated spells, else you'll be underpowered for your level.");
		inputInt("press any button to continue to main menu");
		MainMenu();
	}
	
	public static void DiceRoll (){
		//rolls a dice based on what the user inputs
			System.out.println("Enter the number of dice to roll:");
			int num=inputInt("\n");
			System.out.println("Enter the max number of sides on the dice:");
			int max=inputInt("\n");
			int add=Bonus();
			System.out.println("Total: " + RollDie(num, max, true)+add);
			inputInt("press any button to continue to main menu");
			MainMenu();
	}
	
	public static void StatCheck (){
		//rolls a stat check based on the DC and user input'
		System.out.println("Enter the relevent attack stat \n i.e 1-20");
		int AttackStat=inputInt("\n");
		System.out.println("Enter the DC of the stat check \n i.e 1-30");
		int DC=inputInt("\n");
		System.out.println("Enter your proficiency score \n i.e 1-6");
		int Proficiency=inputInt("\n");
		System.out.println("Do you have advantage, disadvantage, or none\n A for advantage, D for disadvantage, N for none.");
		char Advantage=inputChar("\n");
		//rolling the check with advantage or disadvantage
		int Res=0;
		int BonusRoll=Bonus();
		if (Advantage=='A'||Advantage=='a'||Advantage=='D'||Advantage=='d'){
			if (Advantage=='a'){
				Advantage='A';
			} else if (Advantage=='d'){
				Advantage='D';
			}
			int Res1=Contest(AttackStat+Proficiency, DC, true, BonusRoll);
			int Res2=Contest(AttackStat+Proficiency, DC, true, BonusRoll);
			if (Res1==1&&Advantage=='A'||Res1==3&&Advantage=='A'){
				Res=Res1;
			} else if (Res2==1&&Advantage=='A'||Res2==3&&Advantage=='A'){
				Res=Res2;
			} else if (Res1==2&&Advantage=='D'||Res1==4&&Advantage=='D'){
				Res=Res1;
			} else if (Res2==2&&Advantage=='D'||Res2==4&&Advantage=='D'){
				Res=Res2;
			}
		} else {
			Res=Contest(AttackStat+Proficiency, DC, true, BonusRoll);
		}
		if (Res==2){
			System.out.println("Stat Check Passed!");
		} else if (Res==4){
			System.out.println("Stat Check Crit Passed! Let the DM know");
		} else if (Res==3) {
			System.out.println("Stat check Crit Failed, let the DM know");
		} else {
			System.out.println("Stat check Failed");
		}
		inputInt("press any button to continue to main menu");
		MainMenu();
	}
	
	public static void AttackRoll (){
		//Does all of the neccesary calculation to roll an attack.
		//gathering the data
		System.out.println("Enter the relevent attack stat \n i.e 1-20");
		int AttackStat=inputInt("\n");
		System.out.println("Enter the Armor Class of the enemy \n i.e 1-20");
		int ArmorClass=inputInt("\n");
		System.out.println("Enter your proficiency score \n i.e 1-6");
		int Proficiency=inputInt("\n");
		int ArmorBonus=Bonus();
		//checks if the attack will hit through the armor
		int checkRes=Contest(AttackStat+Proficiency, ArmorClass, true, ArmorBonus);
		boolean crit=false;
		if (checkRes==3){
			System.out.println("critical miss");
		} else if (checkRes==4){
			System.out.println("Critical hit!");
			crit=true;
		}
			if (checkRes==2||checkRes==4){
				//gathering the attack data
				System.out.println("You get past the enemy's armor");
				System.out.println("Enter your Attack's number of die to roll");
				int num=inputInt("\n");
				System.out.println("Enter the Max sides on the die you are rolling");
				int max=inputInt("\n");
				int Dmg=0;
				System.out.println("Do you have advantage, disadvantage, or none\n A for advantage, D for disadvantage, N for none.");
				char Advantage=inputChar("\n");
				//rolling the attack with advantage or disadvantage
				if (Advantage=='A'||Advantage=='a'||Advantage=='D'||Advantage=='d'){
					int Dmg1=RollDie(num, max, true)+((AttackStat/2)-5)+Proficiency;
					int Dmg2=RollDie(num, max, true)+((AttackStat/2)-5)+Proficiency;
					if (Dmg1>Dmg2&&Advantage=='A'||Dmg1>Dmg2&&Advantage=='a'){
						Dmg=Dmg1;
					} else if (Dmg2>Dmg1&&Advantage=='A'||Dmg2>Dmg1&&Advantage=='a'){
						Dmg=Dmg2;
					}
					if (Dmg1<Dmg2&&Advantage=='D'||Dmg1<Dmg2&&Advantage=='d'){
						Dmg=Dmg1;
					} else if (Dmg2<Dmg1&&Advantage=='D'||Dmg2<Dmg1&&Advantage=='d'){
						Dmg=Dmg2;
					}
				} else {
					Dmg=RollDie(num, max, true)+((AttackStat/2)-5)+Proficiency;
				}
				Dmg=Dmg+Bonus();
				if (crit==true){
					Dmg=Dmg*2;
				}
				System.out.println("You dealt a total of "+Dmg+" damage to the enemy!");
			} else if (checkRes==1||checkRes==3) {
				System.out.println ("You did not penetrate the enemy's armor");
			}
			inputInt("press any button to continue to main menu");
			MainMenu();
	}
	
	public static void DM(){
		System.out.println("Fuck you, what would I even hide in a program for D&D?");
	}
		
	public static int Bonus(){
		System.out.println("Bonuses? (Y/N)");
		char Bonus=inputChar("\n");
		int addnum=1;
		int addmax=0;
		int out=0;
		if (Bonus=='Y'||Bonus=='y'){
			System.out.println("Enter your Bonus's number of die");
			addnum=inputInt("\n");
			System.out.println("Enter your Max sides on the die");
			addmax=inputInt("\n");
			if (addmax==678){
				secrets=true;
			}
		}
		while (Bonus=='Y'||Bonus=='y'){
			out=out+RollDie(addnum, addmax, true);
			System.out.println("Bonuses? (Y/N)");
			Bonus=inputChar("\n");
			addnum=1;
			addmax=0;
			if (Bonus=='Y'||Bonus=='y'){
			System.out.println("Enter your Bonus's number of die");
			addnum=inputInt("\n");
			System.out.println("Enter your Max sides on the die");
			addmax=inputInt("\n");
		}
		}
		return out;
}

	public static int Contest(int offense, int defense, boolean AC, int Bonus){
	//rolls a contest of d20s and outputs if the offender won
	//if AC is true, the defenders stats will not be rolled.
	//1 is a loss, 2 is a win, 3 is a miss, 4 is a crit
	offense=(offense/2)-5;
	if (AC==false){
	defense=(defense/2)-5;
	}
	int def=defense;
	int off= RollDie(1, 20, true)+offense;
	if (off==1){
		return 3;
	} else if (off==20){
		return 4;
	}
	if (AC==false){
	def= RollDie(1, 20, true)+defense;
	}
	off=off+Bonus;
	if (off < def){
		return 1;
	} else if (off > def) {
		return 2;
	} else {
		//if it's a draw, reset the stats and do it all again.
		if (AC==false){
		defense=(defense*2)+5;
		}
		return Contest(offense*2+5, defense, true, Bonus);
	}
}
	
	public static int RollDie(int num,int max,boolean print){
		//rolls a die and optionally prints it... duh
		double m = max;
		double out=0;
		int i=0;
		double total=0;
		while (i<num){
			out=Math.random()*max+1;
			out=Math.round(out);
			total=total+out;
			i++;
		}
		int output=(int)Math.round(total);
		return output;
	}
	
	//stolen from IBIO bc I'm lazy
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
	
	//now my own code
	
	static boolean secrets=false;
	
	public static void main(String[] args) {
	MainMenu();
}
}
