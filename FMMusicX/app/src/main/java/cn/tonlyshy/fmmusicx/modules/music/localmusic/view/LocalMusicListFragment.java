package cn.tonlyshy.fmmusicx.modules.music.localmusic.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.tonlyshy.fmmusicx.R;
import cn.tonlyshy.fmmusicx.base.ToastManager;
import cn.tonlyshy.fmmusicx.databinding.FragmentLocalMusicListBinding;
import cn.tonlyshy.fmmusicx.manager.MusicManager;
import cn.tonlyshy.fmmusicx.modules.apaters.MusicItemAdapter;
import cn.tonlyshy.fmmusicx.modules.main.presenter.IMusicLoad;
import cn.tonlyshy.fmmusicx.modules.main.presenter.MusicListPresenter;
import cn.tonlyshy.fmmusicx.music.PBMusic;

/**
 * Created by lxx on 2017/8/10.
 */
public class LocalMusicListFragment extends Fragment implements IMusicLoad.LocalMusicView {
    public static String TAG = "LocalMusicListFragment";

    FragmentLocalMusicListBinding binding;
    MusicListPresenter mPresenter;
    MusicItemAdapter musicItemAdapter;
    SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_local_music_list, container, false);
        initView();
        initListener();
        initData();
        return binding.getRoot();
    }

    private void initView() {
        binding.rvMusicList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initData() {
        mPresenter = new MusicListPresenter(getContext(), this, null);
        musicItemAdapter = new MusicItemAdapter(getContext());
        binding.rvMusicList.setAdapter(musicItemAdapter);
        mPresenter.loadMusicFormLocal(0, 5000);
    }

    private void initListener() {
        refreshLayout = binding.srlMusicList;
        refreshLayout.setOnRefreshListener(() -> {
            mPresenter.loadMusicFormLocal(0, 5000);
        });
    }

    @Override
    public void onLoadLocalMusicStart() {

    }

    @Override
    public void onLoadLocalMusicSuccess(List<PBMusic> mList) {
        Log.d(TAG, "onLoadLocalMusicSuccess: mList.size=" + mList.size());
        MusicManager.get(getContext()).setMusicList(mList);
        musicItemAdapter.setMusicList(mList);
        musicItemAdapter.notifyDataSetChanged();
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
            ToastManager.show(getContext(), "更新成功，共%s首", mList.size());
        }
    }

    @Override
    public void onLoadLocalMusicFail() {
        refreshLayout.setRefreshing(false);
        ToastManager.show(getContext(), "加载失败");
    }
}
