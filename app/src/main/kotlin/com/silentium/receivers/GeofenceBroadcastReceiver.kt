package com.silentium.receivers

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.silentium.R
import com.silentium.ui.main.MainActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error

class GeofenceBroadcastReceiver : BroadcastReceiver(), AnkoLogger {

    /***
     * Handles the Broadcast message sent when the Geofence Transition is triggered
     * Careful here though, this is running on the main thread so make sure you start an AsyncTask for
     * anything that takes longer than say 10 second to run
     *
     * @param context
     * @param intent
     */
    override fun onReceive(context: Context, intent: Intent) {
        // Get the Geofence Event from the Intent sent through
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            error(String.format("Error code : %d", geofencingEvent.errorCode))
            return
        }

        // Get the transition type.
        val geofenceTransition = geofencingEvent.geofenceTransition
        // Check which transition type has triggered this event
        when (geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER ->
                setRingerMode(context, AudioManager.RINGER_MODE_SILENT)
            Geofence.GEOFENCE_TRANSITION_EXIT ->
                setRingerMode(context, AudioManager.RINGER_MODE_NORMAL)
            else -> {
                // Log the error.
                error(String.format("Unknown transition : %d", geofenceTransition))
                // No need to do anything else
                return
            }
        }
        // Send the notification
        sendNotification(context, geofenceTransition)
    }


    /**
     * Posts a notification in the notification bar when a transition is detected
     * Uses different icon drawables for different transition types
     * If the user clicks the notification, control goes to the MainActivity
     *
     * @param context        The calling context for building a task stack
     * @param transitionType The geofence transition type, can be Geofence.GEOFENCE_TRANSITION_ENTER
     * or Geofence.GEOFENCE_TRANSITION_EXIT
     */
    private fun sendNotification(context: Context, transitionType: Int) {
        // Create an explicit content Intent that starts the main Activity.
        val notificationIntent = Intent(context, MainActivity::class.java)

        // Construct a task stack.
        val stackBuilder = TaskStackBuilder.create(context)

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(MainActivity::class.java)

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent)

        // Get a PendingIntent containing the entire back stack.
        val notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

        // Get a notification builder
        val builder = NotificationCompat.Builder(context)

        // Check the transition type to display the relevant icon image
        when(transitionType){
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                builder.setSmallIcon(R.drawable.ic_volume_off_white_24dp)
                        .setLargeIcon(BitmapFactory.decodeResource(context.resources,
                                R.drawable.ic_volume_off_white_24dp))
                        .setContentTitle(context.getString(R.string.silent_mode_activated))
            }

            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                builder.setSmallIcon(R.drawable.ic_volume_up_white_24dp)
                        .setLargeIcon(BitmapFactory.decodeResource(context.resources,
                                R.drawable.ic_volume_up_white_24dp))
                        .setContentTitle(context.getString(R.string.back_to_normal))
            }
        }

        // Continue building the notification
        builder.setContentText(context.getString(R.string.touch_to_relaunch))
        builder.setContentIntent(notificationPendingIntent)

        // Dismiss notification once the user touches it.
        builder.setAutoCancel(true)

        // Get an instance of the Notification manager
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Issue the notification
        mNotificationManager.notify(0, builder.build())
    }

    /**
     * Changes the ringer mode on the device to either silent or back to normal
     *
     * @param context The context to access AUDIO_SERVICE
     * @param mode    The desired mode to switch device to, can be AudioManager.RINGER_MODE_SILENT or
     * AudioManager.RINGER_MODE_NORMAL
     */
    private fun setRingerMode(context: Context, mode: Int) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Check for DND permissions for API 24+
        if (android.os.Build.VERSION.SDK_INT < 24 || android.os.Build.VERSION.SDK_INT >= 24 && !nm.isNotificationPolicyAccessGranted) {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.ringerMode = mode
        }
    }
}
