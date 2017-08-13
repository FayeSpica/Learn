package cn.tonlyshy.fmmusicx.manager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import cn.tonlyshy.fmmusicx.modules.bean.MusicInfo;
import cn.tonlyshy.fmmusicx.music.PBMusic;
import cn.tonlyshy.fmmusicx.service.MusicService;

/**
 * Created by lxx on 2017/8/10.
 */

public class MusicManager {
    private volatile static MusicManager singleton;

    private List<PBMusic> musicList;
    private ArrayList<MusicInfo> serviceMusicList;

    private Context context;

    private MusicManager(Context context) {
        if (this.context == null)
            this.context = context.getApplicationContext();
        musicList = new ArrayList<>();
        serviceMusicList = new ArrayList<>();
    }

    public static MusicManager get(Context context) {
        if (singleton == null) {
            synchronized (MusicManager.class) {
                if (singleton == null) {
                    singleton = new MusicManager(context);
                }
            }
        }
        return singleton;
    }

    public List<PBMusic> getMusicList() {
        return musicList;
    }

    public void setMusicList(List<PBMusic> musicList) {
        this.musicList = musicList;
        serviceMusicList = new ArrayList<>();
        for (PBMusic music : musicList) {
            serviceMusicList.add(new MusicInfo(music));
        }
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra(MusicService.TYPE, MusicService.ACTION_MUSIC_LIST);
        intent.putParcelableArrayListExtra(MusicService.MUSIC_LIST, serviceMusicList);
        context.startService(intent);
    }

    public void musicPlayAt(int position) {
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra(MusicService.TYPE, MusicService.ACTION_MUSIC_PLAY_POSITION);
        intent.putExtra(MusicService.MUSIC_PLAY_POSITION, position);
        context.startService(intent);
    }

    public void musicPlay() {
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra(MusicService.TYPE, MusicService.ACTION_MUSIC_PLAY);
        context.startService(intent);
    }

    public void musicPause() {
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra(MusicService.TYPE, MusicService.ACTION_MUSIC_PAUSE);
        context.startService(intent);
    }

    public void musicPlayPause() {
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra(MusicService.TYPE, MusicService.ACTION_MUSIC_PLAYPAUSE);
        context.startService(intent);
    }

    public void musicStop() {
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra(MusicService.TYPE, MusicService.ACTION_MUSIC_STOP);
        context.startService(intent);
    }

    public void musicNext() {
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra(MusicService.TYPE, MusicService.ACTION_MUSIC_NEXT);
        context.startService(intent);
    }

    public void musicPre() {
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra(MusicService.TYPE, MusicService.ACTION_MUSIC_PRE);
        context.startService(intent);
    }

    public void musicChangeMode() {
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra(MusicService.TYPE, MusicService.ACTION_MUSIC_CHANGE_PLAYMODE);
        context.startService(intent);
    }

    public void musicSeekTo(int position) {
        Intent intent = new Intent(context, MusicService.class);
        intent.putExtra(MusicService.MUSIC_SEEKTO, position);
        intent.putExtra(MusicService.TYPE, MusicService.ACTION_MUSIC_SEEKTO);
        context.startService(intent);
    }

    public Uri getAlbumUri(int index) {
        return Uri.parse(musicList.get(index).getAlbumUri());
    }
}
