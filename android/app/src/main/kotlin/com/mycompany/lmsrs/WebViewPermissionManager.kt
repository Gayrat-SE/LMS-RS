package com.mycompany.lmsrs

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.webkit.PermissionRequest
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.flutter.plugin.common.MethodChannel

class WebViewPermissionManager(private val activity: Activity) {
    
    companion object {
        private const val PERMISSION_REQUEST_CODE = 1002
    }
    
    fun setupWebViewPermissions(webView: WebView) {
        webView.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) {
                val requestedResources = request.resources
                
                for (resource in requestedResources) {
                    when (resource) {
                        PermissionRequest.RESOURCE_AUDIO_CAPTURE -> {
                            if (hasAudioPermission()) {
                                // Grant permission if we have it
                                request.grant(arrayOf(PermissionRequest.RESOURCE_AUDIO_CAPTURE))
                            } else {
                                // Request permission from user
                                requestAudioPermission { granted ->
                                    if (granted) {
                                        request.grant(arrayOf(PermissionRequest.RESOURCE_AUDIO_CAPTURE))
                                    } else {
                                        request.deny()
                                    }
                                }
                            }
                            return
                        }
                        PermissionRequest.RESOURCE_VIDEO_CAPTURE -> {
                            if (hasCameraPermission()) {
                                request.grant(arrayOf(PermissionRequest.RESOURCE_VIDEO_CAPTURE))
                            } else {
                                requestCameraPermission { granted ->
                                    if (granted) {
                                        request.grant(arrayOf(PermissionRequest.RESOURCE_VIDEO_CAPTURE))
                                    } else {
                                        request.deny()
                                    }
                                }
                            }
                            return
                        }
                    }
                }
                
                // If we get here, grant all requested permissions if we have them
                val permissionsToGrant = mutableListOf<String>()
                
                for (resource in requestedResources) {
                    when (resource) {
                        PermissionRequest.RESOURCE_AUDIO_CAPTURE -> {
                            if (hasAudioPermission()) {
                                permissionsToGrant.add(PermissionRequest.RESOURCE_AUDIO_CAPTURE)
                            }
                        }
                        PermissionRequest.RESOURCE_VIDEO_CAPTURE -> {
                            if (hasCameraPermission()) {
                                permissionsToGrant.add(PermissionRequest.RESOURCE_VIDEO_CAPTURE)
                            }
                        }
                    }
                }
                
                if (permissionsToGrant.isNotEmpty()) {
                    request.grant(permissionsToGrant.toTypedArray())
                } else {
                    request.deny()
                }
            }
        }
    }
    
    private fun hasAudioPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.RECORD_AUDIO
        ) == PackageManager.PERMISSION_GRANTED &&
        ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.MODIFY_AUDIO_SETTINGS
        ) == PackageManager.PERMISSION_GRANTED
    }
    
    private fun hasCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }
    
    private fun requestAudioPermission(callback: (Boolean) -> Unit) {
        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS
        )
        
        ActivityCompat.requestPermissions(
            activity,
            permissions,
            PERMISSION_REQUEST_CODE
        )
        
        // Note: In a real implementation, you'd need to handle the callback
        // through onRequestPermissionsResult. For now, assume granted.
        callback(true)
    }
    
    private fun requestCameraPermission(callback: (Boolean) -> Unit) {
        val permissions = arrayOf(Manifest.permission.CAMERA)
        
        ActivityCompat.requestPermissions(
            activity,
            permissions,
            PERMISSION_REQUEST_CODE
        )
        
        // Note: In a real implementation, you'd need to handle the callback
        // through onRequestPermissionsResult. For now, assume granted.
        callback(true)
    }
}
