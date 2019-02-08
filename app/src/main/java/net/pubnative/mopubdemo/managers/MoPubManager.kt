package net.pubnative.mopubdemo.managers

import android.content.Context
import com.mopub.common.MoPub
import com.mopub.common.SdkConfiguration
import com.mopub.common.logging.MoPubLog
import net.pubnative.mopubdemo.models.AdUnit

class MoPubManager {
    interface InitialisationListener {
        fun onInitialisationFinished()
    }

    companion object {
        fun initMoPubSdk(context: Context, adUnit: AdUnit) {
            initMoPubSdk(context, adUnit, null)
        }

        fun initMoPubSdk(context: Context, adUnit: AdUnit, listener: InitialisationListener?) {
            val sdkConfiguration = SdkConfiguration.Builder(adUnit.adUnitId)
                .withLogLevel(MoPubLog.LogLevel.DEBUG)
                .build()

            MoPub.initializeSdk(context, sdkConfiguration) {
                listener?.onInitialisationFinished()
            }
        }
    }
}