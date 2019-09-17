package com.siroca.testpoint.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.LinearLayout
import com.siroca.testpoint.R
import kotlinx.android.synthetic.main.base_dialog_layout.view.*


/**
 * Created by azamat  on 8/13/19.
 */
class DialogUtils(context: Context) : AlertDialog(context) {

    private val customView = View.inflate(context, R.layout.alert_dialog_layout, null)
    private var messageContainer: View

    init {
        setView(customView)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        messageContainer = View.inflate(context, R.layout.base_dialog_layout, null)
        customView.findViewById<LinearLayout>(R.id.container).addView(messageContainer)
        setCancelable(false)
    }

    fun showBaseDialog(text: String): DialogUtils {
        messageContainer.message.text = text
        return this
    }

    fun isMyCopy(message: String): Boolean {
        return messageContainer.message.text == message && isShowing
    }
}