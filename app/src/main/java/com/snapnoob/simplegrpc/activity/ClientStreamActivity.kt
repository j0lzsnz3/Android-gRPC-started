package com.snapnoob.simplegrpc.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.snapnoob.simplegrpc.R
import com.snapnoob.simplegrpc.grpc.GrpcChannel
import com.xhhuango.springwithgrpcdemo.GreetingServiceGrpc
import com.xhhuango.springwithgrpcdemo.GreetingServiceOuterClass
import io.grpc.stub.StreamObserver
import kotlinx.android.synthetic.main.activity_client_stream.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.stream.Stream
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

class ClientStreamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_stream)

        btnRequestStream.setOnClickListener { giveServerStreamRequest() }
    }

    // **************** Client Streaming Call ***************//
    private fun giveServerStreamRequest() {
        CoroutineScope(Dispatchers.IO).launch {
            val asyncStub = GreetingServiceGrpc.newStub(GrpcChannel.getChannelLocalSpring())
            val requestStream = asyncStub.greetingWithRequestStream(object : StreamObserver<GreetingServiceOuterClass.HelloResponse> {
                override fun onNext(value: GreetingServiceOuterClass.HelloResponse?) {

                }

                override fun onError(t: Throwable?) {
                    Log.e("ClientStream", "Error : ${t?.printStackTrace()}")
                }

                override fun onCompleted() {
                    GrpcChannel.getChannelLocalSpring()?.shutdown()
                }
            })

            Stream.of("Power Ranger Merah", "Power Ranger Biru", "Power Ranger Ijo", "Power Ranger Ungu")
                .map { word -> GreetingServiceOuterClass.HelloRequest.newBuilder().setName(word).build() }
                .forEach{ requestStream.onNext(it) }
            requestStream.onCompleted()
        }
    }
}