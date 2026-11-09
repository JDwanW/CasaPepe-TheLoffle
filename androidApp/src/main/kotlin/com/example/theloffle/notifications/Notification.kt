package com.example.theloffle.notifications

import android.annotation.SuppressLint
import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.ListenableWorker.Result
import com.example.theloffle.MainActivity
import com.example.theloffle.R
import com.example.theloffle.Route
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object Notification {

    fun scheduleNotification(
        context: Context,
        title: String,
        message: String,
        delaySeconds: Long,
        //TODO: It should depend if user is logged in or not
        toScreen: Route = Route.HomeScreen
    ) {
        val data = Data.Builder()
            .putString("title", title)
            .putString("message", message)
            .putString("route",Json.encodeToString<Route>(toScreen))
            .build()

        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(delaySeconds, TimeUnit.SECONDS)
            .setInputData(data)
            .build()

        WorkManager.getInstance(context).enqueue(workRequest)
    }
}

class NotificationWorker(
    appContext: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(appContext, workerParameters) {

    override suspend fun doWork(): Result {
        val title = inputData.getString("title") ?: "Notificación"
        val message = inputData.getString("message") ?: "Vuelve!!"
        val toScreenJson = inputData.getString("route")
        showNotification(title, message,toScreenJson)

        return Result.success()
    }

    @SuppressLint("SuspiciousIndentation")
    fun createNotificationChannel(
        channelId: String,
        channelName: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
                applicationContext
                    .getSystemService(NotificationManager::class.java)
                    .createNotificationChannel(channel)
        }
    }

    fun showNotification(
        title: String,
        message: String,
        toScreenJson: String?
    ) {
        val channelId = "CHANNEL_ID_TEST"
        createNotificationChannel(channelId,"Work Manager Notification")

        val notificationId = System.currentTimeMillis().toInt()
        val intent = Intent(applicationContext, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("EXTRA_TO_SCREEN",toScreenJson)
        }
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.logo100)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        applicationContext
            .getSystemService(NotificationManager::class.java)
            .notify(notificationId,notification)
    }
}
