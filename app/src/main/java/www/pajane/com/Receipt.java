package www.pajane.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Receipt extends AppCompatActivity {

    private EditText qrcodeValue;
    private ImageView qrcode;
    private LottieAnimationView lotiLoading;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        qrcodeValue = findViewById(R.id.codevalue);
        qrcode = findViewById(R.id.qrcode);
        lotiLoading = findViewById(R.id.animationView);
        btn = findViewById(R.id.qrcode_btn);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sText = qrcodeValue.getText().toString().trim();

                MultiFormatWriter writer = new MultiFormatWriter();

                try {
                    BitMatrix matrix = writer.encode(sText, BarcodeFormat.QR_CODE,400, 400);

                    BarcodeEncoder encoder = new BarcodeEncoder();

                    Bitmap bitmap = encoder.createBitmap(matrix);

                    qrcode.setImageBitmap(bitmap);

                    lotiLoading.setVisibility(View.GONE);
                    qrcode.setVisibility(View.VISIBLE);

                    InputMethodManager manager = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE
                    );

                    manager.hideSoftInputFromWindow(qrcodeValue.getApplicationWindowToken(), 0);

                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}