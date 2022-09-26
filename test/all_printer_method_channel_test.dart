import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:all_printer/all_printer_method_channel.dart';

void main() {
  MethodChannelAllPrinter platform = MethodChannelAllPrinter();
  const MethodChannel channel = MethodChannel('all_printer');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
