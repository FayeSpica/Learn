package cn.tonlyshy.fmmusicx.modules.music.remotemusic.view;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cn.tonlyshy.fmmusicx.R;
import cn.tonlyshy.fmmusicx.base.LogManager;
import cn.tonlyshy.fmmusicx.base.ToastManager;
import cn.tonlyshy.fmmusicx.databinding.FragmentRemoteMusicListBinding;
import cn.tonlyshy.fmmusicx.modules.apaters.MusicItemAdapter;
import cn.tonlyshy.fmmusicx.modules.main.presenter.IMusicLoad;
import cn.tonlyshy.fmmusicx.modules.main.presenter.MusicListPresenter;
import cn.tonlyshy.fmmusicx.music.PBMusic;

/**
 * A simple {@link Fragment} subclass.
 */
public class RemoteMusicListFragment extends Fragment implements IMusicLoad.RemoteMusicView {
    FragmentRemoteMusicListBinding binding;

    MusicListPresenter mPresenter;
    MusicItemAdapter musicItemAdapter;
    SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_remote_music_list, container, false);
        initView();
        initListener();
        initData();
        return binding.getRoot();
    }

    private void initView() {
        binding.rvMusicList.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initData() {
        mPresenter = new MusicListPresenter(getContext(), null, this);
        musicItemAdapter = new MusicItemAdapter(getContext());
        binding.rvMusicList.setAdapter(musicItemAdapter);
        mPresenter.loadMusicFormRemote(0, 5000);
    }

    private void initListener() {
        refreshLayout = binding.srlMusicList;
        refreshLayout.setOnRefreshListener(() -> {
            mPresenter.loadMusicFormRemote(0, 5000);
        });
    }

    public RemoteMusicListFragment() {
        super();
    }

    @Override
    public void onLoadRemoteMusicStart() {

    }

    @Override
    public void onLoadRemoteMusicSuccess(List<PBMusic> mList) {
        LogManager.d("onLoadRemoteMusicSuccess size = [%s]", mList.size());
        musicItemAdapter.setMusicList(mList);
        musicItemAdapter.notifyDataSetChanged();
        if (refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
            ToastManager.show(getContext(), "更新成功，共%s首", mList.size());
        }
    }

    @Override
    public void onLoadRemoteMusicFail() {

    }
}
