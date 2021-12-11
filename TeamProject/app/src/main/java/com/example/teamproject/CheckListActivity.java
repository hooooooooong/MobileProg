package com.example.teamproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CheckListActivity extends AppCompatActivity {
    private ListView checkListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listItem;
    private String checkListString = "";
    private String checkedString = "";
    private String FILECHECKED = "file_checked.txt";
    private String FILELIST = "file_list.txt";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
        checkedString = fileRead(FILECHECKED);
        checkListString = fileRead(FILELIST);
        CheckList();
        AdapterView.OnItemClickListener a = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), adapter.getItem(i), Toast.LENGTH_SHORT).show();
                checkListView.onSaveInstanceState();
            }
        };
        checkListView.setOnItemClickListener(a);
    }

    public void CheckList(){
        String []stringCheckList = checkListString.split("/");
        String []checkedList = checkedString.split("/");
        listItem = new ArrayList<>(Arrays.asList(stringCheckList));
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, listItem);
        checkListView = findViewById(R.id.regionListView);
        checkListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        checkListView.setAdapter(adapter);
        int i = 0;
        for(String str : stringCheckList){
            for (String strc : checkedList)
                if (str.equals(strc))
                    checkListView.setItemChecked(i,true);
            i++;
        }
    }

    public void onClickSave(View view){
        SparseBooleanArray checkedItems = checkListView.getCheckedItemPositions();
        int count = adapter.getCount() ;
        checkedString = "";
        for (int i = 0; i < count; i++) {
            if (checkedItems.get(i)) {
                String st = (String) checkListView.getAdapter().getItem(i);
                checkedString += st + "/";
            }
        }
        fileWrite(FILELIST, checkListString);
        fileWrite(FILECHECKED, checkedString);
    }

    public void initialize(){
        //텍스트파일 초기화때만 사용.
        String[] checkList = {"결제 수단", "카메라", "보조배터리", "상비약", "멀티어댑터", "티슈", "옷",
                "속옷", "잠옷", "신발", "양말", "수건", "화장품", "세면도구", "계절용품", "우산", "충전기", "예약확인"};
        String arrayToString = String.join("/", checkList);
        fileWrite(FILELIST, arrayToString);
        fileWrite(FILECHECKED,"");
        System.out.println("초기화 완료 내용");
        System.out.println(arrayToString);
    }

    public void onClickDelete(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("삭제").setMessage("체크된 요소들이 삭제됩니다. 계속하시겠습니까?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                String []stringCheckList = checkListString.split("/");
                SparseBooleanArray checkedItems = checkListView.getCheckedItemPositions();
                int count = adapter.getCount();
                for (int i = 0; i < count; i++) {
                    if (checkedItems.get(i)) {
                        String temp = stringCheckList[i] + "/";
                        checkListString = checkListString.replace(temp,"");
                        CheckList();
                    }
                }
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void onClickAppend(View view){
        EditText edittext = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("삭제").setMessage("추가하고자 하는 요소 입력.").setView(edittext);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                String []stringCheckList = checkListString.split("/");
                String append_str = edittext.getText().toString();
                if (Arrays.asList(stringCheckList).contains(append_str))
                    Toast.makeText(getApplicationContext(),"이미 존재합니다.", Toast.LENGTH_SHORT).show();
                else{
                    checkListString += append_str +"/";
                    CheckList();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    //test

    public void fileWrite(String fileName ,String toWrite){
        //파일 쓰기
        try {
            FileOutputStream fos = openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(toWrite.getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String fileRead(String fileName){
        String ret = "";
        //아래 코드는 텍스트파일 내용을 불러오는 과정이다.
        try {
            FileInputStream fis = openFileInput(fileName);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            ret = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
