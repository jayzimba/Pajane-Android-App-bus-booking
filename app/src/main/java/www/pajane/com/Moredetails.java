package www.pajane.com;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.LayoutTransition;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.travijuu.numberpicker.library.NumberPicker;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Moredetails extends AppCompatActivity {

    private TextView dateToGo;
    private Calendar calender;
    private SimpleDateFormat dateFormat;
    private String Date;
    private TextView daytv;
    private ImageView DatePicker;
    private ImageView Backbtn;
    private TextView from;
    private TextView to;
    private TextView discount;
    private TextView amount;
    private TextView station;
    private boolean fromHome;
    private ImageButton paynow;
    private LinearLayout datelayout;
    private LinearLayout passengers;

    private LinearLayout passengerView;
    private LinearLayout passengerViewContainer;
    private TextView AdultTxt, ChildrenTxt, AdultPolicyTxt, ChildrenPolicyTxt, PassengerPolicyTxt;
    private NumberPicker Adult_numberPicker, Children_numberPicker;
    private Button savePassengerData;
    private ImageView ic;
    private TextView passenger_counterID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moredetails);

        paynow = (ImageButton) findViewById(R.id.paynowbtn);
        datelayout = (LinearLayout) findViewById(R.id.datelayout);
        passengers = (LinearLayout) findViewById(R.id.passengers);
        passenger_counterID = (TextView) findViewById(R.id.passenger_counterID);



        //passenger hooks
        passengerView = findViewById(R.id.passengerView);
        passengerViewContainer = findViewById(R.id.passengerContainer);
        PassengerPolicyTxt = findViewById(R.id.PassengerPolicyTxt);
        AdultTxt = findViewById(R.id.AdultsTxt);
        ChildrenTxt = findViewById(R.id.ChildrenTxt);
        AdultPolicyTxt = findViewById(R.id.AdultsPolicyTxt);
        ChildrenPolicyTxt = findViewById(R.id.ChildrenPolicyTxt);
        Adult_numberPicker = findViewById(R.id.Adults_number_picker);
        Children_numberPicker = findViewById(R.id.Children_number_picker);
        savePassengerData = findViewById(R.id.savePassengerData);
        ic = findViewById(R.id.arrow);
        // end here

        String bus_name = getIntent().getStringExtra("NAME");
        String bus_from = getIntent().getStringExtra("FROM");
        String bus_to = getIntent().getStringExtra("TO");
        String bus_amount = (getIntent().getStringExtra("AMOUNT"));
        String bus_station = getIntent().getStringExtra("STATION");

        fromHome = getIntent().getBooleanExtra("from_home",true);

        passengerViewContainer.getLayoutTransition().enableTransitionType(LayoutTransition.CHANGING);


        //Number Pickers
        Adult_numberPicker.setValue(1);
        Children_numberPicker.setValue(0);



        paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheet bottomSheet = new BottomSheet();
                bottomSheet.show(getSupportFragmentManager(),"TAG");


            }
        });



        from = (TextView) findViewById(R.id.fromID);
        to = (TextView) findViewById(R.id.toID);
        amount = (TextView) findViewById(R.id.amount);
        discount = (TextView) findViewById(R.id.dis_amount);
        station = (TextView) findViewById(R.id.station);


        from.setText(bus_from);
        to.setText(bus_to);
        amount.setText(bus_amount);
        station.setText(bus_station);

        calender = Calendar.getInstance();
        dateToGo = findViewById(R.id.date);
        Backbtn = (ImageView) findViewById(R.id.backbtn);

        dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy");
        DatePicker = (ImageView) findViewById(R.id.datePicker);
        daytv = (TextView)findViewById(R.id.day);


        Date = dateFormat.format(calender.getTime());
        dateToGo.setText(Date);

        final int year = calender.get(Calendar.YEAR);
        final int month = calender.get(Calendar.MONTH);
        final int day = calender.get(Calendar.DAY_OF_MONTH);

        datelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Moredetails.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int dayOfMoth) {

                        calender.set(year, month, dayOfMoth);
                        Date = dateFormat.format(calender.getTime());
                        dateToGo.setText(Date);

                        daytv.setVisibility(View.GONE);
                    }
                }, year, month,  day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                datePickerDialog.show();
            }
        });

        passengers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        backButtonclicked();



    }

    private void backButtonclicked() {
        Backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fromHome == true)
                {
                    Intent intent = new Intent(Moredetails.this, Home.class );
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(Moredetails.this, departed.class );
                    startActivity(intent);
                    finish();
                }

            }
        });
    }

//    passengerView = findViewById(R.id.passengerView);
//    passengerViewContainer = findViewById(R.id.passengerContainer);
//                          AdultTxt = findViewById(R.id.AdultsTxt);
//                          ChildrenTxt = findViewById(R.id.ChildrenTxt);
//                          AdultPolicyTxt = findViewById(R.id.AdultsPolicyTxt);
//                          ChildrenPolicyTxt = findViewById(R.id.ChildrenPolicyTxt);
//                          Adult_numberPicker = findViewById(R.id.Adults_number_picker);
//                          Children_numberPicker = findViewById(R.id.Children_number_picker);
//                          savePassengerData = findViewById(R.id.savePassengerData);


    public void expandPassengerView(View view) {

        int v = (passengerViewContainer.getVisibility() == View.GONE)? View.VISIBLE : View.GONE;

        if( v == View.GONE )
        {
            ic.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_white);
            savePassengerData.setVisibility(View.VISIBLE);
            PassengerPolicyTxt.setVisibility(View.VISIBLE);
            AdultTxt.setVisibility(View.VISIBLE);
            ChildrenTxt.setVisibility(View.VISIBLE);
            AdultPolicyTxt.setVisibility(View.VISIBLE);
            ChildrenPolicyTxt.setVisibility(View.VISIBLE);
            Adult_numberPicker.setVisibility(View.VISIBLE);
            Children_numberPicker.setVisibility(View.VISIBLE);

            savePassengerData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ic.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_white);
                    savePassengerData.setVisibility(View.GONE);
                    PassengerPolicyTxt.setVisibility(View.GONE);
                    AdultTxt.setVisibility(View.GONE);
                    ChildrenTxt.setVisibility(View.GONE);
                    AdultPolicyTxt.setVisibility(View.GONE);
                    ChildrenPolicyTxt.setVisibility(View.GONE);
                    Adult_numberPicker.setVisibility(View.GONE);
                    Children_numberPicker.setVisibility(View.GONE);

                    int adult_counter = Adult_numberPicker.getValue();
                    int children_counter = Children_numberPicker.getValue();

                    int totalPassengers = adult_counter + children_counter;

                    passenger_counterID.setText( Integer.toString(totalPassengers) );
                }
            });

            ic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ic.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_white);
                    savePassengerData.setVisibility(View.GONE);
                    AdultTxt.setVisibility(View.GONE);
                    PassengerPolicyTxt.setVisibility(View.GONE);
                    ChildrenTxt.setVisibility(View.GONE);
                    AdultPolicyTxt.setVisibility(View.GONE);
                    ChildrenPolicyTxt.setVisibility(View.GONE);
                    Adult_numberPicker.setVisibility(View.GONE);
                    Children_numberPicker.setVisibility(View.GONE);
                    return;
                }
            });
        }
        else
        {
            ic.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_white);
            savePassengerData.setVisibility(View.GONE);
            AdultTxt.setVisibility(View.GONE);
            ChildrenTxt.setVisibility(View.GONE);
            AdultPolicyTxt.setVisibility(View.GONE);
            ChildrenPolicyTxt.setVisibility(View.GONE);
            Adult_numberPicker.setVisibility(View.GONE);
            Children_numberPicker.setVisibility(View.GONE);

            expandPassengerView(view);
        }
        TransitionManager.beginDelayedTransition(passengerViewContainer, new AutoTransition());

    }
}