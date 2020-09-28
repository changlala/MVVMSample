package com.chang.mvvmsample.viewmodel;

import android.os.Handler;
import android.os.Looper;

import com.chang.mvvmsample.model.dao.UserDao;
import com.chang.mvvmsample.model.database.AppDB;
import com.chang.mvvmsample.model.entity.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private UserDao mUserDao ;

    //可修改的LiveData
    private MutableLiveData<List<User>> _userList = new MutableLiveData<>();
    //对外暴露一个不可修改的LiveData
    public LiveData<List<User>> userList = _userList;

    //执行线程池 数据库的访问需要在子线程执行
    private ExecutorService mThreadPool = Executors.newSingleThreadExecutor();

    //延迟到调用时初始化
    private void isDaoInit(){
        if(mUserDao == null)
            mUserDao = AppDB.getInstance().getUserDao();
    }

    public void getUserList() {
        isDaoInit();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                final List<User> users = mUserDao.getAllUser();
                //在子线程更新LiveData需调用postValue
                _userList.postValue(users);
            }
        });
    }

    public void inserUser(final User u, final Callback callback){
        isDaoInit();
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                long id = mUserDao.insertUser(u);
                callback.onInsertUserDone(id);
            }
        });


    }

    public interface Callback{
        //insert完成回调
        void onInsertUserDone(long id);
    }
}
