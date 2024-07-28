import 'dart:typed_data';

import 'package:all_printer/utils/logger.dart';
import 'package:all_printer/utils/screen_shot.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';
import 'package:flutter_usb_printer/flutter_usb_printer.dart';
import 'package:permission_handler/permission_handler.dart';
import 'dart:io';

import 'dart:io';
import 'dart:ui' as ui;
import 'package:path_provider/path_provider.dart';


import 'all_printer_platform_interface.dart';

class AllPrinter {
  Future<String?> getPlatformVersion() {
    return AllPrinterPlatform.instance.getPlatformVersion();
  }

  Future<String?> getDeviceSerial() {
    return AllPrinterPlatform.instance.getDeviceSerial();
  }

  Future<String?> paperCut() {
    return AllPrinterPlatform.instance.paperCut();
  }

  Future<String?> printCashBox() {
    return AllPrinterPlatform.instance.printCashBox();
  }

  Future<String?> printImage({required String imagePath}) {
    return  AllPrinterPlatform.instance.printImage(imagePath);
  }

  Future<String?> print({required dynamic invoice,int? size=1,int? alignment=1,int? textDirection=1,String? logoPath}) {

    return AllPrinterPlatform.instance.print(invoice,size,alignment,textDirection,logoPath);
  }

  /// printSingleLine
  /// @funParameter Does something fun
  /// 	- String (text to print)
  /// 	- int(size of text)[1-5]
  /// 	- int(direction of text)[1-3]
  /// 	- int(font of text)[1-5]
  /// 	- int(alignement of text)[1-3]
  /// 	- boolean(true for bold text, else false)
  /// 	- boolean(true for underline text, else false)
  Future<String?> printSingleLine({required String line,int? size=1,int?alignment=1,int?textDirection=1}) {
    return AllPrinterPlatform.instance.printLine(line,size,alignment,textDirection);
  }

  Future printReyFinish() {
    return AllPrinterPlatform.instance.printFinish();
  }

  Future getPermission() async {
    if (!(await Permission.storage.isGranted)) {
      await Permission.storage.request();
    }
  }

  // # logo image # w=348  h=133 dep =24 dpi =120 B&W
  Future download(Dio dio, String url, String savePath) async {
    // if(!(await Permission.storage.isGranted)){
    //   await Permission.storage.request();
    // }
    if (!(await File(savePath).exists())) {
      return await AllPrinterPlatform.instance.download(dio, url, savePath);
    } else {
      AppLogger.logInfo("download(): $savePath File exists ! ");
      return true;
    }
  }

  Future<String> getDownloadPath(String? uniqueId) async {
    return await AllPrinterPlatform.instance.getDownloadPath(uniqueId);
  }

  Future<String> getExternalDocumentPath({String? folder}) async {
    return await AllPrinterPlatform.instance.getExternalDocumentPath(folder: folder);
  }

  Future<String?> printQrCode({required String? qrData}) async {
    return await AllPrinterPlatform.instance.printQrCode(qrData);
  }
  Future<String?> printBarcode({required String? codeData}) async {
    return await AllPrinterPlatform.instance.printBarcode(codeData);
  }

  GlobalKey<OverRepaintBoundaryState> globalKey = GlobalKey<OverRepaintBoundaryState>();
  FlutterUsbPrinter flutterUsbPrinter = FlutterUsbPrinter();

  connect(int vendorId, int productId) async {
    bool? returned = false;
    try {
      returned = await flutterUsbPrinter.connect(vendorId, productId);
      AppLogger.logInfo("returned $returned");
    } on PlatformException {
      //response = 'Failed to get platform version.';
    }
    return returned;
  }

  printScreen({bool? openCashBox=false,bool? runPrintReyFinish=false}) async {
    try {
      var renderObject =
      globalKey.currentContext?.findRenderObject();

      RenderRepaintBoundary? boundary = renderObject as RenderRepaintBoundary?;
      ui.Image captureImage = await boundary!.toImage(pixelRatio: 2);

      ByteData? byteData =
      await captureImage.toByteData(format: ui.ImageByteFormat.png);
      Uint8List pngBytes = byteData!.buffer.asUint8List();

      final directory = await getApplicationDocumentsDirectory();
      final imagePath = await File('${directory.path}/image.png').create();
      await imagePath.writeAsBytes(pngBytes);

      AppLogger.logInfo("imagePath $imagePath");
      // showDialog(builder: (context) =>  AlertDialog(content: SizedBox(width: 1000,child: Image.memory(pngBytes),),), context: context ,);
      // return ;

      await printImage(imagePath: imagePath.path);
      if (openCashBox==true) {
        await printCashBox();
      }

      if(runPrintReyFinish==true)
      {
       Future.delayed(const Duration(milliseconds: 800),()async {
         await printReyFinish();
       },);
      }


    } catch (e) {
      // Future.delayed(
      //   const Duration(seconds: 1),
      //   () => printScreen(),
      // );
    }
  }



}
