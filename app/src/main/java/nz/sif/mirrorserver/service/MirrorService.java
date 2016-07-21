package nz.sif.mirrorserver.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import nz.sif.mirrorserver.messaging.MessageBase;

/**
 * Created by simon on 13/07/16.
 */
public class MirrorService extends Service {
    private static boolean isStarted;
    private Thread listenerThread;
    private static Queue<MessageBase> messageQueue;

    public MirrorService() {
        messageQueue = new ConcurrentLinkedQueue<MessageBase>();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Starting service", Toast.LENGTH_LONG).show();
        isStarted = true;

        startListenerThread();

        return super.onStartCommand(intent, flags, startId);
    }

    public static boolean getIsStarted() {
        return isStarted;
    }

    public static void stopService() {
        isStarted = false;
    }

    public Queue<MessageBase> getMessageQueue() { return this.messageQueue; }

    public void startListenerThread() {
        this.listenerThread = new Thread(new MirrorServiceListener(this));
        this.listenerThread.start();
    }

    public static void postMessage(MessageBase message) {
        messageQueue.add(message);
    }
}
