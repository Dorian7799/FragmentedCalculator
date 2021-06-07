package com.example.fragmentedcalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView result;
    private TextView motd;
    public float res;

    private float firstNum;
    private char currentOp;
    private int rootID;
    private int powerID;

    private FragmentManager frags;
    private FragmentTransaction trans;
    private boolean onSci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frags = getSupportFragmentManager();
        trans = frags.beginTransaction();
        trans.add(R.id.fragment, new FirstCalculator()).commit();

        motd = findViewById(R.id.motd);
        result = findViewById(R.id.result);

        onSci = false;

        /*mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(currentUser.getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Person person = dataSnapshot.getValue(Person.class);
                motd.setText(person.getName());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });*/


    }

    private void checkEndOp() {
        String checkOp = result.getText().toString();
        if (checkOp.length() > 1 && (checkOp.charAt(checkOp.length() - 1) == 'X' || checkOp.charAt(checkOp.length() - 1) == '-' || checkOp.charAt(checkOp.length() - 1) == '+' || checkOp.charAt(checkOp.length() - 1) == '/' || checkOp.charAt(checkOp.length() - 1) == '%'))
            result.setText(checkOp.substring(0, checkOp.length() - 1));
    }

    public void Sci(View view) {
        if (!onSci) {
            trans = frags.beginTransaction();
            trans.add(R.id.secondCal, new SecondCalculator()).addToBackStack(null).commit();
            rootID = SecondCalculator.getRootID();
            powerID = SecondCalculator.getPowerID();
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            onSci = true;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        onSci = false;

    }

    public void op(View view) {
        Button button = (Button) view;
        if (result.getText().toString().length() > 0)
            firstNum = Float.parseFloat(result.getText().toString());

        result.setText("0");

        switch (button.getText().toString()) {
            case "+":
                currentOp = '+';
                break;
            case "/":
                currentOp = '/';
                break;
            case "-":
                currentOp = '-';
                break;
            case "X":
                currentOp = 'X';
                break;
            case "%":
                currentOp = '%';
        }
    }

    public void clear(View view) {
        result.setText("0");
    }

    public void number(View view) {
        Button button = (Button) view;
        if (result.getText().toString() == "0")
            result.setText("");
        result.append(button.getText().toString());
    }

    public void factorial(View view) {
        int num = (int) Float.parseFloat(result.getText().toString());
        int res = 1;
        while (num > 1) {
            res *= num;
            num--;
        }
        result.setText(res + "");
    }


    public void specialOp(View view) {
        ImageButton button = (ImageButton) view;
        if (button.getId() == rootID)
            result.setText("" + Math.sqrt(Float.parseFloat(result.getText().toString())));
        if (button.getId() == powerID) {
            firstNum = Float.parseFloat(result.getText().toString());
            result.setText("0");
            currentOp = 'p';

        }

    }

    public void equal(View view) {
        double res = 0;
        float secondNum = Float.parseFloat(result.getText().toString());
        switch (currentOp) {
            case '+':
                res = firstNum + secondNum;
                break;
            case 'X':
                res = firstNum * secondNum;
                break;
            case '-':
                res = firstNum - secondNum;
                break;
            case '/':
                res = firstNum / secondNum;
                break;
            case 'p':
                res = Math.pow(firstNum, secondNum);
                break;
            case '%':
                res = firstNum % secondNum;
                break;
        }

        result.setText("" + res);
    }
}