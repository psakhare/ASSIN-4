package edu.cse523.psakhare.tttgameusingmessagingprotocol;

/**
 * Created by Priyanka on 11/11/2017.
 */
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import java.util.Observable;
import java.util.Observer;

public class TTTButton extends Button implements Observer {
    int index=0;
    public TTTButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        String packageName = "http://schemas.android.com/apk/res-auto";
        index = attrs.getAttributeIntValue(packageName,"index",0);  //default value 0 will be returned
        setText(Integer.toString(index));
    }

    public int getIndex(){
        return index;
    }

    @Override
    public void update(Observable o, Object arg) {
        String symbol = (String) arg;
        System.out.println(arg + " " + ((DataCell)o).getValue());
        setText(symbol);
    }
}