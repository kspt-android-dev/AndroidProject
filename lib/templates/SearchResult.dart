import 'package:flutter/material.dart';
import 'package:polytable/Group.dart';

class SearchResult extends StatelessWidget {
  const SearchResult({this.name, this.faculty_abbr});

  final String name;
  final String faculty_abbr;

  @override
  Widget build(BuildContext context) {
    return Card(
      key: Key("search-result-$name"),
      child: ListTile(
        onTap: () {
          Navigator.push(context,
              MaterialPageRoute(builder: (context) => new Group(name: name)));
        },
        title: Text(name),
        subtitle: (faculty_abbr != null) ? Text(faculty_abbr) : Text(""),
      ),
    );
  }
}