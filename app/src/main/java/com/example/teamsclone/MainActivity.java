package com.example.teamsclone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn=(Button)findViewById(R.id.bt1);
        Button btn_copy=(Button)findViewById(R.id.copyID);
        EditText t=(EditText)findViewById(R.id.conferenceName);
        ImageView iv=findViewById(R.id.imageID);
        iv.setImageResource(R.drawable.app_image);
        try {
            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL(""))
                    .setWelcomePageEnabled(false)
                    //.setFeatureFlag("invite.enabled",false)
                    .build();

            JitsiMeet.setDefaultConferenceOptions(options);

            // JitsiMeetConferenceOptions.Builder builder =   new JitsiMeetConferenceOptions.Builder();
            //builder.setFeatureFlag("invite.enabled",false);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Uri uri = getIntent().getData();
        if (uri != null) {
            String param = uri.getLastPathSegment();
            t.setText(param);
        }

        btn_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable tex=t.getText();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copied string", "www.teamsclonemeet.com/"+tex);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(MainActivity.this,"Link copied to clipboard",Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void onButtonClick(View v) {
        EditText editText = findViewById(R.id.conferenceName);
        String text = editText.getText().toString();

        /*ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("copied string", "www.jitsiexpmeet.com/"+text);
        clipboard.setPrimaryClip(clip);*/

        if (text.length() > 0) {
            JitsiMeetConferenceOptions options
                    = new JitsiMeetConferenceOptions.Builder()
                    .setRoom(text)
                    .setFeatureFlag("invite.enabled",false)
                    .build();
            JitsiMeetActivity.launch(this, options);
        }
    }

}