package com.hzy.wanandroid.ui.activity.collect.mycollect;

import android.arch.lifecycle.LifecycleOwner;

import com.hzy.wanandroid.App;
import com.hzy.wanandroid.base.mvp.BasePAV;
import com.hzy.wanandroid.service.ApiService;
import com.hzy.wanandroid.utils.RxSchedulers;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import javax.inject.Inject;

/**
 * Created by hzy on 2019/3/5
 *
 * @author Administrator
 * */
public class MyCollectPresenter extends BasePAV<MyCollectContract.View> implements MyCollectContract.Presenter {

    @Inject
    public MyCollectPresenter() {
    }

    @Override
    public void getData(int page) {
        App.apiService(ApiService.class)
                .getCollect(page)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    mView.updateView(responseBean.getData());
                }, throwable -> {
                    mView.onFail();
                });
    }

    @Override
    public void unCollectArticle(int id, String originId, int position) {
        App.apiService(ApiService.class)
                .myPageUncollect(id, originId)
                .compose(RxSchedulers.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mView)))
                .subscribe(responseBean -> {
                    mView.updateUnCollect(responseBean, position);
                }, throwable -> {
                    mView.onFail();
                });
    }
}
