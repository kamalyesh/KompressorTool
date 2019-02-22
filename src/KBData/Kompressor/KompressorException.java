package KBData.Kompressor;

//import OldKompressor.*;


//  Exception class for Kompressor
public class KompressorException extends Throwable {
    String msg = "";
    public KompressorException(String message) {
        //  update msg by message
        msg = message;
    }

    @Override
    public String toString(){
        //  return Exception message
        return super.toString()+"\n"+msg;
    }
}
