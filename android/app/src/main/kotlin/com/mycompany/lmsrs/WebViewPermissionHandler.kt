package com.mycompany.lmsrs

import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebSettings

class WebViewPermissionHandler {
    
    companion object {
        fun configureWebView(webView: WebView) {
            val settings = webView.settings
            
            // Enable JavaScript
            settings.javaScriptEnabled = true
            
            // Enable media playback
            settings.mediaPlaybackRequiresUserGesture = false
            
            // Enable DOM storage
            settings.domStorageEnabled = true
            
            // Enable database
            settings.databaseEnabled = true
            
            // Set WebChromeClient to handle permissions
            webView.webChromeClient = object : WebChromeClient() {
                override fun onPermissionRequest(request: PermissionRequest?) {
                    request?.let {
                        // Grant all requested permissions
                        it.grant(it.resources)
                    }
                }
            }
            
            // Set WebViewClient for general navigation
            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    return false
                }
            }
        }
    }
}
