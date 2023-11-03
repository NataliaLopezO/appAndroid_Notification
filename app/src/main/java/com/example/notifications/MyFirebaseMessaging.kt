package com.example.notifications

import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.remoteMessage

class MyFirebaseMessaging: FirebaseMessagingService() {

    val TAG  = "MyFirebaseMessaging"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, "Mensaje recibido" )

        val notification = message.notification
        val title: String = notification!!.title!!
        val msg: String = notification.body!!

        sendNotification(title, msg)
    }

    private fun sendNotification(title: String, msg: String) {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            8,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )


        val notification = MyNotification(this, MyNotification.CHANNEL_ID_NOTIFICATIONS)
        notification.build(R.drawable.ic_launcher_foreground, title, msg, pendingIntent)
        notification.addChannel("Notificaciones")
        notification.createChannelGroup(
            MyNotification.CHANNEL_GROUP_GENERAL,
            R.string.notification_channel_group_general
        )
        notification.show(MyNotification.NOTIFICATION_ID)


    }




}