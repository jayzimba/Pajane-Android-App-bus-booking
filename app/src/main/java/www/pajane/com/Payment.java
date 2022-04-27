package www.pajane.com;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RaveUiManager;
import com.flutterwave.raveandroid.rave_java_commons.RaveConstants;
import com.manojbhadane.PaymentCardView;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;


public class Payment extends AppCompatActivity {
    private LinearLayout mobileMode, CardMode, mainLayOut;
    private TextView details, CardDetails, payAmount;
    private ImageView ic, CardIc, backBtn;
    private Button mobileMoneyPayNowBtn;
    private ProgressBar progressBar, CardprogressBar;
    private PaymentCardView paymentCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mobileMode = findViewById(R.id.contentMobile);
        details = findViewById(R.id.details);
        ic = findViewById(R.id.iconupdown);
        mobileMoneyPayNowBtn = (Button) findViewById(R.id.mobilePayNowBtn);
        progressBar = (ProgressBar) findViewById(R.id.progressWaittoApprove);
        payAmount = (TextView) findViewById(R.id.amountToPay);

        CardMode = findViewById(R.id.Cardcontent);
        CardDetails = findViewById(R.id.Carddetails);
        CardIc = findViewById(R.id.Cardiconupdown);
        CardprogressBar = (ProgressBar) findViewById(R.id.cardprogressWaittoApprove);

        mainLayOut = (LinearLayout) findViewById(R.id.mainLayout);

        backBtn = (ImageView) findViewById(R.id.backBtn);

        final gettoken gt = new gettoken();

        new AsyncTask(){

            @Override
            protected Object doInBackground(Object[] objects) {

                try {
                    gt.thetoken();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();


        backBtnClicked();

        Intent intent = getIntent();
        String bus_amount = intent.getStringExtra("bus_amount");

        payAmount.setText(bus_amount);

        //card handlers
        paymentCard = (PaymentCardView) findViewById(R.id.creditCard);

        //Callbacks
        paymentCard.setOnPaymentCardEventListener(new PaymentCardView.OnPaymentCardEventListener() {
            @Override
            public void onCardDetailsSubmit(String month, String year, String cardNumber, String cvv) {
                CardprogressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(Payment.this, error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelClick() {
                Toast.makeText(Payment.this, R.string.errorOnCancelCard, Toast.LENGTH_SHORT).show();
            }
        });

        mobileMode.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        CardMode.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);
        mainLayOut.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);

    }

    private void backBtnClicked() {

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Payment.this, Moredetails.class));
                finish();
            }
        });
    }

    public void expand1(View view) {
        int v = (details.getVisibility() == View.GONE)? View.VISIBLE : View.GONE;

        if(v == View.VISIBLE)
        {
             ic.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
             mobileMoneyPayNowBtn.setVisibility(View.VISIBLE);

             mobileMoneyPayNowBtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     mobileMoneyPayNowBtn.setVisibility(View.GONE);
                     progressBar.setVisibility(View.VISIBLE);

                     startActivity(new Intent(Payment.this, Successful.class));
                     finish();


                 }
             });

        }
        else
        {
             ic.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
             mobileMoneyPayNowBtn.setVisibility(View.GONE);
             progressBar.setVisibility(View.GONE);
        }
        TransitionManager.beginDelayedTransition(mobileMode, new AutoTransition());
        details.setVisibility(v);
    }

    public void expand2(View view) {
        int v = (CardDetails.getVisibility() == View.GONE)? View.VISIBLE : View.GONE;

        if(v == View.VISIBLE)
        {
            CardIc.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
            paymentCard.setVisibility(View.VISIBLE);
        }
        else
        {
            CardIc.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
            CardprogressBar.setVisibility(View.GONE);
            paymentCard.setVisibility(View.GONE);
        }
        TransitionManager.beginDelayedTransition(CardMode, new AutoTransition());
        CardDetails.setVisibility(v);
    }

}