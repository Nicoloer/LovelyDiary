package com.example.lovelydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Console;
import java.sql.Blob;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddDiary extends AppCompatActivity {
    private EditText title,author,date,content;
    private SQLiteDatabase dbDiary;
    private DBhelper dbhelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diary);
        dbhelper=new DBhelper(this,"diary.db",null,3);
//      加载界面元素
        title=(EditText) findViewById(R.id.title);
        author=(EditText) findViewById(R.id.author);
        date=(EditText) findViewById(R.id.date);
        content=(EditText) findViewById(R.id.content);
        Button buttonSave=findViewById(R.id.save);
        Button buttonCancel=findViewById(R.id.cancel);
        String setAuthor;
            SharedPreferences pref=getSharedPreferences("author_info",MODE_PRIVATE);
            String authorname=pref.getString("name","Sam");
            setAuthor=authorname;
        author.setText(setAuthor);

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

                strTitle=title.getText().toString();
                strAuthor=author.getText().toString();
                strDate=date.getText().toString();
                strContent=content.getText().toString();
                //异常扑捉
               if(isValidDate(strDate)==false){
                   Toast.makeText(AddDiary.this,"日期不正确喔!",Toast.LENGTH_SHORT).show();
                   //返回界面
               }
                else if(strTitle.equals("")&&strContent.equals("")){
                    Toast.makeText(AddDiary.this,"要输入内容哦!",Toast.LENGTH_SHORT).show();
                    //返回界面
                   //////hjjjkkjkjk
                }
                else{
                    ContentValues values = new ContentValues();
                    values.put("title", strTitle);
                    values.put("author", strAuthor);
                    values.put("date", strDate);
                    values.put("content", strContent);
                    values.put("image", strImage);

                    Log.d("insert", "title" + strTitle);

                    dbDiary.insert("diary", null, values);

                    Intent intent = new Intent();
                    intent.putExtra("data_return", "hello");
                    setResult(RESULT_OK, intent);
                    finish();
                }
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

    }

// 点击返回键
    @Override
    public void onBackPressed(){
        Intent intent=new Intent();
        intent.putExtra("data_return","bye");
        setResult(RESULT_CANCELED,intent);
        finish();
    }

    public static boolean isValidDate(String sDate) {
        String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
        String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
                + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
                + "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
                + "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
                + "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        if ((sDate != null)) {
            Pattern pattern = Pattern.compile(datePattern1);
            Matcher match = pattern.matcher(sDate);
            if (match.matches()) {
                pattern = Pattern.compile(datePattern2);
                match = pattern.matcher(sDate);
                return match.matches();
            } else {
                return false;
            }
        }
        return false;
    }
}