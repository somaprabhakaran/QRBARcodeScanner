package tamil.com.qrbarcodescanner;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import tamil.com.qrbarcodescanner.preferences.Preferences;


public class QRBARcodeScanner extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener{
    private TextView resultTextView;
    private QRCodeReaderView qrCodeReaderView;
    Preferences prefs_date;
    String barcodedata = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.layout_scannerview);
        prefs_date = new Preferences(this);
        qrCodeReaderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        resultTextView = (TextView) findViewById(R.id.textview_result);
        qrCodeReaderView.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        qrCodeReaderView.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
       qrCodeReaderView.setAutofocusInterval(2000L);

        // Use this function to enable/disable Torch
        qrCodeReaderView.setTorchEnabled(true);

        // Use this function to set back camera preview
        qrCodeReaderView.setBackCamera();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        ((Vibrator)getSystemService(VIBRATOR_SERVICE)).vibrate(50);
        resultTextView.setText(text);
        Intent intent_scanmainscreen = new Intent();
        prefs_date.setString("Scan_Barcode_GUID", text);
        setResult(0, intent_scanmainscreen);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        qrCodeReaderView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrCodeReaderView.stopCamera();
    }

}
