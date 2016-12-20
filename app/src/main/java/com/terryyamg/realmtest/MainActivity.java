package com.terryyamg.realmtest;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm realm;
    TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();

        tvMessage = (TextView) findViewById(R.id.tvMessage);

        //Create
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });
        //Update
        FloatingActionButton fabUpdate = (FloatingActionButton) findViewById(R.id.fabUpdate);
        fabUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
        //Delete
        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fabDelete);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        read();
    }

    private void create() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.table_dialog);
        final EditText etName = (EditText) dialog.findViewById(R.id.etName);
        final EditText etAge = (EditText) dialog.findViewById(R.id.etAge);
        Button btnSave = (Button) dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        int personCount;
                        //PrimaryKey增加
                        try {
                            personCount = realm.where(Person.class).max("id").intValue() + 1;
                        } catch (Exception e) {
                            personCount = 1; //從1開始
                        }
                        Log.i("personCount", personCount + "");
                        // 人 Table
                        Person personC = realm.createObject(Person.class, personCount);
                        personC.setName(etName.getText().toString());   // 輸入姓名
                        personC.setAge(Integer.parseInt(etAge.getText().toString()));   //輸入年齡

                        /* 公司 Table 測試 */
//                        Company company1 = realm.createObject(Company.class);
//                        company1.setId(4);
//                        company1.setCompanyName("公司2");
//                        personC.getCompany().add(company1);
//
//                        Company company2 = realm.createObject(Company.class);
//                        company2.setId(5);
//                        company2.setCompanyName("公司3");
//                        personC.getCompany().add(company2);
//
//                        Company company3 = realm.createObject(Company.class);
//                        company3.setId(6);
//                        company3.setCompanyName("公司4");
//                        personC.getCompany().add(company3);

                        read();
                        dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
    }

    private void read() {
        /* 公司 Table 測試 */
//        RealmResults<Person> person = realm.where(Person.class).equalTo("company.companyName", "公司2").equalTo("company.companyName", "公司1").findAll();
        RealmResults<Person> person = realm.where(Person.class).findAll();
        String message = "";
        Log.i("person.size()",person.size()+"");
        for (int i = 0; i < person.size(); i++) {
            message += person.get(i).getName() + " - " + person.get(i).getAge() + "\n";
            /* 公司 Table 測試 */
//            Log.i("getCompany.size()",person.get(i).getCompany().size()+"");
//            for(int j = 0; j <person.get(i).getCompany().size();j++) {
//                message += person.get(i).getName() + " - " + person.get(i).getAge() + ":" + person.get(i).getCompany().get(j).getCompanyName() + "\n";
//            }
        }
        tvMessage.setText(message);
    }

    private void update() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.table_dialog);
        final EditText etName = (EditText) dialog.findViewById(R.id.etName);
        final EditText etAge = (EditText) dialog.findViewById(R.id.etAge);
        Button btnSave = (Button) dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        //修改第一筆
                        Person person = realm.where(Person.class).equalTo("id", 1).findFirst();
                        person.setName(etName.getText().toString());
                        person.setAge(Integer.parseInt(etAge.getText().toString()));

                        read();
                        dialog.dismiss();
                    }
                });
            }
        });

        dialog.show();
    }

    private void delete() {
        final RealmResults<Person> results = realm.where(Person.class).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                //刪除某一筆
//                Person p = results.get(1);
//                p.deleteFromRealm();
                //刪除第一筆
//                results.deleteFirstFromRealm();
                //刪除最後一筆
//                results.deleteLastFromRealm();
                //刪除全部
                results.deleteAllFromRealm();
            }
        });
        read();
    }

}
