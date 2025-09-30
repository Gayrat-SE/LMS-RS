package com.mycompany.lmsrs

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
// Import our custom WebView permission manager
// Note: This import will be resolved when the file is in the same package

class MainActivity: FlutterActivity() {
    private val PERMISSION_REQUEST_CODE = 1001
    private val CHANNEL = "com.mycompany.lmsrs/webview"
    private lateinit var webViewPermissionManager: WebViewPermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize WebView permission manager
        webViewPermissionManager = WebViewPermissionManager(this)

        // Enable WebView debugging
        WebView.setWebContentsDebuggingEnabled(true)

        requestMicrophonePermission()
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            when (call.method) {
                "configureWebView" -> {
                    configureWebViewPermissions()
                    result.success(true)
                }
                else -> result.notImplemented()
            }
        }
    }

    private fun configureWebViewPermissions() {
        // Configure WebView to handle permissions properly
        WebView.setWebContentsDebuggingEnabled(true)

        // Set up WebView permission handling
        // This will be used by webviewx_plus plugin
    }

    private fun requestMicrophonePermission() {
        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS
        )

        val permissionsToRequest = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            val allPermissionsGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }
            if (!allPermissionsGranted) {
                // Handle permission denial if needed
                // You might want to show a dialog explaining why the permission is needed
            }
        }
    }
}
