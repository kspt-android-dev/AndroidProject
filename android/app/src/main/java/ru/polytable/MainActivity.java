package ru.polytable;

import java.util.*;
import java.text.SimpleDateFormat;
import android.os.Bundle;
import io.flutter.app.FlutterActivity;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

public class MainActivity extends FlutterActivity {
  public static final String CHANNEL = "polytable.flutter.io/week";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);

    new MethodChannel(getFlutterView(), CHANNEL).setMethodCallHandler(
            new MethodCallHandler() {
              @Override
              public void onMethodCall(MethodCall call, Result result)  {
                if (call.method.equals("getWeekNumber")) {
                  int weekNumber = getWeekNumber(call.argument("date").toString());
                  System.out.println(weekNumber);
                  if (weekNumber == -1)
                    result.error("ERROR", "Wrong data format present", null);
                  result.success(weekNumber);
                } else {
                  result.notImplemented();
                }
              }
            }
    );
  }

  private int getWeekNumber(String date) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    try {
      Date d = format.parse(date);

      Calendar c = Calendar.getInstance();
      c.setTime(d);

      return c.get(Calendar.WEEK_OF_YEAR);
    } catch (Exception e) {
      return -1;
    }
  }
}
