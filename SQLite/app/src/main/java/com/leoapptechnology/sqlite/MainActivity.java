package com.leoapptechnology.sqlite;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    MaterialButton insertButton, viewButton, deleteButton, updateButton;
    TextInputEditText personName, personUserName, personPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        insertButton = findViewById(R.id.insertButton);
        deleteButton = findViewById(R.id.deleteButton);
        updateButton = findViewById(R.id.updateButton);
        viewButton = findViewById(R.id.showButton);
        personName = findViewById(R.id.name);
        personPassword = findViewById(R.id.password);
        personUserName = findViewById(R.id.userName);

        MyDatabase myDatabase = new MyDatabase(this);
          SQLiteDatabase db = myDatabase.getReadableDatabase();


        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = personName.getText().toString();
                String userName = personUserName.getText().toString();
                String password = personPassword.getText().toString().trim().replaceAll(" ", "");


                if (name.equals("") || userName.equals("") || password.equals("")) {
                    Toast.makeText(MainActivity.this, "Please Insert All Data", Toast.LENGTH_SHORT).show();
                } else {

                    boolean i = myDatabase.insert_data(name, userName, password);

                    if (i == true) {
                        Toast.makeText(MainActivity.this, "Data inserted Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Data inserted failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor c = myDatabase.getinfo();

                if (c.getCount()==0){
                    Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_SHORT).show();
                }else {

                    StringBuffer stringBuffer = new StringBuffer();
                    while (c.moveToNext()){
                        stringBuffer.append("Id: "+c.getString(0)+"\n");
                        stringBuffer.append("Name: "+c.getString(1)+"\n");
                        stringBuffer.append("User Name: "+c.getString(2)+"\n");
                        stringBuffer.append("Password: "+c.getString(3)+"\n\n\n");

                    }
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Sign Up User Data")
                            .setMessage(stringBuffer.toString())
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).show();
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = personName.getText().toString();
                String userName = personUserName.getText().toString();
                String password = personPassword.getText().toString().trim().replaceAll(" ", "");

                boolean i = myDatabase.update_data(name,userName,password);

                    if (i==true){
                        Toast.makeText(MainActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(MainActivity.this, "Not Successfull", Toast.LENGTH_SHORT).show();
                    }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = personUserName.getText().toString();

                boolean i = myDatabase.delete_data(username);

                if (i==true){
                    Toast.makeText(MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Data NOT deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}