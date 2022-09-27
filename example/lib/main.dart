import 'package:all_printer/models/InvoiceListModel.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:all_printer/all_printer.dart';
import 'package:dio/dio.dart';

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

  InvoiceListModel? invoiceListModel;
  dynamic invoice = [];
  String invoiceText = '';
  String merchantId = "50608101";

  getInvoice() async {
    invoiceListModel = InvoiceListModel(invoice: [
      Invoice(
          key: 'text',
          value: "The Quick Brown fox jumped over The Lazy Dog test"),
      Invoice(key: 'date', value: "2022-01-30 10:25:35"),
      Invoice(key: 'merchent', value: "Merchent ID: $merchantId"),
      Invoice(key: 'terminal', value: "Terminal ID: 11111111"),
      Invoice(key: 'star1', value: "****************&&**************"),
    ]);

    invoice = <String, dynamic>{
      'text': "The Quick Brown fox jumped over The Lazy Dog",
      'date': "Date:2022-01-30 10:25:35",
      'name': "Name: Altkamul Printer Test",
      'merchent': "Merchent ID: $merchantId",
      'terminal': "Terminal ID: 667766776",
      'transaction': "Transaction ID: 10000001",
      'voucher': "Voucher No: 22-003111",
      'car': "Car No: 1001k",
      'customer': "Customer No: 971512345678",
      'star1': "******************************",
      'title': "Tax Invoice",
      // 'logoPath':
      //     "/storage/emulated/0/Download/unzipFolder/files/10001002/printing.bmp",
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
      'address': "City: Dubai UAE Call Us : 05123456789",
      'star5': "-------------------------------",
      'footer': "Thanks you for try our Flutter base POS",
    };

    for (Invoice item in invoiceListModel?.invoice ?? []) {
      invoice[item.key] = item.value;
    }
  }

  @override
  void initState() {
    _allPrinterPlugin.getPermission();
    super.initState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion = 'starting ... ';

    String fullPath = await _allPrinterPlugin.getDownloadPath(merchantId);

    bool isDone = await _allPrinterPlugin.download(
        dio,
        "http://smartepaystaging.altkamul.ae/Content/Merchants/$merchantId/$merchantId/printing.bmp",
        fullPath);

    await getInvoice();
    // invoice['logoPath'] = fullPath;

    if (isDone) {
      platformVersion =
          await _allPrinterPlugin.printImage(imagePath: fullPath) ?? '';
    }

    platformVersion = await _allPrinterPlugin.print(invoice: invoice) ?? '';

    platformVersion =
        await _allPrinterPlugin.printSingleLine(line: "this normal text !") ??
            '';
    _allPrinterPlugin.printQrCode(qrData: "data");

    _allPrinterPlugin.printReyFinish();

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
        // floatingActionButton: FloatingActionButton(
        //     onPressed: () => initPlatformState(),
        //     child: const Icon(Icons.print)),
        body: SingleChildScrollView(
          padding: const EdgeInsets.all(15),
          child: Column(
            children: [
              const SizedBox(
                height: 20,
              ),
              Center(
                child: Text('print result: $_platformVersion\n'),
              ),

              ElevatedButton(
                onPressed: () => initPlatformState(),
                child: const SizedBox(
                  width: double.infinity,
                  child: Text("Print Full invoice", textAlign: TextAlign.center),
                ),
              ),

              ElevatedButton(
                onPressed: () => printText(),
                child: const SizedBox(
                  width: double.infinity,
                  child: Text("Print Text", textAlign: TextAlign.center),
                ),
              ),
              ElevatedButton(
                onPressed: () => printImage(),
                child: const SizedBox(
                  width: double.infinity,
                  child: Text("Print Image", textAlign: TextAlign.center),
                ),
              ),
              ElevatedButton(
                onPressed: () => printQrCode(),
                child: const SizedBox(
                  width: double.infinity,
                  child: Text("Print QrCode", textAlign: TextAlign.center),
                ),
              )
            ],
          ),
        ),
      ),
    );
  }

  printText() async {
    String platformVersion = 'starting ... ';
    await getInvoice();
    platformVersion = await _allPrinterPlugin.printSingleLine(
            line:
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.") ??
        '';
    _allPrinterPlugin.printReyFinish();
    setState(() {
      _platformVersion = platformVersion;
    });
  }

  printImage()async {
    String platformVersion = 'starting ... ';

    String fullPath = await _allPrinterPlugin.getDownloadPath(merchantId);

    bool isDone = await _allPrinterPlugin.download(
        dio,
        "http://smartepaystaging.altkamul.ae/Content/Merchants/$merchantId/$merchantId/printing.bmp",
        fullPath);
    if (isDone) {
      platformVersion =
          await _allPrinterPlugin.printImage(imagePath: fullPath) ?? '';
      _allPrinterPlugin.printReyFinish();
    }
    setState(() {
      _platformVersion = platformVersion;
    });
  }

  printQrCode() async{
    String platformVersion = 'starting ... ';
    platformVersion =
        await _allPrinterPlugin.printQrCode(qrData: "this is Data ") ?? '';
    _allPrinterPlugin.printReyFinish();
    setState(() {
      _platformVersion = platformVersion;
    });
  }
}
