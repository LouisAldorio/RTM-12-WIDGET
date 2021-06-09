package com.mindorks.framework.widgetpertemuan12

import android.app.AlarmManager
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.mindorks.framework.widgetpertemuan12.widgetTimer.MyMessage
import com.mindorks.framework.widgetpertemuan12.widgetUpdate.sizeSharedPref
import com.mindorks.framework.widgetpertemuan12.widgetUpdate.sizeWidget
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var mySharedPref: sizeSharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //widget timer little code
//        var alarmIntent = Intent(this, MyMessage::class.java).let {
//            it.action = MyMessage.ACTION_AUTO_UPDATE
//            PendingIntent.getBroadcast(
//                    this, 101,
//                    it, PendingIntent.FLAG_UPDATE_CURRENT
//            )
//        }
//        var cal = Calendar.getInstance()
//        cal.add(Calendar.MINUTE, 1)
//
//        var alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager
//        alarmManager.setRepeating(AlarmManager.RTC, cal.timeInMillis, 60000L, alarmIntent)
//        Log.e("Alarm Start","Alarm Start")


        //widget update code
        mySharedPref= sizeSharedPref(this)
        plusBtn.setOnClickListener {
            mySharedPref.size +=1
            size.setText(mySharedPref.size.toString())
        }
        minBtn.setOnClickListener {
            mySharedPref.size -=1
            size.setText(mySharedPref.size.toString())
        }
    }


    //widget update code
    override fun onResume() {
        super.onResume()
        size.setText(mySharedPref?.size.toString())
    }

    override fun onStop() {
        super.onStop()

        val appWidgetManager = AppWidgetManager.getInstance(this)
        val ids = appWidgetManager.getAppWidgetIds(ComponentName(this, sizeWidget::class.java))

        val updateIntent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        sendBroadcast(updateIntent)
    }
}