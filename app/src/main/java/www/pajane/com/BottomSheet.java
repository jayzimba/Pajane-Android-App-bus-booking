package www.pajane.com;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class BottomSheet extends BottomSheetDialogFragment {


    private ProgressBar progressBar;
    private EditText phoneNo;

    public BottomSheet() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view1 = inflater.inflate(R.layout.getnumber, container, false);

        ImageButton comfirmNumber = view1.findViewById(R.id.comfirmpayBtn);
        progressBar = view1.findViewById(R.id.progress_bar);
        phoneNo = view1.findViewById(R.id.phoneID);

        comfirmNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText number = view1.findViewById(R.id.phoneID);

                if(!TextUtils.isEmpty(number.getText().toString())){
                    String number_S = number.getText().toString().trim();

                    progressBar.setVisibility(View.VISIBLE);
                    comfirmNumber.setVisibility(View.INVISIBLE);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+26"+phoneNo.getText().toString(),
                            60,
                            TimeUnit.SECONDS,
                            getActivity(),
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    progressBar.setVisibility(View.GONE);
                                    comfirmNumber.setVisibility(View.VISIBLE);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    progressBar.setVisibility(View.GONE);
                                    comfirmNumber.setVisibility(View.VISIBLE);
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationID, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    comfirmNumber.setVisibility(View.INVISIBLE);

                                    Intent intent = new Intent(getContext(), OTP.class);
                                    intent.putExtra("number", number_S);
                                    intent.putExtra("V_ID", verificationID);

                                    startActivity(intent);
                                    dismiss();
                                }
                            }
                    );

                }
                else
                {
                    Toast.makeText(getContext(), "phone number is required", Toast.LENGTH_SHORT).show();
                }

            }
        });


       return view1;
    }
}
