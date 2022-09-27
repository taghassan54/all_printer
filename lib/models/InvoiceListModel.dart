import 'dart:convert';
/// invoice : [{"key":"date","value":"value"}]

InvoiceListModel invoiceListModelFromJson(String str) => InvoiceListModel.fromJson(json.decode(str));
String invoiceListModelToJson(InvoiceListModel data) => json.encode(data.toJson());
class InvoiceListModel {
  InvoiceListModel({
      List<Invoice>? invoice,}){
    _invoice = invoice;
}

  InvoiceListModel.fromJson(dynamic json) {
    if (json['invoice'] != null) {
      _invoice = [];
      json['invoice'].forEach((v) {
        _invoice?.add(Invoice.fromJson(v));
      });
    }
  }
  List<Invoice>? _invoice;
InvoiceListModel copyWith({  List<Invoice>? invoice,
}) => InvoiceListModel(  invoice: invoice ?? _invoice,
);
  List<Invoice>? get invoice => _invoice;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    if (_invoice != null) {
      map['invoice'] = _invoice?.map((v) => v.toJson()).toList();
    }
    return map;
  }

}

/// key : "date"
/// value : "value"

Invoice invoiceFromJson(String str) => Invoice.fromJson(json.decode(str));
String invoiceToJson(Invoice data) => json.encode(data.toJson());
class Invoice {
  Invoice({
      String? key, 
      String? value,}){
    _key = key;
    _value = value;
}

  Invoice.fromJson(dynamic json) {
    _key = json['key'];
    _value = json['value'];
  }
  String? _key;
  String? _value;
Invoice copyWith({  String? key,
  String? value,
}) => Invoice(  key: key ?? _key,
  value: value ?? _value,
);
  String? get key => _key;
  String? get value => _value;

  Map<String, dynamic> toJson() {
    final map = <String, dynamic>{};
    map['key'] = _key;
    map['value'] = _value;
    return map;
  }

}