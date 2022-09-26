#import "AllPrinterPlugin.h"
#if __has_include(<all_printer/all_printer-Swift.h>)
#import <all_printer/all_printer-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "all_printer-Swift.h"
#endif

@implementation AllPrinterPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftAllPrinterPlugin registerWithRegistrar:registrar];
}
@end
