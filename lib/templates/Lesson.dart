import 'package:flutter/material.dart';
import 'package:polytable/data/CalendarData.dart';
import 'package:polytable/templates/ImageGallery.dart';

class Lesson extends StatefulWidget {
  Lesson(
      {this.title,
        this.type,
        this.time_start,
        this.time_end,
        this.teachers,
        this.teacher,
        this.places,
        this.place,
        this.homework});

  final String title;
  final String type;
  final String time_start;
  final String time_end;
  final List<dynamic> teachers;
  final String teacher;
  final List<dynamic> places;
  final String place;
  final Homework homework;

  @override
  _LessonState createState() => new _LessonState();
}

class _LessonState extends State<Lesson> {
  bool _homeworkExpanded = false;

  Widget buildField(String field, Color textColor, double fontSize) {
    return (field != null)
        ? Text(
      field,
      textAlign: TextAlign.center,
      overflow: TextOverflow.clip,
      style: TextStyle(fontSize: fontSize, color: textColor),
    )
        : Text("");
  }

  Widget buildFields(List<dynamic> fields, double fontSize) {
    if (fields.isEmpty) return Container(child: Text(""));
    List<Widget> text = List();
    fields.forEach((field) {
      String name = (field is Teacher) ? field.name : (field as Building).name;
      text.add(Text(name,
          textAlign: TextAlign.center,
          overflow: TextOverflow.clip,
          style: TextStyle(fontSize: fontSize)));
    });
    return Column(children: text);
  }

  Widget _buildHomework() {
    return Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: <Widget>[
          Padding(
              padding: EdgeInsets.all(5),
              child: Text(
                widget.homework.text,
                textAlign: TextAlign.left,
                style: TextStyle(fontSize: 15),
              )),
          ThumbnailGallery(images: widget.homework.all)
        ]);
  }

  @override
  Widget build(BuildContext context) {
    Color typeColor() {
      switch (widget.type) {
        case "Лекции":
          return Colors.green;
          break;
        case "Лабораторные":
          return Colors.blue;
          break;
        case "Практика":
          return Colors.blue[400];
          break;
        default:
          return Colors.green;
          break;
      }
    }

    return (widget.title != null)
        ? InkWell(
      onTap: () {
        setState(() {
          print('Lesson ${widget.title} tapped');
        });
      },
      child: new Card(
        margin: EdgeInsets.all(8.0),
        child: Padding(
          padding: const EdgeInsets.all(8.0),
          child: Column(
            children: <Widget>[
              buildField(widget.title, Colors.black, 18.0),
              buildField(widget.type, typeColor(), 16.0),
              buildField(widget.time_start + " - " + widget.time_end,
                  Colors.black, 16.0),
              buildFields(widget.teachers, 16.0),
              buildFields(widget.places, 16.0),
              homework()
            ],
          ),
        ),
      ),
    )
        : new Container(width: 0.0, height: 0.0);
  }

  Widget homework() {
    return (widget.homework.exists)
        ? Column(mainAxisAlignment: MainAxisAlignment.start, children: <Widget>[
      Row(
        mainAxisSize: MainAxisSize.max,
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        //crossAxisAlignment: CrossAxisAlignment.stretch,
        children: <Widget>[
          Padding(
              padding: EdgeInsets.only(left: 8.0),
              child: Text(
                (_homeworkExpanded) ? "Скрыть ДЗ" : "Показать ДЗ",
                textAlign: TextAlign.left,
                style: TextStyle(fontSize: 15),
              )),
          IconButton(
              icon: Icon((_homeworkExpanded)
                  ? Icons.keyboard_arrow_up
                  : Icons.keyboard_arrow_down),
              iconSize: 20,
              onPressed: () {
                setState(() {
                  _homeworkExpanded = !_homeworkExpanded;
                });
              }),
        ],
      ),
      (_homeworkExpanded) ? _buildHomework() : Container(),
    ])
        : Container();
  }
}