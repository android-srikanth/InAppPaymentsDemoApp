package com.sri.droid.squareinapppayments

import android.app.Application
import sqip.InAppPaymentsSdk

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        InAppPaymentsSdk.squareApplicationId =  "sqip.sandbox-sq0idb-hvCboyw1xevODTHzpEDv-A\""
     }
}