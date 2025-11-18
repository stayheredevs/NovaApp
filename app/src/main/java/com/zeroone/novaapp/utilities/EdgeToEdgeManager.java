package com.zeroone.novaapp.utilities;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class EdgeToEdgeManager {


    public static void makeStatusBarGreenWithWhiteIcons(Window window, int greenColorRes, Context context) {
        // 1. Edge-to-edge (mandatory for SDK 35)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowCompat.setDecorFitsSystemWindows(window, false);
        }


    }


    public static void handleEdgeToEdge(Window window, View rootView, int greenColorRes) {
        // 1. Enable edge-to-edge — safe only on API 30+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowCompat.setDecorFitsSystemWindows(window, false);
        }

        // 2. Apply insets (works on API 21+ via ViewCompat)
        if (rootView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
                Insets systemInsets = insets.getInsets(
                        WindowInsetsCompat.Type.statusBars() |
                                WindowInsetsCompat.Type.navigationBars() |
                                WindowInsetsCompat.Type.displayCutout()
                );

                v.setPadding(
                        systemInsets.left,
                        systemInsets.top,
                        systemInsets.right,
                        systemInsets.bottom
                );

                return WindowInsetsCompat.CONSUMED;
            });
        }





        // 4. White (light) icons – false = light icons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) {
            WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(window, window.getDecorView());
            if (controller != null) {
                controller.setAppearanceLightStatusBars(true); // false → light icons
            }

        } else {
            // 3. Set the status-bar colour
            window.setStatusBarColor(window.getContext().getColor(greenColorRes));

            WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(window, window.getDecorView());
            if (controller != null) {
                controller.setAppearanceLightStatusBars(false); // false → light icons
            }
        }



    }
}
