package saleh.sale7;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by berre on 6/2/2017.
 */

public class DailyJob {

    private static final String TAG = "DailyJob";
    private static final String ONE_TIME = "once";

    public static void Set12nnAlarm(Context context) {

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        intent.putExtra(ONE_TIME, Boolean.FALSE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);

        // every day at 19
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());

        // if it's after or equal 9 am schedule for next day
        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 19) {
            Log.e(TAG, "Alarm will schedule for next day!");
            calendar.add(Calendar.DAY_OF_YEAR, 1); // add, not set!
        } else {
            Log.e(TAG, "Alarm will schedule for today!");
        }

        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 32);
        calendar.set(Calendar.SECOND, 0);
        Toast.makeText(context, "setting the alarm at " + calendar.toString(), Toast.LENGTH_SHORT).show();

        // calendar.set(Calendar.SECOND, Calendar.getInstance().get(Calendar.SECOND) + 10);

        am.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pi);
    }
}
