package saleh.sale7;


import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

class UserSessionManager {
    private static final String PREFER_NAME = "default",

    IS_FIRST_START = "is_first_start",
            LAST_UPDATE = "last_update",
            DAY = "DAY";
    int day;

    private final Context context;

    private SharedPreferences preferences;

    private SharedPreferences.Editor editor;

    public UserSessionManager(Context context) {
        this.context = context;
        int PRIVATE_MODE = 0;
        preferences = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void setNotFirstTime() {
        editor.putBoolean(IS_FIRST_START, false);
        editor.commit();
    }

    public boolean isFirstInstall() {
        return preferences.getBoolean(IS_FIRST_START, true);
    }

    public void setDay(int day) {
        editor.putInt(DAY, day);
        editor.commit();
    }

    public int getDay() {
        //  FirebaseDatabase.getInstance().getReference().child("day").setValue(preferences.getInt(DAY, 1));
        day = 1;
        //  return preferences.getInt(DAY, 1);
        FirebaseDatabase.getInstance().getReference().child("day").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot == null) day = 1;
                else day = dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return day;
    }

    public void setLastUpdate(int day) {
        editor.putInt(DAY, day);
        editor.commit();
    }

    public int getLastUpdate() {
        FirebaseDatabase.getInstance().getReference().child("day").setValue(preferences.getInt(DAY, 1));
        return preferences.getInt(DAY, 1);
    }
}
