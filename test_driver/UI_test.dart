import 'dart:async';

// Imports the Flutter Driver API
import 'package:flutter_driver/flutter_driver.dart';
import 'package:test/test.dart';

void main() {
  group('main page tests', () {
    FlutterDriver driver;

    setUpAll(() async {
      // Connects to the app
      driver = await FlutterDriver.connect();
    });

    tearDownAll(() async {
      if (driver != null) {
        // Closes the connection
        driver.close();
      }
    });

    test('page load', () async {
      SerializableFinder textField = find.byValueKey("search-group");
      await driver.waitFor(textField);
    });

    test('go to groups', () async {
      SerializableFinder icon = find.byType("Icon");
      await driver.tap(icon, timeout: Duration(milliseconds: 300));
      await driver.waitFor(find.text("Группа"));
      await driver.tap(find.text("Группа"));
    });

    test("groups load", () async {
      await driver.waitFor(find.text("33531/2"));
    });

  });
}
