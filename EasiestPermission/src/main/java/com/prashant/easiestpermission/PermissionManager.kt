package com.prashant.easiestpermission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionManager {
    private const val PERMISSION_REQUEST_CODE = 777

    private lateinit var activity: AppCompatActivity
    private lateinit var permissionCallback: PermissionCallback

    interface PermissionCallback {
        fun onPermissionGranted()
        fun onPermissionDenied()
        fun onPermissionPermanentDenied(permissions: Array<String>)
    }

    fun checkSinglePermission(activity: AppCompatActivity, permission: String, callback: PermissionCallback) {
        this.activity = activity
        this.permissionCallback = callback

        if (isPermissionGranted(permission)) {
            permissionCallback.onPermissionGranted()
        } else {
            ActivityCompat.requestPermissions(activity, arrayOf(permission), PERMISSION_REQUEST_CODE)
        }
    }

    fun checkMultiplePermissions(activity: AppCompatActivity, permissions: Array<String>, callback: PermissionCallback) {
        this.activity = activity
        this.permissionCallback = callback

        val deniedPermissions = mutableListOf<String>()

        permissions.forEach { permission ->
            if (!isPermissionGranted(permission)) {
                deniedPermissions.add(permission)
            }
        }

        if (deniedPermissions.isEmpty()) {
            permissionCallback.onPermissionGranted()
        } else {
            ActivityCompat.requestPermissions(activity, deniedPermissions.toTypedArray(), PERMISSION_REQUEST_CODE)
        }
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            var allPermissionsGranted = true
            var permanentlyDenied = false
            val permanentDenied = mutableListOf<String>()

            for (i in permissions.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false
                    if (!shouldShowRequestPermissionRationale(permissions[i])) {
                        permanentlyDenied = true
                        permanentDenied.add(permissions[i])
//                        break
                    }
                }
            }

            if (allPermissionsGranted) {
                permissionCallback.onPermissionGranted()
            } else {
                if (permanentlyDenied) {
                    permissionCallback.onPermissionPermanentDenied(permanentDenied.toTypedArray())
                } else {
                    permissionCallback.onPermissionDenied()
                }
            }
        }
    }

    private fun isPermissionGranted(permission: String): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }

    private fun shouldShowRequestPermissionRationale(permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    }

    fun getRationaleMessage(permission: String): String {
        // Customize the rationale message based on the permission
        return when (permission) {
            Manifest.permission.CAMERA -> "This app requires camera permission to take photos."
            Manifest.permission.READ_CONTACTS -> "This app requires permission to access your contacts."
            // Add more permissions and their respective rationale messages here
            else -> "This app requires permission to perform this action."
        }
    }
}