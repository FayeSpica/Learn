package cn.tonlyshy.fmmusicx.modules.main.presenter;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.tonlyshy.fmmusicx.base.LogManager;
import cn.tonlyshy.fmmusicx.music.PBMusic;
import cn.tonlyshy.fmmusicx.util.MusicGetter;

/**
 * Created by liaowm5 on 2017/7/27.
 */

public class MusicListPresenter extends IMusicLoad.Presenter {
    IMusicLoad.LocalMusicView mView;
    IMusicLoad.RemoteMusicView mRemoteView;
    Context mContext;
    List<PBMusic> mList;

    public MusicListPresenter(Context mContext, IMusicLoad.LocalMusicView mView, IMusicLoad.RemoteMusicView mRemoteView) {
        this.mView = mView;
        this.mRemoteView = mRemoteView;
        this.mContext = mContext;
        mList = new ArrayList<>();
    }

    @Override
    public void loadMusicFormLocal(int offset, int limit) {
        mView.onLoadLocalMusicStart();
        try {
            mList = new ArrayList<>();
            Cursor cursor = mContext.getContentResolver().query(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, null, "duration > 60000", null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            getMusicInfo(mList, cursor);
            cursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, "duration > 60000", null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
            getMusicInfo(mList, cursor);
            mView.onLoadLocalMusicSuccess(mList);
        } catch (Exception e) {
            e.printStackTrace();
            mView.onLoadLocalMusicFail();
        }
    }

    protected void getMusicInfo(List<PBMusic> mList, Cursor cursor) {
        int c = 0;
        while (cursor.moveToNext()) {
            c++;
            PBMusic.Builder builder = PBMusic.newBuilder();
            String id = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            String duration = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
            String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
            String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
            //get imageUri
            String albumUri = "content://media/external/audio/albumart/" + cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
            builder.setId(id)
                    .setTitle(title)
                    .setDuration(duration)
                    .setArtist(artist)
                    .setAlbum(album)
                    .setPath(path)
                    .setAlbumUri(albumUri);
            mList.add(builder.build());
            Log.d("hint", (cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE))) + "" + cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)));
        }
    }

    @Override
    public void loadMusicFormRemote(int offset, int limit) {
        RemoteTask task = new RemoteTask();
        task.execute();
    }

    private class RemoteTask extends AsyncTask<Integer, Integer, List<PBMusic>> {
        @Override
        protected List<PBMusic> doInBackground(Integer... integers) {
            return MusicGetter.get();
        }

        @Override
        protected void onPostExecute(List<PBMusic> pbMusics) {
            if(pbMusics!=null) {
                mRemoteView.onLoadRemoteMusicSuccess(pbMusics);
            }else{
                LogManager.d("onPostExecute pbMusics == null");
            }
        }
    }
}
