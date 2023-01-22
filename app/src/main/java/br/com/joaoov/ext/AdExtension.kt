package br.com.joaoov.ext

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import br.com.joaoov.BuildConfig
import br.com.joaoov.ui.billing.BillingViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView

fun adInterstitialId() =
    if (BuildConfig.DEBUG) BuildConfig.AD_INTERSTITIAL_TEST else BuildConfig.AD_INTERSTITIAL

fun adBannerId() =
    if (BuildConfig.DEBUG) BuildConfig.AD_BANNER_TEST else BuildConfig.AD_BANNER

fun Fragment.requestAd(viewModel: BillingViewModel, onShow: (AdView) -> Unit) {
    if (viewModel.shouldHideAd()) return
    val adView = createAd()
    onShow(adView)

    lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            super.onDestroy(owner)
            adView.destroy()
        }
    })
}

@SuppressLint("MissingPermission")
private fun Fragment.createAd() = AdView(requireContext()).apply {
    setAdSize(AdSize.FULL_BANNER)
    adUnitId = adBannerId()
    loadAd(AdRequest.Builder().build())
}
