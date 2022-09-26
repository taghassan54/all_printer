package com.example.all_printer

import android.R.attr.capitalize
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import java.util.*


/** AllPrinterPlugin */
class AllPrinterPlugin : FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel

    var mContext: Context? = null
    private var printerObject: PrintingMethods? = null


    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "all_printer")
        channel.setMethodCallHandler(this)
        printerObject = PrintingMethods(flutterPluginBinding.applicationContext)
        printerObject?.initializePrinters()
        mContext = flutterPluginBinding.applicationContext
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "getPlatformVersion" -> {
                result.success("Android ${android.os.Build.VERSION.RELEASE}")
            }
            "print" -> {
                val logoBitmap = call.argument<ByteArray>("logoBitmap")
                val text = call.argument<String>("text")
                val date = call.argument<String>("date")
                val name = call.argument<String>("name")
                val merchent = call.argument<String>("merchent")
                val terminal = call.argument<String>("terminal")
                val transaction = call.argument<String>("transaction")
                val voucher = call.argument<String>("voucher")
                val car = call.argument<String>("car")
                val customer = call.argument<String>("customer")
                val star1 = call.argument<String>("star1")
                val title = call.argument<String>("title")
                val star2 = call.argument<String>("star2")
                val product = call.argument<String>("product")
                val service = call.argument<String>("service")
                val price = call.argument<String>("price")
                val qty = call.argument<String>("qty")
                val tqty = call.argument<String>("tqty")
                val totalbeforvat = call.argument<String>("totalbeforvat")
                val vat = call.argument<String>("vat")
                val star3 = call.argument<String>("star3")
                val total = call.argument<String>("total")
                val star4 = call.argument<String>("star4")
                val address = call.argument<String>("address")
                val footer = call.argument<String>("footer")

                var loremX500 = "The Quick Brown fox jumped over The Lazy Dog"

                loremX500 += date
                loremX500 += "\n"
                loremX500 += name
                loremX500 += "\n"
                loremX500 += merchent
                loremX500 += "\n"
                loremX500 += terminal
                loremX500 += "\n"
                loremX500 += transaction
                loremX500 += "\n"
                loremX500 += voucher
                loremX500 += "\n"
                loremX500 += car
                loremX500 += "\n"
                loremX500 += customer
                loremX500 += "\n"
                loremX500 += star1
                loremX500 += "\n"
                loremX500 += title
                loremX500 += "\n"
                loremX500 += star2
                loremX500 += "\n"
                loremX500 += product
                loremX500 += "\n"
                loremX500 += service
                loremX500 += "\n"
                loremX500 += price
                loremX500 += "\n"
                loremX500 += qty
                loremX500 += "\n"
                loremX500 += tqty
                loremX500 += "\n"
                loremX500 += totalbeforvat
                loremX500 += "\n"
                loremX500 += vat
                loremX500 += "\n"
                loremX500 += star3
                loremX500 += "\n"
                loremX500 += total
                loremX500 += "\n"
                loremX500 += star4
                loremX500 += "\n"
                loremX500 += address
                loremX500 += "\n"
                loremX500 += footer
                loremX500 += "\n"


                var deviceName = printRey(loremX500, logoBitmap);
                result.success("printer device Name : $deviceName");
            }
            else -> {
                result.notImplemented()
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    private fun printRey(loremX500: String, logoBitmap: ByteArray?): String {
        Log.d("PosType", Constant.posType)

        var text = "\n\n---------------------------- \n\n"
        text += "${getDeviceName()}" + "\n"
        text = "\n\n---------------------------- \n\n"
        text += "\n\n $loremX500"
        text += "\n\n\n\n\n\n\n\n\n\n\n\n"
//        printerObject?.printReyBitmap("http://smartepaystaging.altkamul.ae/Content/img/printing.bmp")
        printerObject?.printRey(text)
        return "${getDeviceName()}"
    }


    fun getDeviceName(): String? {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return model
    }

}
