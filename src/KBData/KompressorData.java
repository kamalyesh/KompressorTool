package KBData;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author kamalyesh
 */
public class KompressorData {

    static File userFile;

    public static File getDataFrom( char c) throws FileNotFoundException {
        if(c!='_') {
            return getFullResources(c);
        }else{
            //return new FileInputStream(userFile);
        }
        return null;
    }




    private static File getFullResources(char curr_c) {
        return new File("/mnt/Engineering/NetBeansProjects/Work/KompressorTool/raw/"+curr_c);
    }

    public static File getTestData(){
        return new File("/mnt/Engineering/NetBeansProjects/Work/KompressorTool/test/test");
    }
    /*public void addUserWord(File userFile, String userWord) {
        Scanner s=null;
        PrintWriter writer=null;
        this.userFile = userFile;

        try {
            // String path = userFile.getAbsoluteFile().toString();
            ArrayList<String> words = new ArrayList<String>();
            s = new Scanner(userFile);
            while(s.hasNext()) {
                words.add(s.next());
            }
            s.close();
            writer = new PrintWriter(userFile);
            if(!writer.checkError()){
                writer.append(userWord + "\n");
                for(int i=0; i< words.size();   ++i){
                    writer.append(words.get(i).toString()+"\n");
                }
            }else{
                //Log.d("stkdemo", "Word: "+userWord+"In "+getClass().getName()+".addUserWord(), \"user\" raw resource not found");
                System.out.println("Word: "+userWord+"In "+getClass().getName()+".addUserWord(), \"user\" raw resource not found");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            //Log.d("stkdemo", "In "+getClass().getName()+".addUserWord(), "+e);
            System.out.println("In \"+getClass().getName()+\".addUserWord(), "+e);
        }finally {
            s.close();
        }
    }*/

    public static String getOutPath() {
        return "/mnt/Engineering/NetBeansProjects/Work/KompressorTool/out";
    }
}