package com.mindorks.framework.widgetpertemuan12.widgetTimer

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.mindorks.framework.widgetpertemuan12.R
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
class MyMessage : AppWidgetProvider() {

    private var myPref : WidgetIdsPref? = null

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        if(myPref == null){
            myPref = WidgetIdsPref(context)
        }

        var ids = myPref?.getIds()

        myPref?.getIds()?.clear()
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            ids?.add(appWidgetId.toString())
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }

        if(ids != null)
            myPref?.setIds(ids)
    }

    override fun onEnabled(context: Context) {
        var alarmIntent = Intent(context, MyMessage::class.java).let {
            it.action = ACTION_AUTO_UPDATE
            PendingIntent.getBroadcast(context, 101,
                it, PendingIntent.FLAG_UPDATE_CURRENT)
        }
        var cal = Calendar.getInstance()
        cal.add(Calendar.SECOND,5)

        var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(AlarmManager.RTC, cal.timeInMillis,60000L,alarmIntent)
    }

    override fun onDisabled(context: Context) {

        var alarmIntent = Intent(context, MyMessage::class.java).let {
            it.action = ACTION_AUTO_UPDATE
            PendingIntent.getBroadcast(context, 101,
                it, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        var alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(alarmIntent)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if(ACTION_AUTO_UPDATE == intent?.action){

            if(myPref == null)
                myPref = WidgetIdsPref(context!!)

            for(ids in myPref?.getIds()!!){
                updateAppWidget(context!!, AppWidgetManager.getInstance(context),ids.toInt())
            }
        }
        super.onReceive(context, intent)
    }

    companion object {
        var pesan = saveMessage()
        val ACTION_AUTO_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE"

        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val widgetText = pesan.getMessage()
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.my_message)
            views.setTextViewText(R.id.appwidget_text, widgetText)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

