import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class SimEnka {
	                                                                                                                                                                     
	public SimEnka() {
	}
	
	public static String path = "/Users/lavsmacbook/Downloads/lab1_primjeri[1]/test33/test.a"; //UNESITE PATH MAPE SA ULAZNIM PODACIMA

	public static String getPath() {
		return path;
	}
	
	public List<List<String>> ulazi = new ArrayList<List<String>>(); 
	//public List<String[]> ulazi = new ArrayList<>();
	private static TreeSet<String> stanja = new TreeSet<>();
	private static TreeSet<String> abeceda = new TreeSet<>();
	public static TreeMap<String, TreeMap<String, List<String>>> prijelazi = new TreeMap<>();
	private static TreeSet<String> prihvStanja = new TreeSet<>();
	
	public static TreeSet<String> vrati = new TreeSet<>();
	
	public static int brStanja = 0;
	public static int duljAbe = 0;
	public static String pocStanje = null;
	
	public void epsilonko(String tStan) {
		
		if (!(prijelazi.get(tStan) == null)) {
			if (prijelazi.get(tStan).containsKey("$")) {
				for (String dodaj: prijelazi.get(tStan).get("$")) {
					if (vrati.contains(dodaj)) {
						continue;
					} else {
						if (!dodaj.equals("#")) {
							vrati.add(dodaj);
							epsilonko(dodaj);
						}
					}
				}
			}
		}
		
		
		return;
		
	}
	
	public void loadPODACI() {
		
		int cnt = 1;
		
		Scanner sc = new Scanner(System.in);
		
		while (sc.hasNextLine()) {
			
			//System.out.println(cnt);
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
					prijelazi.put(linija, null);
					brStanja = stanja.size();
				}	
			}
			
			else if (cnt == 3) {
				String[] splits = line.split(",");
				for (String linija: splits) {
					abeceda.add(linija);
					duljAbe = abeceda.size();
				}	
			}
			
			else if (cnt == 4) {
				String[] splits = line.split(",");
				for (String linija: splits) {
					prihvStanja.add(linija);
				}	
			}
			
			else if (cnt == 5) {
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

				TreeMap<String, List<String>> ubacuj = new TreeMap<>();
				List<String> ns = new ArrayList<String>();
				
				if (prijelazi.get(splits[0]) != null) {
					ubacuj = prijelazi.get(splits[0]);
				
				}
				
				for (int i=2; i<splits.length; i++) {
					ns.add(splits[i]);
				}

				ubacuj.put(splits[1], ns);
				
				prijelazi.replace(splits[0], ubacuj);	
					
					
				
			}
			
			cnt++;
		}
				

			////------
//			for (String kljuc: prijelazi.keySet()) {
//				System.out.println(kljuc + " : " + prijelazi.get(kljuc));
//			}
			////------
			
			
		
		Boolean first = true;
		for (int i=0; i<ulazi.size(); i++) {
			if (!first) {
				System.out.println();
			}
			first = false;
			vrati.clear();
			
			vrati.add(pocStanje);
			epsilonko(pocStanje);
			
			ArrayList<String> trenStanja = new ArrayList<>(vrati);
			trenStanja.sort(null);
			
			Boolean ppprvi = true;
			for (String s: trenStanja) {
				if (!ppprvi) {
					System.out.printf(",");
				}
				ppprvi = false;
				System.out.printf(s);
			}
			
			for (int j=0; j<ulazi.get(i).size(); j++) {
				TreeSet<String> novStanja = new TreeSet<>();
				
				
				System.out.printf("|");
				

				vrati.clear();
				
				for (String kk: trenStanja) {
					if (prijelazi.get(kk) != null) {
						if (prijelazi.get(kk).containsKey( (ulazi.get(i)).get(j) ) ) {
							List<String> izlazi = prijelazi.get(kk).get((ulazi.get(i)).get(j)) ;
							for(String s: izlazi) {
								if (!s.equals("#")) {
									novStanja.add(s);
									vrati.add(s);
									epsilonko(s);
								}
							}
						}
					}
					
					
				}
				
				novStanja.addAll(vrati);
				
				Boolean prvi = true;
				for (String pp: novStanja) {
					if (!prvi) {
						System.out.printf(",");
					}
					prvi = false;
					System.out.printf(pp);
				}
				
				
				trenStanja = new ArrayList<>(novStanja);
				if (trenStanja.size() == 0) {   //trenStanja -> novStanja
					System.out.printf("#");  
				}
				
			}
			
			
		}
		
	}
	public static void main(String[] args) {
		SimEnka proba = new SimEnka();
		proba.loadPODACI();
		
	}
	
	
}
