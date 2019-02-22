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
public class KompressorTool {
    //  It is abstract view for KompressorGrid
    
    KompressorGrid krid;
    public KompressorTool(){
        //  initialize KompressorGrid
        krid = new KompressorGrid();
    }
    
    public void mapWord(String str){
        //  evaluate and arrange -or simply- map the string str into the structure
        //  KompressorGrid
        krid.kompressNew(str);
    }
    
    public String getSuggestionFor(String str){
        //  get potentially next letters that may follow string str
        return krid.getSuggestion(str);
    }
    
    public String export(int i, int j, int k){
        //  this method is useful in listing only check values of
        //  KompressorCell indexed by start_c = 'a'+i, 
        //                            current_c = 'a'+j,
        //                            next_c = 'a'+k
        //  and returns all those values in format:
        //  next=<next_c> :: check_list=<check1>:<check2>:...:<checkN>
        
        //  initialize stringbuilder as per format
        StringBuilder sb = new StringBuilder("next="+(char)('a'+k)+" :: check_list=");
        //  get KompressorCell corresponding to i, j
        KompressorCell kcell = krid.krid[i][j];
        //  get list of check values corresponding to next_c 'a'+k
        ArrayList<Integer> list = kcell.getCheckList((char)('a'+k));
        //  put all check values from list in format
        //  separated by delimiter ':'
        for(int x=0; x<list.size(); ++x){
            sb.append(list.get(x));
            sb.append(":");
        }
        //  return string as per format
        return sb.toString();
    }

}
