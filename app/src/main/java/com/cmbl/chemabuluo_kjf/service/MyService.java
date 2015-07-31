package com.cmbl.chemabuluo_kjf.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.KJLoger;

import java.util.logging.LogRecord;

public class MyService extends Service {
    private Thread mThread;
    private boolean flag = true;
    private int i = 0;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        KJLoger.state(this.getClass().getName(), "Service --------- onCreate ");
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (flag) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                    KJLoger.state(this.getClass().getName(), "Service --------- println " + i);
                    myHandler.sendEmptyMessage(1);
                }
            }
        });
        mThread.start();
    }

    private Handler myHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            ViewInject.toast(MyService.this, "service");
            return false;
        }
    });

    @Override
    public void onDestroy() {
        super.onDestroy();

        KJLoger.state(this.getClass().getName(), "Service --------- onDestroy ");
        flag = false;
        mThread.interrupt();
    }
}
