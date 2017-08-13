package cn.tonlyshy.fmmusicx.modules.apaters;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.tonlyshy.fmmusicx.R;
import cn.tonlyshy.fmmusicx.manager.MusicManager;
import cn.tonlyshy.fmmusicx.music.PBMusic;
import cn.tonlyshy.fmmusicx.util.ImageLoader;

/**
 * Created by lxx on 2017/8/10.
 */

public class MusicItemAdapter extends RecyclerView.Adapter<MusicItemAdapter.MusicViewHolder> {
    public static final int TYPE_MAIN = 0;
    public static final int TYPE_MUSIC = 1;
    int type;
    private Context context;
    private List<PBMusic> musicList;

    public MusicItemAdapter(Context context) {
        this.context = context;
        musicList = new ArrayList<>();
    }

    public MusicItemAdapter(Context context, List<PBMusic> musicList) {
        this.context = context;
        this.musicList = musicList;
    }

    public MusicItemAdapter(Context context, List<PBMusic> musicList, int type) {
        this.context = context;
        this.musicList = musicList;
        this.type = type;
    }

    @Override
    public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MusicViewHolder holder = new MusicViewHolder(LayoutInflater.from(context).inflate(R.layout.list_music_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MusicViewHolder holder, int position) {
        holder.title.setText(musicList.get(position).getTitle());
        holder.artistAlbum.setText(musicList.get(position).getArtist());
        if (type == TYPE_MUSIC) {
            holder.title.setTextColor(Color.WHITE);
            holder.artistAlbum.setTextColor(Color.WHITE);
        }
        ImageLoader.load(context, Uri.parse(musicList.get(position).getAlbumUri()), holder.img);
        holder.parent.setOnClickListener(v -> {
            MusicManager.get(context).musicPlayAt(position);
        });
        //Log.d("TAG", String.format("onBindViewHolder position = [%s]",position,musicList.get(position).getTitle(),musicList.get(position).getTitle()));
    }

    @Override
    public int getItemCount() {
        return musicList.size();
    }

    public class MusicViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public ImageButton more;
        public TextView title, artistAlbum;
        public View parent;

        public MusicViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.iv_music);
            more = (ImageButton) itemView.findViewById(R.id.ibtn_more);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            artistAlbum = (TextView) itemView.findViewById(R.id.tv_artist);
            parent = itemView.findViewById(R.id.ll_content);
        }
    }

    public void setMusicList(List<PBMusic> musicList) {
        this.musicList = musicList;
    }
}
