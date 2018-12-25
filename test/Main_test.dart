import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';

import 'package:polytable/templates/Lesson.dart';
import 'package:polytable/data/CalendarData.dart';
import 'package:polytable/calendar/flutter_calendar.dart';

void main() {
  testWidgets('Lesson1', (WidgetTester tester) async {
    await tester.pumpWidget(
      MaterialApp(home: Scaffold(
        body: Lesson(
          title: "Проектирование мобильных приложений",
          type: "Практика",
          time_start: "12:00",
          time_end: "13:40",
          teachers: List(),
          places: List(),
          homework: Homework(Map<String, dynamic>.fromEntries([
            MapEntry<String, dynamic>("files", null),
            MapEntry<String, dynamic>("text", "Сдать курсовой проект")
          ])),
        ),
      ))
    );

    expect(find.byType(Lesson), findsOneWidget);
    expect(find.text("Показать ДЗ"), findsOneWidget);
    expect(find.text("Сдать курсовой проект"), findsNothing);
    expect(find.byType(IconButton), findsOneWidget);
  });

  testWidgets("Calendar", (WidgetTester tester) async {

    await tester.pumpWidget(
        MaterialApp(home: Scaffold(
          body: Calendar()
        ))
    );

  });
}