package com.snapnoob.simplegrpc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.system.messenger.MessengerGrpc
import com.system.messenger.MessengerProto
import com.xhhuango.springwithgrpcdemo.GreetingServiceGrpc
import com.xhhuango.springwithgrpcdemo.GreetingServiceOuterClass
import io.grpc.stub.StreamObserver
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var stringBuilder: StringBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        stringBuilder = StringBuilder()

        btnSubmit.setOnClickListener {
            val input = edtInput.text.toString()
            getClientStreamResponse(input)
        }
    }

    private fun getUnaryResponseFromServer(input: String) {
        val messageResponse: MessengerProto.MessageResponse?
        val blockingStub = MessengerGrpc.newBlockingStub(GrpcUtils.getChannelLocal())

        val request = MessengerProto.MessageRequest.newBuilder()
            .setText(input)
            .build()

        messageResponse = blockingStub.getMessageUnary(request)

        tvServerResponse.text = messageResponse.text
    }

    private fun getClientStreamResponse(input: String) {
        val greetingResponse = handleResponseStreamObserver()

        val asyncStub = GreetingServiceGrpc.newStub(GrpcUtils.getChannelLocalSpring())

        val request = GreetingServiceOuterClass.HelloRequest.newBuilder()
            .setName(input)
            .build()

        asyncStub.greetingWithResponseStream(request, greetingResponse)
    }

    private fun handleResponseStreamObserver(): StreamObserver<GreetingServiceOuterClass.HelloResponse> =
        object : StreamObserver<GreetingServiceOuterClass.HelloResponse> {
            override fun onNext(value: GreetingServiceOuterClass.HelloResponse?) {
                Log.d("IMAM", "Stream from server ${value?.greeting}")
                stringBuilder.append("${value?.greeting} / ")
            }

            override fun onError(t: Throwable?) {
                Log.e("IMAM", "Stream Error ${t?.cause?.message}")
            }

            override fun onCompleted() {
                tvServerResponse.text = ""
                tvServerResponse.text = stringBuilder.replaceFirst(Regex(" / "), "\n")
            }
        }
}
