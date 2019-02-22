/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KBData.Kompressor;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import kompressortool.Primes;

/**
 *
 * @author kamalyesh
 */
public class KompressorGrid {
    //  2-D array of KompressorCell, structure to store the processed data
    KompressorCell[][] krid;
    //  a single KompressorCell to manipulate data of any of cell from krid
    KompressorCell kcell;
    //  not used following enumeration Index
    //static enum Index {A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z};
    //  start character, which should be used throught the string
    //  if you want to change the string, you should do so by calling startReading(start)
    //  This will re-initialize start character and also do some necessary processing
    char start ;
    //  check value necessary for processing the structure
    //  It will be re-initialize to zero with each startReading
    private int check = 0;
    
    public KompressorGrid(){
        //  initialize kompressorGrid or krid
        //  i for indexing start_c
        //  j for indexing current_c
        krid = new KompressorCell[26][26];
        for(int i=0; i< 26; ++i){
            for(int j=0; j<26; ++j){
                //  initialize each cell with corresponding start_c and current_c
                krid[i][j] = new KompressorCell((char)('a'+i), (char)('a'+j));
            }
        }
    }
    
    private void startReading(char start){
        
        //check=0;
        //  start read operation
        //  here start_c = start; and current_c = start
        kcell = krid[start-'a'][start-'a'];
        //  we calculate check for current_c = start, with current position = 0
        //  since we are reevaluating check for first character of string, we do not need to specifically 
        //  assign check = 0; hence it is commented
        check = calculateCheck(start, 0);
        //  initialize class variable start with parameter start
        this.start = start;
    }
    
    public int calculateCheck(char c, int pos){
        //  evaluating value check necessary for the structure
        int value = (int)c;        
        //  formula
        return check + (value * Primes.primes[pos]);
    }
    
    private void shiftTo(char next, int pos) throws KompressorException{
        //TODO: this is where some logical error might occur
        //  This method shifts current kcell to the next cell under observation
        //  here next is next character to be processed, which should exist in 
        //  next potentials of current kcell
        
        //  we first get the list which might contain next_c in it
        ArrayList<Integer> list = kcell.getCheckList(next);
        if(list.contains(check)){
            //  if list contains check in it, we can shift to corresponding KompressorCell
            //  in the krid, kcell = krid[start index][current index]
            //  since after shifting, next will become current, hence
            //  current index = next-'a'
            kcell = krid[start-'a'][next-'a'];
            //  evaluate check for new validated string
            check = calculateCheck(next, pos);
        }else{
            //  list does not contain check in it
            //  this is exception
            //TODO: see how this exception should never occur
            check = -1;
            //  exception is "invalid word"
            throw new KompressorException("invalid word");
        }
    }
    
    public int kompressNew(String word){
        //  this method will evaluate and arrange given word in KompressorGrid structure
        //  initialize check to zero, to evaluate it fresh
        check = 0;
        //  initialize start with first character of the input word
        char start = word.charAt(0);
        //  current and next will be used as needed
        char current, next;
        //  process the whole word
        for(int i=0; i<word.length(); ++i){
            //  get current character to process from the word
            current = word.charAt(i);
            //  evaluate check for current character
            check = calculateCheck(current, i);
            //  we will now operate on KompressorCell where start_c = start index
            //  and current_c = current index
            kcell = krid[start-'a'][current-'a'];
            //  we need to establish if word has ended or not
            //  we compare it with index i and word length
            if(i+1 < word.length()){
                //  there is a potential next character
                next = word.charAt(i+1);
            }else{
                //  there is no next character
                //  word is now completely processed
                next = 'z'+1;
            }
            //  add new check value corresponding to the next letter in the word.
            kcell.addCheck(next, check);
            /*
            Thus, KompressorCell[start_c][current_c].list[next_c] will contain
            check value of current_c
            To see if a next_c is potential next character of the string,
            if calculated check of string upto the character just before
            next character is found in 
            KompressorCell[start_c][current_c].list[next_c]
            then next_c is potential next character that may form the valid word
            */
        }
        //  arrange the next_c characters' cell in order
        //  for ease of furrther operation, as it is now structured
        kcell.arrange(KompressorCell.Order.ASCENDING);
        //  when we have processed the word, we can return the evaluaed check
        //  if check is zero, it will indicate the word has not been processed
        return check;
    }
    
    public String getSuggestion(String word){
        //  this method will evaluate return potential characters that may
        //  form a valid word, where valid word is a string processed and 
        //  stored in the KompressorGrid
        
        //  initialize check to zero, to evaluate it fresh
        check = 0;
        //  initialize start with first character of the input word
        char start = word.charAt(0);
        //  next will be used as needed
        char next;

        //  start reading the word in the structure with help of its first
        //  character
        startReading(start);
        try{
            //  process the whole word
            for(int i=1; i< word.length(); ++i){
                //  get next_c from position i
                next = word.charAt(i);
                //  shift to next_c which is at position i
                shiftTo(next, i);
            }
        } catch (KompressorException ex) {
            //  since shiftTo method might throw exception, we catch it here
            Logger.getLogger(KompressorGrid.class.getName()).log(Level.SEVERE, null, ex);
        }
        //  This stringBuilder will collect all the next potential characters
        StringBuilder sb = new StringBuilder();
        //  check if word is valid according to the structure KompressorGrid
        if(check != -1){
            //  check != 1, means the word is valid for KompressorGrid
            //  now we will find potential next characters from all 26 alphabets
            //  hence 0 to 25
            for(int i=0; i< 25; ++i){
                //  get check list for potential next letter which is i'th alphabet
                ArrayList<Integer> list = kcell.getCheckList((char) ('a'+i));
                /*
                KompressorCell[start_c][current_c].list[next_c] will contain
                check value of current_c
                To see if a next_c is potential next character of the string,
                if calculated check of string upto the character just before
                next character is found in 
                KompressorCell[start_c][current_c].list[next_c]
                then next_c is potential next character that may form the valid word
                */
                if(list.contains(check)){
                    //  list contains check value,
                    //  i'th letter is potentially next letter
                    char c = (char)(i+'a');
                    
                    //  check if this letter is already collected or not
                    if(!sb.toString().contains(c+"")){
                        //  if this letter is not collected yet, collect it
                        sb.append(c);
                    }
                }
            }
        }
        //  return all collected potentially next letters of string word
        //  which may help form a valid word
        return sb.toString();
    }
}
