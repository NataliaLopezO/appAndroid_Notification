package com.example.notifications

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.util.Calendar

@Composable
fun Notificaciones(){
    val context = LocalContext.current
    val ID_CHANNEL = "MyChannel"

    val ID_NOTIFICATION = 0

    //Funcion de creacion propia como corrutina
    LaunchedEffect(Unit){

        crearNotificacion(ID_CHANNEL, context){
            
        }

    }


    val modifier = Modifier
        .padding(18.dp)
        .fillMaxWidth()


    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(18.dp)
            .fillMaxSize()
    ){

        Text(
            "Notificaciones",
            modifier = Modifier.padding(bottom = 100.dp)

        )

        Button(onClick = {notificacionBasica(context, ID_CHANNEL, ID_NOTIFICATION, "Notificacion simple", " Ejemplo de notificacion basica con prioridad por defecto")},
            modifier = modifier){
            Text(text = "Notificacion Simple")
        }

        Button(onClick = {notificacionTextoLargo(context, ID_CHANNEL, ID_NOTIFICATION+1, "Notificacion con texto largo", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ")},
            modifier = modifier){
            Text(text = "Notificacion con Texto largo")
        }

        Button(onClick = {notificacionProgramada(context)},
                modifier = modifier){
            Text(text = "Notificacion Programada")
        }

    }

}

@SuppressLint("ScheduleExactAlarm")
fun notificacionProgramada(context: Context) {

    val intent = Intent(context, NotificacionProgramada::class.java)
    val pendingIntent = PendingIntent.getBroadcast(
        context,
        3,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)

        var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            Calendar.getInstance().timeInMillis + 4000,
            pendingIntent
        )







}

fun notificacionTextoLargo(
    context: Context,
    idChannel: String,
    idNotification: Int,
    title: String,
    content: String,
    priority:Int = NotificationCompat.PRIORITY_DEFAULT) {


    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    val flag = if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        PendingIntent.FLAG_IMMUTABLE else 0
    val pendingIntent : PendingIntent = PendingIntent.getActivity(context, 0, intent, flag)


    var icono = BitmapFactory.decodeResource(context.resources, R.drawable.baseline_verified_user_24)
    val builder = NotificationCompat.Builder(context, idChannel)
        .setSmallIcon(R.drawable.baseline_verified_user_24)
        .setContentTitle(title)
        .setContentText(content)
        .setContentIntent(pendingIntent)
        //.setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.baseline_verified_user_24))
        .setStyle(NotificationCompat.BigTextStyle().bigText(content))
        .setPriority(priority)


    with(NotificationManagerCompat.from(context)){
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notify(idNotification, builder.build())
    }


}

fun notificacionBasica(context: Context,
                       idChannel: String,
                       idNotification: Int,
                       title: String,
                       content: String,
                       priority:Int = NotificationCompat.PRIORITY_DEFAULT) {

    val builder = NotificationCompat.Builder(context, idChannel)
        .setSmallIcon(R.drawable.baseline_verified_user_24)
        .setContentTitle(title)
        .setContentText(content)
        .setPriority(priority)

    with(NotificationManagerCompat.from(context)){
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notify(idNotification, builder.build())
    }



}

fun crearNotificacion(
    idChannel: String,
    context: Context,
    function: () -> Unit) {

    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
        val name = "MyChannel"
        var description = "Nuevo canal de notificaciones"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel =  NotificationChannel(idChannel, name, importance).apply {
            description = description

        }
        //gestor de notificaiones
        val notificationManager : NotificationManager =  context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //crear el canal
        notificationManager.createNotificationChannel(channel)

    }

}


