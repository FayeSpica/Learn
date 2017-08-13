package cn.tonlyshy.fmmusicx.modules.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lxx on 2017/8/10.
 */

public class MusicStatus implements Parcelable {
    private int index;
    private int isPlaying;
    private int duration;
    private int position;
    private int playmode;

    public MusicStatus() {

    }

    public MusicStatus(int index, int isPlaying, int duration, int position, int playmode) {
        this.index = index;
        this.position = position;
        this.isPlaying = isPlaying;
        this.duration = duration;
        this.playmode = playmode;
    }

    public MusicStatus(Parcel in) {
        index = in.readInt();
        isPlaying = in.readInt();
        duration = in.readInt();
        position = in.readInt();
        playmode = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(index);
        dest.writeInt(isPlaying);
        dest.writeInt(duration);
        dest.writeInt(position);
        dest.writeInt(playmode);
    }

    public static final Parcelable.Creator<MusicStatus> CREATOR = new Parcelable.Creator<MusicStatus>() {
        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public MusicStatus createFromParcel(Parcel parcel) {
            return new MusicStatus(parcel);
        }

        @Override
        public MusicStatus[] newArray(int i) {
            return new MusicStatus[i];
        }
    };

    @Override
    public String toString() {
        return String.format("index = [%s] isPlaying = [%s] duration = [%s] position = [%s]", index, isPlaying, duration, position);
    }


    public int getIndex() {
        return index;
    }

    public int getIsPlaying() {
        return isPlaying;
    }


    public int getDuration() {
        return duration;
    }


    public int getPosition() {
        return position;
    }


    public int getPlaymode() {
        return playmode;
    }
}
