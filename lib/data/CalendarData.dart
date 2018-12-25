import 'dart:convert';

import 'package:flutter/services.dart';
import 'package:intl/intl.dart';
import 'package:http/http.dart' as http;

class CalendarData {
  static final String POLYTABLE_API_URL = "https://polytable.ru/action.php?action=calendar&group=";

  static final DateFormat _dateFormat = DateFormat("yyyy-MM-dd");
  static final MethodChannel _channel = MethodChannel("polytable.flutter.io/week");

  final String groupName;
  int staticStartWeek;

  DateTime _timetableStart;
  DateTime _timetableEnd;
  DateTime _staticStart;
  DateTime _staticEnd;

  Week _odd;
  Week _even;
  Map<String, Day> _dynamic = Map();
  Map<String, dynamic> _homework = Map();
  Map<String, Day> _ready = Map();

  CalendarData(String groupName) : this.groupName = groupName;

  String getDateKey(DateTime date) => _dateFormat.format(date);
  int getSemesterLength() => _timetableEnd.difference(_timetableStart).inDays + 1;

  Future<List<Day>> load () async {
    await http.get("$POLYTABLE_API_URL$groupName").then((response) async {
      Map<String, dynamic> res = json.decode(utf8.decode(response.bodyBytes))['data'];
      _timetableStart = DateTime.parse(res['timetable_start']);
      _timetableEnd = DateTime.parse(res['timetable_end']);
      _staticStart = DateTime.parse(res['static_start']);
      _staticEnd = DateTime.parse(res['static_end']);
      staticStartWeek = await getWeek(res['static_start']);
      _even = Week(res["static"][0]);
      _odd = Week(res['static'][1]);
      try {
        _dynamic = (res['dynamic'] as Map<String, dynamic>).map((key, day) =>
            MapEntry(key, Day.static(day)));
      } catch (e) {
        print("Dynamic cache not present");
      }
      try {
        _homework = (res['homework'] as Map<String, dynamic>);
      } catch (e) {
        print("No homework for $groupName found");
      }
    }).catchError((e) => print(e));
    DateTime now = DateTime.now();
    return [
      await this.get(_dateFormat.format(now.subtract(Duration(days: 2)))),
      await this.get(_dateFormat.format(now.subtract(Duration(days: 1)))),
      await this.get(_dateFormat.format(now)),
      await this.get(_dateFormat.format(now.add(Duration(days: 1)))),
      await this.get(_dateFormat.format(now.add(Duration(days: 2))))
    ];
  }

  Future<List<Day>> getAcross(DateTime date) async {
    return [
      await this.get(_dateFormat.format(date.subtract(Duration(days: 2)))),
      await this.get(_dateFormat.format(date.subtract(Duration(days: 1)))),
      await this.get(_dateFormat.format(date)),
      await this.get(_dateFormat.format(date.add(Duration(days: 1)))),
      await this.get(_dateFormat.format(date.add(Duration(days: 2))))
    ];
  }

  Future<Day> get(String key) async {
    if (_ready.containsKey(key))
      return _ready[key];
    else {
      DateTime date = DateTime.parse(key);
      bool isOdd =  (await getWeek(key) - staticStartWeek) % 2 == 0;
      var homework = (_homework.containsKey(key)) ? _homework[key] : null;
      int dateInt = date.millisecondsSinceEpoch;
      if (dateInt >= _timetableStart.millisecondsSinceEpoch && dateInt <= _timetableEnd.millisecondsSinceEpoch) {
        if (dateInt >= _staticStart.millisecondsSinceEpoch && dateInt <= _staticEnd.millisecondsSinceEpoch) {
          Map<int, LessonData> lessons = Map();
          if (_dynamic.containsKey(key))
            lessons.addAll(_dynamic[key].lessons.asMap().map((key, value) => MapEntry(value.lesson, value)));
          if (((isOdd) ? _odd : _even).days.containsKey(date.weekday))
          lessons.addAll(((isOdd) ? _odd : _even)
              .days[date.weekday]
              .lessons
              .where((lesson) => !lessons.containsKey(lesson.lesson))
              .toList().asMap()
              .map((key, value) => MapEntry(value.lesson, LessonData.copy(value))));
          lessons.removeWhere((key, lesson) => lesson.erase);
          return _ready[key] = Day(lessons, date, isOdd, homework);
        } else
          if (_dynamic.containsKey(key)) {
            Map<int, LessonData> lessons = _dynamic[key]
                .lessons
                .asMap()
                .map((key, value) => MapEntry(value.lesson, value));
            return _ready[key] = Day(lessons, date, isOdd, homework);
          }
      }
      return _ready[key] = Day(Map(), date, isOdd, homework);
    }
  }

  static Future<int> getWeek(String date) async {
    int weekday = -1;

    await _channel.invokeMethod("getWeekNumber", <String, String>{
      "date" : date
    }).then((v) => weekday = v);

    return weekday;
  }

}

class Week {
    Map<int, Day> days = Map();
    Week(Map<String, dynamic> days) {
      this.days = days.map((key, value) => MapEntry<int, Day>(int.parse(key), Day.static(value)));
    }

    operator [](int key) => days.containsKey(key) ? days[key] : Day.empty();
}

class Day {
  static final DateFormat _dateFormat = DateFormat("yyyy-MM-dd");

  List<LessonData> lessons;
  DateTime date;
  String dateKey;
  int weekday;
  bool isOdd;

  Day (Map<int, LessonData> lessons, DateTime date, bool isOdd, dynamic homework) {
    this.lessons = List();
    lessons.values.forEach((lesson) => this.lessons.add(lesson));
    this.date = date;
    this.dateKey = _dateFormat.format(date);
    this.isOdd = isOdd;
    this.weekday = date.weekday;

    if (homework is List)
      for (int i = 0; i < homework.length; i++)
        this.lessons[i].homework = Homework(homework[i]);
    else if (homework is Map)
      homework.forEach((key, value) => () {
        LessonData les = this.lessons.where((LessonData l) => l.lesson == int.parse(key)).first;
        if (les != null)
          les.homework = Homework(value);
      });
        //homework.forEach((key, value) => this.lessons[int.parse(key)].homework = Homework(value));

  }
  Day.static(dynamic lessons) {
    this.lessons = List();
    (((lessons is Map) ? (lessons as Map<String, dynamic>).values : lessons) as Iterable<dynamic>)
        .forEach((lesson) => this.lessons.add(LessonData(lesson)));
  }
  Day.empty() {
    this.lessons = [];
  }

  LessonData operator [](int key) => lessons[key];
}

class LessonData {
  bool isOdd;
  int lesson;
  int weekday;
  String subject;
  String type;
  String timeStart;
  String timeEnd;
  List<Teacher> teachers;
  List<Building> places;
  Homework homework;

  bool erase = false;

  LessonData(Map<String, dynamic> json) {
    this.isOdd = (json['is_odd'] == "0") ? false : true;
    this.lesson = int.parse(json['lesson']);
    this.weekday = int.parse(json['weekday']);
    this.subject = json['subject'];
    this.type = json['type'];
    this.timeStart = json['time_start'];
    this.timeEnd = json['time_end'];
    this.teachers = List<Teacher>();
    (json['teachers'] as List<dynamic>).forEach((teacherData) => this.teachers.add(Teacher(teacherData)));
    this.places = List<Building>();
    (json['places'] as List<dynamic>).forEach((place) => this.places.add(Building(place)));

    this.homework = Homework(null);
    this.erase = (json.containsKey('action') && json['action'] == "ERASE");
  }

  LessonData.copy(LessonData other) {
    this.isOdd = other.isOdd;
    this.lesson = other.lesson;
    this.weekday = other.weekday;
    this.subject = other.subject;
    this.type = other.type;
    this.timeStart = other.timeStart;
    this.timeEnd = other.timeEnd;
    this.teachers = other.teachers;
    this.places = other.places;
    this.homework = Homework(null);
  }
}

class Homework {
  List<File> _files = List();
  bool exists;
  String text;

  List<File> get images => files.where((file) => file.image).toList();
  List<File> get files => files.where((file) => !file.image).toList();
  List<File> get all => _files;

  Homework(Map<String, dynamic> json) {
    exists =  json != null;
    if (exists) {
      if (json['files'] != null)
         (json['files'] as List<dynamic>).forEach((file) => this._files.add(File(file)));
      this.text = (json['text'] != null) ? json['text'] : "";
    }
  }

}

class File {
  String name;
  String origin;
  bool image;

  String get url => "https://polytable.ru/uploads/${(image) ? "images" : "files"}/" + name;
  String get thumbnail => "https://polytable.ru/uploads/thumbnails/" + name;

  File(Map<String, dynamic> json) {
    this.name = json['name'];
    this.origin = json['original'];
    this.image = json['showable'] == "1";
  }

}

class Building {
  String name;
  int buildingId;
  int roomId;

  Building(Map<String, dynamic> json) {
    this.name = json['name'];
    this.buildingId = json['building_id'];
    this.roomId = json['room_id'];
  }
}

class Teacher {
  String name;
  int id;

  Teacher(Map<String, dynamic> json) {
    this.name = json['name'];
    this.id = json['id'];
  }
}
