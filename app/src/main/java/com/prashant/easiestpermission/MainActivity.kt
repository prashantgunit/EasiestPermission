package com.prashant.easiestpermission

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PermissionManager.checkMultiplePermissions(
            this,
            arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            permissionCallback
        )
    }


    private val permissionCallback = object : PermissionManager.PermissionCallback {
        override fun onPermissionGranted() {
            Toast.makeText(this@MainActivity, "Camera permission granted!", Toast.LENGTH_SHORT)
                .show()
        }

        override fun onPermissionDenied() {
            Toast.makeText(this@MainActivity, "Camera permission denied!", Toast.LENGTH_SHORT)
                .show()
        }

        override fun onPermissionPermanentDenied(permissions: Array<String>) {
            if (permissions.size > 1)
                Toast.makeText(
                    this@MainActivity,
                    "There multiple permissions which are required for smooth functioning of app",
                    Toast.LENGTH_SHORT
                )
                    .show()
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}