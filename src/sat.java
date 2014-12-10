import java.io.*;
import java.util.Scanner;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

public class sat {

    public static void main(String[] args) {
		//int []arr1 = {0,1};
		//int [][]arr2 ={{1,1,1,0,0,0,0,0,0,0},
		//		       {2,1,0,1,0,0,0,0,0,0}};
		
				       
		
    	
    	
		int x;
		int y;
	
		
		
		int a = 0;
		int b = 0;
		int c = 0;
		int n = 8;
    	
		System.out.println("Input expression:");
		Scanner keyboard = new Scanner(System.in);
		
    	String s1 = keyboard.nextLine();
    	String s2 = keyboard.nextLine();
    	String s3 = keyboard.nextLine();
    	
    	s1 = s1.replaceAll(" ", "");
    	s2 = s2.replaceAll(" ", "");
    	s3 = s3.replaceAll(" ", "");

    	if (s1.substring(0, s1.indexOf('a')).equals("")) {
    		x = 1;
    	} else {
    		x = Integer.parseInt(s1.substring(0, s1.indexOf('a')));
    	}
    	
    	if (s1.substring(s1.indexOf('+') + 1, s1.indexOf('b')).equals("")) {
    		y = 1;
    	} else {
    		y = Integer.parseInt(s1.substring(s1.indexOf('+'), s1.indexOf('b')));
    	}
    	
    	
    	
    	String sub1 = s2.substring(0, s2.indexOf("="));
    	String sub2 = s2.substring(s2.indexOf("=") + 1);
    	if (sub1.equals("a")) {
    		a = Integer.parseInt(sub2);
    	} else if (sub1.equals("b")) {
    		b = Integer.parseInt(sub2);
    	} else if (sub1.equals("c")) {
    		c = Integer.parseInt(sub2);
    	}
    	
    	
    	sub1 = s3.substring(0, s3.indexOf("="));
    	sub2 = s3.substring(s3.indexOf("=") + 1);
    	if (sub1.equals("a")) {
    		a = Integer.parseInt(sub2);
    	} else if (sub1.equals("b")) {
    		b = Integer.parseInt(sub2);
    	} else if (sub1.equals("c")) {
    		c = Integer.parseInt(sub2);
    	}
    	
    	BitVector bit = new BitVector();
    	
		
		
		int []arr1 = new int[x + y];
		for (int i=0; i<x; i++) {
			arr1[i] = 0;
		}
		for (int i=0; i<y; i++) {
			arr1[x + i] = 1;
		}
		
		
		int count_var = 0;
		if (a > 0) {
			count_var++;
		}
		if (b > 0) {
			count_var++;
		}
		if (c > 0) {
			count_var++;
		}
		
		int [][]arr2 = new int[count_var][2*n + 2];
		
		int []arr_arg0 = new int[2*n + 2];
		int []arr_arg1 = new int[2*n + 2];
		int []arr_arg2 = new int[2*n + 2];
		
		for (int i=0; i < (2*n + 2); i++) {
			arr_arg0[i] = 0;			
			arr_arg1[i] = 0;
			arr_arg2[i] = 0;
		}
		
		int index = 0;
		
		if (a > 0) {
			String string_arg0 = Integer.toBinaryString(a);
			for (int i=0; i<string_arg0.length(); i++) {
				arr_arg0[string_arg0.length() - i] = (int)string_arg0.charAt(i) - 48;
			}
			arr2[index] = arr_arg0;
			index++;
		}

		if (b > 0) {
			String string_arg1 = Integer.toBinaryString(b);
			arr_arg1[0] = 1;
			for (int i=0; i<string_arg1.length(); i++) {
				arr_arg1[string_arg1.length() - i] = (int)string_arg1.charAt(i) - 48;
			}
			arr2[index] = arr_arg1;
			index++;
		}

		if (c > 0) {
			String string_arg2 = Integer.toBinaryString(c);
			arr_arg2[0] = 2;
			for (int i=0; i<string_arg2.length(); i++) {
				arr_arg2[string_arg2.length() - i] = (int)string_arg2.charAt(i) - 48;
			}
			for (int i=0; i<arr_arg2.length; i++) {
				System.out.print(arr_arg2[i]);			
			}
			arr2[index] = arr_arg2;
			index++;
			System.out.println();
		}
		
		String fileName = "C:\\Users\\rayleigh\\Desktop\\CNF.in";
		
		PrintWriter fO;
		try {
			fO = new PrintWriter(new BufferedWriter(new FileWriter(fileName + "_temp")));
			int max = bit.add_vector(arr1, 2, 2*n+1, fO);
			bit.add_number(arr2, 2*n+1, fO);
			fO.close();
			
			
			
			int count_var2 = (max + 1) * (2 * n + 1);
			LineNumberReader  lnr = new LineNumberReader(new FileReader(new File(fileName + "_temp")));
			lnr.skip(Long.MAX_VALUE);
			fO = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
			int lines = lnr.getLineNumber();
			fO.println("p cnf " + count_var2 + " " + lnr.getLineNumber());
			
			
			FileInputStream fis = new FileInputStream(fileName + "_temp");
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = null;
			while ((line = br.readLine()) != null) {
				fO.println(line);
			}
			System.out.println("Number variables: " + count_var2);
			System.out.println("Number clause logic: " + lines);
			br.close();
			fO.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
    	
        ISolver solver = SolverFactory.newDefault();
        solver.setTimeout(3600); // 1 hour timeout
        Reader reader = new DimacsReader(solver);
        try {
        	System.out.println("Start...");
        	IProblem problem = reader.parseInstance(fileName);
            if (problem.isSatisfiable()) {
                System.out.println("Satisfiable !");
                String aa = reader.decode(problem.model());
                System.out.println(aa);
                String []sss = new String[4 * (2 * n + 1)];
                sss = aa.split(" ");
                String result = "";
                int decimalValue;
                for (int i=0; i<sss.length; i++) {
                	if (Integer.parseInt(sss[i]) > 0){
                		result = "1" + result;
        			}else{
        				result = "0" + result;
        			}
                	if ((i + 1) % (n * 2 + 1) == 0 && i < (2 * n + 1) * 3) {
                		decimalValue = Integer.parseInt(result, 2);
                		System.out.print(result + " ");
                		System.out.println(decimalValue);
                		result = "";
                	}
                }
            } else {
                System.out.println("Unsatisfiable !");
            }
            System.out.println("End");
        } catch (FileNotFoundException e) {
        	System.out.println("---ko co file");
            // TODO Auto-generated catch block
        } catch (ParseFormatException e) {
        	System.out.println("---ko dung format");
            // TODO Auto-generated catch block
        } catch (IOException e) {
        	System.out.println("---ko doc duoc file");
            // TODO Auto-generated catch block
        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        } catch (TimeoutException e) {
            System.out.println("Timeout, sorry!");      
        }
    }
}