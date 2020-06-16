package com.snapnoob.simplegrpc.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.snapnoob.simplegrpc.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnUnaryCall.setOnClickListener {
            startActivity(Intent(this, UnaryCallActivity::class.java))
        }

        btnServerStream.setOnClickListener {
            startActivity(Intent(this, ServerStreamActivity::class.java))
        }

        btnClientStream.setOnClickListener {
            startActivity(Intent(this, ClientStreamActivity::class.java))
        }

        btnBidirecrionalStream.setOnClickListener {
            startActivity(Intent(this, BidirectionalActivity::class.java))
        }
    }
}
