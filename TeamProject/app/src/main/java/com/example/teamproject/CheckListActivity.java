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
        checkListAdapt();
        AdapterView.OnItemClickListener a = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                checkListView.onSaveInstanceState();
            }
        };
        checkListView.setOnItemClickListener(a);
        longClickDelete();
    }

    public void checkListAdapt(){
        String []stringCheckList = checkListString.split("/");
        String []checkedList = checkedString.split("/");
        listItem = new ArrayList<>(Arrays.asList(stringCheckList));
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, listItem);
        checkListView = findViewById(R.id.regionListView);
        checkListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        checkListView.setAdapter(adapter);
        //아무것도 없는 상황의 경우, stringCheckList는 null이 아니라 [""]임.
        if (stringCheckList[0].equals("")){
            ArrayList <String> items = new ArrayList<String>();
            adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, items);
            checkListView.setAdapter(adapter);
            return;
        }
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
        Toast.makeText(getApplicationContext(),"저장완료", Toast.LENGTH_SHORT).show();
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

    public void longClickDelete(){
        checkListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> a_parent, View a_view, int a_position, long a_id) {
                String []stringCheckList = checkListString.split("/");
                delete(stringCheckList[a_position]);
                return true;
            }
        });
    }

    public void delete(String target){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("삭제").setMessage("해당 물품을 삭제하시겠습니까?");
        builder.setPositiveButton("예", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                checkListString = checkListString.replace(target + "/","");
                checkedString = checkedString.replace(target + "/","");
                checkListString = checkListString.replace(target,"");
                checkedString = checkedString.replace(target,"");
                checkListAdapt();
                Toast.makeText(getApplicationContext(),"삭제완료", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener(){
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
        builder.setTitle("추가").setMessage("추가하고자 하는 물품 입력.").setView(edittext);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                String []stringCheckList = checkListString.split("/");
                String append_str = edittext.getText().toString();
                if (append_str.equals("")){
                    Toast.makeText(getApplicationContext(),"추가하고자 하는 물품을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else if (append_str.contains("/")){
                    Toast.makeText(getApplicationContext(),"특수문자 / 는 포함 할 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
                else if (Arrays.asList(stringCheckList).contains(append_str))
                    Toast.makeText(getApplicationContext(),"이미 존재합니다.", Toast.LENGTH_SHORT).show();
                else{
                    checkListString += append_str +"/";
                    checkListAdapt();
                    Toast.makeText(getApplicationContext(),append_str + " 추가완료", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
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
        //파일 읽기
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
