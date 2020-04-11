package com.example.tasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.OneShotPreDrawListener;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class NewTaskAct extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    TextView titlepage,addtitle,adddesc,adddate;
    EditText titledoes,descdoes,datedoes;
    Button btnSaveTask,btnCancel;
    DatabaseReference reference;
    Integer doesNum = new Random().nextInt();
    String keydoes=Integer.toString(doesNum);

    DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        titlepage=findViewById(R.id.titlepage);

        addtitle=findViewById(R.id.addtitle);
        adddesc=findViewById(R.id.adddesc);
        adddate=findViewById(R.id.adddate);

        titledoes=findViewById(R.id.titledoes);
        descdoes=findViewById(R.id.descdoes);
        datedoes=findViewById(R.id.datedoes);

        btnSaveTask=findViewById(R.id.btnSaveTask);
        btnCancel=findViewById(R.id.btnCancel);

        datedoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();


            }
        });


        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Insert Data to Database
                reference= FirebaseDatabase.getInstance().getReference().child("BoxDoes").child("Does"+doesNum);
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot.getRef().child("titledoes").setValue(titledoes.getText().toString());
                        dataSnapshot.getRef().child("descdoes").setValue(descdoes.getText().toString());
                        dataSnapshot.getRef().child("datedoes").setValue(datedoes.getText().toString());
                        dataSnapshot.getRef().child("keydoes").setValue(keydoes);

                        Intent a = new Intent(NewTaskAct.this,MainActivity.class);
                        startActivity(a);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });


    }

    private void showDatePickerDialog() {
        datePickerDialog = new DatePickerDialog(this,this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        String date=month+"/"+dayOfMonth+"/"+year;
        datedoes.setText(date);
    }
}
