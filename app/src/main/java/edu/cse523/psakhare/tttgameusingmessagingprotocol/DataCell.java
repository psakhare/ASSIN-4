package edu.cse523.psakhare.tttgameusingmessagingprotocol;

/**
 * Created by Priyanka on 11/11/2017.
 */
import java.util.ArrayList;
import java.util.Observable;

public class DataCell extends Observable {
    ArrayList<TTTButton> observers = new ArrayList<TTTButton>();
    private String value=null;

    public DataCell(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }
    public void setValue(String value){
        this.value = value;
        setChanged();
        notifyObservers(value);
    }
}
