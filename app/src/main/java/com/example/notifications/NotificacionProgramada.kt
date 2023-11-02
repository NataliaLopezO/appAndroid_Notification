package com.example.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class NotificacionProgramada: BroadcastReceiver() {

    companion object{
        const val NOTIFICATION_ID = 5
    }

    override fun onReceive(context: Context, intent: Intent?) {
        crearNotificacion(context)
        TODO("Not yet implemented")
    }

    private fun crearNotificacion(context: Context) {

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val flag = if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            PendingIntent.FLAG_IMMUTABLE else 0
        val pendingIntent : PendingIntent = PendingIntent.getActivity(context, 0, intent, flag)

        val builder = NotificationCompat.Builder(context, "MyChannel")
            .setSmallIcon(R.drawable.baseline_verified_user_24)
            .setContentTitle("Notificaion programada")
            .setContentText("Ejemplo de notificacion programda")
            .setContentIntent(pendingIntent)
            //.setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.baseline_verified_user_24))
            .setStyle(NotificationCompat.BigTextStyle().bigText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()


        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, builder)
    }
}