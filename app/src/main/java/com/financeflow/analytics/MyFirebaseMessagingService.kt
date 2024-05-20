package com.financeflow.analytics

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

import android.graphics.BitmapFactory

import android.app.NotificationChannel

import android.app.NotificationManager

import android.media.RingtoneManager

import android.app.PendingIntent

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import com.financeflow.screen.Splash
import java.lang.Exception
import android.graphics.Bitmap
import com.financeflow.util.PrefManager
import com.google.firebase.messaging.FirebaseMessaging
import java.io.IOException
import java.io.InputStream
import java.net.URL


class MyFirebaseMessagingService : FirebaseMessagingService() {
    private var count = 0
    lateinit var mypref: PrefManager

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.d("token:",token)
        Log.d("SUCC1:", token)

        mypref = PrefManager(this)

        Log.d("SUCC2:", mypref.usertoken.toString())

        if(mypref.usertoken.toString().equals("")) {
            mypref.usertoken = token
        }

        //  FirebaseMessaging.getInstance().subscribeToTopic("com.financeflow");

        //  SmartPush.getInstance(WeakReference(this)).setDevicePushToken(token)
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("DMessage1:",remoteMessage.data.toString())


       // SmartPush.getInstance(WeakReference(applicationContext)).handlePushNotification(remoteMessage.data.toString())

        //Here notification is recieved from server
        //Here notification is recieved from server
        try {


                sendNotification(
                    remoteMessage.data["title"]!!,
                    remoteMessage.data["body"]!!,)



        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    private fun sendNotification(title: String, messageBody: String) {
        val intent = Intent(applicationContext, Splash::class.java)
        //you can use your launcher Activity insted of SplashActivity, But if the Activity you used here is not launcher Activty than its not work when App is in background.
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        //Add Any key-value to pass extras to intent
        /*intent.putExtra("pushnotification", "yes")
        intent.putExtra("courseID", courseid.toInt())
        intent.putExtra("category", "course")
        intent.putExtra("id", notificationID)
*/
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        val defaultSoundUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val mNotifyManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        //For Android Version Orio and greater than orio.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel("Sesame", "Sesame", importance)
            mChannel.description = messageBody
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            mNotifyManager.createNotificationChannel(mChannel)
        }

        //For Android Version lower than oreo.
        val PACKAGE_NAME = applicationContext.packageName
        val imgId = resources.getIdentifier("$PACKAGE_NAME:drawable/ic_notification", null, null)
       // val imgId1 = resources.getIdentifier("$PACKAGE_NAME:drawable/ic_celebrity", null, null)

       /* var remote_picture: Bitmap? = null
        val notiStyle = NotificationCompat.BigPictureStyle()
        try {
            remote_picture =
                BitmapFactory.decodeStream(URL(image).getContent() as InputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        notiStyle.bigPicture(remote_picture)*/

        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, "Seasame")
        mBuilder.setContentTitle(title)
            .setContentText(messageBody)
            .setSmallIcon(imgId)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setColor(Color.parseColor("#D42E95"))
            .setContentIntent(pendingIntent)
            .setChannelId("Sesame")
            .setPriority(NotificationCompat.PRIORITY_LOW)
        mNotifyManager.notify(count, mBuilder.build())
        count++
    }




}