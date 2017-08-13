package cn.tonlyshy.fmmusicx.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

import cn.tonlyshy.fmmusicx.base.LogManager;
import cn.tonlyshy.fmmusicx.base.ToastManager;
import cn.tonlyshy.fmmusicx.modules.bean.MusicInfo;
import cn.tonlyshy.fmmusicx.modules.bean.MusicStatus;

public class MusicService extends Service {
    public static final String MUSIC_LIST = "music_list";
    public static final String MUSIC_PLAY_POSITION = "music_play_position";
    public static final String MUSIC_PLAY = "music_play";
    public static final String MUSIC_PAUSE = "music_pause";
    public static final String MUSIC_STOP = "music_stop";
    public static final String MUSIC_SEEKTO = "MUSIC_SEEKTO";
    public static final String TYPE = "type";

    public static final int ACTION_MUSIC_LIST = 0;
    public static final int ACTION_MUSIC_PLAY_POSITION = 1;
    public static final int ACTION_MUSIC_PLAY = 2;
    public static final int ACTION_MUSIC_PAUSE = 3;
    public static final int ACTION_MUSIC_STOP = 4;
    public static final int ACTION_MUSIC_NEXT = 5;
    public static final int ACTION_MUSIC_PRE = 6;
    public static final int ACTION_MUSIC_PLAYPAUSE = 7;
    public static final int ACTION_MUSIC_CHANGE_PLAYMODE = 8;
    public static final int ACTION_MUSIC_SEEKTO = 9;

    public static final int PLAYMODE_CIRCLE_LIST = 0;//1 列表循环 2 随机 3 单曲循环
    public static final int PLAYMODE_CIRCLE_SINGLE = 1;
    public static final int PLAYMODE_RAMDON = 2;

    public static final String MUSIC_STATUS_INTENT = "cn.tonlyshy.fmmusicx.PLAYSTATUS";
    public static final String MUSIC_STATUS = "STATUS";
    public static final String MUSIC_INFO = "INFO";

    private MediaPlayer mediaPlayer = new MediaPlayer();
    private ArrayList<MusicInfo> musicInfoList;
    private int mPosition;

    public int playmode = -1;//1 列表循环 2 随机 3 单曲循环

    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(updateStatusRunable).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int messageType = intent.getIntExtra(TYPE, -1);
        action(messageType, intent);
        //监听音频播放完的代码，实现音频的自动循环播放
        mediaPlayer.setOnCompletionListener(mp -> {
            switch (playmode) {
                case PLAYMODE_CIRCLE_LIST:
                    mp.seekTo(0);
                    nextMusic();
                    break;
                case PLAYMODE_RAMDON:
                    mp.seekTo(0);
                    Random rand = new Random();
                    playAt(rand.nextInt(musicInfoList.size() - 1));
                    break;
                case PLAYMODE_CIRCLE_SINGLE:
                    mp.seekTo(0);
                    mp.start();
                    break;
            }

        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    void action(int messageType, Intent intent) {
        if (messageType == -1)
            return;
        switch (messageType) {
            case ACTION_MUSIC_LIST:
                updateList(intent);
                break;
            case ACTION_MUSIC_PLAY_POSITION:
                actionPlayAt(intent);
                break;
            case ACTION_MUSIC_PLAY:
                play();
                break;
            case ACTION_MUSIC_PAUSE:
                pause();
                break;
            case ACTION_MUSIC_STOP:
                stop();
                break;
            case ACTION_MUSIC_PLAYPAUSE:
                if (isPlaying())
                    pause();
                else
                    play();
                break;
            case ACTION_MUSIC_NEXT:
                nextMusic();
                break;
            case ACTION_MUSIC_PRE:
                preMusic();
                break;
            case ACTION_MUSIC_CHANGE_PLAYMODE:
                changePlayMode();
                break;
            case ACTION_MUSIC_SEEKTO:
                seekTo(intent);
                break;
        }
    }

    private void updateList(Intent intent) {
        musicInfoList = intent.getParcelableArrayListExtra(MUSIC_LIST);
        LogManager.d("ACTION_MUSIC_LIST musicInfoList = [%s]", musicInfoList.size());
    }

    private void actionPlayAt(Intent intent) {
        mPosition = intent.getIntExtra(MUSIC_PLAY_POSITION, -1);
        playAt(mPosition);
    }

    private void playAt(int position) {
        LogManager.d("ACTION_MUSIC_PLAY_POSITION musicInfoList = [%s] playAt = [%s]", musicInfoList.size(), position);
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(musicInfoList.get(position).getUri());
            mediaPlayer.prepare();
            mediaPlayer.start();
            updateStatusDetail();
        } catch (Exception e) {
            ToastManager.show(this, "播放失败");
        }
    }

    boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    private void stop() {
        if (isPlaying())
            mediaPlayer.stop();
        updateStatusDetail();
    }

    private void play() {
        if (!isPlaying())
            mediaPlayer.start();
        updateStatusDetail();
    }

    private void pause() {
        if (isPlaying())
            mediaPlayer.pause();
        updateStatusDetail();
    }

    public void nextMusic() {
        if (mediaPlayer != null && mPosition < musicInfoList.size()) {
            switch (playmode) {
                case 2:
                    Random rand = new Random();
                    mPosition = rand.nextInt(musicInfoList.size() - 1);
                    playAt(mPosition);
                    break;
                default:
                    if (mPosition != musicInfoList.size() - 1)
                        ++mPosition;
                    else
                        mPosition = 0;
                    playAt(mPosition);
                    break;
            }
        }
    }

    public void preMusic() {
        if (mediaPlayer != null && mPosition > 0) {
            switch (playmode) {
                case 2:
                    Random rand = new Random();
                    mPosition = rand.nextInt(musicInfoList.size() - 1);
                    playAt(mPosition);
                    break;
                default:
                    if (mPosition != 0)
                        --mPosition;
                    else
                        mPosition = musicInfoList.size() - 1;
                    playAt(mPosition);
                    break;
            }
        }
    }

    public void changePlayMode() {
        playmode = playmode + 1 > 2 ? 0 : playmode + 1;
    }

    public void seekTo(Intent intent) {
        if (intent.getIntExtra(MUSIC_SEEKTO, -1) != -1) {
            mediaPlayer.seekTo(intent.getIntExtra(MUSIC_SEEKTO, -1));
        }
    }

    public void updateStatus() {
        try {
            Intent intent = new Intent();
            intent.setAction(MUSIC_STATUS_INTENT);
            intent.putExtra(MUSIC_STATUS, new MusicStatus(mPosition, mediaPlayer.isPlaying() ? 1 : 0, mediaPlayer.getDuration(), mediaPlayer.getCurrentPosition(), playmode));
            LogManager.d("updateStatus musicInfoList = [%s]", musicInfoList.size());
            sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStatusDetail() {
        try {
            Intent intent = new Intent();
            intent.setAction(MUSIC_STATUS_INTENT);
            ;
            intent.putExtra(MUSIC_INFO, new MusicInfo(mPosition, musicInfoList.get(mPosition)));
            LogManager.d("updateStatus musicInfoList = [%s]", musicInfoList.size());
            sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Runnable updateStatusRunable = () -> {
        while (true) {
            try {
                updateStatus();
                updateStatusDetail();
                Thread.sleep(200);
            } catch (InterruptedException e) {

            }
        }
    };

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }
}
