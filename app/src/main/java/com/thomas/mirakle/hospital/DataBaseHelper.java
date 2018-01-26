package com.thomas.mirakle.hospital;

/**
 * Created by thomas on 30/12/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DataBaseHelper";

    private Context mycontext;
    private String DB_PATH;

    private static String DB_NAME = "mirakle.db";

    public SQLiteDatabase myDataBase;

    public DataBaseHelper(Context context) throws IOException {
        super(context, DB_NAME, null, 1);

        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";


        this.mycontext = context;
        boolean dbexist = checkdatabase();
        if (dbexist) {
            opendatabase();
        } else {
            System.out.println("Database doesn't exist");
            createdatabase();
        }
    }

    public void createdatabase() throws IOException {
        boolean dbexist = checkdatabase();
        if (!dbexist) {
            this.getReadableDatabase();
            try {
                copydatabase();
            } catch (IOException e) {
                e.printStackTrace();
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkdatabase() {

        boolean checkdb = false;
        try {
            String myPath = DB_PATH + DB_NAME;
            File dbfile = new File(myPath);
            checkdb = dbfile.exists();
        } catch (SQLiteException e) {
            System.out.println("Database doesn't exist");
        }
        return checkdb;
    }

    private void copydatabase() throws IOException {

        InputStream myinput = mycontext.getAssets().open(DB_NAME);

        String outfilename = DB_PATH + DB_NAME;

        OutputStream myoutput = new FileOutputStream(outfilename);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }

        myoutput.flush();
        myoutput.close();
        myinput.close();
    }

    public void opendatabase() throws SQLException {

        String mypath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mypath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }



    public String get_admin_Password(String id) {
        String password = "";
        String selectQuery = "SELECT password FROM admin WHERE id = " + "'" + id + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            password = cursor.getString(0);
        } else {
            return "Wrong User ID";
        }
        cursor.close();
        db.close();

        return password;
    }
    public String db_check_username(String id,String table_name){
        String user_name = "";
        String selectQuery = "SELECT id FROM "+table_name+" WHERE id = " + "'" + id + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() == 0) {
            return "";
        } else {
            user_name=cursor.getString(0);
        }
        cursor.close();
        db.close();
        return user_name;
    }
    public String get_patient_Password(String id) {
        String password = "";
        String selectQuery = "SELECT password FROM patient WHERE id = " + "'" + id + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            password = cursor.getString(0);
        } else {
            return "Wrong User ID";
        }
        cursor.close();
        db.close();

        return password;
    }
    public String get_patient_name(String message) {
        String name = "";
        String selectQuery = "SELECT name FROM patient WHERE id = " + "'" + message + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            name = cursor.getString(0);
        } else {
            return null;
        }
        cursor.close();
        db.close();

        return name;
    }

    public String get_doctor_Password(String id) {
        String password = "";
        String selectQuery = "SELECT password FROM doctor WHERE id = " + "'" + id + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            password = cursor.getString(0);
        } else {
            return "Wrong User ID";
        }
        cursor.close();
        db.close();

        return password;
    }
    public String get_nurse_Password(String id) {
        String password = "";
        String selectQuery = "SELECT password FROM nurse WHERE id = " + "'" + id + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            password = cursor.getString(0);
        } else {
            return "Wrong User ID";
        }
        cursor.close();
        db.close();

        return password;
    }

    public long add_by_admin(String table_name, String uid,String name,String password,String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put("id",uid);
        value.put("name", name);
        value.put("password",password);
        value.put("address", address);
        long insid = db.insert("admin", null, value);
        //db.execSQL("INSERT INTO "+"'" + table_name + "' VALUES("+uid+","+name+","+password+","+address+");");
        db.close();
        return insid;
    }
    public Long AddUser(String uid, String name,String password,String dob,String bg,String address) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",uid);
        values.put("name", name);
        values.put("password",password);
        values.put("dob",dob);
        values.put("blood_group",bg);
        values.put("address", address);

        long insid = db.insert("patient", null, values);
        db.close();
        return insid;

    }

     ArrayList<String> list_user(String table_name,String a) {

         String selectQuery = "SELECT id FROM "+table_name;

         SQLiteDatabase db = this.getReadableDatabase();
         StringBuffer buffer=new StringBuffer();
         ContentValues values = new ContentValues();
         ContentValues val = new ContentValues();

         Cursor cursor = db.rawQuery(selectQuery, null);
         ArrayList<String> allTableNames=new ArrayList();
         if(cursor.getCount()>0)
         {
             for(int i=0;i<cursor.getCount();i++)
             {
                 cursor.moveToNext();
                 //allTableNames.add(cursor.getString(cursor.getColumnIndex(name)));
                 //values.put("id      ",cursor.getString(0));
                 //values.put("\nname        ",cursor.getString(1));
                 //values.put("\npassword      ",cursor.getString(2));
                 //values.put("\naddress       ",cursor.getString(3));
                    //String ab=cursor.getString(1);
                 buffer.append(cursor.getString(0));
                 //buffer.append("Name        : "+cursor.getString(1)+"\n\t");
                 //buffer.append("Password : "+cursor.getString(2)+"\n\t");
                 //buffer.append("Address    : "+cursor.getString(3)+"\n\t");
                 //Log.d("how are you da",ab);
                 allTableNames.add(String.valueOf(buffer));

                 buffer.delete(0,buffer.length());
                 //val.put("", String.valueOf(values));
                 //System.out.println("hai iam cool"+val);
                 //allTableNames.add(cursor.getString(cursor.getColumnIndex("name")));
                 //allTableNames.addAll(Arrays.asList(cursor.getString(cursor.getColumnIndex(name))));
                 //allTableNames.addAll();
                  //while(cursor.moveToNext())
                 //{
                 //buffer.append("Employee id: "+cursor.getString(0)+"\n");
                  //buffer.append("Name: "+cursor.getString(1)+"\n");
                  //buffer.append("salary: "+cursor.getString(2)+"\n\n");
                   //  allTableNames.add(String.valueOf(buffer));
                 //}

             }
         }

         //System.out.println("hai always cool baby cool"+val);
         cursor.close();
         db.close();
         return allTableNames;

     }

     public ArrayList list_user(String table_name) {

        String selectQuery = "SELECT * FROM "+table_name+" ORDER BY id ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        StringBuffer buffer=new StringBuffer();
        ContentValues values = new ContentValues();
        ContentValues val = new ContentValues();

        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<String> allTableNames=new ArrayList();
        if(cursor.getCount()>0)
        {
            for(int i=0;i<cursor.getCount();i++)
            {
                cursor.moveToNext();
                //allTableNames.add(cursor.getString(cursor.getColumnIndex(name)));
                //values.put("id      ",cursor.getString(0));
                //values.put("\nname        ",cursor.getString(1));
                //values.put("\npassword      ",cursor.getString(2));
                //values.put("\naddress       ",cursor.getString(3));

                buffer.append("\tUser-id      : "+cursor.getString(0)+"\n\t");
                buffer.append("Name        : "+cursor.getString(1)+"\n\t");
                buffer.append("Password : "+cursor.getString(2)+"\n\t");
                buffer.append("Address    : "+cursor.getString(3)+"\n\t");

                allTableNames.add(String.valueOf(buffer));

                buffer.delete(0,buffer.length());
                //val.put("", String.valueOf(values));
                //System.out.println("hai iam cool"+val);
                //allTableNames.add(cursor.getString(cursor.getColumnIndex("name")));
                //allTableNames.addAll(Arrays.asList(cursor.getString(cursor.getColumnIndex(name))));
                //allTableNames.addAll();
                // while(cursor.moveToNext())
                //{
                //buffer.append("Employee id: "+cursor.getString(0)+"\n");
                // buffer.append("Name: "+cursor.getString(1)+"\n");
                //  buffer.append("salary: "+cursor.getString(2)+"\n\n");
                //}

            }
        }

        //System.out.println("hai always cool baby cool"+val);
        cursor.close();
        db.close();
        return allTableNames;

    }

    public ArrayList get_pat_history(String table_name,String id) {
        String selectQuery = "SELECT * FROM "+table_name;//+"; where id="+id+" ORDER BY id ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        StringBuffer buffer=new StringBuffer();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<String> allTableNames=new ArrayList();
        if(cursor.getCount()<=0)
        {
            for(int i=0;i<cursor.getCount();i++) {

                cursor.moveToNext();
                buffer.append("\tCheck-up date      : "+cursor.getString(0)+"\n\t");
                allTableNames.add(String.valueOf(buffer));
                buffer.delete(0,buffer.length());
            }
        }
        else if(cursor.getCount()>0)
        {
            for(int i=0;i<cursor.getCount();i++)
            {
                cursor.moveToNext();
                buffer.append("\tCheck-up date      : "+cursor.getString(0)+"\n\t");
                buffer.append("\tPatient-id      : "+cursor.getString(1)+"\n\t");
                allTableNames.add(String.valueOf(buffer));
                buffer.delete(0,buffer.length());
            }
        }
        if(cursor.getCount()<=0)
        {
            for(int i=0;i<cursor.getCount();i++)
            {
                cursor.moveToNext();
                allTableNames.add("No records Found");
            }
        }
        cursor.close();
        db.close();
        return allTableNames;
    }


    public ArrayList get_pat_desc_detil(String table_name,String id) {
        //String selectQuery = "SELECT * FROM "+table_name;
        String selectQuery ="select * from patient_desc where id='"+id+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        StringBuffer buffer=new StringBuffer();

        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList allTableNames=new ArrayList();
        if(cursor.getCount()>0)
        {
            for(int i=0;i<cursor.getCount();i++)
            {
                cursor.moveToNext();
                buffer.append("\tCheck-up date      : "+cursor.getString(0)+"\n\t");
                buffer.append("\tPatient-id      : "+cursor.getString(1)+"\n\t");
                buffer.append("Age       : "+cursor.getString(2)+"\n\t");
                buffer.append("disease  : "+cursor.getString(3)+"\n\t");
                buffer.append("\tMedicine\t\t-\t\tTaking time"+"\n\t");
                buffer.append(cursor.getString(4)+"\t\t-"+cursor.getString(5)+"\n\t");
                buffer.append(cursor.getString(6)+"\t\t-"+cursor.getString(7)+"\n\t");
                buffer.append(cursor.getString(8)+"\t\t-"+cursor.getString(9)+"\n\t");
                buffer.append(cursor.getString(10)+"\t\t-"+cursor.getString(11)+"\n\t");
                buffer.append(cursor.getString(12)+"\t\t-"+cursor.getString(13)+"\n\t");
                buffer.append(cursor.getString(14)+"\t\t-"+cursor.getString(15)+"\n\t");
                buffer.append(cursor.getString(16)+"\t\t-"+cursor.getString(17)+"\n\t");
                buffer.append(cursor.getString(18)+"\t\t-"+cursor.getString(19)+"\n\t");
                buffer.append(cursor.getString(20)+"\t\t-"+cursor.getString(21)+"\n\t");
                buffer.append(cursor.getString(22)+"\t\t-"+cursor.getString(23)+"\n\t");
                allTableNames.add(String.valueOf(buffer));
                buffer.delete(0,buffer.length());

            }
        }
        if(cursor.getCount()==0)
        {
            for(int i=0;i<cursor.getCount();i++) {

                cursor.moveToNext();
                allTableNames.add("No records Found");
            }
        }
        //allTableNames.add(String.valueOf(buffer));
        cursor.close();
        db.close();
        return allTableNames;

    }


    public String logged_user(String table_name,String id) {
        String logged_user_name = "";
        //String selectQuery = "SELECT name FROM "+table_name+ " WHERE id = " + "'" + id + "'";
        String selectQuery ="select * from patient_desc where id='"+id+"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            logged_user_name = cursor.getString(0);
        } else {
            return "Wrong User ID";
        }
        cursor.close();
        db.close();

        return logged_user_name.toUpperCase();
    }


    public String[] get_data(String tb_name, String userid) {
        String selectQuery = "SELECT name,password,address FROM "+tb_name+" WHERE id = "+"'" + userid + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        ArrayList<String> allTableNames=new ArrayList();
        String[] name = new String[3];
        String pass;
        String address;
        StringBuffer buffer=new StringBuffer();
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {

            name[0]=(cursor.getString(0));
            name[1]=(cursor.getString(1));
            name[2]=(cursor.getString(2));
            //allTableNames.add(String.valueOf(buffer));
        }
        cursor.close();
        db.close();
        return name;
    }

    public void update(String table, String primary_key, String name, String password, String address) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        contentValues.put("address", address);
        db.update(table, contentValues, "id = ?", new String[]{primary_key});
        db.close();
    }
    public Long Add_desc(String date, String id,int age,String disease,String desc,String m1,String t1,
                        String m2,String t2,String m3,String t3,String m4,String t4,String m5,String t5,
                        String m6,String t6,String m7,String t7,String m8,String t8,String m9,String t9,
                        String m10,String t10) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("checkupdate",date);
        values.put("id", id);
        values.put("age",age);
        values.put("disease",disease);
        values.put("desc",desc);
        values.put("medicine1", m1);
        values.put("taking_time1", t1);
        values.put("medicine2", m2);
        values.put("taking_time2", t2);
        values.put("medicine3", m3);
        values.put("taking_time3", t3);
        values.put("medicine4", m4);
        values.put("taking_time4", t4);
        values.put("medicine5", m5);
        values.put("taking_time5", t5);
        values.put("medicine6", m6);
        values.put("taking_time6", t6);
        values.put("medicine7", m7);
        values.put("taking_time7", t7);
        values.put("medicine8", m8);
        values.put("taking_time8", t8);
        values.put("medicine9", m9);
        values.put("taking_time9", t9);
        values.put("medicine10", m10);
        values.put("taking_time10", t10);


        long insid = db.insert("patient_desc", null, values);
        db.close();
        return insid;

    }


    public void drop_desc_details(String message, int i) {
        Log.i("droptable detail", "drop_desc_details: "+message+"cool"+i);
    }
}
