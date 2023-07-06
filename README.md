#### EasiestPermission
Android Runtime Permission

- Very short code.
- Can check multiple permissions at once.
- Quick support.
- Open source and fully customizable.

#### Dependecy:

Step1: Add it in your root build.gradle at the end of repositories:

        allprojects { repositories { maven { url 'https://jitpack.io' } } }

Step2: Add the dependency

        dependencies { implementation 'com.github.prashantgunit:EasiestPermission:0.1.0' }

#### Usage:
Add following code in your activity

      PermissionManager.checkMultiplePermissions(
            this,
            arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            permissionCallback
        )


Now create a callback


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

Override onRequestPermissionsResult onfuntion like this

       override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

  
