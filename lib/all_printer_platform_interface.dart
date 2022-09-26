import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'all_printer_method_channel.dart';

abstract class AllPrinterPlatform extends PlatformInterface {
  /// Constructs a AllPrinterPlatform.
  AllPrinterPlatform() : super(token: _token);

  static final Object _token = Object();

  static AllPrinterPlatform _instance = MethodChannelAllPrinter();

  /// The default instance of [AllPrinterPlatform] to use.
  ///
  /// Defaults to [MethodChannelAllPrinter].
  static AllPrinterPlatform get instance => _instance;
  
  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [AllPrinterPlatform] when
  /// they register themselves.
  static set instance(AllPrinterPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
  Future<String?> print(dynamic invoice){
    throw UnimplementedError('print() has not been implemented.');
  }
}
