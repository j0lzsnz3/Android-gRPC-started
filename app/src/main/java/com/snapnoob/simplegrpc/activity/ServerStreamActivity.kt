package com.snapnoob.simplegrpc.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.snapnoob.simplegrpc.R
import com.snapnoob.simplegrpc.grpc.GrpcChannel
import com.xhhuango.springwithgrpcdemo.GreetingServiceGrpc
import com.xhhuango.springwithgrpcdemo.GreetingServiceOuterClass
import io.grpc.stub.StreamObserver
import kotlinx.android.synthetic.main.activity_server_stream.*
import kotlinx.coroutines.*

class ServerStreamActivity : AppCompatActivity() {

    private val stringBuilder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server_stream)

        btnServerStream.setOnClickListener {
            serverStreamCall(edtInputRequest.text.toString())
        }
    }

    // **************** Server Stream Call ***************//
    private fun serverStreamCall(input: String) {

        CoroutineScope(Dispatchers.IO).launch {
            val asyncStub = GreetingServiceGrpc.newStub(GrpcChannel.getChannelLocalSpring())

            val request = GreetingServiceOuterClass.HelloRequest.newBuilder()
                .setName(input)
                .build()

            asyncStub.greetingWithResponseStream(request, handleResponseStreamObserver())
        }
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
                CoroutineScope(Dispatchers.Main).launch {
                    edtResponseStream.setText(stringBuilder.replace(Regex(" / "), "\n"))
                    stringBuilder.clear()
                    GrpcChannel.getChannelLocalSpring()?.shutdown()
                }
            }
        }
    // **************** ***************** ***************//
}