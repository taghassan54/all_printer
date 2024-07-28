package com.example.all_printer.kPrinterAPI

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Parcelable
import android.util.Log

 class UsbBroadCastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val device = intent.getParcelableExtra<Parcelable>(UsbManager.EXTRA_DEVICE) as UsbDevice?
        when (intent.action) {
            UsbManager.ACTION_USB_DEVICE_ATTACHED ->                     // 当USB设备连接到USB总线时，在主机模式下发送此意图。
                Log.d("UsbBroadCastReceiver","插入设备 vid:" + device!!.vendorId + "  pid:" + device.productId)
            UsbManager.ACTION_USB_DEVICE_DETACHED ->                     // 当USB设备在主机模式下脱离USB总线时发送此意图。
                Log.d("UsbBroadCastReceiver","拔出设备 vid:" + device!!.vendorId + "  pid:" + device.productId)
        }
    }

}