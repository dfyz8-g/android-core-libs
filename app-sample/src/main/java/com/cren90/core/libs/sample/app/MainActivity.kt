package com.cren90.core.libs.sample.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cren90.android.core.logging.ModularLogger
import com.cren90.android.core.logging.strategies.AndroidLoggingStrategy
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        ModularLogger(AndroidLoggingStrategy()).withData(
            mapOf<String, Any?>(
                "key1" to mapOf<String, Any?>(
                    "innerkey1" to 22,
                    "innerkey2" to 9.5,
                    "innerkey3" to null
                ),
                "key2" to "value2",
                "key3" to UUID.randomUUID()
            )
        ).info("test log")
    }
}