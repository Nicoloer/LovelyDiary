package com.example.lovelydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Blob;

public class  UpdateDiary extends AppCompatActivity {
    private EditText title,author,date,content;
    private SQLiteDatabase dbDiary;
    private DBhelper dbhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_diary);
        dbhelper=new DBhelper(this,"diary.db",null,3);

//      加载界面元素
        title=(EditText) findViewById(R.id.title);
        author=(EditText) findViewById(R.id.author);
        date=(EditText) findViewById(R.id.date);
        content=(EditText) findViewById(R.id.content);
        Button buttonSave=findViewById(R.id.save);
        Button buttonCancel=findViewById(R.id.cancel);
        Button buttonDelete=findViewById(R.id.delete);

        Intent intent=getIntent();
        title.setText(intent.getStringExtra("title"));
        author.setText(intent.getStringExtra("author"));
        date.setText(intent.getStringExtra("date"));
        content.setText(intent.getStringExtra("content"));

        buttonSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//      获取数据（先初始化为null）
                String strTitle="";
                String strAuthor="";
                String strDate="";
                String strContent="";
//                Byte[] blob = new Byte[0];
                String strImage="";
                dbDiary=dbhelper.getWritableDatabase();
                int id= Integer.parseInt(getIntent().getStringExtra("diaryId"));
                strTitle=title.getText().toString();
                strAuthor=author.getText().toString();
                strDate=date.getText().toString();
                strContent=content.getText().toString();

                if(strTitle.equals("")&&strDate.equals("")&&strContent.equals("")){
                    Toast.makeText(UpdateDiary.this,"要输入内容哦!",Toast.LENGTH_SHORT).show();
                    //返回界面
                    Intent intent=new Intent();
                    intent.putExtra("data_return","empty");
                    setResult(RESULT_OK,intent);
                    finish();
                }
                ContentValues values=new ContentValues();
                values.put("title",strTitle);
                values.put("content",strContent);
                values.put("date",strDate);
                values.put("author",strAuthor);
                values.put("image", strImage);

                dbDiary.update("diary",values,"id=?",new String[]{String.valueOf(id)});

                Toast.makeText(UpdateDiary.this,"更新成功!",Toast.LENGTH_SHORT).show();
                //返回界面
                Intent intent=new Intent();
                intent.putExtra("data_return","hello");
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.putExtra("data_return","bye");
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbDiary=dbhelper.getWritableDatabase();
                int id= Integer.parseInt(getIntent().getStringExtra("diaryId"));
                dbDiary.delete("diary","id=?",new String[]{String.valueOf(id)});
                Intent intent=new Intent();
                intent.putExtra("data_return","bye");
                setResult(RESULT_CANCELED,intent);
                finish();
            }
        });
    }

    // 点击返回键
    @Override
    public void onBackPressed(){
        Intent intent=new Intent();
        intent.putExtra("data_return","bye");
        setResult(RESULT_CANCELED,intent);
        finish();
    }

}