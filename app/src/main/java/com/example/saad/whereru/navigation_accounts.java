package com.example.saad.whereru;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.UUID;



import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class navigation_accounts extends AppCompatActivity {
    public static final int RESULT_LOAD_IMAGE=0;
    static String name ="";
    String[] mobileArray ;
    int[] ids;
    Integer[] imageid ;
    String times[] = {};
    static ProgressDialog pdMarkers;
    private ListView listView;
    EditText txt ;
    ImageView img ;
    Button select ;
    Button cancel ;

    private Button buttonChoose;
    private Button buttonUpload;

    private ImageView imageView;

    //private EditText editTextName;




   // public static final String UPLOAD_URL = "http://192.168.0.103/uploadImage.php";
  //  public static final String IMAGES_URL = "http://192.168.94.1/AndroidImageUpload/getImages.php";

    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;

    private String KEY_IMAGE = "image";
    private String KEY_NAME = "name";

    private String names[] = {



    };








    final Context context = this;
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_friends:

                    listView = (ListView) findViewById(R.id.listView);

                    updateList();

                    final CustomList customList = new CustomList((Activity) context, mobileArray,ids, imageid, times,2);


                    txt.setVisibility(View.VISIBLE);
                    img.setVisibility(View.GONE);
                    select.setVisibility(View.GONE);
                    cancel.setVisibility(View.GONE);
                    listView.setEnabled(true);


                    listView.setAdapter(customList);
                    listView.setVisibility(View.VISIBLE);

                    return true;
                case R.id.navigation_search:

                     asd();


                    return true;
                case R.id.navigation_avatar:
                    showFileChooser();
                return true;
                case R.id.navigation_notifications:

                    listView = (ListView) findViewById(R.id.listView);

                    updateList(1);

                    final CustomList customList2 = new CustomList((Activity) context, mobileArray,ids, imageid, times,3);


                    txt.setVisibility(View.VISIBLE);
                    img.setVisibility(View.GONE);
                    select.setVisibility(View.GONE);
                    cancel.setVisibility(View.GONE);
                    listView.setEnabled(true);
                    listView.setAdapter(customList2);
                    listView.setVisibility(View.VISIBLE);





                    return true;
            }
            return false;
        }

    };

    private void asd(){



        pdMarkers.show();
      logger l = new logger((Activity) context, (Activity) context, "--");
            l.execute("dlAll", "", "");
          }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_accounts);
        requestStoragePermission();



        txt = (EditText) findViewById(R.id.textView1);
        img = (ImageView) findViewById(R.id.imageView4);
        select = (Button) findViewById(R.id.selectButton);
        cancel = (Button) findViewById(R.id.cancelButton);

        listView = (ListView) findViewById(R.id.listView);

        updateList();

        final CustomList customList = new CustomList((Activity) context, mobileArray,ids, imageid, times,2);


        txt.setVisibility(View.VISIBLE);
        img.setVisibility(View.GONE);
        select.setVisibility(View.GONE);
        cancel.setVisibility(View.GONE);
        listView.setEnabled(true);


        listView.setAdapter(customList);
        listView.setVisibility(View.VISIBLE);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadMultipart();

            }
        });


        //The OnClick for SearchAll


        pdMarkers = new ProgressDialog(navigation_accounts.this);
        pdMarkers.setTitle("Downloading");
        pdMarkers.setMessage("Locations");
        pdMarkers.setCancelable(false);
        pdMarkers.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {


                String times[] = {"","","","","","","","",};
                //When download ends
                try {
                      int count = name.length() - name.replace("-", "").length();
                      names=new String[count];
                    ids=new int[count];
                      imageid = new Integer[count];
                    times=new String[count];
                    for (int aa = 0; aa < count; aa++) {
                        //parsing the downloaded data
                        int c = name.indexOf(",");
                         String n = name.substring(0, c);
                          names[aa] = n;
                          times[aa]="";
                        int cc = name.indexOf("-");
                        int gid = Integer.parseInt(name.substring(c + 1, cc));
                        imageid[aa] = R.drawable.batman;
                        name = name.substring(cc + 1);
                     }


                } catch (Exception r) {
                    Toast.makeText(getApplicationContext(), r.getMessage(), Toast.LENGTH_LONG).show();
                }
                listView = (ListView) findViewById(R.id.listView);


                final CustomList customList = new CustomList((Activity) context, names,ids, imageid, times,1);
                txt.setVisibility(View.VISIBLE);
                img.setVisibility(View.GONE);
                select.setVisibility(View.GONE);
                cancel.setVisibility(View.GONE);
                listView.setEnabled(true);


                listView.setAdapter(customList);
                listView.setVisibility(View.VISIBLE);




            }
        });




        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    public void uploadMultipart() {
        //getting name for the image
        String name = "saad";
        String path="";
        //getting the actual path of the image
        try{ path = getPath(filePath);}
        catch (Exception rr){Toast.makeText(getApplicationContext(),rr.getMessage(),Toast.LENGTH_LONG).show();}

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId,"http://192.168.0.103/uploadImage.php")
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("name", name) //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(5)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    //handling the image chooser activity result


    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }



       /* changeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //updateList("change");
                String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};
                Integer[] imageid = {R.drawable.punisher,R.drawable.punisher,R.drawable.punisher,R.drawable.punisher,
                        R.drawable.punisher,R.drawable.punisher,R.drawable.punisher,R.drawable.punisher,

                };
                String times[] = {"","","","","","","","",};
                final CustomList customList = new CustomList((Activity) context, names,   imageid,times);
                listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(customList);
                listView.setVisibility(View.VISIBLE);


            }
        });*/




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        filePath = data.getData();

        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
       // Toast.makeText(navigation_accounts.this, "Bitmap: "+filePath, Toast.LENGTH_LONG).show();
        img.setVisibility(View.VISIBLE);
        img.setImageBitmap(bitmap);
        listView.setVisibility(View.GONE);
        select.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);
        img.setImageBitmap(bitmap);

//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            filePath = data.getData();
//            try {
//                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                imageView.setImageBitmap(bitmap);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }






    public void updateList() {
        mobileArray = new String[MapsActivity.p.frnds.size()];
        imageid = new Integer[mobileArray.length];
        times= new String[mobileArray.length];
        ids=new int[mobileArray.length];
//        Toast.makeText(context, MapsActivity.p.frnds.size()+"",Toast.LENGTH_LONG).show();
//
//        mobileArray =new String[] {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};
//        imageid =new Integer[] {R.drawable.punisher,R.drawable.punisher,R.drawable.punisher,R.drawable.punisher,
//                R.drawable.punisher,R.drawable.punisher,R.drawable.punisher,R.drawable.punisher,};
//        times= new String[]{"", "", "", "", "", "", "", "",};
//



            for (int c = 0; c < mobileArray.length; c++) {
                mobileArray[c]=MapsActivity.p.frnds.get(c).name;//names[c] = MainActivity.userFriendList[c].name;
                times[c]="Just Now";
                ids[c]=MapsActivity.p.frnds.get(c).id;
                int asd=0;//int asd = MainActivity.userFriendList[c].avatar;
                if (asd == 0) {
                    imageid[c] = R.drawable.punisher;
                }
                if (asd == 1) {
                    imageid[c] = R.drawable.flash;
                }
                if (asd == 2) {
                    imageid[c] = R.drawable.ironman;
                }
                if (asd == 3) {
                    imageid[c] = R.drawable.batman;
                }


            }


    }
    public void updateList(int i) {
        mobileArray = new String[MapsActivity.p.frnds.size()];
        imageid = new Integer[mobileArray.length];
        times= new String[mobileArray.length];
        ids=new int[mobileArray.length];
//        Toast.makeText(context, MapsActivity.p.frnds.size()+"",Toast.LENGTH_LONG).show();
//
//        mobileArray =new String[] {"Android","IPhone","WindowsMobile","Blackberry","WebOS","Ubuntu","Windows7","Max OS X"};
//        imageid =new Integer[] {R.drawable.punisher,R.drawable.punisher,R.drawable.punisher,R.drawable.punisher,
//                R.drawable.punisher,R.drawable.punisher,R.drawable.punisher,R.drawable.punisher,};
//        times= new String[]{"", "", "", "", "", "", "", "",};
//



        for (int c = 0; c < mobileArray.length; c++) {
            mobileArray[c]=MapsActivity.p.frnds.get(c).name;//names[c] = MainActivity.userFriendList[c].name;
            times[c]="Just Now";
            ids[c]=MapsActivity.p.frnds.get(c).id;
            int asd=0;//int asd = MainActivity.userFriendList[c].avatar;
            if (asd == 0) {
                imageid[c] = R.drawable.punisher;
            }
            if (asd == 1) {
                imageid[c] = R.drawable.flash;
            }
            if (asd == 2) {
                imageid[c] = R.drawable.ironman;
            }
            if (asd == 3) {
                imageid[c] = R.drawable.batman;
            }


        }


    }


}
