package nz.sif.mirrorserver.viewmodel;

import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

/**
 * Created by SimonFi on 14/07/2016.
 */
public class MainActivityViewModel {
    private ObservableArrayList<Object> notifications = new ObservableArrayList<Object>();

    public ObservableField<Boolean> startServerEnabled = new ObservableField<Boolean>(false);
    public ObservableField<Boolean> stopServerEnabled = new ObservableField<Boolean>(false);
    public ObservableField<Boolean> sendMessageEnabled = new ObservableField<Boolean>(false);

    public void startServer() {

    }

    public void stopServer() {

    }

    public void sendTestMessage() {
        this.notifications.add("This is a test message");
    }

    public ObservableArrayList<Object> getNotifications() {
        return this.notifications;
    }
}
