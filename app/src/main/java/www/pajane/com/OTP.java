package www.pajane.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {
    private TextView mess, countDown;
    private EditText otp;
    private ProgressBar progressBar;
    private TextView validateTxt;
    private LinearLayout verifyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mess = (TextView) findViewById(R.id.code_message);
        otp = (EditText) findViewById(R.id.otp);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        validateTxt = (TextView) findViewById(R.id.validate);
        countDown = (TextView) findViewById(R.id.countDown);
        verifyBtn = (LinearLayout) findViewById(R.id.verifyBtn);

        Long duration = TimeUnit.MINUTES.toMillis(1);

        new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long l) {
                String sDuration = String.format(Locale.ENGLISH,"%02d : %02d"
                ,TimeUnit.MILLISECONDS.toMinutes(l)
                ,TimeUnit.MILLISECONDS.toSeconds(l) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));

                countDown.setText(sDuration);
            }

            @Override
            public void onFinish() {
                countDown.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(), "OTP sent has expired, click on resend OTP", Toast.LENGTH_SHORT).show();

            }
        }.start();


        Intent intent = getIntent();
        String phoneNo = intent.getStringExtra("number");
        String bus_amount = intent.getStringExtra("bus_amount");



        mess.setText("Enter the verification code sent to +26"+phoneNo);

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                verifyBtn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                String verificationCode = intent.getStringExtra("V_ID");
                String code = otp.getText().toString().trim();

                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                        verificationCode,
                        code
                );


                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful())
                                {
                                    FirebaseUser user = task.getResult().getUser();
                                    Log.d("TAG", "number: "+user.getPhoneNumber());
                                    Intent intent1 = new Intent(OTP.this, Payment.class);
                                    intent1.setFlags(intent1.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent1.putExtra("mobileNumber", phoneNo);
                                    intent1.putExtra("bus_amounut", bus_amount);
                                    finish();
                                    startActivity(intent1);
                                }
                                else
                                {
                                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                        // The verification code entered was invalid
                                        Toast.makeText(OTP.this, "Verification code entered is invalid", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        });

            }
        });


    }


}