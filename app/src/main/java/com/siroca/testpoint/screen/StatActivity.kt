package com.siroca.testpoint.screen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.siroca.testpoint.R
import com.siroca.testpoint.utils.CacheHelper
import kotlinx.android.synthetic.main.activity_stat2.*

class StatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stat2)

        successCount.text = CacheHelper.getStat().first
        totalCount.text = CacheHelper.getStat().second
    }
}
