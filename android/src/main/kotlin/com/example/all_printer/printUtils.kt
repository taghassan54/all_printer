package com.example.all_printer


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.os.Environment
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.util.Log
import android.util.Printer
import com.example.all_printer.SunmiRestaurant.AidlUtil
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.mediatek.settings.service.DeviceInfo
import com.mobiiot.androidqapi.api.CsDevice
import com.mobiiot.androidqapi.api.CsLocation
import com.mobiiot.androidqapi.api.CsOperation
import com.mobiiot.androidqapi.api.CsPrinter
import com.mobiiot.androidqapi.api.Utils.PrinterServiceUtil
import com.mobiiot.androidqapi.api.Utils.ServiceUtil
import com.sagereal.printer.PrinterInterface
import java.io.*
import java.net.URL
import java.nio.channels.Channels
import java.util.*


class PrintingMethods {


    companion object {
        @SuppressLint("StaticFieldLeak")
        var LoginActivity: Context? = null
        var sizeValue = 0
        var fontValue: kotlin.Int = 0
        var directionValue: kotlin.Int = 0
        var alignmentValue: kotlin.Int = 0



        var print: Printer? = null

        @SuppressLint("StaticFieldLeak")
//        private var mPrinter: wangpos.sdk4.libbasebinder.Printer? = null
        private val bloop = false
        private var bthreadrunning = false
        private val printInterfaceService: PrinterInterface? = null


    }

    constructor(context: Context) {
        LoginActivity = context

        Constant.posType = getDeviceName()
    }

    fun initializePrinters() {

        Log.d("PosType", Constant.posType)
        try {
            if (LoginActivity != null) {
                PrinterServiceUtil.bindService(LoginActivity)
                ServiceUtil.bindRemoteService(LoginActivity)
                Log.d("success", "Printer Service Util Started Successfully")
            } else {
                Log.e("PrinterNotBound", LoginActivity.toString() + "")
            }

//                LoginActivity.bindService(getPrintIntent(), serviceConnection, Service.BIND_AUTO_CREATE);
        } catch (ex: java.lang.Exception) {
            Log.e("Rey Muzamil MP3_Plus", ex.toString() + "")
        }
    }

    fun isProbablyArabic(s: String): Boolean {
        var i = 0
        while (i < s.length) {
            val c = s.codePointAt(i)
            if (c >= 0x0600 && c <= 0x06E0) return true
            i += Character.charCount(c)
        }
        return false
    }

    fun preprocessLines(line: String): ArrayList<String> {
        val lines = ArrayList<String>()
        var start = 0
        while (line.substring(start).contains("\n")) {
            var end = start + line.substring(start).indexOf("\n") + 1
            end = Math.min(end, line.length)
            lines.add(line.substring(start, end))
            start = end
        }
        if (start < line.length) lines.add(line.substring(start))
        return lines
    }


    private fun checkSize(
        arrayList: ArrayList<*>,
        temp: java.lang.StringBuilder
    ): StringBuilder {
        if (arrayList.size > 100) {
            temp.setLength(0)
            for (i in 0..99) {
                temp.append(
                    """
                    ${arrayList[i]}
                    
                    """.trimIndent()
                )
                arrayList.removeAt(i)
            }
        } else for (i in arrayList.indices) temp.append(
            """
            ${arrayList[i]}
            
            """.trimIndent()
        )
        return temp
    }

    fun printRey(string: String) {
        var string = string

        val temp = StringBuilder()
        val arrayList = ArrayList<String>()
        arrayList.clear()
        temp.setLength(0)
        val scan = Scanner(string)
        val sb = StringBuilder(string)
        while (scan.hasNextLine()) {
            val oneLine = scan.nextLine()
            arrayList.add(oneLine)
            temp.append(
                """
                            $oneLine
                            
                            """.trimIndent()
            )
        }
        scan.close()
        if (arrayList.size > 100) {
            var i = 0
            while (i < arrayList.size) {
                val text: StringBuilder = checkSize(arrayList, temp)

                //  CsPrinter.printText_FullParam2_r(string, 0, 1, 1, 0, false, false, false);
                val result = CsPrinter.printText_FullParam2_r(
                    text.toString() + "",
                    0, directionValue, 1, alignmentValue, false, false, false
                )
                i = i + 100
            }
        } else {
            val result = CsPrinter.printText_FullParam2_r(
                string + "",
                0, directionValue, 1, alignmentValue, false, false, false
            )
        }
    }

    fun printRey(string: String, size: Int) {
        try {
            Log.e("printReyprintRey", "$string size:$size")
            Log.e("Constant.posType", Constant.posType.toString() + " ")


            try {
                if (Constant.isArabicPrintAllowed || isProbablyArabic(string)) CsPrinter.printText_FullParm(
                    string,
                    size - 1,
                    1,
                    1,
                    0,
                    false,
                    false
                ) else CsPrinter.printText_FullParm(string, size - 1, 0, 2, 0, false, false)

                Log.e("printReyprintRey 1",  "${ CsPrinter.getLastError()}");
                Log.e("printReyprintRey 2",   " ${CsPrinter.getPrinterStatus()} ");
                Log.e("printReyprintRey 3",  " ${CsPrinter.getCurrentVoltageStatus() } ");
                Log.e("printReyprintRey 4",  " ${CsPrinter.getPaperStatus() } ");
                Log.e("printReyprintRey 5",  " ${CsPrinter.getPowerState() } ");
                Log.e("printReyprintRey 6",  " ${CsPrinter.getTempStatus()} ");
                Log.e("printReyprintRey 7",  " ${CsPrinter.printGetPrintedLength() } ");
            } catch (ex: Exception) {
                Log.e("Rey Exception MP3_Plus", ex.toString() + "")
            }


        } catch (ex: java.lang.Exception) {
            Log.e("9 Exception Printtt", ex.toString() + "")
        }
    }

    fun printQrCode(bitmap: Bitmap?, string: String?) {
        try {
            Log.e("Constant.posType", Constant.posType.toString() + " QRPint")

            try {
                try {
                    CsPrinter.printBitmap(
                        CsPrinter.createBarQrCode(
                            string,
                            BarcodeFormat.QR_CODE,
                            384,
                            384
                        )
                    )
                } catch (e: WriterException) {
                    e.printStackTrace()
                }
                CsPrinter.printEndLine()
            } catch (ex: java.lang.Exception) {
                Log.e("Rey Exception MP3_Plus", ex.toString() + "")
            }

        } catch (ex: java.lang.Exception) {
            Log.e("9 Exception Printtt", ex.toString() + "")
        }
    }

    fun printReyFinish() {
        Log.d("printReyFinish", "called")

        try {
            CsPrinter.printText("\n\n\n\n")
            CsPrinter.printEndLine()
        } catch (ex: java.lang.Exception) {
            Log.e("Rey Exception MP3_Plus", ex.toString() + "")
        }

    }


    private fun cutPaper() {
        if (Constant.posType == "T2mini" || Constant.posType == "T1mini-G" || Constant.posType == "T2mini_s" || Constant.posType == "D2mini"
        ) {
            try {
                AidlUtil.woyouService.cutPaper(null)
            } catch (ex: java.lang.Exception) {
                Log.e("Drawer Exception", ex.toString() + "")
            }
        }
    }


    fun printReyBitmap(string: String) {
        Log.d("printReyBitmap", "called")
        try {
            try {
                Log.d("printReyBitmap", "im in right place")
                Log.d(
                    "printReyBitmap",
                    LoginActivity?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                        ?.getPath()
                        .toString() + "/unzipFolder/files/10001002/logo.bmp"
                )
                printRey("\n", 1)

//                    if(true)
//                        return;
//                    Image2();
                var file = File(string)
                if (!file.exists()) file = File(
                    LoginActivity?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                        ?.getPath()
                        .toString() + "/unzipFolder/files/10001002/logo.bmp"
                )
                val options = BitmapFactory.Options()
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
                val bitmap =
                    BitmapFactory.decodeStream(FileInputStream(file), null, options)
                val arrayBitmap = ArrayList<Bitmap?>()
                val empty: Bitmap =
                    drawText("", null, 20, true, false, 0, null)
                arrayBitmap.add(empty)
                arrayBitmap.add(bitmap)
                val is_black = FileInputStream(string)
                val input: ByteArray = InputStreamToByte(is_black)
                CsPrinter.printBitmap(input)
                val size = file.length().toInt()
                val bytes = ByteArray(size)
                try {
                    val buf = BufferedInputStream(FileInputStream(file))
                    buf.read(bytes, 0, bytes.size)
                    buf.close()
                } catch (e: FileNotFoundException) {
                    Log.e("Rey Exception MP3_Plus", e.toString() + "")

                    // TODO Auto-generated catch block
                    e.printStackTrace()
                } catch (e: IOException) {
                    Log.e("Rey Exception MP3_Plus", e.toString() + "")

                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }
            } catch (ex: java.lang.Exception) {
                Log.e("Rey Exception MP3_Plus", ex.toString() + "")
            }
            try {
                Log.e("printReyprintRey 1", CsPrinter.getLastError().toString() + " ")
                Log.e("printReyprintRey 2", CsPrinter.getPrinterStatus().toString() + " ")
                Log.e(
                    "printReyprintRey 3",
                    CsPrinter.getCurrentVoltageStatus().toString() + " "
                )
                Log.e("printReyprintRey 4", CsPrinter.getPaperStatus().toString() + " ")
                Log.e("printReyprintRey 5", CsPrinter.getPowerState().toString() + " ")
                Log.e("printReyprintRey 6", CsPrinter.getTempStatus().toString() + " ")
                Log.e(
                    "printReyprintRey 7",
                    CsPrinter.printGetPrintedLength().toString() + " "
                )
            } catch (ex: java.lang.Exception) {
                Log.e("printReyprintRey", ex.message!!)
            }
            printRey("\n", 1)

        } catch (ex: java.lang.Exception) {
            Log.e("1 Exception WiseNet", ex.toString() + "")
        }
    }

    fun screenShot(){
        CsOperation.screenShot()
    }

    fun enableLocation(){
        CsLocation.enableLocation()
    }
    fun disableLocation(){
        CsLocation.disableLocation()
    }

    fun getLocation(): String? {
       return CsLocation.getLocation()
    }

    fun getAddress(): String? {
       return CsLocation.getAddress()
    }

    fun getDeviceInformation(): DeviceInfo? {
        return CsDevice.getDeviceInformation()
    }


    private fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        return Build.MODEL
    }

    fun getBlackWhiteBitmap(bitmap: Bitmap?): Bitmap? {
        val w = bitmap?.width
        val h = bitmap?.height
        val resultBitmap = Bitmap.createBitmap(w!!, h!!, Bitmap.Config.RGB_565)
        var color = 0
        var a: Int
        var r: Int
        var g: Int
        var b: Int
        var r1: Int
        var g1: Int
        var b1: Int
        val oldPx = IntArray(w * h)
        val newPx = IntArray(w * h)
        bitmap.getPixels(oldPx, 0, w, 0, 0, w, h)
        for (i in 0 until w * h) {
            color = oldPx[i]
            r = Color.red(color)
            g = Color.green(color)
            b = Color.blue(color)
            a = Color.alpha(color)
            //黑白矩阵
            r1 = (0.33 * r + 0.59 * g + 0.11 * b).toInt()
            g1 = (0.33 * r + 0.59 * g + 0.11 * b).toInt()
            b1 = (0.33 * r + 0.59 * g + 0.11 * b).toInt()
            //检查各像素值是否超出范围
            if (r1 > 255) {
                r1 = 255
            }
            if (g1 > 255) {
                g1 = 255
            }
            if (b1 > 255) {
                b1 = 255
            }
            newPx[i] = Color.argb(a, r1, g1, b1)
        }
        resultBitmap.setPixels(newPx, 0, w, 0, 0, w, h)
        return getGreyBitmap(resultBitmap)
    }

    fun getGreyBitmap(bitmap: Bitmap?): Bitmap? {
        return if (bitmap == null) {
            null
        } else {
            val width = bitmap.width
            val height = bitmap.height
            val pixels = IntArray(width * height)
            bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
            val gray = IntArray(height * width)
            var e: Int
            var i: Int
            var j: Int
            var g: Int
            e = 0
            while (e < height) {
                i = 0
                while (i < width) {
                    j = pixels[width * e + i]
                    g = j and 16711680 shr 16
                    gray[width * e + i] = g
                    ++i
                }
                ++e
            }
            i = 0
            while (i < height) {
                j = 0
                while (j < width) {
                    g = gray[width * i + j]
                    if (g >= 128) {
                        pixels[width * i + j] = -1
                        e = g - 255
                    } else {
                        pixels[width * i + j] = -16777216
                        e = g - 0
                    }
                    if (j < width - 1 && i < height - 1) {
                        gray[width * i + j + 1] += 3 * e / 8
                        gray[width * (i + 1) + j] += 3 * e / 8
                        gray[width * (i + 1) + j + 1] += e / 4
                    } else if (j == width - 1 && i < height - 1) {
                        gray[width * (i + 1) + j] += 3 * e / 8
                    } else if (j < width - 1 && i == height - 1) {
                        gray[width * i + j + 1] += e / 4
                    }
                    ++j
                }
                ++i
            }
            val mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            mBitmap.setPixels(pixels, 0, width, 0, 0, width, height)
            mBitmap
        }
    }

    fun round(value: Double, places: Int): Double {
        var value = value
        require(places >= 0)
        val factor = Math.pow(10.0, places.toDouble()).toLong()
        value = value * factor
        val tmp = Math.round(value)
        return tmp.toDouble() / factor
    }

    fun downloadFile(url: URL, outputFileName: String) {
        url.openStream().use {
            Channels.newChannel(it).use { rbc ->
                FileOutputStream(outputFileName).use { fos ->
                    fos.channel.transferFrom(rbc, 0, Long.MAX_VALUE)
                }
            }
        }
    }

    fun drawText(
        text: String?,
        textTwo: String?,
        textSize: Int,
        isBold: Boolean,
        isUnderline: Boolean,
        align: Int,
        ttf: Typeface?
    ): Bitmap {
        var text = text
        val textWidth = 384
        if (textTwo != null) {
            val llen = 20
            val rlen = 20
            val text1: String = padRight(text, rlen)
            val text2: String = padLeft(textTwo, llen)
            text = text1 + text2
        }
        val textPaint = TextPaint(
            Paint.ANTI_ALIAS_FLAG
                    or Paint.LINEAR_TEXT_FLAG
        )
        textPaint.style = Paint.Style.FILL
        textPaint.color = Color.BLACK
        textPaint.textAlign = Paint.Align.LEFT
        textPaint.textSize = textSize.toFloat()
        textPaint.isFakeBoldText = isBold
        textPaint.typeface = ttf
        if (isUnderline) textPaint.flags = Paint.UNDERLINE_TEXT_FLAG
        var mTextLayout = StaticLayout(
            text, textPaint,
            textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false
        )
        if (align == 0) mTextLayout = StaticLayout(
            text, textPaint,
            textWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false
        ) else if (align == 1) mTextLayout = StaticLayout(
            text, textPaint,
            textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false
        ) else if (align == 2) mTextLayout = StaticLayout(
            text, textPaint,
            textWidth, Layout.Alignment.ALIGN_OPPOSITE, 1.0f, 0.0f, false
        )
        val b = Bitmap.createBitmap(textWidth, mTextLayout.height, Bitmap.Config.RGB_565)
        val c = Canvas(b)
        val paint = Paint(
            (Paint.ANTI_ALIAS_FLAG
                    or Paint.LINEAR_TEXT_FLAG)
        )
        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        c.drawPaint(paint)
        c.save()
        c.translate(0f, 0f)
        mTextLayout.draw(c)
        c.restore()
        return b
    }


    fun padRight(s: String?, n: Int): String {
        return String.format("%1$-" + n + "s", s)
    }

    fun padLeft(s: String?, n: Int): String {
        return String.format("%1$" + n + "s", s)
        //String.format("%10s", "foo");
    }

    fun FooterPrint() {
//        pDialog.hide();
    }

    @Throws(IOException::class)
    fun InputStreamToByte(`is`: InputStream): ByteArray {
        val bytestream = ByteArrayOutputStream()
        val buffer = ByteArray(1024)
        var ch: Int
        while (`is`.read(buffer).also { ch = it } != -1) {
            bytestream.write(buffer, 0, ch)
        }
        val data = bytestream.toByteArray()
        bytestream.close()
        return data
    }

    fun returnStars(): String {
        return when (Constant.posType) {
            "T2mini", "T1mini-G", "T2mini_s", "D2mini", "D4-505", "D1", "D1-Pro", "M2-Max", "Swift 1","S1", "M2-Pro" -> "************************************************"
            "MP3_Plus", "MP4", "Mobiwire MP4", "MobiPrint4_Plus", "k80hd_bsp_fwv_512m" -> "******************************"
            else -> "******************************"
        }
    }

    fun returnLines(): String {
        return when (Constant.posType) {
            "T2mini", "T1mini-G", "T2mini_s", "D2mini", "D4-505", "D1", "D1-Pro", "M2-Max", "Swift 1","S1", "M2-Pro" -> "------------------------------------------------"
            "MP3_Plus", "MP4", "Mobiwire MP4", "MobiPrint4_Plus", "k80hd_bsp_fwv_512m" -> "--------------------------------"
            else -> "--------------------------------"
        }
    }


}