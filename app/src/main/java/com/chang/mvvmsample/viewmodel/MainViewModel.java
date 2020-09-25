package com.chang.mvvmsample.viewmodel;

import com.chang.mvvmsample.model.dao.UserDao;
import com.chang.mvvmsample.model.entity.User;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private UserDao mUserDao;

    //可修改的LiveData
    private MutableLiveData<List<User>> _userList = new MutableLiveData<>();
    //对外暴露一个不可修改的LiveData
    public LiveData<List<User>> userList = _userList;

    //执行线程池 数据库的访问需要在子线程执行
    private ExecutorService mThreadPool = Executors.newSingleThreadExecutor();

    public void getUserList() {
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                List<User> users = mUserDao.getAllUser();
                _userList.setValue(users);
            }
        });
    }

    public void inserUser(final User u, final Callback callback){
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                long id = mUserDao.insertUser(u);
                callback.onInsertUserDone(id);
            }
        });


    }

    public interface Callback{

        void onInsertUserDone(long id);
    }
}
