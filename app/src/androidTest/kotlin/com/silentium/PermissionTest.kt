package com.silentium

import android.Manifest
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.manifest.AndroidManifest
import org.robolectric.res.Fs
import java.net.URL

/**
 * @author lusinabrian on 25/09/17.
 * @Notes Test permissions added to application
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class PermissionTest {

    private val expectedPermissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET
    )

    private val mergedManifest = "build/intermediates/manifests/full/debug/universal/AndroidManifest.xml"

    @Test
    fun testShouldMatchManifest() {
        val manifest = AndroidManifest(Fs.fromURL(URL(mergedManifest)), null, null)

        assertThat(HashSet(manifest.usedPermissions)).contains(expectedPermissions)
    }
}