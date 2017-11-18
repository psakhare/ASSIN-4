package edu.cse523.psakhare.tttgameusingmessagingprotocol;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Priyanka on 11/15/2017.
 */

public class gameMainActivity extends AppCompatActivity implements View.OnClickListener{
    String CurrentPlayerName="";
    Player[] players = new Player[2];
    Player currentPlayer = null;
    TTTButton[] tttButton = new TTTButton[9];
    DataCell[] cells = new DataCell[9];
    int current=0;
    TextView labelGame = null, whoseTurn = null;
    IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");

    void swapPlayer(){
        currentPlayer = currentPlayer == players[0] ? players[1] : players[0];
        TextView textViewToChange = (TextView) findViewById(R.id.labelGame);
        textViewToChange.setText("Player "+currentPlayer.getPlayerSymbol()+"'s Turn");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gamelayout);

        CurrentPlayerName = getIntent().getExtras().getString("PlayerName");
        labelGame =(TextView)findViewById(R.id.labelGame);
        labelGame.setText(CurrentPlayerName);
        whoseTurn = (TextView)findViewById(R.id.turnofPlayer);

        currentPlayer = players[0] = new Player("Player 1","X","5554");
        players[1] = new Player("Player 2","O","5556");

        for(int i=0;i<9;i++) {
            tttButton[i]= (TTTButton) findViewById(R.id.firstBtn+i);
            tttButton[i].setOnClickListener(this);

            cells[i] = new DataCell("");
            cells[i].addObserver(tttButton[i]);
        }

        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                    String text="";
                    String number="";
                    for (SmsMessage m : messages) {
                        number = m.getDisplayOriginatingAddress();
                        text = m.getDisplayMessageBody();
                    }
                    if ((text.contains("TTTGame") && (text.contains("index")))) {
                        String[] splited = text.split("\\s+");
                        int index = Integer.valueOf(splited[3]);

                        if(splited[1].contains("PLAYER1")){
                            cells[index-1].setValue("X");
                            whoseTurn.setText("PLAYER2");
                        }
                        else if(splited[1].contains("PLAYER2")){
                            cells[index-1].setValue("O");
                            whoseTurn.setText("PLAYER1");
                        }
                        tttButton[index-1].setEnabled(false);
                    }
                    else if ((text.contains("TTTGame") && (text.contains("WON")))) {
                        String[] splited = text.split("\\s+");

                        labelGame.setText("Game Over!! "+splited[1] + " Won!!");

                        for(int i=0;i<9;i++) {
                            tttButton[i].setEnabled(false);
                        }
                    }
                }
            }
        };
        registerReceiver(br,intentFilter);
    }

    @Override
    public void onClick(View view) {
        current++;
        TTTButton currentBtn = (TTTButton) view;
        int index = currentBtn.getIndex();
        //labelGame.setText(CurrentPlayerName + String.valueOf(index));

        TelephonyManager tMgr = (TelephonyManager)view.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        //labelGame.setText(CurrentPlayerName + String.valueOf(index) + mPhoneNumber);

        String msg = "TTTGame " + CurrentPlayerName + " index " + index;
        String num="";
        if(CurrentPlayerName.contains("PLAYER1")) {
            num = "15555215556";                               //Player1 will send message to Player2 (5556)
            cells[index-1].setValue("X");
        }
        else if(CurrentPlayerName.contains("PLAYER2")) {
            num = "15555215554";                               //Player1 will send message to Player2 (5556)
            cells[index-1].setValue("O");
        }

        //labelGame.setText(num);

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(num, null, msg, null, null);



        boolean gameWon = false;
        if((cells[0].getValue()=="X" && cells[1].getValue()=="X" && cells[2].getValue()=="X") || (cells[0].getValue()=="O" && cells[1].getValue()=="O" && cells[2].getValue()=="O")){
            TextView textViewToChange = (TextView) findViewById(R.id.labelGame);
            textViewToChange.setText("Game Over!! "+CurrentPlayerName+ " won!!");
            gameWon=true;
        }
        else if((cells[3].getValue()=="X" && cells[4].getValue()=="X" && cells[5].getValue()=="X") || (cells[3].getValue()=="O" && cells[4].getValue()=="O" && cells[5].getValue()=="O")){
            TextView textViewToChange = (TextView) findViewById(R.id.labelGame);
            textViewToChange.setText("Game Over!! "+CurrentPlayerName+ " won!!");
            gameWon=true;
        }
        else if((cells[6].getValue()=="X" && cells[7].getValue()=="X" && cells[8].getValue()=="X") || (cells[6].getValue()=="O" && cells[7].getValue()=="O" && cells[8].getValue()=="O")){
            TextView textViewToChange = (TextView) findViewById(R.id.labelGame);
            textViewToChange.setText("Game Over!! "+CurrentPlayerName+ " won!!");
            gameWon=true;
        }
        else if((cells[0].getValue()=="X" && cells[3].getValue()=="X" && cells[6].getValue()=="X") || (cells[0].getValue()=="O" && cells[3].getValue()=="O" && cells[6].getValue()=="O")){
            TextView textViewToChange = (TextView) findViewById(R.id.labelGame);
            textViewToChange.setText("Game Over!! "+CurrentPlayerName+ " won!!");
            gameWon=true;
        }
        else if((cells[1].getValue()=="X" && cells[4].getValue()=="X" && cells[7].getValue()=="X") || (cells[1].getValue()=="O" && cells[4].getValue()=="O" && cells[7].getValue()=="O")){
            TextView textViewToChange = (TextView) findViewById(R.id.labelGame);
            textViewToChange.setText("Game Over!! "+CurrentPlayerName+ " won!!");
            gameWon=true;
        }
        else if((cells[2].getValue()=="X" && cells[5].getValue()=="X" && cells[8].getValue()=="X") || (cells[2].getValue()=="O" && cells[5].getValue()=="O" && cells[8].getValue()=="O")){
            TextView textViewToChange = (TextView) findViewById(R.id.labelGame);
            textViewToChange.setText("Game Over!! "+CurrentPlayerName+ " won!!");
            gameWon=true;
        }
        else if((cells[0].getValue()=="X" && cells[4].getValue()=="X" && cells[8].getValue()=="X") || (cells[0].getValue()=="O" && cells[4].getValue()=="O" && cells[8].getValue()=="O")){
            TextView textViewToChange = (TextView) findViewById(R.id.labelGame);
            textViewToChange.setText("Game Over!! "+CurrentPlayerName+ " won!!");
            gameWon=true;
        }
        else if((cells[2].getValue()=="X" && cells[4].getValue()=="X" && cells[6].getValue()=="X") || (cells[2].getValue()=="O" && cells[4].getValue()=="O" && cells[6].getValue()=="O")){
            TextView textViewToChange = (TextView) findViewById(R.id.labelGame);
            textViewToChange.setText("Game Over!! "+CurrentPlayerName+ " won!!");
            gameWon=true;
        }


        currentBtn.setEnabled(false);

        if(CurrentPlayerName.contains("PLAYER1")) {
            whoseTurn.setText("PLAYER2");
        }
        else if(CurrentPlayerName.contains("PLAYER2")) {
            whoseTurn.setText("PLAYER1");
        }

        if(gameWon==true) {
            disableAllButtons();

            String msg1 = "TTTGame " + CurrentPlayerName + " WON ";

            SmsManager smsManager1 = SmsManager.getDefault();
            smsManager1.sendTextMessage(num, null, msg1, null, null);
        }
        /*else if(gameWon==false && current==9){
            TextView textViewToChange = (TextView) findViewById(R.id.labelGame);
            textViewToChange.setText("Game Draw");
        }*/
    }


    public void disableAllButtons(){
        for(int i=0;i<9;i++) {
            tttButton[i]= (TTTButton) findViewById(R.id.firstBtn+i);
            tttButton[i].setEnabled(false);
        }
    }
}
