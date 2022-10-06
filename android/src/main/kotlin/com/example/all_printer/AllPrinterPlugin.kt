package com.example.all_printer

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import androidx.core.content.ContextCompat as Compat
import com.imin.library.SystemPropManager


/** AllPrinterPlugin */
class AllPrinterPlugin : FlutterPlugin, MethodCallHandler, ActivityAware {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel

    var mContext: Context? = null
    var aContext: Context? = null

    private var activity: Activity? = null

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

                val deviceModel: String = SystemPropManager.getModel()
                val brand = SystemPropManager.getBrand()

                result.success(
                    "Android ${android.os.Build.VERSION.RELEASE} \n" +
                            " Device Name : ${getDeviceName()} \n device Model :   $deviceModel \n Brand : $brand"
                )

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
                    printerObject?.printQrCode(null, "\n ${call.arguments} \n")
                    result.success("success !")
                } catch (e: Exception) {
                    result.success("${e.message}");
                }

            }
            "printLine" -> {

                if (call.arguments != null) {
                    try {
                        printerObject?.printRey("${call.arguments}")
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
//                        printerObject?.printRey("\n")
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

                    val hashMap = call.arguments as HashMap<*, *>

                    val logoPath = call.argument<String>("logoPath")

                    var loremX500 = ""

                    var index=0
                    hashMap.forEach {
                        if(it.key!="logoPath")
                        loremX500 += "${hashMap["$index"]} \n"
                        index++
                    }
                    Log.d("loremX500", loremX500)
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
        var text="";

//         text += "\n---------------------------- \n"
//        text += "${getDeviceName()}" + "\n"
//        text += "\n---------------------------- \n"
        text += "\n$loremX500 \n"

        if (logoPath != null)
            printerObject?.printReyBitmap(logoPath)

        text += "\n"
        printerObject?.printRey(text,45)


        return "${getDeviceName()}"
    }


    fun getDeviceName(): String? {
        val manufacturer = Build.MANUFACTURER
        return Build.MODEL
    }

    private fun checkPermission() {
        if (mContext != null)
            if (Compat.checkSelfPermission(
                    mContext!!,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) !== PackageManager.PERMISSION_GRANTED
            ) {
                if (activity != null) {
                    ActivityCompat.requestPermissions(
                        activity!!, arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ), 0
                    )
                }
            } else {
                Toast.makeText(mContext, "Permission already granted", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        activity = binding.activity
    }

    override fun onDetachedFromActivityForConfigChanges() {

    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {

    }

    override fun onDetachedFromActivity() {

    }

}
