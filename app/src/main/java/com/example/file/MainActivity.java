package com.example.file;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    private static final String FILE_NAME = "example.txt";
    EditText mEdTxt;
    Button save , load , save_ex , read_ex;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEdTxt = findViewById(R.id.ed_txt);
        save = findViewById(R.id.save_btn);
        load = findViewById(R.id.load_btn);
        save_ex = findViewById(R.id.save_ex_btn);
        read_ex = findViewById(R.id.read_ex_btn);
        textView = findViewById(R.id.txt_view);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEdTxt.getText().toString();
                FileOutputStream fos = null;

                try {
                    fos = openFileOutput(FILE_NAME , MODE_PRIVATE);
                    fos.write(text.getBytes());

                    mEdTxt.getText().clear();
                    Toast.makeText(MainActivity.this, "Saved to "+ getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileInputStream fis = null;

                try {
                    fis = openFileInput(FILE_NAME);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    StringBuilder sb =new StringBuilder();
                    String text;

                    while ((text = br.readLine()) != null){
                        sb.append(text).append("\n");
                    }
                    textView.setText(sb.toString());
                    mEdTxt.setText(sb.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if(fis != null){
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });


        //////////////////////EXTERNAL//////////////////



        save_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String state ;
                    state = Environment.getExternalStorageState();
                    if(Environment.MEDIA_MOUNTED.equals(state)){
                        File Root = new File(Environment.getExternalStorageState());
                        File Dir = new File(Root.getAbsolutePath()+ "/MyAppFile");
                            if(!Dir.exists()){
                                Dir.mkdir();
                            }
                            File file = new File(Dir , "MyMessage.txt");
                            String Message = mEdTxt.getText().toString();
                            try {
                                FileOutputStream fileOutputStream = new FileOutputStream(file);
                                fileOutputStream.write(Message.getBytes());
                                fileOutputStream.close();
                                mEdTxt.setText("");
                                Toast.makeText(MainActivity.this, "Message Saved", Toast.LENGTH_SHORT).show();

                            } catch (FileNotFoundException e){
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                    } else {
                        Toast.makeText(MainActivity.this, "SD Card not found", Toast.LENGTH_SHORT).show();
                    }

            }
        });

        read_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File Root = Environment.getExternalStorageDirectory();
                File Dir = new File(Root.getAbsolutePath()+ "/MyAppFile");
                File file = new File(Dir , "MyMessage.txt");
                String Message;
                try {
                    FileInputStream fis = new FileInputStream(file);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    StringBuffer sb = new StringBuffer();

                    while ((Message = br.readLine()) != null){
                        sb.append(Message + "\n");
                    }
                    textView.setText(sb.toString());
                    textView.setVisibility(View.VISIBLE);



                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }


}
