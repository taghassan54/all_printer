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
