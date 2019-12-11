package com.snapnoob.simplegrpc

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import java.util.concurrent.TimeUnit

object GrpcUtils {

    fun getChannelLocalSpring(): ManagedChannel? {
        var channel: ManagedChannel? = null
        return try {
            channel = ManagedChannelBuilder
                .forTarget("10.0.2.2:8081")
                .idleTimeout(30, TimeUnit.SECONDS)
                .usePlaintext(true)
                .build()
            channel
        } catch (ex: Exception) {
            channel
        }

    }

    fun getChannelLocal(): ManagedChannel? {
        var channel: ManagedChannel? = null
        return try {
            channel = ManagedChannelBuilder
                .forTarget("172.16.41.122:8080")
                .idleTimeout(30, TimeUnit.SECONDS)
                .usePlaintext(true)
                .build()
            channel
        } catch (ex: Exception) {
            channel
        }

    }

}