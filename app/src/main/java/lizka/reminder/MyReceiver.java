package lizka.reminder;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Objects;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
        Log.i("BUGG", "onRECEIVE1212");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = Objects.requireNonNull(intent.getExtras()).getString("taskText");
        Log.i("BUGG", "title = " + title);
        Intent intent1 = new Intent(context, MyNewIntentService.class);
        context.startService(intent1.putExtra("title", title));
    }
}

