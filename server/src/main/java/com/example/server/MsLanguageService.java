package com.example.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.example.server.ILanguage;

public class MsLanguageService extends Service {
    //private IBinder binder = new LanguageQueryBinder();
    private String[] names = {"python", "php", "java", "kotlin", "c", "swift"};

    private String query(int num) {
        if (num > 0 && num < 6) {
            return names[num - 1];
        }
        return null;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("TAG", "**********执行Server端的OnBind()方法*********");
        return new LanguageQueryBinder();
    }

    private final class LanguageQueryBinder extends ILanguage.Stub {
        @Override
        public String queryLanguage(int num) throws RemoteException {
            Log.d("TAG","**********执行Server端的queryLanguage()方法*********");
            return query(num);

        }
    }
}