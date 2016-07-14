package nz.sif.mirrorserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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

    public void startListenerThread() {
        this.listenerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (MirrorService.getIsStarted()) {
                    try {
                        Log.i("MirrorService", "Listening on port 9011");
                        ServerSocket serverSocket = new ServerSocket(9011);
                        Socket clientSocket = serverSocket.accept();
                        Log.i("MirrorService", "Socket connected");

                        OutputStream outputStream = clientSocket.getOutputStream();

                        while (getIsStarted()) {
                            if (messageQueue.peek() == null) {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException iex) {
                                }
                                continue;
                            }

                            MessageBase message = messageQueue.remove();
                            Log.i("MirrorService", "message found, sending");

                            byte[] bytes = SerializationUtils.serialize(message);
                            outputStream.write(bytes);
                            outputStream.flush();
                        }

                        serverSocket.close();
                        Log.i("MirrorService", "Socket Closed");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        this.listenerThread.start();
    }

    public static void postMessage(MessageBase message) {
        messageQueue.add(message);
    }
}
