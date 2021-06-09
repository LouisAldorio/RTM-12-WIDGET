package com.mindorks.framework.widgetpertemuan12.widgetUpdate

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.mindorks.framework.widgetpertemuan12.MainActivity
import com.mindorks.framework.widgetpertemuan12.R

/**
 * Implementation of App Widget functionality.
 */
class sizeWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        var sizeWidget = sizeSharedPref(context!!)
        if(AddOnClick.equals(intent?.action)){
            sizeWidget.size += 1
        }
        else if(MinOnClick.equals(intent?.action)){
            sizeWidget.size -= 1
        }

        val appWidgetManager = AppWidgetManager.getInstance(context)
        val thisAppWidgetComponenName = ComponentName(context!!.packageName, javaClass.name)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidgetComponenName)

        for(appWidgetId in appWidgetIds){
            updateAppWidget(context, appWidgetManager,appWidgetId)
        }
    }

    companion object {

        private val AddOnClick = "AddOnClick"
        private val MinOnClick = "MinOnClick"

        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val views = RemoteViews(context.packageName, R.layout.size_widget)

            var sizeWidget = sizeSharedPref(context)

            views.setTextViewText(R.id.appwidget_text_size,sizeWidget.size.toString())

            views.setOnClickPendingIntent(R.id.appwidget_btn_plus,
                getPendingSelfIntent(context, AddOnClick))

            views.setOnClickPendingIntent(R.id.appwidget_btn_min,
                getPendingSelfIntent(context, MinOnClick))

            views.setOnClickPendingIntent(R.id.widgetLayout,
                getPendingIntent(context))

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        private fun getPendingSelfIntent(context: Context, addOnClick: String): PendingIntent? = Intent(context,
            sizeWidget::class.java).let {
            it.action = addOnClick
            PendingIntent.getBroadcast(context,105,it,PendingIntent.FLAG_UPDATE_CURRENT)
        }

        private fun getPendingIntent(context: Context) : PendingIntent {
            var myIntent = Intent(context, MainActivity::class.java)
            return PendingIntent.getActivity(context, 111, myIntent ,0)
        }
    }
}

