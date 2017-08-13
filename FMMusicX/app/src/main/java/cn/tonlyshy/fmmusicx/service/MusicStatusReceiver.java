package cn.tonlyshy.fmmusicx.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import cn.tonlyshy.fmmusicx.modules.bean.MusicInfo;
import cn.tonlyshy.fmmusicx.modules.bean.MusicStatus;

public class MusicStatusReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (intent != null) {
            onReceive(intent.getParcelableExtra(MusicService.MUSIC_STATUS));
            onReceiveInfo(intent.getParcelableExtra(MusicService.MUSIC_INFO));
        }
    }

    public void onReceive(MusicStatus status) {
        //LogManager.d("status = [%s]", status);
    }

    public void onReceiveInfo(MusicInfo info) {
        //LogManager.d("info = [%s]", info);
    }
}
