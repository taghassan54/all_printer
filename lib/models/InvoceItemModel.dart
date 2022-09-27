import 'dart:convert';
/// key : "date"
/// value : "27/09/2022"

InvoceItemModel invoceItemModelFromJson(String str) => InvoceItemModel.fromJson(json.decode(str));
String invoceItemModelToJson(InvoceItemModel data) => json.encode(data.toJson());
class InvoceItemModel {
  InvoceItemModel({
      String? key, 
      String? value,}){
    _key = key;
    _value = value;
}

  InvoceItemModel.fromJson(dynamic json) {
    _key = json['key'];
    _value = json['value'];
  }
  String? _key;
  String? _value;
InvoceItemModel copyWith({  String? key,
  String? value,
}) => InvoceItemModel(  key: key ?? _key,
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