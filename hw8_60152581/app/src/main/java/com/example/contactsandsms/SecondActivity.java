package com.example.contactsandsms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;

import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;



public class SecondActivity extends AppCompatActivity {
    final int REQUEST_CODE_READ_CONTACTS = 1;
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // 권한 있음! 해당 데이터나 장치에 접근!
        Intent intent = getIntent();
        int what = intent.getIntExtra("what",0);

        list = findViewById(R.id.listview);
        if(what == 0){
            if (ContextCompat.checkSelfPermission(SecondActivity.this, Manifest.permission.READ_CONTACTS)
                    != PackageManager.PERMISSION_GRANTED) { // 권한이 없으므로, 사용자에게 권한 요청 다이얼로그 표시
                ActivityCompat.requestPermissions(SecondActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE_READ_CONTACTS);
            } else
                getContacts();
        }
        if(what == 1){
            if (ContextCompat.checkSelfPermission(SecondActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SecondActivity.this, new String[]{Manifest.permission.READ_SMS}, REQUEST_CODE_READ_CONTACTS);
            }
            else
                SMSContacts();

        }

    }
    private void SMSContacts(){

        Uri uri = Uri.parse("content://sms/inbox");

        String[] reqCols = new String[]{"_id", "address", "body"};

// Get Content Resolver object, which will deal with Content Provider
        ContentResolver cr = getContentResolver();

// Fetch Inbox SMS Message from Built-in Content Provider
        Cursor c = cr.query(uri, reqCols, null, null, null);

        String[] contactsColumns = {
                "address",
                "body"
        };

        int[] contactsListItems = { // 열의 값을 출력할 뷰 ID (layout/item.xml 내)
                R.id.text01,
                R.id.text02
        };

        SimpleCursorAdapter adapter1 = new SimpleCursorAdapter(this,
                R.layout.listview,
                c,
                contactsColumns,
                contactsListItems,
                0);


        adapter1.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if(columnIndex == 1){
                    String number = cursor.getString(1);
                    String name = name(number);
                    TextView text = (TextView) view;
                    text.setText(name);
                    return true;
                }
                return false;
            }
        });
        list.setAdapter(adapter1);

    }
    private String name(String number){
        Uri uri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));
        String name = " ";

        ContentResolver contentResolver = getContentResolver();
        Cursor contactLookup = contentResolver.query(uri, new String[] {
                        BaseColumns._ID, ContactsContract.PhoneLookup.DISPLAY_NAME },
                null, null, null);

        try {
            if (contactLookup != null && contactLookup.getCount() > 0) {
                contactLookup.moveToNext();
                name = contactLookup.getString(contactLookup
                        .getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
            }
        } finally {
            if (contactLookup != null) {
                contactLookup.close();
            }
        }
        if(name == " ")
            return number;
        return name;
    }

    private void getContacts() {



        String [] projection = {
                ContactsContract.CommonDataKinds.Phone._ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER

        };

        // 연락처 전화번호 타입에 따른 행 선택을 위한 선택 절
        String selectionClause = ContactsContract.CommonDataKinds.Phone.TYPE + " = ? ";

        // 전화번호 타입이 'MOBILE'인 것을 지정
        String[] selectionArgs = {""+ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE};

        Cursor c = getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,  // 조회할 데이터 URI
                projection,         // 조회할 컬럼 들
                null,    // 선택될 행들에 대한선택될 행들에 대한 조건절
                null,      // 조건절에 필요한 파라미터
                null);              // 정렬 안



        String[] contactsColumns = { // 쿼리결과인 Cursor 객체로부터 출력할 열들
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };

        int[] contactsListItems = { // 열의 값을 출력할 뷰 ID (layout/item.xml 내)
                R.id.text01,
                R.id.text02
        };

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.listview,
                c,
                contactsColumns,
                contactsListItems,
                0);



        list.setAdapter(adapter);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContacts();
            } else {
                Toast.makeText(getApplicationContext(), "READ_CONTACTS 접근 권한이 필요합니다", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
