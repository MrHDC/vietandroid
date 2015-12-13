package com.android.conghd.justanapplication;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Calendar;

public class Interactive extends AppCompatActivity {

    TextView txtnum;
    Button btnAdd, btnSubtract;
    CheckBox cbxWhip, cbxChoco;
    EditText edtName;
    int dongia = 5;

    private boolean longClickActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interactive);

        btnSubtract = (Button) findViewById(R.id.btnSubtract);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        txtnum = (TextView) findViewById(R.id.txtNum);
        cbxWhip = (CheckBox) findViewById(R.id.cbxwhip);
        cbxChoco = (CheckBox) findViewById(R.id.cbxchoco);
        edtName = (EditText) findViewById(R.id.edtName);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numProcess("add");
            }
        });

        btnSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numProcess("sub");
            }
        });

        init();

//        btnAdd.setOnLongClickListener(new View.OnLongClickListener() {
//            public boolean onLongClick(View arg0) {
//                Toast.makeText(getApplicationContext(), "Long Clicked ", Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });

    }

    public void init(){
        if (cbxWhip.isChecked())
            dongia += 2;
        if (cbxChoco.isChecked()){
            dongia += 3;
        }
        display(1);
        displayPrice(1 * dongia);
    }


    public void numProcess(String type) {
        int inum = Integer.parseInt(txtnum.getText().toString());

//        if (cbxWhip.isChecked())
//            dongia += 2;
//        if (cbxChoco.isChecked())
//            dongia +=3;

        if (type.toLowerCase().equals("add")) {
            inum++;
        } else if (type.toLowerCase().equals("sub")) {
            if (inum == 0) return;
            inum--;
        }

        if (inum > 20) {
            txtnum.setTextColor(Color.RED);
        } else if (inum > 10) {
            txtnum.setTextColor(Color.YELLOW);
        } else {
            txtnum.setTextColor(Color.parseColor("#4689C8"));
        }

        display(inum);
        displayPrice(inum * dongia);
    }


    public void submitOrder(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setTitle("Order Infomation");

        TextView text = (TextView) dialog.findViewById(R.id.txtInfo);
        text.setText(getOrderInfo());

        Button dialogButtonOk = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                String dear = "Dear " + edtName.getText() + "," + "\nHere is your order infomation\n\n";
                String thanks = "\n\nThanks for choosing our service!";
                composeEmail(new String[]{""}, "Order Infomation", dear + getOrderInfo() + thanks);
            }
        });

        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void composeEmail(String[] addresses, String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT,body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private String getOrderInfo() {
        String info = "Cus: " + (edtName.getText().length()==0?"Customer69":edtName.getText())
                + "\nAdd: " + (cbxWhip.isChecked()?"Cream, ":"") + (cbxChoco.isChecked()?"Chocolate":"") + (!cbxWhip.isChecked() && !cbxChoco.isChecked()?"None":"")
                + "\nNum: " + txtnum.getText() + " cup"
                + "\nTotal: " + ((TextView) findViewById(R.id.txtPrice)).getText().toString();
        return info;
    }

    public void display(int x) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.txtNum);
        quantityTextView.setText("" + x);
    }

    public void displayPrice(int x) {
        TextView priceTextView = (TextView) findViewById(R.id.txtPrice);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(x));
    }

    public void checkWhip(View view) {
        if (cbxWhip.isChecked()) {
            dongia += 2;
        }else{
            dongia -=2;
        }

        int inum = Integer.parseInt(txtnum.getText().toString());
        display(inum);
        displayPrice(inum * dongia);

    }

    public void checkChoco(View view){
        if (cbxChoco.isChecked()) {
            dongia += 3;
        } else {
            dongia -= 3;
        }

        int inum = Integer.parseInt(txtnum.getText().toString());
        display(inum);
        displayPrice(inum * dongia);
    }
}
