package com.example.all_printer

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
import java.io.ByteArrayInputStream


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
                result.success("Android ${android.os.Build.VERSION.RELEASE} - Device Name : ${getDeviceName()}")
            }
            "printReyFinish" -> {
                try {
                    printerObject?.printReyFinish()
                    result.success("success !")
                } catch (e: Exception) {
                    result.success("${e.message}");
                }

            }
            "printQrCode" -> {
                try {
                    printerObject?.printQrCode(null,"\n ${call.arguments} \n")
                    result.success("success !")
                } catch (e: Exception) {
                    result.success("${e.message}");
                }

            }
            "printLine" -> {
                if (call.arguments != null) {
                    try {
                        printerObject?.printRey("\n ${call.arguments} \n")
                        result.success("success !")
                    } catch (e: Exception) {
                        result.success("${e.message}");
                    }
                } else {
                    result.success("line not found !")
                }
            }
            "printImage" -> {

                if (call.arguments != null) {
                    try {
                        printerObject?.printReyBitmap("${call.arguments}")
                        printerObject?.printRey("\n")
                        result.success("success ! ")
                    } catch (e: Exception) {
                        result.success("${e.message}");
                    }
                } else {
                    result.success("image not found !")
                }

            }
            "print" -> {

                try {


                    result.success("printer device Name : none")

                    val hashMap = call.arguments as HashMap<*,*>

                    val logoPath = call.argument<String>("logoPath")

                    var loremX500 =   ""

                    hashMap.forEach { item ->
                        loremX500 += "\n $item"
                    }
                    Log.d("loremX500",loremX500)
                    val deviceName = printRey(loremX500, logoPath);
                    result.success("printer device Name : $deviceName");
                } catch (e: Exception) {
                    result.success("${e.message}");
                }
            }
            else -> {
                result.notImplemented()
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }

    private fun printRey(loremX500: String, logoPath: String?): String {
        Log.d("PosType", Constant.posType)

        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888

//        val arrayInputStream = ByteArrayInputStream(logoBitmap)
//
//        val bitmap = BitmapFactory.decodeStream(arrayInputStream,null,options)


        var text = "\n---------------------------- \n"
        text += "${getDeviceName()}" + "\n"
        text += "\n---------------------------- \n"
        text += "\n$loremX500 \n"

        if (logoPath != null)
            printerObject?.printReyBitmap(logoPath)

        text += "\n"
        printerObject?.printRey(text)


        return "${getDeviceName()}"
    }


    fun getDeviceName(): String? {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return model
    }

}
