import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;


import javax.swing.text.html.HTMLDocument.Iterator;

public class MinDka {
	
	private static TreeSet<String> stanja = new TreeSet<>();
	private static TreeSet<String> abeceda = new TreeSet<>();
	private static TreeSet<String> prihvStanja = new TreeSet<>();
	public static TreeMap<String, TreeMap<String, String>> matricaPrijelaza = new TreeMap<>();
	public static String pocStanje = null;
	public static int brStanja = 0;
	public static int duljAbe = 0;
	
	public MinDka() {
	}
	
	public static <T> boolean listEqualsIgnoreOrder(List<T> list1, List<T> list2) {
	    return new HashSet<>(list1).equals(new HashSet<>(list2));
	}
	
	private Integer getIndex(String stanje, ArrayList<TreeSet<String>> Setovi) {
		int c = -1;
		for(int i=0; i<Setovi.size(); i++) {
			if (Setovi.get(i).contains(stanje)) {
				c = i+1;
			}
		}
		return c;
	}
	public Boolean sadrzi(TreeMap<Integer, TreeMap<Integer, TreeSet<String>>> mapica, TreeSet<Integer> trazimo) {
		ArrayList<Integer> myList = new ArrayList<Integer>(trazimo);
		Collections.sort(myList);
		Boolean vrati = false;
		if (mapica.keySet().contains(Collections.min(trazimo))) {
			if (mapica.get(Collections.min(trazimo)).containsKey(Collections.max(trazimo)) ) {
				vrati = true;
			}
		}
		
		
		
		return vrati;
	}
	
	public void loadPODACI() {
		int cnt = 1;
		
		Scanner sc = new Scanner(System.in);
		
		while (sc.hasNextLine()) {
			
			//System.out.println(cnt);
			String line = sc.nextLine();
			
			if (cnt == 1) {
				String[] splits = line.split(",");
				for (String linija: splits) {
					stanja.add(linija);
					//matricaPrijelaza.put(linija, null);
					brStanja = stanja.size();
				}	
			}
			
			else if (cnt == 2) {
				String[] splits = line.split(",");
				for (String linija: splits) {
					abeceda.add(linija);
					duljAbe = abeceda.size();
				}	
			}
			
			else if (cnt == 3) {
				String[] splits = line.split(",");
				for (String linija: splits) {
					prihvStanja.add(linija);
				}	
			}
			
			else if (cnt == 4) {
				pocStanje = line;
			}
			else if (line.equals("")) {
				break;
			}
			else {
				String delimiter1 = "->";
				String delimiter2 = ",";
				String novi = line.toString().replaceAll(delimiter1, delimiter2);
				String[] splits = novi.split(",");
				
				TreeMap<String, String> ubacuj = new TreeMap<>();				
				
				if (matricaPrijelaza.containsKey(splits[0])) {
					ubacuj = matricaPrijelaza.get(splits[0]);
				}
				ubacuj.put(splits[1], splits[2]);
				
				if (matricaPrijelaza.containsKey(splits[0])) {
					matricaPrijelaza.replace(splits[0], ubacuj);	
				} else {
					matricaPrijelaza.put(splits[0], ubacuj);	
				}
	
			}
			
			cnt++;
		}
		
		
		//------
		for (String kljuc: matricaPrijelaza.keySet()) {
//			System.out.println(kljuc + " : " + matricaPrijelaza.get(kljuc));
		}
		//------
		
		//DOSTIZNA STANJA
		TreeSet<String> reachableStates = new TreeSet<>();
        Queue<String> queue = new LinkedList<>();
        
        reachableStates.add(pocStanje);
        queue.add(pocStanje);
        
        while (!queue.isEmpty()) {
            String currentState = queue.remove();
            TreeMap<String, String> transitions = matricaPrijelaza.get(currentState);
            for (Map.Entry<String, String> entry : transitions.entrySet()) {
                String symbol = entry.getKey();
                String nextState = entry.getValue();
                if (!reachableStates.contains(nextState)) {
                    reachableStates.add(nextState);
                    queue.add(nextState);
                }
            }
        }
//        System.out.println("Dostizna stanja: " + reachableStates);
		//kraj DOSTIZNA
		
		
		ArrayList<TreeSet<String>> eqSetovi0 = new ArrayList<>();
		TreeSet<String> nePrihv = new TreeSet<>();
		TreeSet<String> Prihv = new TreeSet<>();
		for(String s: matricaPrijelaza.keySet()) {
			if (reachableStates.contains(s)) {
				if (prihvStanja.contains(s)) {
					Prihv.add(s);
				}
				else {
					nePrihv.add(s);
				}
				
			}
		}
		eqSetovi0.add(nePrihv);
		eqSetovi0.add(Prihv);
		
		//int cau = 0;
		while(true) {
			
			//if (cau == 3) break;
		
			ArrayList<TreeSet<String>> eqSetovi1 = new ArrayList<>();
			
			for (int i=0; i<eqSetovi0.size(); i++) {  //SUBSET

				TreeMap<Integer, TreeSet<String>> pripremaZaEq1 = new TreeMap<>();
				//if (eqSetovi0.get(i).size() > 1) {
					for (String xStanje: eqSetovi0.get(i)) { //STANJA U SUBSETU
						ArrayList<Integer> jesuIsti = new ArrayList<>();
						for(String ulz: matricaPrijelaza.get(xStanje).keySet()) { //SVI ULAZI ZA STANJE
							jesuIsti.add(getIndex(matricaPrijelaza.get(xStanje).get(ulz), eqSetovi0));
						}
						int sumica = 0;
						for (int xi : jesuIsti) {
						    sumica += xi;
						}
						
						TreeSet<String> bzvz = new TreeSet<>();
						if(pripremaZaEq1.containsKey(sumica )) {
							bzvz = pripremaZaEq1.get(sumica);
							bzvz.add(xStanje);
							pripremaZaEq1.replace(sumica, bzvz);
						} else {
							bzvz.add(xStanje);
							pripremaZaEq1.put(sumica, bzvz);
						}
						
					}
					///na kraju svakog subseta
					    for (Integer entry2: pripremaZaEq1.keySet()) {
					        if (pripremaZaEq1.get(entry2).size() > 0) {
					            eqSetovi1.add(pripremaZaEq1.get(entry2));
					        }
					    }
					}
				//}
				
			
			// Define a custom Comparator
			Comparator<TreeSet<String>> setComparator = new Comparator<TreeSet<String>>() {
			    public int compare(TreeSet<String> set1, TreeSet<String> set2) {
			        return set1.toString().compareTo(set2.toString());
			    }
			};

			// Sort the ArrayList
			Collections.sort(eqSetovi0, setComparator);
			Collections.sort(eqSetovi1, setComparator);
//			System.out.println(eqSetovi0);
//			System.out.println(eqSetovi1);


			//provjeri, ako su iste onda izadi
			if (eqSetovi0.equals(eqSetovi1)) {
				//System.out.println("rod rodeni");
				
				break;
				
			}
			
			//kopriaj novu u staru
			eqSetovi0.clear();
			eqSetovi0.addAll(eqSetovi1);
			
			
		//cau++;
			
		}
		///DRUGI DIO, SREDIVANJE ISPISA
		Boolean prvii = true;
		TreeSet<String> ispisStanja = new TreeSet<>();
		for (int i=0; i<eqSetovi0.size(); i++) {
			if (!prvii) System.out.printf(",");
			ispisStanja.add( eqSetovi0.get(i).first() );
			System.out.printf(eqSetovi0.get(i).first());
			prvii = false;
		}
		System.out.println();
		
		prvii = true;
		for (String s: abeceda) {
			if (!prvii) System.out.printf(",");
			System.out.printf(s);
			prvii = false;
		}
		System.out.println();
		
		TreeSet<String> prihvStanja2 = new TreeSet<>();
		for(String k: Prihv) {
			for (int i=0; i<eqSetovi0.size(); i++) {
				if ( eqSetovi0.get(i).contains(k) ) {
					prihvStanja2.add( eqSetovi0.get(i).first() );
				}
			}
		}
		prvii = true;
		for (String s: prihvStanja2) {
			if (!prvii) System.out.printf(",");
			System.out.printf(s);
			prvii = false;
		}
		System.out.println();
		
		for (int i=0; i<eqSetovi0.size(); i++) {
			if ( eqSetovi0.get(i).contains(pocStanje) ) {
				System.out.printf( eqSetovi0.get(i).first() );
				break;
			}
		}
		System.out.println();
		
		for (String s: ispisStanja) {
			for (String a: abeceda) {
				if (matricaPrijelaza.get(s).get(a) != null) {
					System.out.printf( s + "," + a + "->" );
					for (int i=0; i<eqSetovi0.size(); i++) {
						if ( eqSetovi0.get(i).contains(matricaPrijelaza.get(s).get(a)) ) {
							System.out.println( eqSetovi0.get(i).first() );
						}
					}
				}
			}
		}
		
		
	}
	
	
	

	public static void main(String[] args) {
		MinDka proba = new MinDka();
		proba.loadPODACI();
		
	}
}
