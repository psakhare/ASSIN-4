package edu.cse523.psakhare.tttgameusingmessagingprotocol;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Priyanka on 11/12/2017.
 */

public class InviteToPlay extends AppCompatActivity{
    TextView textView = null;
    TextView pNumber = null, mText =null ;
    Button smsBtn = null;
    BroadcastReceiver br=null;
    IntentFilter intentFilter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");

    String msg="";
    String num="";
    String number=null;
    String text=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invitetoplay);

        textView = (TextView) findViewById(R.id.receivedMsgInvite);

        pNumber = (TextView)findViewById(R.id.enterPhoneNo);
        //mText = (TextView)findViewById(R.id.dispMsg);

        smsBtn =(Button)findViewById(R.id.sendBtn);

        smsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String msg = messageText.getText().toString();
                msg = "TTTGame INVITE PLAYER1";
                num = pNumber.getText().toString();
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(num,null,msg,null,null);
            }
        });

        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

                    for (SmsMessage m : messages) {
                        number = m.getDisplayOriginatingAddress();
                        text = m.getDisplayMessageBody();
                    }
                    textView.setText(text);
                    if ((text.contains("TTTGame") && (text.contains("ACCEPTED")))) {
                        Intent i = new Intent(context, gameMainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        i.putExtra("PlayerName","PLAYER1");
                        startActivity(i);
                    }
                }
            }
        };
        registerReceiver(br,intentFilter);
    }
}