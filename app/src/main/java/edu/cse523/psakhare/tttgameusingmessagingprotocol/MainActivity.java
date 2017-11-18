package edu.cse523.psakhare.tttgameusingmessagingprotocol;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Telephony;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
    Button continueBtn = null;
    TextView textView = null;
    //String actionA ="edu.Oakland.brApplication.MSGA" ;                    //shud have in common file
    //String actionB ="edu.Oakland.brApplication.MSGB" ;
    String number=null;
    String text=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.receivedMsg);

        continueBtn = (Button)findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), InviteToPlay.class);
                i.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);       //allow us to send if they are in pause mode
                startActivity(i);
            }
        });

        BroadcastReceiver br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                    SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

                    for (SmsMessage m: messages) {
                        number = m.getDisplayOriginatingAddress();
                        text = m.getDisplayMessageBody();
                    }
                    textView.setText(text);

                    if((text.contains("TTTGame") && (text.contains("INVITE")))){
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.asktoacceptgameinvitation);
                        dialog.setTitle("Custom Alert Dialog");
                        dialog.show();

                        Button okBtn = (Button) dialog.findViewById(R.id.yesButton);
                        okBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                String msg = "TTTGame ACCEPTED PLAYER2";
                                String num = number;
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(num,null,msg,null,null);

                                Intent intent =new Intent(v.getContext(), gameMainActivity.class);
                                intent.putExtra("PlayerName","PLAYER2");
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intent);
                            }
                        });
                        Button noBtn = (Button) dialog.findViewById(R.id.noButton);
                        noBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                String msg = "TTTGame DECLINED PLAYERB";
                                String num = number;
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(num,null,msg,null,null);
                            }
                        });
                    }
                }
            }
        };
        registerReceiver(br,filter);
    }
}
