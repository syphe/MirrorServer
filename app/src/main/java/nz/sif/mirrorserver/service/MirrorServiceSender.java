package nz.sif.mirrorserver.service;

/**
 * Created by SimonFi on 20/07/2016.
 */
public class MirrorServiceSender implements Runnable {
    private final MirrorService mirrorService;

    public MirrorServiceSender(MirrorService mirrorService) {
        this.mirrorService = mirrorService;
    }
    @Override
    public void run() {

    }
}
