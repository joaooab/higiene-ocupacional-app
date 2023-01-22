package br.com.joaoov.ext

import br.com.joaoov.BuildConfig

fun adInterstitialId() =
    if (BuildConfig.DEBUG) BuildConfig.AD_INTERSTITIAL_TEST else BuildConfig.AD_INTERSTITIAL

fun adBannerId() =
    if (BuildConfig.DEBUG) BuildConfig.AD_BANNER_TEST else BuildConfig.AD_BANNER