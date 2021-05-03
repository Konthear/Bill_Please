package sg.edu.rp.c346.id20003116.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    EditText amount;
    EditText numPax;
    ToggleButton svs;
    ToggleButton gst;
    TextView totalBill;
    TextView eachPaxPay;
    Button split;
    Button reset;
    EditText discount;
    RadioGroup paymentMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amount = findViewById(R.id.editAmount);
        numPax = findViewById(R.id.editNumPax);
        totalBill = findViewById(R.id.totalBill);
        eachPaxPay = findViewById(R.id.eachPay);
        svs = findViewById(R.id.svsBtn);
        gst = findViewById(R.id.gstBtn);
        split = findViewById(R.id.split);
        reset = findViewById(R.id.reset);
        discount = findViewById(R.id.editDiscount);
        paymentMethod = findViewById(R.id.radioGroupPaymentMethod);

        split.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount.getText().toString().trim().length() != 0 && numPax.getText().toString().trim().length() != 0) {
                    double origAmount = Double.parseDouble(amount.getText().toString());
                    double newAmount = 0.0;
                    if (!svs.isChecked() && !gst.isChecked()) {
                        newAmount = origAmount;
                    } else if (svs.isChecked() && !gst.isChecked()) {
                        newAmount = origAmount * 1.1;
                    } else if (!svs.isChecked() && gst.isChecked()) {
                        newAmount = origAmount * 1.07;
                    } else {
                        newAmount = origAmount * 1.17;
                    }

                    if (discount.getText().toString().trim().length() != 0) {
                        newAmount *= 1 - Double.parseDouble(discount.getText().toString()) / 100;
                    }

                    String mode = " in cash";
                    if (paymentMethod.getCheckedRadioButtonId() == R.id.payByPayNow) {
                        mode = " via PayNow to 912345678";
                    }

                    if (amount.getText().toString().trim().length() == 0  | numPax.getText().toString().trim().length() == 0 | discount.getText().toString().trim().length() == 0) {
                        Toast.makeText(MainActivity.this, "Error: Please do not leave any blanks", Toast.LENGTH_LONG).show();
                    }

                    totalBill.setText("Total Bill: $" + String.format("%.2f", newAmount));
                    int numOfPerson = Integer.parseInt(numPax.getText().toString());
                    if (numOfPerson != 1) {
                        eachPaxPay.setText("Each Pays: $" + String.format("%.2f", newAmount / numOfPerson) + mode);
                    } else {
                        eachPaxPay.setText("Each Pays: $" + newAmount + mode);
                    }
                }
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText("");
                numPax.setText("");
                svs.setChecked(false);
                gst.setChecked(false);
                paymentMethod.check(R.id.payByCash);
                discount.setText("");
                totalBill.setText("");
                eachPaxPay.setText("");
            }
        });
    }
}

