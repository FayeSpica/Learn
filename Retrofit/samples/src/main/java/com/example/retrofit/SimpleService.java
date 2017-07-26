/*
 * Copyright (C) 2012 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.retrofit;

import java.io.IOException;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public final class SimpleService {
    public static final String API_URL = "https://api.github.com";

    public static class Contributor {
        public final String login;
        public final int contributions;

        public Contributor(String login, int contributions) {
            this.login = login;
            this.contributions = contributions;
        }
    }

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
            }
        }
    }

    public interface GitHub {
        @GET("/repos/{owner}/{repo}/contributors")
        Call<List<Contributor>> contributors(
                @Path("owner") String owner,
                @Path("repo") String repo);
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


    public static void main(String... args) throws IOException {
//        // Create a very simple REST adapter which points the GitHub API.
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(API_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        // Create an instance of our GitHub API interface.
//        GitHub github = retrofit.create(GitHub.class);
//
//        // Create a call instance for looking up Retrofit contributors.
//        Call<List<Contributor>> call = github.contributors("square", "retrofit");
//
//        // Fetch and print a list of the contributors to the library.
//        List<Contributor> contributors = call.execute().body();
//        for (Contributor contributor : contributors) {
//            System.out.println(contributor.login + " (" + contributor.contributions + ")");
//        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_NET_EASE_MUSIC_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetEaseMusic netEaseMusic = retrofit.create(NetEaseMusic.class);


        String musicId = "4330336";
        Call<MusicDetail> call = netEaseMusic.musicDetail(musicId, String.format("[%s]", musicId));
        MusicDetail musicDetail = call.execute().body();

        System.out.println(String.format("name = [%s]", musicDetail.baseInfoList.get(0).name));


        String playlistId = "716389650";
        Call<PlaylistDetail> playlistDetailCall = netEaseMusic.playlistDetail(playlistId);
        PlaylistDetail playlistDetail = playlistDetailCall.execute().body();

        System.out.println(String.format("listName = [%s] trackCount = [%s] playCount = [%s]", playlistDetail.result.name, playlistDetail.result.trackCount, playlistDetail.result.playCount));

        for (PlaylistDetail.Result.Music music : playlistDetail.result.tracks) {
            System.out.println(String.format("%s", music.name));
        }
    }
}
