package com.snapnoob.simplegrpc.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.snapnoob.simplegrpc.R
import com.snapnoob.simplegrpc.grpc.GrpcChannel
import com.xhhuango.springwithgrpcdemo.GreetingServiceGrpc
import com.xhhuango.springwithgrpcdemo.GreetingServiceOuterClass
import kotlinx.android.synthetic.main.activity_unary_call.*

class UnaryCallActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unary_call)

        btnGetResponseUnary.setOnClickListener {
            requestUnaryCall(edtInputMessage.text.toString())
        }
    }

    // ************** Unary Call *********** //
    private fun requestUnaryCall(input: String) {
        val messageResponse: GreetingServiceOuterClass.HelloResponse?
        val blockingStub = GreetingServiceGrpc.newBlockingStub(GrpcChannel.getChannelLocalSpring())

        val request = GreetingServiceOuterClass.HelloRequest.newBuilder()
            .setName(input)
            .build()

        messageResponse = blockingStub.greeting(request)

        tvResponseUnary.text = messageResponse?.greeting
        GrpcChannel.getChannelLocalSpring()?.shutdown()
    }
}