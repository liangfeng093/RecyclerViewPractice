package com.liangfeng.recyclerviewpractice

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG = this.javaClass.name
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tbn_1?.setOnClickListener {
            startActivity(Intent(this@MainActivity, GalleryActivity::class.java))
        }
        //

        //微信支付

    }
}
