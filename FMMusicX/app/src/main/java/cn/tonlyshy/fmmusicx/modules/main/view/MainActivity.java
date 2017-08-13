package cn.tonlyshy.fmmusicx.modules.main.view;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.tonlyshy.fmmusicx.R;
import cn.tonlyshy.fmmusicx.base.LogManager;
import cn.tonlyshy.fmmusicx.databinding.ActivityMainBinding;
import cn.tonlyshy.fmmusicx.manager.MusicManager;
import cn.tonlyshy.fmmusicx.modules.apaters.MainFragmentAdapter;
import cn.tonlyshy.fmmusicx.modules.bean.MusicInfo;
import cn.tonlyshy.fmmusicx.modules.bean.MusicStatus;
import cn.tonlyshy.fmmusicx.modules.music.localmusic.view.LocalMusicListFragment;
import cn.tonlyshy.fmmusicx.modules.music.remotemusic.view.RemoteMusicListFragment;
import cn.tonlyshy.fmmusicx.service.MusicService;
import cn.tonlyshy.fmmusicx.service.MusicStatusReceiver;
import cn.tonlyshy.fmmusicx.util.ImageLoader;

/**
 * Created by lxx on 2017/8/10.
 */
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    MainFragmentAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        setSupportActionBar(binding.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        if (permissionCheck()) {
            UIStart();
        }
    }

    private void initData() {

    }

    private void initListener() {
        binding.ivControl.setOnClickListener(v -> {
            MusicManager.get(this).musicPlayPause();
        });
        binding.llActionMusicActivity.setOnClickListener(v -> {
            Intent intent = new Intent(this, MusicActivity.class);
            startActivity(intent);
        });
    }

    /**
     * 双击返回桌面
     */
    private long times = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - times > 1000)) {
                Toast.makeText(this, "再按一次返回桌面", Toast.LENGTH_SHORT).show();
                times = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                this.finish();
            }
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }

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
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        fragments.add(new LocalMusicListFragment());
        fragments.add(new RemoteMusicListFragment());

        titles.add("本地音乐");
        titles.add("云音乐");
        mAdapter = new MainFragmentAdapter(getSupportFragmentManager(), fragments, titles);

        binding.vpContent.setAdapter(mAdapter);
        binding.vpContent.setCurrentItem(0);
        initTab();
    }

    private void initTab() {
        binding.tlTitle.setTabMode(TabLayout.MODE_FIXED);
        binding.tlTitle.setupWithViewPager(binding.vpContent);
//        LinearLayout sp = (LinearLayout) binding.tlTitle.getChildAt(0);
//        sp.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
//        sp.setDividerDrawable();
//        sp.setDividerPadding(dp2px(15));
        setSupportActionBar(binding.toolbar);
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
                binding.pbBar.setMax(status.getDuration());
                binding.pbBar.setProgress(status.getPosition());
                if (status.getIsPlaying() == 0)
                    binding.ivControl.setImageDrawable(getResources().getDrawable(R.drawable.play_btn));
                else
                    binding.ivControl.setImageDrawable(getResources().getDrawable(R.drawable.pause_btn));
            }
        }

        @Override
        public void onReceiveInfo(MusicInfo info) {
            if (info != null && onReceiveInfoIndex != info.getIndex()) {
                onReceiveInfoIndex = info.getIndex();
                ImageLoader.load(getApplicationContext(), Uri.parse(info.getAlbumUri()), binding.ivPlaybarImg);
                binding.tvPlaybarSinger.setText(info.getArtist());
                binding.tvPlaybarInfo.setText(info.getTitle());
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
}
