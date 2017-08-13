package cn.tonlyshy.fmmusicx.event;

/**
 * Created by lxx on 2017/8/10.
 */

public class PlayEvent {
    public static final int EVENT_PLAY = 0;
    public static final int EVENT_PAUSE = 1;
    public static final int EVENT_STOP = 2;

    public int event;

    public PlayEvent(int event) {
        this.event = event;
    }
}
