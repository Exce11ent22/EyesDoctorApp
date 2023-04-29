package com.vsu.eyesdoctorapp.service.map

import android.webkit.GeolocationPermissions
import android.webkit.WebChromeClient

class CustomWebChromeClient : WebChromeClient() {

    override fun onGeolocationPermissionsShowPrompt(
        origin: String?,
        callback: GeolocationPermissions.Callback?
    ) {
        callback?.invoke(origin, true, false)
    }

}