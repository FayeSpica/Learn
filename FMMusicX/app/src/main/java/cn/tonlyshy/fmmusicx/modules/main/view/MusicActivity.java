package cn.tonlyshy.fmmusicx.modules.main.view;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.tonlyshy.fmmusicx.R;
import cn.tonlyshy.fmmusicx.base.LogManager;
import cn.tonlyshy.fmmusicx.databinding.ContentMusicImageBinding;
import cn.tonlyshy.fmmusicx.databinding.ContentMusicPlaylistBinding;
import cn.tonlyshy.fmmusicx.databinding.MusicMainBinding;
import cn.tonlyshy.fmmusicx.manager.MusicManager;
import cn.tonlyshy.fmmusicx.modules.apaters.MusicItemAdapter;
import cn.tonlyshy.fmmusicx.modules.bean.MusicInfo;
import cn.tonlyshy.fmmusicx.modules.bean.MusicStatus;
import cn.tonlyshy.fmmusicx.service.MusicService;
import cn.tonlyshy.fmmusicx.service.MusicStatusReceiver;
import cn.tonlyshy.fmmusicx.util.ImageLoader;

/**
 * Created by lxx on 2017/8/10.
 */
public class MusicActivity extends AppCompatActivity {

    MusicMainBinding binding;
    ContentMusicImageBinding musicImageBinding;
    ContentMusicPlaylistBinding musicPlaylistBinding;
    private List<View> vList = new ArrayList<>();
    private HashMap<View, String> vTitle = new HashMap<>();
    private SimpleDateFormat time = new SimpleDateFormat("m:ss");
    MusicItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MusicActivity.this, R.layout.music_main);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        setSupportActionBar(binding.toolbar);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        binding.toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mAdapter = new MusicItemAdapter(this, MusicManager.get(getApplicationContext()).getMusicList(), MusicItemAdapter.TYPE_MUSIC);
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (permissionCheck()) {
            UIStart();
        }
    }

    private void initData() {

    }

    private void initListener() {
        binding.ibPlayPause.setOnClickListener(v->{
            MusicManager.get(getApplicationContext()).musicPlayPause();
        });
        binding.ibNext.setOnClickListener(v -> {
            MusicManager.get(getApplicationContext()).musicNext();
        });
        binding.ibPre.setOnClickListener(v -> {
            MusicManager.get(getApplicationContext()).musicPre();
        });
        binding.ibPlayMode.setOnClickListener(v -> {
            MusicManager.get(getApplicationContext()).musicChangeMode();
        });
    }

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1;

    public boolean permissionCheck() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            return false;
        }
        return true;
    }

    private void UIStart() {
        LayoutInflater inflater = getLayoutInflater();
        musicImageBinding = ContentMusicImageBinding.inflate(inflater);
        musicPlaylistBinding = ContentMusicPlaylistBinding.inflate(inflater);

        musicPlaylistBinding.rvPlayList.setAdapter(mAdapter);
        musicPlaylistBinding.rvPlayList.setLayoutManager(new LinearLayoutManager(this));

        View playingView = musicImageBinding.getRoot();
        View playlistView = musicPlaylistBinding.getRoot();

        vList.add(playingView);
        vList.add(playlistView);
        vTitle.put(playingView, "正在播放");
        vTitle.put(playlistView, "列表");

        ViewPager vp = (ViewPager) findViewById(R.id.vp_content);
        PagerTabStrip pts = (PagerTabStrip) findViewById(R.id.pts_content);

        pts.setTabIndicatorColorResource(R.color.colorPrimary);
        pts.setTextColor(Color.WHITE);
        pts.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        vp.setAdapter(pagerAdapter);
    }

    public int dp2px(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5);
    }

    MusicStatusReceiver musicStatusReceiver = new MusicStatusReceiver() {

        int onReceiveInfoIndex = -1;

        @Override
        public void onReceive(MusicStatus status) {
            if (status != null) {
                //super.onReceive(status);
                LogManager.d("MainActivity status = [%s]", status);
                binding.sbSeekBar.setMax(status.getDuration());
                binding.sbSeekBar.setProgress(status.getPosition());

                if (status.getIsPlaying() == 0)
                    binding.ibPlayPause.setImageDrawable(getResources().getDrawable(R.drawable.play_btn_play_w));
                else
                    binding.ibPlayPause.setImageDrawable(getResources().getDrawable(R.drawable.play_btn_pause_w));
                binding.tvDtime.setText(time.format(status.getPosition()));
                binding.tvLeftTime.setText(time.format(status.getDuration() - status.getPosition()));

                switch (status.getPlaymode()) {
                    case MusicService.PLAYMODE_CIRCLE_LIST:
                        binding.ibPlayMode.setImageResource(R.drawable.play_icn_loop);
                        break;
                    case MusicService.PLAYMODE_RAMDON:
                        binding.ibPlayMode.setImageResource(R.drawable.play_icn_shuffle);
                        break;
                    case MusicService.PLAYMODE_CIRCLE_SINGLE:
                        binding.ibPlayMode.setImageResource(R.drawable.play_icn_one);
                        break;
                }
                binding.sbSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            MusicManager.get(getApplicationContext()).musicSeekTo(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
            }
        }

        @Override
        public void onReceiveInfo(MusicInfo info) {
            if (info != null && onReceiveInfoIndex != info.getIndex()) {
                onReceiveInfoIndex = info.getIndex();
                ImageLoader.load(getApplicationContext(), Uri.parse(info.getAlbumUri()), musicImageBinding.albumImageView);/**/
                binding.toolbar.setTitle(info.getTitle());
                binding.toolbar.setSubtitle(info.getArtist());
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        try {
            IntentFilter filter = new IntentFilter();
            filter.addAction(MusicService.MUSIC_STATUS_INTENT);
            registerReceiver(musicStatusReceiver, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(musicStatusReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }

    PagerAdapter pagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return vList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return vTitle.get(vList.get(position));
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(vList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(vList.get(position));
            return vList.get(position);
        }
    };
}
