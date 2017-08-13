package cn.tonlyshy.fmmusicx.modules.main.presenter;

import java.util.List;

import cn.tonlyshy.fmmusicx.music.PBMusic;

/**
 * Created by liaowm5 on 2017/7/27.
 */

public interface IMusicLoad {
    abstract class Presenter {
        public abstract void loadMusicFormLocal(int offset, int limit);

        public abstract void loadMusicFormRemote(int offset,int limit);
    }

    interface LocalMusicView {
        void onLoadLocalMusicStart();

        void onLoadLocalMusicSuccess(List<PBMusic> mList);

        void onLoadLocalMusicFail();
    }

    interface RemoteMusicView {
        void onLoadRemoteMusicStart();

        void onLoadRemoteMusicSuccess(List<PBMusic> mList);

        void onLoadRemoteMusicFail();
    }

}
