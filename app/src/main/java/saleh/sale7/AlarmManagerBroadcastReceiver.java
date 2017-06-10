package saleh.sale7;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;


public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "alarm!", Toast.LENGTH_SHORT).show();
        DailyJob.Set12nnAlarm(context);
        this.context = context;
        addNotification();
        UserSessionManager sessionManager = new UserSessionManager(context);
        sessionManager.setDay(sessionManager.getDay() + 1);
    }

    private void addNotification() {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.olive)
                        .setContentTitle("wchaaaaa!")
                        .setColor(256)
                        //.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setContentText("kayna pic jdida :3 <3");

        Intent notificationIntent = new Intent(context, Main.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
