package com.example.a2unidadeprova

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ClipDescription
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationUtils {
    val CHANNEL_ID = "padrao"
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(context: Context){
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelName = "Padrão"
        val channelDescription = "Canal padrão de Notificações"

        val channel = NotificationChannel(
            CHANNEL_ID,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = channelDescription
            enableLights(true)
            lightColor = Color.GREEN
            vibrationPattern =
                longArrayOf(100,200,300,400,500,400,300,200,400)
        }
        notificationManager.createNotificationChannel(channel)
    }

    fun notificationSimple(context: Context, name:String, description: String, id: Int){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel(context)
        }

        val notificationBuilder =
            NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_list)
                .setContentTitle(name)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setColor(ActivityCompat.getColor(context,R.color.colorAccent))
                .setDefaults(Notification.DEFAULT_ALL)
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1,notificationBuilder.build())
    }
}
