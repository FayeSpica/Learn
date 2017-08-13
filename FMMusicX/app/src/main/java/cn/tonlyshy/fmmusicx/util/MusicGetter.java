package cn.tonlyshy.fmmusicx.util;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.tonlyshy.fmmusicx.music.PBMusic;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lxx on 2017/8/11.
 */

public class MusicGetter {
    public static class MusicDetail {
        @SerializedName("songs")
        public final List<BaseInfo> baseInfoList;

        public MusicDetail(List<BaseInfo> baseInfoList) {
            this.baseInfoList = baseInfoList;
        }

        class BaseInfo {
            @SerializedName("name")
            public String name;
            @SerializedName("id")
            public String id;

            BaseInfo(BaseInfo baseInfo) {
                name = baseInfo.name;
            }
        }
    }

    public static class PlaylistDetail {

        @SerializedName("result")
        public Result result;

        public class Result {
            @SerializedName("name")
            public String name;
            @SerializedName("trackCount")
            public String trackCount;

            @SerializedName("playCount")
            public String playCount;

            @SerializedName("tracks")
            public List<Music> tracks;

            public class Music {
                @SerializedName("name")
                public String name;
                @SerializedName("id")
                public String id;
                @SerializedName("artists")
                public List<Artist> artists;
                @SerializedName("album")
                public Album album;
            }

            public class Artist {
                @SerializedName("name")
                public String name;
            }

            public class Album {
                @SerializedName("name")
                public String name;
                @SerializedName("picUrl")
                public String picUrl;
                @SerializedName("blurPicUrl")
                public String blurPicUrl;
            }
        }
    }

    public static final String API_NET_EASE_MUSIC_URL = "http://music.163.com";

    public interface NetEaseMusic {
        @GET("/api/song/detail/")
        Call<MusicDetail> musicDetail(
                @Query("id") String id,
                @Query("ids") String ids
        );

        @GET("/api/playlist/detail")
        Call<PlaylistDetail> playlistDetail(
                @Query("id") String palylistId
        );
    }


    public static List<PBMusic> get() {
//        // Create a very simple REST adapter which points the GitHub API.
        try {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_NET_EASE_MUSIC_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            NetEaseMusic netEaseMusic = retrofit.create(NetEaseMusic.class);


            String musicId = "4330336";
            Call<MusicDetail> call = netEaseMusic.musicDetail(musicId, String.format("[%s]", musicId));
            MusicDetail musicDetail = call.execute().body();

            System.out.println(String.format("name = [%s] id = [%s]", musicDetail.baseInfoList.get(0).name, musicDetail.baseInfoList.get(0).id));


            String playlistId = "716389650";
            Call<PlaylistDetail> playlistDetailCall = netEaseMusic.playlistDetail(playlistId);
            PlaylistDetail playlistDetail = playlistDetailCall.execute().body();

            System.out.println(String.format("listName = [%s] trackCount = [%s] playCount = [%s]", playlistDetail.result.name, playlistDetail.result.trackCount, playlistDetail.result.playCount));

            List<PBMusic> pbMusicList = new ArrayList<>();
            PBMusic.Builder builder = PBMusic.newBuilder();
            for (PlaylistDetail.Result.Music music : playlistDetail.result.tracks) {
                System.out.println(String.format("id = [%s] name = [%s] url = [%s]", music.id, music.name, music.album.picUrl));
                builder.setArtist(music.artists.get(0).name);
                builder.setAlbumUri(music.album.blurPicUrl);
                builder.setTitle(music.name);
                builder.setId(music.id);
                pbMusicList.add(builder.build());
            }
            return pbMusicList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
