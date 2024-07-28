import 'package:all_printer/models/InvoiceListModel.dart';
import 'package:all_printer/utils/logger.dart';
import 'package:all_printer_example/receipt_screen.dart';
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
    // invoiceListModel = InvoiceListModel(invoice: [
    //   Invoice(
    //       key: 'text',
    //       value: "The Quick Brown fox jumped over The Lazy Dog test"),
    //   Invoice(key: 'date', value: "2022-01-30 10:25:35"),
    //   Invoice(key: 'merchent', value: "Merchent ID: $merchantId"),
    //   Invoice(key: 'terminal', value: "Terminal ID: 11111111"),
    //   Invoice(key: 'star1', value: "****************&&**************"),
    // ]);

    //
    try {
      // var response = await Dio().get('http://213.159.5.155:410/invoice.json');
      var index = 0;
      setState(() {
        invoice = {
          "$index": "The Quick Brown fox jumped over The Lazy Dog",
          "${++index}": "hello ",
          // "${++index}": "السلام عليكم ورحمة الله",
          "${++index}": "Date:2022-01-30 10:25:35",
          "${++index}": "Name: Altkamul Printer Test",
          "${++index}": "Merchent ID: $merchantId",
          "${++index}": "Terminal ID: 667766776",
          "${++index}": "Transaction ID: 10000001",
          "${++index}": "Voucher No: 22-003111",
          "${++index}": "Car No: 1001k",
          "${++index}": "Customer No: 971512345678",
          "${++index}": "******************************",
          "${++index}": "size:2",
          "${++index}": "Tax Invoice",
          "${++index}": "size:1",
          "${++index}": "******************************",
          "${++index}": "Title: Exterir Wash Small Car",
          "${++index}": "service: Wash",
          "${++index}": "price: 35.00",
          "${++index}": "qty: 2",
          "${++index}": "Total Qty: 2",
          "${++index}": "Total Befor Vat: 70.00 AED",
          "${++index}": "Vat: @5%: 11.00 AED",
          "${++index}": "-------------------------------",
          "${++index}": "Total: 71.00 AED",
          "${++index}": "******************************",
          // "${++index}": "نص تاني بالعربي",
          "${++index}": "******************************",
          "${++index}": "qrCode:This is QrCode",
          "${++index}": "******************************",
          "${++index}": "City: Dubai UAE Call Us : 05123456789",
          "${++index}": "-------------------------------",
          "${++index}": "Thanks you for try our Flutter base POS"
        };
      });
    } catch (e) {
      print("Error : ${e.toString()}");
    }
    //
    //
    // for (Invoice item in invoiceListModel?.invoice ?? []) {
    //   invoice[item.key] = item.value;
    // }
  }

  @override
  void initState() {
    // _allPrinterPlugin.getPermission();
    _allPrinterPlugin.getDeviceSerial();
    super.initState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion = 'starting ... ';

    String fullPath =
        await _allPrinterPlugin.getExternalDocumentPath(folder: merchantId);

    bool isDone = await _allPrinterPlugin.download(
        dio,
        "https://raw.githubusercontent.com/taghassan54/printer/main/printing.bmp",
        fullPath);

    await getInvoice();

    if (isDone) {
      print("fullPath $fullPath");
      // invoice['logoPath'] = fullPath;
      // invoice['logoPath'] = fullPath;

      // platformVersion =
      //     await _allPrinterPlugin.printImage(imagePath: fullPath) ?? '';
    }

    // platformVersion = await _allPrinterPlugin.printSingleLine(
    //     line: "this normal text !",
    //     textDirection: 0,
    //     alignment: 0,
    //     size: 0) ??
    //     '';
    // await _allPrinterPlugin.printQrCode(qrData: "data");
    //
    // await _allPrinterPlugin.printBarcode(qrData: "123456789000798");


    platformVersion = await _allPrinterPlugin.print(
            invoice: invoice,
            textDirection: 0,
            alignment: 0,
            size: 1,
            logoPath: fullPath) ??
        '';


    await _allPrinterPlugin.printReyFinish();

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
    var screenWidth=MediaQuery.of(context).size.width;
    var screenHeight=MediaQuery.of(context).size.height;

    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title:  Text('Plugin example app'),
        ),
        floatingActionButtonLocation: FloatingActionButtonLocation.centerFloat,
        // floatingActionButton: FloatingActionButton(
        //     onPressed: () => initPlatformState(),
        //     child: const Icon(Icons.print)),
        bottomNavigationBar: Text('print result: $_platformVersion\n'),
        body: screenWidth>700? Row(
          children: [

            SizedBox(width: screenWidth*0.3,child: invoiceScreen(),),
            SizedBox(width: screenWidth*0.7,child: printerTestButtons(),)
          ],
        ) : SingleChildScrollView(
          child: Column(
            children: [
              SizedBox(height: screenHeight*0.4,child: invoiceScreen(),),
              SizedBox(height : screenHeight*0.4,child: printerTestButtons(),)
            ],
          ),
        )
      ),
    );
  }

  printText() async {
    String platformVersion = 'starting ... ';
    await getInvoice();
    platformVersion = await _allPrinterPlugin.printSingleLine(
            line: "Lorem ipsum.", size: 2, alignment: 2, textDirection: 2) ??
        '';
    _allPrinterPlugin.printReyFinish();
    setState(() {
      _platformVersion = platformVersion;
    });
  }

  printTextAr() async {
    String platformVersion = 'starting ... ';
    await getInvoice();
    platformVersion = await _allPrinterPlugin.printSingleLine(
            line: """  مولّد نص لوريم إيبسوم حقيقي على الإنترنت.  """) ??
        '';
    _allPrinterPlugin.printReyFinish();
    setState(() {
      _platformVersion = platformVersion;
    });
  }

  printImage() async {
    String platformVersion = 'starting ... ';

    String fullPath =
        await _allPrinterPlugin.getExternalDocumentPath(folder: merchantId);
    // String fullPath = await _allPrinterPlugin.getDownloadPath(merchantId);

    bool isDone = await _allPrinterPlugin.download(
        dio,
        "https://raw.githubusercontent.com/taghassan54/printer/main/printing.bmp",
        fullPath);

    if (isDone) {
      // fullPath="storage/emulated/0/download/printing.bmp";
      platformVersion =
          await _allPrinterPlugin.printImage(imagePath: fullPath) ?? '';
      _allPrinterPlugin.printReyFinish();
    }
    setState(() {
      _platformVersion = platformVersion;
    });
  }

  printQrCode() async {
    String platformVersion = 'starting ... ';
    platformVersion =
        await _allPrinterPlugin.printQrCode(qrData: "this is Data ") ?? '';
    _allPrinterPlugin.printReyFinish();
    setState(() {
      _platformVersion = platformVersion;
    });
  }

  getPlatformVersion() async {
    String? platformVersion = 'starting ... ';
    platformVersion = await _allPrinterPlugin.getPlatformVersion();
    setState(() {
      _platformVersion = platformVersion!;
    });
  }

  printerTestButtons() =>GridView(
    padding: const EdgeInsets.all(15),
    gridDelegate:  SliverGridDelegateWithFixedCrossAxisCount(
      crossAxisCount: MediaQuery.of(context).size.width > 700 ?7:3,
      crossAxisSpacing: 10.0,
      mainAxisSpacing: 10.0,
    ),
    children: [


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
        onPressed: () => printTextAr(),
        child: const SizedBox(
          width: double.infinity,
          child: Text("Print Arabic Text", textAlign: TextAlign.center),
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
      ),
      ElevatedButton(
        onPressed: () {
          _allPrinterPlugin.printBarcode(codeData: "86881").then((value) {
            _allPrinterPlugin.printBarcode(codeData: "31977"); 
          },);

        },
        child: const SizedBox(
          width: double.infinity,
          child: Text("Print Barcode", textAlign: TextAlign.center),
        ),
      ),

      ElevatedButton(
        onPressed: () => _allPrinterPlugin.printReyFinish(),
        child: const SizedBox(
          width: double.infinity,
          child: Text("Print Finish", textAlign: TextAlign.center),
        ),
      ),

      ElevatedButton(
        onPressed: () => _allPrinterPlugin.paperCut(),
        child: const SizedBox(
          width: double.infinity,
          child: Text("paperCut", textAlign: TextAlign.center),
        ),
      ),

      ElevatedButton(
        onPressed: () => _allPrinterPlugin.printCashBox(),
        child: const SizedBox(
          width: double.infinity,
          child: Text("Open CashBox", textAlign: TextAlign.center),
        ),
      ),

      ElevatedButton(
        onPressed: () => _allPrinterPlugin.printScreen(runPrintReyFinish: true),
        child: const SizedBox(
          width: double.infinity,
          child:
          Text("print Screen", textAlign: TextAlign.center),
        ),
      ),
      ElevatedButton(
        onPressed: () => getPlatformVersion(),
        child: const SizedBox(
          width: double.infinity,
          child:
          Text("Check Platform Version", textAlign: TextAlign.center),
        ),
      )
    ],
  );


  invoiceScreen() =>ReceiptScreen(allPrinterPlugin: _allPrinterPlugin,);
}
