import 'dart:io';
import 'dart:ui' as ui;

import 'package:all_printer/all_printer.dart';
import 'package:all_printer/utils/logger.dart';
import 'package:all_printer/utils/screen_shot.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';

import 'package:path_provider/path_provider.dart';

class ReceiptScreen extends StatelessWidget {

  final int orderId;
  final allPrinterPlugin;
  final bool isReprint;
  final double transactionClaimAmount;


  ReceiptScreen(
      {super.key,

      required this.orderId,
required this.allPrinterPlugin,
      required this.transactionClaimAmount,

      this.isReprint = false}) {
    // Future.delayed(
    //   const Duration(milliseconds: 1000),
    //   () => printScreen(),
    // );
  }


  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      body: Capturer(
        overRepaintKey: allPrinterPlugin.globalKey,
        child: Container(
          color: Colors.white,
          child: SingleChildScrollView(
            padding: const EdgeInsets.all(16.0),
            child: Container(
              // width: MediaQuery.of(context).size.width * 0.2,
              color: Colors.white,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: <Widget>[
                  Image.network("https://raw.githubusercontent.com/taghassan54/printer/main/printing.bmp",width: MediaQuery.of(context).size.width*0.9,),
                  const TextRowDisplayWidget(value: "Text", textKey: "Test"),
                  const TextRowDisplayWidget(value: "Text", textKey: "Test"),
                  const TextRowDisplayWidget(value: "Text", textKey: "Test"),
                  const TextRowDisplayWidget(value: "Text", textKey: "Test"),
                  const TextRowDisplayWidget(value: "Text", textKey: "Test"),
                  const TextRowDisplayWidget(value: "Text", textKey: "Test"),
                  const TextRowDisplayWidget(value: "Text", textKey: "Test"),
                  const TextRowDisplayWidget(value: "Text", textKey: "Test"),
                  const TextRowDisplayWidget(value: "Text", textKey: "Test"),
                  const TextRowDisplayWidget(value: "Text", textKey: "Test"),
                  const TextRowDisplayWidget(value: "Text", textKey: "Test"),
                  const TextRowDisplayWidget(value: "Text", textKey: "Test"),
  
                  Image.network("https://raw.githubusercontent.com/taghassan54/printer/main/printing.bmp",width: MediaQuery.of(context).size.width*0.9,),
                  SizedBox(height: 10,),
                  Text(DateTime.now().toIso8601String(),style: const TextStyle(fontWeight: FontWeight.w600),)

                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}

class TextRowDisplayWidget extends StatelessWidget {
  final String textKey, value;

  const TextRowDisplayWidget(
      {Key? key, required this.value, required this.textKey})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Row(
      mainAxisAlignment: MainAxisAlignment.spaceBetween,
      children: <Widget>[
        Text(
          textKey,
          style: const TextStyle(
            fontSize: 19.0,
            fontWeight: FontWeight.bold,
          ),
        ),
        Text(
          value,
          style:const TextStyle(
            fontSize: 17.0,
            fontWeight: FontWeight.w500,
          ),
        ),
      ],
    );
  }
}
