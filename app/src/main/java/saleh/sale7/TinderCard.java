package saleh.sale7;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;
import com.squareup.picasso.Picasso;


/**
 * Created by janisharali on 19/08/16.
 */
@NonReusable
@Layout(R.layout.tinder_card_view)
public class TinderCard {

    private static int count;

    @View(R.id.image)
    private ImageView profileImageView;


    @View(R.id.caption)
    private TextView locationNameTxt;

    private String caption, imgUrl;
    private Context context;

    public TinderCard(String imgUrl, String caption, Context context) {
        this.caption = caption;
        this.context = context;
        this.imgUrl = imgUrl;
    }

    public TinderCard() {

    }

    @Click(R.id.image)
    private void onClick() {
        Log.d("DEBUG", "profileImageView");
    }

    @Resolve
    private void onResolve() {
        try {
            locationNameTxt.setText(caption);
            try {
                Picasso.with(context).load(imgUrl).into(profileImageView);
            } catch (Exception e) {
             //   Toast.makeText(context, "can't Picasso", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
//            Toast.makeText(context, "can't text", Toast.LENGTH_SHORT).show();

        }
    }

    @SwipeOut
    private void onSwipedOut() {
        Log.d("DEBUG", "onSwipedOut");
    }

    @SwipeCancelState
    private void onSwipeCancelState() {
        Log.d("DEBUG", "onSwipeCancelState");
    }

    @SwipeIn
    private void onSwipeIn() {
        Log.d("DEBUG", "onSwipedIn");
    }

    @SwipeInState
    private void onSwipeInState() {
        Log.d("DEBUG", "onSwipeInState");
    }

    @SwipeOutState
    private void onSwipeOutState() {
        Log.d("DEBUG", "onSwipeOutState");
    }
}
