package com.example.renderscripttest

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat
import com.example.renderscripttest.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private val photoFilter: FilterScript = FilterScript()
    private var mainBitmap: Bitmap? = null
    private val workerThread: ExecutorService = Executors.newCachedThreadPool()
    private val workerHandler = Handler(Looper.getMainLooper())

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        mainBinding.processingBtn.setOnClickListener {

            workerThread.execute {

                mainBitmap = ContextCompat.getDrawable(this, R.drawable.abcd)?.let { it1 ->
                    Utils.drawableToBitmap(
                        it1
                    )
                }

                workerHandler.post {

                    if (mainBitmap != null) {
                        mainBinding.filterImg.setImageBitmap(photoFilter.four(this, mainBitmap!!))
                    } else {
                        Utils.showToast(this, "Bitmap is null")
                    }
                }

            }
        }


    }
}