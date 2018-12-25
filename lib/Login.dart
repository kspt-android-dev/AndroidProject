import 'package:flutter/material.dart';
import 'package:polytable/templates/Header.dart';

class Login extends StatefulWidget {
  @override
  _LoginState createState() => _LoginState();
}

class _LoginState extends State<Login> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.green,
      appBar: Header(),
      body: Column(
        children: <Widget>[
          TextField(
            decoration: InputDecoration(hintText: "Login"),
          ),
          TextField(
            decoration: InputDecoration(hintText: "Password"),
            obscureText: true,
          ),
        ],
      ),
    );
  }
}
