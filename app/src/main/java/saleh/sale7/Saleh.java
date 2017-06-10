package saleh.sale7;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by berre on 6/2/2017.
 */

public class Saleh extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
