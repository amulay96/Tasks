package com.example.tasks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class EditTaskDesk extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    EditText titleDoes,descDoes,dateDoes;
    Button btnSaveUpate,btnDelete;
    DatabaseReference reference;
    DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task_desk);

        titleDoes=findViewById(R.id.titledoes);
        descDoes=findViewById(R.id.descdoes);
        dateDoes=findViewById(R.id.datedoes);

        btnSaveUpate=findViewById(R.id.btnSaveUpdate);
        btnDelete=findViewById(R.id.btnDelete);

        titleDoes.setText(getIntent().getStringExtra("titledoes"));
        descDoes.setText(getIntent().getStringExtra("descdoes"));
        dateDoes.setText(getIntent().getStringExtra("datedoes"));

        dateDoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();


            }
        });


        final String keykeyDoes = getIntent().getStringExtra("keydoes");

        reference= FirebaseDatabase.getInstance().getReference().child("BoxDoes").child("Does"+keykeyDoes);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Intent a = new Intent(EditTaskDesk.this,MainActivity.class);
                            startActivity(a);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Failure!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        //make an event for Button
        btnSaveUpate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference.child("titledoes").setValue(titleDoes.getText().toString());
                        reference.child("descdoes").setValue(descDoes.getText().toString());
                        reference.child("datedoes").setValue(dateDoes.getText().toString());
                        reference.child("keydoes").setValue(keykeyDoes);
                        Intent a = new Intent(EditTaskDesk.this,MainActivity.class);
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
        dateDoes.setText(date);
    }
}
