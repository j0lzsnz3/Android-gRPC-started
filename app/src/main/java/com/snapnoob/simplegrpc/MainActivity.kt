package com.snapnoob.simplegrpc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xhhuango.springwithgrpcdemo.GreetingServiceGrpc
import com.xhhuango.springwithgrpcdemo.GreetingServiceOuterClass
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSubmit.setOnClickListener {
            val input = edtInput.text.toString()
            getResponseFromServer(input)
        }
    }

    private fun getResponseFromServer(input: String) {
        val helloResponse: GreetingServiceOuterClass.HelloResponse?
        val blockingStub = GreetingServiceGrpc.newBlockingStub(GrpcUtils.getChannelSatrio())

        val request = GreetingServiceOuterClass.HelloRequest.newBuilder()
            .setName(input)
            .build()

        helloResponse = blockingStub.greeting(request)

        tvServerResponse.text = helloResponse.greeting
    }
}
