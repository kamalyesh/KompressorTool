/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KBData.KB_IE;

import KBData.KompressorData;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import kompressortool.Primes;

/**
 *
 * @author kamalyesh
 */

/*  KBIEHandler is If-Else Handler
    Ideally, this class will create number of Java class
    where each Java class will be of separate start_c and current_c
    if given parameter check exists for the given combination in check_list
    from KompressorCell, it will collect corresponding next_c.
    When all such next_c are collected, it will return them as a string
*/
public class KBIEHandler{
    String delim; 
    Scanner scanner;
    PrintWriter writer;

    public KBIEHandler() {
    }
    
    
    public void close() {
        scanner=null;
        writer=null;
    }
    
    public void test(String testString, int testCheck){
        char tStart = testString.charAt(0);
        int tLength = testString.length();
        char tCurrent = testString.charAt(tLength-1);
        int tCheck = testCheck;
        try {
            Scanner tScanner = new Scanner(new File(KompressorData.getOutPath()+"/"+tStart+"/"+tCurrent));
            StringBuilder sb = new StringBuilder();
            for(char tNext='a'; tNext<='z'; ++tNext){
                tCheck = testCheck + (tCurrent * Primes.primes[tLength-1]);
                int k = -1;
                while (tScanner.hasNext()) {
                    ++k;
                    String s = tScanner.nextLine();
                    String c = "" + tCheck;
                    if (s.contains(c)) {
                        String l = (char) (k + 'a') + "";
                        if (!sb.toString().contains(l)) {
                            sb.append(l);
                        }
                    }
                }
            }
            System.out.println(testString+":"+"next letters="+sb.toString());
            for(int i=0; i<sb.length(); ++i){
                tCheck = testCheck;
                char tN = sb.charAt(i);
                String t = testString+ tN;
                //tCheck = tCheck + (tCurrent * Primes.primes[tLength-1]) + (tN * Primes.primes[tLength]);
                tCheck = tCheck + (tCurrent * Primes.primes[tLength-1]);
                test(t, tCheck);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KBIEHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void closeAfterRead() throws IOException {
        scanner.close();
    }
    
    private void openToRead(char start, char current) throws FileNotFoundException {
        scanner =  new Scanner(new File("/mnt/Engineering/NetBeansProjects/Work/KompressorTool/src/KBAI/"+(start+"").toUpperCase()+"/"+(current+"").toUpperCase()+".java"));
    }

    public void closeAfterWrite(int i) throws IOException {
        writer=w[i];
        writer.write("\n\t\t\t"
                        + "default: return \"\";"
                + "\n\t\t}"
                + "\n\t}");
        writer.write("\n\n}");
        writer.close();
        ArrayList<Integer> temp = checkIfExists.get(i);
        temp.clear();
        checkIfExists.set(i, temp);
    }

    public void write(int checkValue, String nextString) {
        writer.write("\n\t\t\tcase "+checkValue+":"
                + "\n\t\t\t\treturn "
                + "\""
                + nextString
                + "\""
                + ";"
        );
    }

    PrintWriter w[]=new PrintWriter[26];
    ArrayList<ArrayList<Integer>> checkIfExists = new ArrayList<ArrayList<Integer>>();
    public PrintWriter[] openToWrite(char start, char current) throws IOException {
        checkIfExists.add(new ArrayList<>());
        String s = (start+"").toUpperCase();
        String c = s+(current+"").toUpperCase();
        File file = new File("/mnt/Engineering/NetBeansProjects/Work/KompressorTool/src/KBAI/"+s+"/"+c+".java");
        if(!file.exists()){
            file.createNewFile();
        }
        writer = new PrintWriter(file);
        this.w[current-'a']=writer;
        String packageName = "KBAI."+s;
        String className = c;
        String intro="/*"
                + "\n\tAuthor: Kamalyesh Kannadkar"
                + "\n\tDate: "+(new Date()).toString()
                + "\n\tpackage: "+packageName
                + "\n\tclass: "+className
                + "\n*/";
        
        writer.write(intro);
        writer.write("\n\npackage "+packageName+";");
        writer.write("\n\n");                                                                                               
        writer.write("\npublic class "+className+" {");
        writer.write("\n\n\t"
                + "public static String getNext(int check){"
                + "\n\t\t"
                    + "switch(check) {");
        prev = current;
        return this.w;
    }
    static char prev='z';
    public void findNexts(String testString, int testCheck) throws IOException{
        char tStart = testString.charAt(0);
        int tLength = testString.length();
        char tCurrent = testString.charAt(tLength-1);
        int tCheck = testCheck;
        try {
            Scanner tScanner = new Scanner(new File(KompressorData.getOutPath()+"/"+tStart+"/"+tCurrent));
            StringBuilder sb = new StringBuilder();
            for(char tNext='a'; tNext<='z'; ++tNext){
                tCheck = testCheck + (tCurrent * Primes.primes[tLength-1]);
                int k = -1;
                while (tScanner.hasNext()) {
                    ++k;
                    String s = tScanner.nextLine();
                    String c = "" + tCheck;
                    if (s.contains(c)) {
                        String l = (char) (k + 'a') + "";
                        if (!sb.toString().contains(l)) {
                            sb.append(l);
                        }
                    }
                }
            }
            if(tCurrent != prev){
                prev = tCurrent;
                writer = w[tCurrent-'a'];
            }
            ArrayList temp = checkIfExists.get(tCurrent-'a');
            tCheck = testCheck+(tCurrent * Primes.primes[tLength-1]);
            if(!temp.contains(tCheck)){
                temp.add(tCheck);
                checkIfExists.set(tCurrent-'a', temp);
                write(testCheck+(tCurrent * Primes.primes[tLength-1]), sb.toString());
            }
            System.out.println(testString+":"+"next letters="+sb.toString());
            for(int i=0; i<sb.length(); ++i){
                tCheck = testCheck;
                char tN = sb.charAt(i);
                String t = testString+ tN;
                tCheck = tCheck + (tCurrent * Primes.primes[tLength-1]);
                findNexts(t, tCheck);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(KBIEHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
