package com.example.infrrdassignment

import android.content.Context
import android.net.ConnectivityManager
import android.util.DisplayMetrics

/**
 * This class is utility class which contains common methods which can be used more than twice in application
 */
object Utility  {

    /**
     * This method is responsible to check the network connection
     * @param context This parameter need the context of activity or application
     */
    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isMetered = cm.isActiveNetworkMetered()
        return  isMetered
    }

    /**
     * This method is responsible to convert the pixels into dp
     * @param px contains pixel value of view
     * @param context
     */
    fun pxToDp(px: Int,context: Context): Int {
        val displayMetrics: DisplayMetrics = context.getResources().getDisplayMetrics()
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

}