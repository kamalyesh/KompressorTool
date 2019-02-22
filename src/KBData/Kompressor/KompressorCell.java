/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package KBData.Kompressor;

import java.util.ArrayList;

/**
 *
 * @author kamalyesh
 */

//  Basic unit of Kompressor
//  It is indexed using start_c, current_c, next_c
//      where start_c is first character of the string
//      and current_c is current character of the string
//      and next_c is character after current_c, that is being processed
//          if some next_c should exist, it's check will be indexed using 
//          {start_c, current_c}(check_list[next_c])
//  It will store check, i.e., code or hash of whole string from 
//  start_c to next_c, if and only if string contains next_c
//  enumeration Order will specify the arrangement of "check"s in the KompressorCell
//      ASCENDING will order smaller-number, ..., greater-number
//      DESCENDING will order greater-number, ..., smaller-number
public class KompressorCell {
    char start_c;
    char current_c;
    ArrayList<ArrayList<Integer>> check_list;
    enum Order {ASCENDING, DESCENDING};
    public KompressorCell(char start, char current){
        //  constructor
        //  assign start_c corresponding to the cell
        start_c = start;
        //  assign current_c corresponding to the cell
        current_c = current;
        //  initialize list of list of checks (and it is "list of list", it is not a mistake)
        check_list = new ArrayList<ArrayList<Integer>>();
        for(int i=0; i< 27; ++i){
            //  initialize all lists in list of list of checks
            //  for 27 potential next_c's including(?) appostrophe (')
            check_list.add(new ArrayList<Integer>());
        }
    }
    
    public char getStart(){
        //  get the start_c corresponding to the cell
        return start_c;
    }
    
    public char getCurrent(){
        //  get the current_c corresponding to the cell
        return current_c;
    }
    
    public void addCheck(char next_c, int check){
        //  add new check calculated for next_c of the string
        //  which starts with start_c,
        //  and where, character before next_c is current_c
        
        //  nextPos is index of arrayList, since it is easier than using character index
        int nextPos = next_c - 'a';        
        //  check if mentioned check already exists in the check_list(nextPos)
        ArrayList<Integer> list = check_list.get(nextPos);
        if(!list.contains(check)){
            //  if check does not exists, add it in the check_list(nextPos)
            check_list.get(nextPos).add(check);
        }
    }
    
    public ArrayList<Integer> getCheckList(char next_c){
        //  return the check_list for next_c character next_c
        return check_list.get(next_c-'a');
    }
    
    public void arrange(Order order){
        //  arrange all the check's in the check_list
        for(int i=0; i<26; ++i){
            //  arrange list of list; list by list
            ArrayList<Integer> list = check_list.get(i);
            for(int j=0; j<list.size(); ++j ){
                if(order == Order.ASCENDING){
                    //  ascending order
                    for(int k=i; k<list.size()-1; ++k){
                        int num1= list.get(k);
                        int num2= list.get(k+1);
                        //  ascending order
                        if(num2<num1){
                            //  swap
                            list.set(k, num2);
                            list.set(k+1, num1);
                        }
                    }
                }else if(order == Order.DESCENDING){
                    //  descending order
                    for(int k=i; k<list.size()-1; ++k){
                        int num1= list.get(k);
                        int num2= list.get(k+1);
                        //  descending order
                        if(num2>num1){
                            //  swap
                            list.set(j, num2);
                            list.set(k, num1);
                        }
                    }
                }
            }
            //  put arranged list, back in its place
            check_list.set(i, list);
        }
    }
}
