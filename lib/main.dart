import 'package:flutter/material.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'dart:math';
import 'package:http/http.dart' as http;
import 'dart:convert';

import 'package:polytable/Constants.dart';
import 'package:polytable/Group.dart';
import 'package:polytable/templates/SearchResult.dart';


void main() async {
  runApp(new MyApp());
}

class MyApp extends StatelessWidget {
  //TODO fix navigation, divide into classes
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Polytable',
      theme: ThemeData(
        primaryColor: Colors.green,
      ),
      home: new MyHomePage(title: 'Polytable'),
      localizationsDelegates: [
        // ... app-specific localization delegate[s] here
        GlobalMaterialLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
      ],
      supportedLocales: [
        const Locale('ru', 'RU'),
      ],
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  final String title;
  static var context;

  @override
  _MyHomePageState createState() => new _MyHomePageState();
}

//Убираем эффект от скролла
class MyBehavior extends ScrollBehavior {
  @override
  Widget buildViewportChrome(
      BuildContext context, Widget child, AxisDirection axisDirection) {
    return child;
  }
}

class _MyHomePageState extends State<MyHomePage> {
  void choiceAction(String choice) {
    if (choice == Constants.Group) {
      Navigator.push(context,
          MaterialPageRoute(builder: (context) => new Group(name: "33531/2")));
    } else if (choice == Constants.Profile) {}
  }

  //TODO Добавить рарки
  final phrases = new List.from([
    "Ищешь свою пару?",
    "Не падаем!",
    "Сегодня физра в 8?",
    "У пас пара, возможно лаба, по коням!",
    "Осталась пара вопросов",
    "Закройте окно!",
    "С легкой парой!",
    "Запарная неделя!"
  ]);

  List<SearchResult> results = new List();

  void _findGroups(String group) async {
    results.clear();
    final String url = "https://polytable.ru/search.php?query=";
    String query = url + group.trim();
    await http.get(query)
        .then((response) =>
            json.decode(utf8.decode(response.bodyBytes))
              .forEach((element) => results.add(new SearchResult(
                name: element['name'],
                faculty_abbr: element['faculty_abbr']))))
        .catchError((e) => print(e));

    /*
    if (response.statusCode == 200) {
      // If server returns an OK response, parse the JSON
      List<dynamic> res = json.decode(utf8.decode(response.bodyBytes));
      res.forEach((element) => results.add(new SearchResult(
          name: res[res.indexOf(element)]['name'],
          faculty_abbr: res[res.indexOf(element)]['faculty_abbr'])));
    } else {
      // If that response was not OK, throw an error.
      throw Exception('Failed to load post');
    }
    */
    print(results);
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    final phrase = new Text(phrases[new Random().nextInt(phrases.length)],
        style: TextStyle(color: Colors.white, fontSize: 18.0));
    return Scaffold(
      body: NestedScrollView(
        headerSliverBuilder: (BuildContext context, bool innerBoxIsScrolled) {
          return <Widget>[
            SliverAppBar(
              elevation: 0.0,
              actions: <Widget>[
                Padding(
                    padding: const EdgeInsets.only(right: 8.0),
                    child: new PopupMenuButton(
                      icon: Icon(Icons.account_circle, size: 40.0),
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
              expandedHeight: 200.0,
              floating: false,
              pinned: true,
              flexibleSpace: FlexibleSpaceBar(
                centerTitle: true,
                title: Image(
                  image: new AssetImage('assets/images/biglogo.png'),
                  width: 200.0,
                ),
                background: Column(
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: <Widget>[
                    Padding(
                      padding: EdgeInsets.only(top: 15.0),
                      child: phrase,
                    ),
                  ],
                ),
              ),
            ),
          ];
        },
        body: Container(
          color: Color.fromRGBO(16, 93, 59, 1.0),
          child: ScrollConfiguration(
            behavior: MyBehavior(),
            child: ListView(
              physics: const NeverScrollableScrollPhysics(),
              children: <Widget>[
                Padding(
                  padding:
                      const EdgeInsets.only(top: 60.0, right: 30.0, left: 30.0),
                  child: new TextField(
                    key: Key("search-group"),
                    onSubmitted: _findGroups,
                    style: TextStyle(
                        color: Colors.black, fontWeight: FontWeight.bold),
                    decoration: InputDecoration(
                      fillColor: Colors.white,
                      filled: true,
                      hintText: "Группа",
                      border: OutlineInputBorder(
                          borderSide: new BorderSide(
                              color: Color.fromRGBO(16, 93, 59, 1.0))),
                    ),
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: (results.isNotEmpty)
                      ? Column(children: results)
                      : Container(width: 0.0, height: 0.0),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
