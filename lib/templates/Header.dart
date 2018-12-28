import 'package:flutter/material.dart';
import 'package:polytable/Constants.dart';

class Header extends AppBar {
  static var choiceAction;

  Header({Key key, Widget title, choiceAction, elevation})
      : super(
    elevation: (elevation == null) ? 1.0 : elevation,
    key: key,
    title: title,
    actions: <Widget>[
      Padding(
          padding: const EdgeInsets.only(right: 8.0),
          child: new PopupMenuButton(
            icon: Icon(Icons.account_circle, size: 40.0, key: Key("open-header-bar"),),
            onSelected: choiceAction,
            itemBuilder: (BuildContext context) {
              return Constants.choices.map((String choice) {
                return PopupMenuItem<String>(
                  value: choice,
                  child: Text(choice),
                );
              }).toList();
            },
          ))
    ],
    backgroundColor: Color.fromRGBO(16, 93, 59, 1.0),
  );
}