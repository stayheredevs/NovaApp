package com.zeroone.novaapp.utils

import android.os.Build
import android.view.View
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

/**
 * Helper class to handle Edge-to-Edge display for Android 15+ (API 35+)
 * Only applies edge-to-edge on devices running Android 15 or higher
 */
object EdgeToEdgeHelper {

    /**
     * Android 15 API level (API 35)
     */
    private const val ANDROID_15_API_LEVEL = 35

    /**
     * Check if the device is running Android 15 or higher
     */
    fun isAndroid15OrHigher(): Boolean {
        return Build.VERSION.SDK_INT >= ANDROID_15_API_LEVEL
    }

    /**
     * Enable edge-to-edge mode for AppCompatActivity
     * Only applies on Android 15+ devices
     *
     * @param activity The AppCompatActivity to enable edge-to-edge for
     * @param rootView The root view of the activity layout
     */
    fun enableEdgeToEdge(activity: AppCompatActivity, rootView: View) {
        if (!isAndroid15OrHigher()) {
            // Do nothing for devices below Android 15
            return
        }

        val window = activity.window
        enableEdgeToEdgeForWindow(window, rootView)
    }

    /**
     * Enable edge-to-edge mode for AppCompatActivity with bottom navigation handling
     * Only applies on Android 15+ devices
     * This version handles bottom navigation bars properly by applying padding only to top and sides
     *
     * @param activity The AppCompatActivity to enable edge-to-edge for
     * @param rootView The root view of the activity layout
     * @param bottomNavView Optional bottom navigation view that should handle its own bottom padding
     */
    fun enableEdgeToEdge(
        activity: AppCompatActivity,
        rootView: View,
        bottomNavView: View?
    ) {
        if (!isAndroid15OrHigher()) {
            // Do nothing for devices below Android 15
            return
        }

        val window = activity.window
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT

        val windowInsetsController = WindowCompat.getInsetsController(window, rootView)
        windowInsetsController?.isAppearanceLightStatusBars = true
        windowInsetsController?.isAppearanceLightNavigationBars = true

        // Handle window insets for the root view (top and sides only)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            
            // Apply padding to top and sides only (bottom handled by bottom nav if present)
            view.setPadding(
                insets.left,
                insets.top,
                insets.right,
                0 // No bottom padding on root if bottom nav exists
            )

            WindowInsetsCompat.CONSUMED
        }

        // If bottom nav exists, apply bottom padding to it separately
        bottomNavView?.let { navView ->
            ViewCompat.setOnApplyWindowInsetsListener(navView) { nav, windowInsets ->
                val navInsets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
                nav.setPadding(
                    nav.paddingLeft,
                    nav.paddingTop,
                    nav.paddingRight,
                    navInsets.bottom
                )
                WindowInsetsCompat.CONSUMED
            }
        }
    }

    /**
     * Enable edge-to-edge mode for ComponentActivity
     * Only applies on Android 15+ devices
     *
     * @param activity The ComponentActivity to enable edge-to-edge for
     * @param rootView The root view of the activity layout
     */
    fun enableEdgeToEdge(activity: ComponentActivity, rootView: View) {
        if (!isAndroid15OrHigher()) {
            // Do nothing for devices below Android 15
            return
        }

        val window = activity.window
        enableEdgeToEdgeForWindow(window, rootView)
    }

    /**
     * Internal method to enable edge-to-edge for a window
     */
    private fun enableEdgeToEdgeForWindow(window: Window, rootView: View) {
        // Enable edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Make system bars transparent
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT

        // Set light status bar icons if needed (for light backgrounds)
        val windowInsetsController = WindowCompat.getInsetsController(window, rootView)
        windowInsetsController?.isAppearanceLightStatusBars = true
        windowInsetsController?.isAppearanceLightNavigationBars = true

        // Handle window insets for the root view
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            
            // Apply padding to avoid system bars
            view.setPadding(
                insets.left,
                insets.top,
                insets.right,
                insets.bottom
            )

            WindowInsetsCompat.CONSUMED
        }
    }

    /**
     * Apply edge-to-edge with custom inset handling
     * Useful when you want to handle insets differently for specific views
     *
     * @param activity The AppCompatActivity
     * @param rootView The root view
     * @param onApplyInsets Callback to handle insets custom way
     */
    fun enableEdgeToEdgeWithCustomInsets(
        activity: AppCompatActivity,
        rootView: View,
        onApplyInsets: (View, Insets) -> Unit
    ) {
        if (!isAndroid15OrHigher()) {
            return
        }

        val window = activity.window
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        window.navigationBarColor = android.graphics.Color.TRANSPARENT

        val windowInsetsController = WindowCompat.getInsetsController(window, rootView)
        windowInsetsController?.isAppearanceLightStatusBars = true
        windowInsetsController?.isAppearanceLightNavigationBars = true

        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            onApplyInsets(view, insets)
            WindowInsetsCompat.CONSUMED
        }
    }

    /**
     * Set light or dark status bar icons
     *
     * @param activity The AppCompatActivity
     * @param isLight true for light icons (for dark backgrounds), false for dark icons (for light backgrounds)
     */
    fun setStatusBarIconsLight(activity: AppCompatActivity, isLight: Boolean) {
        if (!isAndroid15OrHigher()) {
            return
        }

        val window = activity.window
        val rootView = activity.window.decorView.rootView
        val windowInsetsController = WindowCompat.getInsetsController(window, rootView)
        windowInsetsController?.isAppearanceLightStatusBars = isLight
        windowInsetsController?.isAppearanceLightNavigationBars = isLight
    }
}
