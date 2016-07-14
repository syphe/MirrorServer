package nz.sif.mirrorserver;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import nz.sif.mirrorserver.notifications.NotificationsRecyclerViewAdapter;
import nz.sif.mirrorserver.viewmodel.MainActivityViewModel;
import nz.sif.mirrorserver.messaging.MessageBase;

public class MainActivity
        extends AppCompatActivity
        implements View.OnClickListener {
    private Button startStopServerButton;
    private Button testSendMessageButton;
    private RecyclerView notificationsRecyclerView;

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Object binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        this.viewModel = new MainActivityViewModel();

        this.startStopServerButton = (Button)this.findViewById(R.id.startServer);
        this.testSendMessageButton = (Button)this.findViewById(R.id.testSendMessage);
        this.notificationsRecyclerView = (RecyclerView)this.findViewById(R.id.notificationsRecyclerView);

        this.startStopServerButton.setOnClickListener(this);
        this.testSendMessageButton.setOnClickListener(this);
        this.notificationsRecyclerView.setAdapter(new NotificationsRecyclerViewAdapter(this.viewModel.getNotifications()));
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
