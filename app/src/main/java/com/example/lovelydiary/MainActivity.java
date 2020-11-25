package com.example.lovelydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase dbDiary;
    private DBhelper dbhelper;
    private DiaryAdapter diaryAdapter;
    private List<Diary> diaryList=new ArrayList<>();

//      当主活动再次调用时执行onResume();
    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);
        Log.d("crate", "onCreate: resume!!");
//       读写数据库
        dbhelper = new DBhelper(this, "diary.db", null, 3);
        dbDiary = dbhelper.getWritableDatabase();
//       加载布局
        Button buttonAdd = (Button) findViewById(R.id.add);
//        TextView test = findViewById(R.id.test);
        ListView listView=findViewById(R.id.listView);
//        初始化DiaryList
        initDiarys();
        Log.d("diarylist", diaryList.toString());
//        配置Adapter
        diaryAdapter=new DiaryAdapter(MainActivity.this,R.layout.diary_item,diaryList);

        listView.setAdapter(diaryAdapter);

//       listview 点击事件到UPdateDairy
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("点击事件position", String.valueOf(position));
//       显示单篇日记的内容
                Intent intent=new Intent(MainActivity.this, UpdateDiary.class);
//为下一个活动传递点击的note的数据
                Diary diary=new Diary();
                diary=diaryList.get(position);
                intent.putExtra("diaryId", Integer.toString(diary.getId()));
                intent.putExtra("title",diary.getTitle());
                intent.putExtra("author", diary.getAuthor());
                intent.putExtra("date", diary.getDate());
                intent.putExtra("content", diary.getContent());
                startActivityForResult(intent,2);
            }
        });
//       buttonAdd——跳转到AddDiary
            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, AddDiary.class);
                    startActivityForResult(intent, 1);
                }
            });
        }

//      当主活动第一次调用时create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        存储作者信息到SP中，onCreate()_设置为默认字段
        SharedPreferences.Editor editor=getSharedPreferences("author_info",MODE_PRIVATE).edit();
        editor.putString("name","Nico");
        editor.putInt("age",18);
        editor.apply();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("crate", "onCreate: create!!");
        dbhelper=new DBhelper(this,"diary.db",null,3);
        dbDiary=dbhelper.getWritableDatabase();
        Button buttonAdd=(Button)findViewById(R.id.add);
        ListView listView=findViewById(R.id.listView);

//        初始化DiaryList
        initDiarys();
        Log.d("diarylist", diaryList.toString());
//        配置Adapter
        diaryAdapter=new DiaryAdapter(MainActivity.this,R.layout.diary_item,diaryList);

        listView.setAdapter(diaryAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("点击事件position", String.valueOf(position));

//       显示单篇日记的内容
                Intent intent=new Intent(MainActivity.this,UpdateDiary.class);
//为下一个showContent活动传递点击的note的数据
                Diary diary=new Diary();
                diary=diaryList.get(position);
                intent.putExtra("diaryId", Integer.toString(diary.getId()));
                intent.putExtra("title",diary.getTitle());
                intent.putExtra("author", diary.getAuthor());
                intent.putExtra("date", diary.getDate());
                intent.putExtra("content", diary.getContent());
                startActivityForResult(intent,2);
            }
        });


//      传intent
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddDiary.class);
                startActivityForResult(intent,1);
            }
        });

    }


    public void initDiarys(){
        Cursor cursor = dbDiary.query("diary", null, null, null, null, null, null);
        diaryList.clear();
        if (cursor.moveToFirst()) {

            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String author = cursor.getString(cursor.getColumnIndex("author"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String image = cursor.getString(cursor.getColumnIndex("image"));
                Diary newdiary=new Diary(id,title,content,date,author,image);
                diaryList.add(newdiary);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }


//  接受不同的intent返回值来执行别的操作
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
            case 2:
                if(resultCode==RESULT_CANCELED){
                    String returnedData=data.getStringExtra("data_return");
                    Log.d("MainActivity",returnedData);
                }
//                else if(requestCode==RESULT_OK){
                else {
                    String returnedData=data.getStringExtra("data_return");
                    Log.d("MainActivity",returnedData);
                }
                break;
            default:
        }
    }
}