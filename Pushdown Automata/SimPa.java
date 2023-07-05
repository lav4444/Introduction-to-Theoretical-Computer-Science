
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class SimPa {
	
	public SimPa() {
	}
	public int getIndex(TreeSet<String> set, String value) {
		int vrati = 0;
		for (String s: set) {
			if (s.equals(value)) {
				break;
			}
			vrati++;
		}
		return vrati;
	}
	
	public List<List<String>> ulazi = new ArrayList<List<String>>(); 
	private static TreeSet<String> stanja = new TreeSet<>();
	private static TreeSet<String> abeceda = new TreeSet<>();  //MALA SLOVA
	private static TreeSet<String> znakoviStoga = new TreeSet<>();  //VELIKA SLOVA
	private static TreeSet<String> prihvStanja = new TreeSet<>();
	public static String pocStanje = null;
	public static String pocStog = null;
	public static String zadnjaAbecedaPrava = null;
	String [ ] [ ] [ ] matrica;
	
	public static int brStanja = 0;
	public static int duljAbe = 0;
	public static int duljZStoga = 0;
	
	//-------STACK
	  private String arr[];
	  private int top;
	  private int capacity;
	  
	  public void createNewStack() {
		  arr = new String[100];
		  capacity = 100;
		  top = -1;
	  }
	  
	  public void push(String x) {
	    if (top == capacity - 1) {
	      System.out.println("Stack OverFlow");
	    }

	    //System.out.println("Inserting " + x);
	    arr[++top] = x;
	  }

	  // pop elements from top of stack
	  public String pop() {

	    if (top == -1) {
	      System.out.println("$");

	    }

	    // pop element from top of stack
	    return arr[top--];
	  }
	  public void printStack() {
		  if (top == -1) {
			  System.out.printf("$");
		  } else {
		    for (int i = top; i >= 0; i--) {
		      System.out.printf(arr[i] + "");
		    }
		  }
	  }
	  public int stackSize() {
		  return top+1;
	  }
	  public String peekStack() {
		  if (top<0) {
			  return "$";
		  }else {
			  return arr[top];
		  }
		  //return arr[top];
	  }
	//------------
	
	public void loadPODACI() {
		
		int cnt = 1;
		
		Scanner sc = new Scanner(System.in);
		
		while (sc.hasNextLine()) {
			
			String line = sc.nextLine();
			
			if (cnt == 1) {
				String[] splits = line.split("\\|");
				//System.out.println(Arrays.toString(splits));
				
				for (String linija: splits) {
					String[] innerList = linija.split(",");
					
					ulazi.add(Arrays.asList(innerList));
					
				}
			}
			
			else if (cnt == 2) {
				String[] splits = line.split(",");
				for (String linija: splits) {
					stanja.add(linija);
					//prijelazi.put(linija, null);
					brStanja = stanja.size();
				}	
			}
			
			else if (cnt == 3) {
				String[] splits = line.split(",");
				for (String linija: splits) {
					abeceda.add(linija);
				}	
				abeceda.add("$");
				duljAbe = abeceda.size();
			}
			
			else if (cnt == 4) {
				String[] splits = line.split(",");
				for (String linija: splits) {
					znakoviStoga.add(linija);
				}	
				duljZStoga = znakoviStoga.size();
				matrica = new String [brStanja][duljAbe][duljZStoga];
			}
			
			else if (cnt == 5) {
				String[] splits = line.split(",");
				for (String linija: splits) {
					prihvStanja.add(linija);
				}	
			}
			
			else if (cnt == 6) {
				pocStanje = line;
			}
			else if (cnt == 7) {
				pocStog = line;
				//push na stog
			}
			else if (line.equals("")) {
				break;
			}
			else {
				String delimiter1 = "->";
				String delimiter2 = ",";
				String novi = line.toString().replaceAll(delimiter1, delimiter2);
				String[] splits = novi.split(",");

				TreeMap<String, List<String>> ubacuj = new TreeMap<>();
				List<String> ns = new ArrayList<String>();
				
				String ubaci = splits[3] + "#" + splits[4];
				matrica[getIndex(stanja, splits[0])][getIndex(abeceda, splits[1])][getIndex(znakoviStoga, splits[2])] = ubaci;
				
			}
			
			cnt++;
		}
		
		
		// Print all elements of the matrix
//        for (int i = 0; i < brStanja; i++) {
//            for (int j = 0; j < duljAbe; j++) {
//                for (int k = 0; k < duljZStoga; k++) {
//                    System.out.println(matrica[i][j][k]);
//                }
//            }
//        }
        
        //OBRADA
        String sljedeci;
		for (int i=0; i<ulazi.size(); i++) {
			createNewStack();
			
			Queue<String> q = new LinkedList<String>();
			for(String s: ulazi.get(i)) {
				q.add(s);
			}
			
			String trStanje22 = pocStanje;
			String trAbec22 = null;
			String trZnakStog22 = pocStog;
			push(pocStog);
			
			System.out.printf(pocStanje + "#" + pocStog + "|");
			
			while (q.size() >= 0) {
				
					if (stackSize()==0) {
						System.out.printf("fail|");
						trStanje22 = null;
						break; //ovo mozda treba maknut?
					}
				
					//provjeri ima li trStanje $ prijelaz
					if (matrica[getIndex(stanja, trStanje22)][getIndex(abeceda, "$")][getIndex(znakoviStoga, trZnakStog22)] != null) {
						sljedeci = matrica[getIndex(stanja, trStanje22)][getIndex(abeceda, "$")][getIndex(znakoviStoga, trZnakStog22)];
					}else{
						//ako nije epsilon prijelaz
						if (q.size() == 0){
							break;
						}
						trAbec22 = q.remove();
						sljedeci = matrica[getIndex(stanja, trStanje22)][getIndex(abeceda, trAbec22)][getIndex(znakoviStoga, trZnakStog22)];

					}
						
					
					if (sljedeci == null) {
							System.out.printf("fail|");
							trStanje22 = null;
							break;
					 
					}
					
					String[] splits = sljedeci.split("#");
					
					trStanje22 = splits[0];
					
					
					if (splits[1].length()>=2) {
						pop();
						int tt=splits[1].length()-1;  //mozda treba i tt=size, pa onda tt--, dok tt>=0
						while (tt>=0) {
							push( String.valueOf(splits[1].charAt(tt)) );
							trZnakStog22 = peekStack() ;
							tt--;
						}
					}
					else {
						if (splits[1].equals("$") ) {
							pop();
							trZnakStog22 = peekStack();
						} else trZnakStog22 = peekStack(); 
					}
						
					
					System.out.printf(trStanje22 + "#");
					printStack();
					System.out.printf("|");
					
					//////////////
					if (q.size() == 0 && prihvStanja.contains(trStanje22)) {  //epsilonko
						break;
					}
					/////////////////////// izadi ako je vec u prihvatljivom

				
				
			
			}
			
			if (trStanje22==null) trStanje22="psps";
			if(prihvStanja.contains(trStanje22)) {
				System.out.println("1");
			} else System.out.println("0");
			//System.out.println("\n");
		}
		
	}
	
	public static void main(String[] args) {
		SimPa proba = new SimPa();
		proba.loadPODACI();
		
	}

}