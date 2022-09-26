import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:all_printer/all_printer.dart';
import 'package:bitmap/bitmap.dart';
import 'package:dio/dio.dart';
import 'package:path_provider/path_provider.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  final _allPrinterPlugin = AllPrinter();

  var dio = Dio();

  dynamic invoice;
  getInvoice()async{
    Bitmap bitmap = await Bitmap.fromProvider(const NetworkImage("http://smartepaystaging.altkamul.ae/Content/img/printing.bmp",),); // Notice this is an async operation

     invoice=<String, dynamic>{
      'date': "Date:2022-01-30 10:25:35",
      'name': "Name: Altkamul Printer Test",
      'merchent': "Merchent ID: 10001002",
      'terminal': "Terminal ID: 667766776",
      'transaction': "Transaction ID: 10000001",
      'voucher': "Voucher No: 22-003111",
      'car': "Car No: 1001k",
      'customer': "Customer No: 971512345678",
      'star1': "******************************",
      'title': "Tax Invoice",
      'logoBitmap':bitmap.content,
      // 'logoBitmap':bitmap.content.toString(),
      'star2': "******************************",
      'product': "Title: Exterir Wash Small Car",
      'service': "service: Wash",
      'price': "price: 35.00",
      'qty': "qty: 2",
      'tqty': "Total Qty: 2",
      'totalbeforvat': "Total Befor Vat: 70.00 AED",
      'vat': "Vat: @5%: 11.00 AED",
      'star3': "-------------------------------",
      'total': "Total: 71.00 AED",
      'star4': "******************************",
      'address': "City: Dubai \nUAE \nCall Us : 05123456789\n",
      'star5': "-------------------------------",
      'footer': "Thanks you for try our Flutter base POS",
    };

  }


  @override
  void initState() {
    super.initState();

  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    try {

      var tempDir = await getApplicationDocumentsDirectory();
      String fullPath = "${tempDir.path}/logo.bmp";
      if (kDebugMode) {
        print('full path $fullPath');
      }

    await getInvoice();
      platformVersion =
          await _allPrinterPlugin.print(invoice) ?? 'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
        floatingActionButton: FloatingActionButton(onPressed:()=> initPlatformState(),child:const Icon(Icons.print)),
        body: Center(
          child: Text('print result: $_platformVersion\n'),
        ),
      ),
    );
  }
}
