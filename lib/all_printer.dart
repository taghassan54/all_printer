
import 'all_printer_platform_interface.dart';

class AllPrinter {
  Future<String?> getPlatformVersion() {
    return AllPrinterPlatform.instance.getPlatformVersion();
  }

  Future<String?> print(dynamic invoice) {
    return AllPrinterPlatform.instance.print(invoice);
  }



}
