/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kompressortool;

import KBAI.KBAIHandler;
import KBData.KompressorData;
import KBData.KB_IE.KBIEHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author kamalyesh
 */
public class KompressorToolHandler {

    static boolean DEBUG = true;
    static boolean SPELL_MAGIC = true;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if(SPELL_MAGIC && DEBUG){
            debugMagic();
        }else if (SPELL_MAGIC){
            spellMagic();
        }else if (DEBUG) {
            debugCode();
        }else{
            //  whatever you have in mind
        }
    }
    
    private static void spellMagic(){
        //  This is latest part of the code,
        //  where files generated in debugCode are used to
        //  create switch case java code where for each
        //  value case, it will return a string of next letters
        
        //  initialize the class which will write the necessary java code
        KBIEHandler kBIEHandler = new KBIEHandler();
            try{
                for(char c1='a'; c1<='z'; ++c1){
                    //  open files
                    for(char c2='a'; c2<='z'; ++c2){                        
                        kBIEHandler.openToWrite(c1, c2);
                    }
                    //  write into files
                    String testString = c1+"";
                    //  get nexts
                    kBIEHandler.findNexts(testString, 0);
                    
                    //  close all 26 files
                    for(char c2='a'; c2<='z'; ++c2){
                        kBIEHandler.closeAfterWrite(c2-'a');
                    }
                }                
            }catch(Exception e){
                e.printStackTrace();
            }   
    }
    
    private static void debugMagic(){
        //  this method checks if what java code we made is correct
        //  but I have prepared another java class to call onto correct java class
        //  that code is in KBAIHandler class
        
        //  initialize list
        ArrayList<String> debugStrings = new ArrayList<String>();
        try {
            //  read test strings from test file
            Scanner scanner = new Scanner(KompressorData.getTestData());
            while (scanner.hasNext()) {
                debugStrings.add(scanner.next());
            }
            //  close scanner
            //  it is good practice to close opened files
            scanner.close();
            
            //  initialize check and KBAIHandler
            int check;
            KBAIHandler aiMagicCaster= new KBAIHandler();
            for(int i=0; i<debugStrings.size(); ++i){
                //  get a test string
                String s = debugStrings.get(i);
                //  initialize nexts string variable
                String nexts="";
                //  initialize check = 0
                check = 0;
                //  get start letter of the test word
                char start = s.charAt(0);
                for(int j=0; j<s.length(); ++j){
                    //  get current letter of the word
                    char current = s.charAt(j);
                    //  calculate check value of the current letter
                    check = check+current*Primes.primes[j];
                    //  get nexts corresponding to start, current, check
                    nexts = aiMagicCaster.getNexts(start, current, check);
                    //  display nexts to determine if it is working correctly
                    System.out.println(s.substring(0, j + 1) + ":" + "next letters=" + nexts);
                }
            }
        }catch(Exception e){
            //  because we have code that words on file, 
            //  we must put exception handler here too.
            e.printStackTrace();
        }
    }

    private static void debugCode() {
        //  initialize Kompressor
        KBData.Kompressor.KompressorTool kompressor = new KBData.Kompressor.KompressorTool();
        //  initialize list
        ArrayList<String> debugStrings = new ArrayList<String>();
        try {
            //  preparation:
            //  put all words in files, one word per line
            //  I have prepared separate 26 files,
            //  each for one character which starts the word
            //  for ease
            
            //  read all words from the files
            //  get them in array.            
            for (char file = 'a'; file <= 'z' + 1; ++file) {
                //  pattern to check whether a character is accpted as alphabet or not
                String pattern = "[a-zA-Z]+";
                //  scanner to read the file
                Scanner scanner;
                if (file == 'z' + 1) {
                    //  get test words, because all files have been mapped
                    scanner = new Scanner(KompressorData.getTestData());
                } else {
                    //  get words from file
                    scanner = new Scanner(KompressorData.getDataFrom(file));
                }
                
                //  check if there are more words to read
                while (scanner.hasNext()) {
                    //  read a word in str
                    String str = scanner.next();
                    //  check if word contains only alphabets or not
                    if (Pattern.matches(pattern, str)) {
                        //  word contains only alphabets
                        //  change all letters to lowercase
                        str.toLowerCase();
                        //  add it to the list
                        debugStrings.add(str);
                    }
                }
                //  close the scanner
                //  it is good practice to close opened file
                scanner.close();
            }
        } catch (FileNotFoundException ex) {
            //  In case I missed something, this will catch such exceptions
            Logger.getLogger(KompressorToolHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        //  now we will map all these strings
        for (int i = 0; i < debugStrings.size(); ++i) {
            //  get one string from the array
            String currentString = debugStrings.get(i);
            //  map it into the Kompressor
            kompressor.mapWord(currentString);
            if (i % 1024 == 0) {
                //  every 1024th word being mapped will be displayed on the screen here
                //  this is just a placebo
                //  to keep checking progress of the mapping
                System.out.println("Mapping " + currentString);
            }
        }
        //  now we will write all these mapped values in another structure
        //  in file format- because we have to store all this mapped data somewhere
        //  we can map them everytime we need, but that will take our precious
        //  computing time- which will store values in file named startc_currentc 
        //  where each line will represent each nextc alphabetically, and each
        //  line will store corresponding values in them
        //  ONE LINE: ONE NEXT_C : ZERO to MANY VALUES
        PrintWriter writer;
        try {
            for (int i = 0; i < 26; ++i) {
                // create the file path upto startc
                String path = KompressorData.getOutPath() + "/" + ((char) (i + 'a')) + "_";
                for (int j = 0; j < 26; ++j) {
                    //  complete path with currentc
                    File file = new File(path + ((char) (j + 'a')));
                    if (!file.exists()) {
                        //  if file is not there, create it
                        file.createNewFile();
                    }
                    //  PrintWriter to handle writing we need to do
                    writer = new PrintWriter(file);
                    //  uncomment following line if you want to see what file is being written
                    //System.out.println("#########   FILE "+(char)('a'+i)+"_"+(char)('a'+j)+"   ##########");
                    for (int k = 0; k < 26; ++k) {
                        //  get KompressorCell corresponding to 
                        //  startc='a'+i, currentc='a'+j
                        //  nextc='a'+k, in string format
                        String s = kompressor.export(i, j, k);
                        //  uncomment following line if you want to see what line is being written in the file
                        //System.out.println(s);
                        
                        //  write into the file
                        writer.write(s);
                        //  after each line, don't forget to add a newline character
                        //  it makes parsing easier
                        writer.write("\n");
                    }
                    //  close writer
                    //  it is good practice to close opened files
                    writer.close();
                }
            }
        } catch (FileNotFoundException ex) {
            //  if by some miracle file is not found even after creating it,
            //  this will catch the exception and let us know
            Logger.getLogger(KompressorToolHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            //  same for input-output
            Logger.getLogger(KompressorToolHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //  following code tests whether there are errors in written file or not
        //  well... not all words, but the test words we put in before :)
        debugStrings = new ArrayList<String>();
        try {
            //  read the test data into array
            Scanner scanner = new Scanner(KompressorData.getTestData());
            while (scanner.hasNext()) {
                //  read data line-by-line
                //  because each line has one word,
                //  hence read data word-by-word
                debugStrings.add(scanner.next());
            }
            //  close the scanner
            //  it is good practice to close opened files
            scanner.close();

            for (int i = 0; i < debugStrings.size(); ++i) {
                //  get one word at a time from array
                String currentString = debugStrings.get(i);
                //  get starting letter of the word
                char start = currentString.charAt(0);
                //  initialize check value
                int check = 0;
                for (int j = 0; j < currentString.length(); ++j) {
                    //  get current letter of the word
                    char current = currentString.charAt(j);
                    char next;
                    if (j == currentString.length() - 1) {
                        //  if current letter is the last letter of the word
                        //  next letter is 'z'+1
                        //  this is to let code know we have used all letter of the word
                        next = 'z' + 1;
                    } else {
                        //  else get next letter from position next to current letter
                        next = currentString.charAt(j + 1);
                    }
                    StringBuilder sb = new StringBuilder();
                    if(!Pattern.matches("[a-z]",""+current)){
                        //  if current letter is not an alphabet
                        //  i.e., if it is '.' or ',' or '
                        // do not include it in calulation
                        continue;
                    }
                    //  read start_current using scanner
                    scanner = new Scanner(new File(KompressorData.getOutPath() + "/" + start + "_" + current));
                    //  calculate check value for current letter
                    check = check + (current * Primes.primes[j]);
                    int k = -1;
                    //  get next letters from the file
                    while (scanner.hasNext()) {
                        ++k;
                        String s = scanner.nextLine();
                        String c = "" + check;
                        // check if line read contains check value
                        if (s.contains(c)) {                            
                            String l = (char) (k + 'a') + "";
                            //  check if next letter is already collected
                            if (!sb.toString().contains(l)) {
                                //  if not collected already, collect it now
                                sb.append(l);
                            }
                        }
                    }
                    //  following line shows what next letters are collected
                    System.out.println(currentString.substring(0, j + 1) + ":" + "next letters=" + sb.toString());
                }
            }
        } catch (FileNotFoundException ex) {
            //  again, exceptions :(
            Logger.getLogger(KompressorToolHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
