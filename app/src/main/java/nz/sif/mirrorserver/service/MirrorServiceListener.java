package nz.sif.mirrorserver.service;

import android.util.Log;

import org.apache.commons.lang3.SerializationUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import nz.sif.mirrorserver.messaging.MessageBase;

/**
 * Created by SimonFi on 20/07/2016.
 */
public class MirrorServiceListener implements Runnable {
    private MirrorService mirrorService;
    private Thread senderThread;

    public MirrorServiceListener(MirrorService mirrorService) {
        this.mirrorService = mirrorService;
    }

    @Override
    public void run() {
        while (MirrorService.getIsStarted()) {
            try {
                Log.i("MirrorService", "Listening on port 9011");
                ServerSocket serverSocket = new ServerSocket(9011);
                Socket clientSocket = serverSocket.accept();
                Log.i("MirrorService", "Socket connected");

                // start a sender thread for this connection.
                startSenderThread();

                OutputStream outputStream = clientSocket.getOutputStream();

                while (this.mirrorService.getIsStarted()) {
                    if (this.mirrorService.getMessageQueue().peek() == null) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException iex) {
                        }
                        continue;
                    }

                    MessageBase message = this.mirrorService.getMessageQueue().remove();
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

    private void startSenderThread() {
        this.senderThread = new Thread(new MirrorServiceSender(this.mirrorService));
        this.senderThread.start();
    }
}
