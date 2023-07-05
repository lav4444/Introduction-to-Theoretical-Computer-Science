
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

public class Parser {

		public Parser() {
			prijelazi.put("S", "aAB-bBA");
			prijelazi.put("A", "bC-a");
			prijelazi.put("B", "ccSbc-$");
			prijelazi.put("C", "AA");
			
		}
		
		public static TreeMap<String, String> prijelazi = new TreeMap<>();
		public static char[] cArray;
		public static char lookAhead;
		public static int index;
		public static int MAXi;
		public static Boolean daNe;
		
		
		public void S() {
			System.out.printf("S");
			if (lookAhead=='a') {
				match('a');
				if (daNe) A();
				if (daNe) B();
			}
			else if(lookAhead=='b') {
				match('b');
				if (daNe) B();
				if (daNe) A();
			}
//			else System.out.println("ERROR!!");
			else daNe = false;
			
		}
		
		public void A() {
			System.out.printf("A");
			if (lookAhead=='b') {
				match('b');
				if (daNe) C();
			}
			else if(lookAhead=='a') {
				match('a');
				//nema daljnjeg pozivanja
			}
			//else System.out.println("ERROR!!");
			else daNe = false;
		}
		
		public void B() {
			System.out.printf("B");
			if (lookAhead=='c') {
				match('c');
				match('c');
				if (daNe) S();
				match('b');
				match('c');
			}
			else {
				return;
			}

		}
		
		public void C() {
			System.out.printf("C");
			
			if (daNe) A();
			if (daNe) A();
			
		}
		
		public void match(char x) {
			if (lookAhead == x) {
				index++;
				if (index >= MAXi) {
					lookAhead = '$';
				} else {
					lookAhead = cArray[index];
				}
			}
			//else System.out.println("PROBLEM LOOKAHEAD_MATCH!");
			else {
				daNe = false;
				return;
			}
			//mozda treba dodati provjeru je li zadnji znak, mozda $
		}
		
		
		
		
		
		
		public void loadPODACI() {
			Scanner sc = new Scanner(System.in);
			String ulazStr = sc.nextLine();
			//System.out.println(ulazStr);
			cArray = ulazStr.toCharArray();
			index = 0;
			MAXi = cArray.length;
			daNe = true;
			lookAhead = cArray[index];
			//System.out.println();
			S(); //poziva se prva funkcija
			if (index<MAXi) daNe = false;
			if (daNe) {
				System.out.println("\nDA");
			} else System.out.println("\nNE");
			
		}
		
		
		
		public static void main(String[] args) {
			Parser proba = new Parser();
			proba.loadPODACI();
			
		}
}
