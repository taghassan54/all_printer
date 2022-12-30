import 'package:dio/src/dio.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:all_printer/all_printer.dart';
import 'package:all_printer/all_printer_platform_interface.dart';
import 'package:all_printer/all_printer_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockAllPrinterPlatform 
    with MockPlatformInterfaceMixin
    implements AllPrinterPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');

  @override
  Future<String?> print(dynamic invoice) {
    throw UnimplementedError();
  }



  @override
  Future download(Dio dio, String url, String savePath) {
    // TODO: implement download
    throw UnimplementedError();
  }

  @override
  void showDownloadProgress(received, total) {
    // TODO: implement showDownloadProgress
  }

  @override
  Future<String> getDownloadPath(String? uniqueId) {
    // TODO: implement getDownloadPath
    throw UnimplementedError();
  }

  @override
  Future<String?> printImage(String imagePath) {
    throw UnimplementedError();
  }

  @override
  Future<String?> printLine(String line) {
    throw UnimplementedError();
  }

  @override
  Future printFinish() {
    // TODO: implement printFinish
    throw UnimplementedError();
  }

  @override
  Future<String?> printQrCode(String? qrData) {
    // TODO: implement printQrCode
    throw UnimplementedError();
  }
  
  @override
  Future<String?> getDeviceSerial() {
    // TODO: implement getDeviceSerial
    throw UnimplementedError();
  }
}

void main() {
  final AllPrinterPlatform initialPlatform = AllPrinterPlatform.instance;

  test('$MethodChannelAllPrinter is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelAllPrinter>());
  });

  test('getPlatformVersion', () async {
    AllPrinter allPrinterPlugin = AllPrinter();
    MockAllPrinterPlatform fakePlatform = MockAllPrinterPlatform();
    AllPrinterPlatform.instance = fakePlatform;
  
    expect(await allPrinterPlugin.getPlatformVersion(), '42');
  });
}
