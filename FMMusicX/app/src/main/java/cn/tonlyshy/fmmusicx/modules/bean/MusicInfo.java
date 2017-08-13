package cn.tonlyshy.fmmusicx.modules.bean;

import android.os.Parcel;
import android.os.Parcelable;

import cn.tonlyshy.fmmusicx.music.PBMusic;

/**
 * Created by lxx on 2017/8/10.
 */

public class MusicInfo implements Parcelable {
    private int index;
    private String uri;
    private String albumUri;
    private String title;
    private String artist;

    public MusicInfo(PBMusic music) {
        index = -11;
        uri = music.getPath();
        albumUri = music.getAlbumUri();
        title = music.getTitle();
        artist = music.getArtist();
    }

    public MusicInfo(int index, MusicInfo musicInfo) {
        this.index = index;
        uri = musicInfo.getUri();
        albumUri = musicInfo.getAlbumUri();
        title = musicInfo.getTitle();
        artist = musicInfo.getArtist();
    }

    public MusicInfo() {
        super();
    }

    public MusicInfo(Parcel in) {
        index = in.readInt();
        uri = in.readString();
        albumUri = in.readString();
        title = in.readString();
        artist = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(index);
        parcel.writeString(uri);
        parcel.writeString(albumUri);
        parcel.writeString(title);
        parcel.writeString(artist);
    }

    public static final Parcelable.Creator<MusicInfo> CREATOR = new Parcelable.Creator<MusicInfo>() {
        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public MusicInfo createFromParcel(Parcel parcel) {
            return new MusicInfo(parcel);
        }

        @Override
        public MusicInfo[] newArray(int i) {
            return new MusicInfo[i];
        }
    };

    public String getUri() {
        return uri;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbumUri() {
        return albumUri;
    }

    public int getIndex() {
        return index;
    }
}
