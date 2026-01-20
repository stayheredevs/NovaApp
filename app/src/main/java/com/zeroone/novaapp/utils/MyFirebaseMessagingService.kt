package com.zeroone.novaapp.utils // Or any other appropriate package

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
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

        AppLog.Log("FCM_MESSAGE",
            "From: ${remoteMessage.from}, " +
            "title: ${remoteMessage.notification?.title}," +
                    "body: ${remoteMessage.notification?.body}, " +
                    "data: ${remoteMessage.data["image_url"]}")

        val title = remoteMessage.notification?.title
        val body = remoteMessage.notification?.body
        // Get the image URL from the data payload
        val imageUrl = remoteMessage.data["image_url"]

        // If there's an image URL, download it first. Otherwise, show a simple notification.
        if (imageUrl != null) {
            downloadImageAndShowNotification(title, body, imageUrl)
        } else {
            sendNotification(title, body, null) // No image, just text
        }
    }

    private fun downloadImageAndShowNotification(title: String?, messageBody: String?, imageUrl: String) {
        // We use Glide to download the image from the URL into a Bitmap.
        // Since this is a service, we use applicationContext.
        Glide.with(applicationContext)
            .asBitmap()
            .load(imageUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // Once the image (Bitmap) is ready, build and show the notification.
                    sendNotification(title, messageBody, resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Not essential to implement, but you could handle cleanup here if needed.
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    // If image download fails, show a standard text notification.
                    sendNotification(title, messageBody, null)
                }
            })
    }

    private fun sendNotification(title: String?, messageBody: String?, image: Bitmap?) {
        val channelId = "default_channel_id" // You should define this in a constants file
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

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
            .setSmallIcon(R.drawable.ic_launcher_foreground) // **IMPORTANT: This icon is still required!**
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)

        // *** THIS IS THE KEY PART FOR SHOWING THE IMAGE ***
        // If an image (Bitmap) is available, apply the BigPictureStyle.
        if (image != null) {
            notificationBuilder.setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(image)
                    .bigLargeIcon(null as Bitmap?) // Setting to null uses the small icon in the collapsed view
            )
        }

        notificationManager.notify(System.currentTimeMillis().toInt() /* Use a unique ID */, notificationBuilder.build())
    }
}
