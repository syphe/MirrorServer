package nz.sif.mirrorserver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import nz.sif.mirrorserver.messaging.MessageBase;

public class MainActivity
        extends AppCompatActivity
        implements View.OnClickListener {
    private Button startStopServerButton;
    private Button testSendMessageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.startStopServerButton = (Button)this.findViewById(R.id.startStopServer);
        this.startStopServerButton.setOnClickListener(this);

        this.testSendMessageButton = (Button)this.findViewById(R.id.testSendMessage);
        this.testSendMessageButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == this.startStopServerButton) {
            Intent intent = new Intent(this, MirrorService.class);
            if (!MirrorService.getIsStarted()) {
                startService(intent);
            } else {
                MirrorService.stopService();
                stopService(intent);
            }
        } else if (v == this.testSendMessageButton) {
            MessageBase message = new MessageBase();
            message.messageType = "TestMessageType";
            MirrorService.postMessage(message);
        }
    }
}
