package saleh.sale7;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;
import com.mindorks.butterknifelite.annotations.OnClick;
import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.listeners.ItemRemovedListener;

import java.util.Collections;
import java.util.LinkedList;

public class Main extends AppCompatActivity {

    private static final String TAG = "ActivityTinder";

    @BindView(R.id.swipeView)
    private SwipePlaceHolderView mSwipView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinder_swipe);
        ButterKnifeLite.bind(this);
        UserSessionManager session = new UserSessionManager(this);
        FirebaseMessaging.getInstance().subscribeToTopic("sameh");
        FirebaseInstanceId.getInstance().getToken();
        if (session.isFirstInstall()) {
            session.setNotFirstTime();
            FirebaseDatabase.getInstance().getReference().child("day").setValue(1);

            //DailyJob.Set12nnAlarm(this);
        } else {

        }

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mSwipView.disableTouchSwipe();
        mSwipView.addItemRemoveListener(new ItemRemovedListener() {

            @Override
            public void onItemRemoved(int count) {
                Log.d(TAG, "onItemRemoved: " + count);
                if (count == 0) {
                    // startActivity(new Intent(SuggestActivity.this, MyModeActivity.class));
                    load();
                }
            }
        });
        mSwipView.getBuilder()
//                .setSwipeType(SwipePlaceHolderView.SWIPE_TYPE_VERTICAL)
                .setDisplayViewCount(3)
                .setIsUndoEnabled(true)
                .setWidthSwipeDistFactor(15)
                .setHeightSwipeDistFactor(20)
              /*  .setSwipeDecor(new SwipeDecor()
//                        .setMarginTop(300)
//                        .setMarginLeft(100)
//                        .setViewGravity(Gravity.TOP)
                        .setPaddingTop(20)
                        .setRelativeScale(0.01f)
                        .setSwipeInMsgLayoutId(R.layout.tinder_swipe_in_msg_view)
                        .setSwipeOutMsgLayoutId(R.layout.tinder_swipe_out_msg_view))*/;
// Read from the database
        load();
      /*  mSwipView.addView(new TinderCard())
                .addView(new TinderCard())
                .addView(new TinderCard());*/
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    mSwipView.enableTouchSwipe();
//                    mSwipView.lockViews();
//                    Thread.currentThread().sleep(4000);
//                    mSwipView.unlockViews();
//                    Thread.currentThread().sleep(4000);
//                    mSwipView.lockViews();
//                    Thread.currentThread().sleep(4000);
//                    mSwipView.unlockViews();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

   /* @OnClick(R.id.rejectBtn)
    private void onRejectClick() {
        mSwipView.doSwipe(false);
    }*/

    @OnClick(R.id.acceptBtn)
    private void onAcceptClick() {
        mSwipView.doSwipe(true);
    }

  /*  @OnClick(R.id.undoBtn)
    private void onUndoClick() {
        mSwipView.undoLastSwipe();
      //  load();
    }*/

    private void load() {
        FirebaseDatabase.getInstance().getReference().child("day").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
                int day = dataSnapshot.getValue(Integer.class);
                if (day <= 0) day = 1;
                Query recentPostsQuery = myRef.child("memories").limitToFirst(day).orderByKey();
                try {

                    recentPostsQuery.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // This method is called once with the initial value and again
                            // whenever data at this location is updated.
                            // String value = dataSnapshot.getValue(String.class);

                            LinkedList<DataSnapshot> posts = new LinkedList<DataSnapshot>();
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                posts.add(data);
                            }
                            Collections.reverse(posts);

                            for (DataSnapshot data : posts) {
                                mSwipView.addView(
                                        new TinderCard(
                                                data.child("imgUrl").getValue(String.class),
                                                data.child("caption").getValue(String.class),
                                                Main.this));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            // Failed to read value
                            Log.w(TAG, "Failed to read value.", error.toException());
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(Main.this, "can't firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}