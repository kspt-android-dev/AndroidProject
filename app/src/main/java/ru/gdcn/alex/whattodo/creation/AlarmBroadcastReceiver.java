package ru.gdcn.alex.whattodo.creation;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import ru.gdcn.alex.whattodo.R;
import ru.gdcn.alex.whattodo.objects.Note;

public class AlarmBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent intent1 = new Intent(context, MainActivity.class);
//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent1);
        Toast.makeText(context, "ALARM!", Toast.LENGTH_SHORT).show();

        Note note = (Note) intent.getSerializableExtra("note");
        Intent notificationIntent = new Intent(context, CreationActivity.class);
        notificationIntent.putExtra("note", note);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(contentIntent)
                // обязательные настройки
                .setSmallIcon(R.drawable.ic_check_box_black_24dp)
                //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
                .setContentTitle(note.getHeader())
                //.setContentText(res.getString(R.string.notifytext))
                .setContentText(note.getContent()) // Текст уведомления
                // необязательные настройки
//                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.hungrycat)) // большая
                // картинка
                //.setTicker(res.getString(R.string.warning)) // текст в строке состояния
                .setTicker(note.getHeader())
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setAutoCancel(true); // автоматически закрыть уведомление после нажатия

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(13, builder.build());
    }

}
