package com.zeroone.novaapp.utilities // Or any other appropriate package

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.zeroone.novaapp.R // Make sure to import your app's R file

class MyFirebaseMessagingService : FirebaseMessagingService() {

    // Called when a new token for the device is generated.
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        // This is important! Log the token to see it in Logcat.
        // You'll need this token to send a test message directly to this device.
        AppLog.Log("FCM_TOKEN", "New FCM Token: $token")

        // You might want to send this token to your server here.
        // sendTokenToServer(token)
    }

    // Called when a message is received.
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        AppLog.Log("FCM_MESSAGE", "From: ${remoteMessage.from}")

        // Check if the message contains a notification payload.
        remoteMessage.notification?.let { notification ->
            AppLog.Log("FCM_MESSAGE", "Message Notification Body: ${notification.body}")
            // Create and show a custom notification.
            sendNotification(notification.title, notification.body)
        }
    }

    private fun sendNotification(title: String?, messageBody: String?) {
        val channelId = "default_channel_id" // You should define this in a constants file
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Default Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // **IMPORTANT: Add a notification icon to your drawable folder**
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}
