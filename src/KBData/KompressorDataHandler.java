package KBData;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class KompressorDataHandler {
    //  Kamalyesh Kannadkar
    //  08-06-2018

    //String start_character;                 //  first part of filre
    //String current_character;               //  filename
    //String next_character;                  //  first word on each line of file
    //String check_list;                      //  contents followed by next_character
    String delim=":";                           //  deliminator


    //  ~/start_character_current_character:
    //  #next_character check_list

    PrintWriter writer;
    Scanner scanner;

    public KompressorDataHandler(){
     
    }

    public void openToWrite(char start, char current) throws IOException {
        //File file = context.getFileStreamPath(start+"_"+current);
        File file = new File("/mnt/Engineering/NetBeansProjects/Work/KompressorTool/out/"+start+"_"+current);
        if(!file.exists()){
            file.createNewFile();
        }
        writer = new PrintWriter(file);
    }

    public void write( char next, ArrayList<Integer> check_list){
        //  open file to write
        //  go to line number #next_character
        //  write check_list
        //  close file
        StringBuilder line = new StringBuilder();
        line.append(next);
        for(int i=0, j=0; i< 26 && j<check_list.size(); ++i) {
            line.append(delim);
            if(check_list.size()==0){
                line.append("__");
            }else {

                if(check_list.get(j)!=null){
                    line.append(check_list.get(j).toString());
                    ++j;
                } else {
                    line.append("__");
                }
            }
        }
        line.append("\n");
        writer.write(line.toString());
    }

    public void closeAfterWrite() throws IOException {
        writer.close();
    }

    private void openToRead(char start, char current) throws FileNotFoundException {
        scanner =  new Scanner(new File("/mnt/Engineering/NetBeansProjects/Work/KompressorTool/out/"+start+"_"+current));
    }

    public StringBuilder read(char start, int check) throws IOException {

        //  for current = 'a' to 'z'
        //      open file current to read
        //      read line
        //      if line contains check
        //          parse line number as alphabet (#next_character + 'a')
        //          if stringbuilder doesnot contain the alphabet
        //              add alphabet to stringbuilder
        //      end if
        //      close file
        //  end for
        //  return stringbuilder
        String line;
        StringBuilder sb = new StringBuilder();
        for(char current='a'; current<='z'; current++){
            openToRead(start, current);
            if(scanner!=null) {
                char next = 'a';
                while (scanner.hasNext()) {
                    if (next > 'z') {
                        break;
                    }
                    line = scanner.nextLine();
                    if (line.contains("" + check)) {
                        char x = line.charAt(0);
                        char y = ' ';
                        int l = -1;
                        while(l<line.length()-1) {
                            String ch =""+check;
                            if(line.length()!=ch.length()){
                                if(line.indexOf(delim)== line.lastIndexOf(delim)) {
                                    break;
                                }
                            }
                            ++l;
                            y = line.charAt(l);
                            if((""+y).equalsIgnoreCase(delim)){

                                int l2= (ch).length();
                                int l3 = l + l2 + 1;
                                String li = line.substring((l+1), l3);

                                if(li.equalsIgnoreCase(""+check)){
                                    if(line.length()==(l3) || line.charAt(l3)==':') {
                                        if (!sb.toString().contains(""+x)) {
                                            sb.append(x);
                                        }
                                        line = line.substring(l3);
                                        l = -1;
                                    }
                                }
                            }

                        }


                    }
                    next++;
                }
            }
            closeAfterRead();
        }
        return sb;
    }

    private void closeAfterRead() throws IOException {
        scanner.close();
    }

    public void close() {
        scanner=null;
        writer=null;
    }
}
