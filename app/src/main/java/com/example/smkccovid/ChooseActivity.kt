package com.example.smkccovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.activity_choose.*
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper

class ChooseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)
        OverScrollDecoratorHelper.setUpOverScroll(sv_choose)

        bt_back2.setOnClickListener { buttonBack() }
    }

    override fun onBackPressed() {
        buttonBack()
    }

    private fun buttonBack() {
        super.onBackPressed()
        AcTrans.Builder(this).performFade()
    }


}
