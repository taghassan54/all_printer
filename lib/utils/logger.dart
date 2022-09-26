import 'package:logger/logger.dart';

class AppLogger{
  static var logger = Logger();

  static logDebug(String message)=>logger.d(message);
  static logVerbose(String message)=>logger.v(message);
  static logInfo(String message)=>logger.i(message);
  static logWarning(String message)=>logger.w(message);
  static logError(String message)=>logger.e(message);
  static logWhat(String message)=>logger.wtf(message);
}