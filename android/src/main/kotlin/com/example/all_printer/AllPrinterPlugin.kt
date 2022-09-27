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
                result.success("Android ${android.os.Build.VERSION.RELEASE}")
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



//                    val text = call.argument<String>("text")
//                    val date = call.argument<String>("date")
//                    val name = call.argument<String>("name")
//                    val merchent = call.argument<String>("merchent")
//                    val terminal = call.argument<String>("terminal")
//                    val transaction = call.argument<String>("transaction")
//                    val voucher = call.argument<String>("voucher")
//                    val car = call.argument<String>("car")
//                    val customer = call.argument<String>("customer")
//                    val star1 = call.argument<String>("star1")
//                    val title = call.argument<String>("title")
//                    val star2 = call.argument<String>("star2")
//                    val product = call.argument<String>("product")
//                    val service = call.argument<String>("service")
//                    val price = call.argument<String>("price")
//                    val qty = call.argument<String>("qty")
//                    val tqty = call.argument<String>("tqty")
//                    val totalbeforvat = call.argument<String>("totalbeforvat")
//                    val vat = call.argument<String>("vat")
//                    val star3 = call.argument<String>("star3")
//                    val total = call.argument<String>("total")
//                    val star4 = call.argument<String>("star4")
//                    val address = call.argument<String>("address")
//                    val footer = call.argument<String>("footer")
                    val logoPath = call.argument<String>("logoPath")

                    var loremX500 =   ""
//                    var loremX500 = text + ""
//                    loremX500 += date
//                    loremX500 += "\n"
//                    loremX500 += name
//                    loremX500 += "\n"
//                    loremX500 += merchent
//                    loremX500 += "\n"
//                    loremX500 += terminal
//                    loremX500 += "\n"
//                    loremX500 += transaction
//                    loremX500 += "\n"
//                    loremX500 += voucher
//                    loremX500 += "\n"
//                    loremX500 += car
//                    loremX500 += "\n"
//                    loremX500 += customer
//                    loremX500 += "\n"
//                    loremX500 += star1
//                    loremX500 += "\n"
//                    loremX500 += title
//                    loremX500 += "\n"
//                    loremX500 += star2
//                    loremX500 += "\n"
//                    loremX500 += product
//                    loremX500 += "\n"
//                    loremX500 += service
//                    loremX500 += "\n"
//                    loremX500 += price
//                    loremX500 += "\n"
//                    loremX500 += qty
//                    loremX500 += "\n"
//                    loremX500 += tqty
//                    loremX500 += "\n"
//                    loremX500 += totalbeforvat
//                    loremX500 += "\n"
//                    loremX500 += vat
//                    loremX500 += "\n"
//                    loremX500 += star3
//                    loremX500 += "\n"
//                    loremX500 += total
//                    loremX500 += "\n"
//                    loremX500 += star4
//                    loremX500 += "\n"
//                    loremX500 += address
//                    loremX500 += "\n"
//                    loremX500 += footer
//                    loremX500 += "\n"

                    hashMap.forEach { item ->
                        loremX500 += "\n $item"
                    }
                    Log.d("loremX500","$loremX500")
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
