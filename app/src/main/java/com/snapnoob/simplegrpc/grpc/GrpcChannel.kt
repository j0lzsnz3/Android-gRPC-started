package com.snapnoob.simplegrpc.grpc

import android.util.Log
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.util.concurrent.TimeUnit

object GrpcChannel {

    fun getChannelLocalSpring(): ManagedChannel? {
        val channel: ManagedChannel
        return try {
            channel = ManagedChannelBuilder
                .forTarget("192.168.43.67:8081") // sesuaikan IP nya
                .idleTimeout(30, TimeUnit.SECONDS)
                .usePlaintext()
                .build()
            channel
        } catch (ex: Exception) {
            Log.e("GrpcChannel", "Error, ${ex.stackTrace}")
            null
        }

    }

}