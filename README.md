# printer

```
  all_printer:
    git:
      url: https://github.com/taghassan54/all_printer.git
      ref: master # branch name
```
## import 

```
import 'package:all_printer/all_printer.dart';
```
## use 
```

 final _allPrinterPlugin = AllPrinter();
 
 Dio dio =Dio();
 
```
 #
```

>  image  width=348  height=133 depth =24  black and white

 for image print use this :
 
 String path = await _allPrinterPlugin.getDownloadPath("unique name");
 
 bool isDone = await _allPrinterPlugin.download(
        dio,
        "https://raw.githubusercontent.com/taghassan54/printer/main/printing.bmp",
        path);
        
await _allPrinterPlugin.printImage(imagePath: path) ?? '';
        
```
  #      
 ```
        var index =0 ;
        dynamic  invoice = {
          "$index": "The Quick Brown fox jumped over The Lazy Dog",
          "${++index}": "hello ",
          "${++index}": "السلام عليكم ورحمة الله",
          "${++index}": "Date:2022-01-30 10:25:35",
          "${++index}": "Name: Altkamul Printer Test",
          "${++index}": "Merchent ID: 9000",
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
          "${++index}": "نص ثاني بالعربي",
          "${++index}": "******************************",
          "${++index}": "City: Dubai UAE Call Us : 05123456789",
          "${++index}": "-------------------------------",
          "${++index}": "Thanks you for try our Flutter base POS"
        };
        
         invoice['logoPath'] = path; // from print image section 
         
         await _allPrinterPlugin.print(invoice: invoice) ?? '';
         
         await _allPrinterPlugin.printSingleLine(line: "this normal text !") ??
            '';
```
#
```            
         _allPrinterPlugin.printQrCode(qrData: "data");

```
#
```
          _allPrinterPlugin.printReyFinish();
        
```
