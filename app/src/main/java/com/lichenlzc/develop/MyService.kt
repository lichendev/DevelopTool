package com.lichenlzc.develop

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.Parcel
import androidx.annotation.RequiresApi

class MyService : Service() {

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onBind(intent: Intent): IBinder {
        return MyBinder()
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    internal class MyBinder: Binder(){
        override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
            return super.onTransact(code, data, reply, flags)
        }
    }
}

