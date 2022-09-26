import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'all_printer_platform_interface.dart';

/// An implementation of [AllPrinterPlatform] that uses method channels.
class MethodChannelAllPrinter extends AllPrinterPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('all_printer');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<String?> print(dynamic invoice)async{
    final printResult = await methodChannel.invokeMethod<String>('print',invoice);
    return printResult;
  }
}
