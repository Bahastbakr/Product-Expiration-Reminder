package com.example.productexpirationreminder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.inputmethod.EditorInfo;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {
    FirebaseFirestore db ;



    OutputStream outputStream;
    DrawerLayout drawerLayout;
    Snackbar snackbar;
    TextView noitem;
    ArrayList<AddItem> exampleList = new ArrayList<>();
    private static final int PICK_IMAGE =100 ;
    public Dialog dialog;
    ImageView calenderB,Imageview;
    EditText calenderE,itemName;
    ImageButton imageButton;
    Button AddItem;
    String file="items";
    private RecyclerView mRecyclerView;
    private ExampleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private AppBarConfiguration mAppBarConfiguration;
    public Uri imageUri;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db=FirebaseFirestore.getInstance();
        additemtodatebase();
        try {
            outputStream=openFileOutput(file,MODE_APPEND);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        noitem=findViewById(R.id.noitem);
        if (exampleList.isEmpty()){
            noitem.setVisibility(View.VISIBLE);
        }



        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog = new Dialog(Home.this);
                        dialog.setContentView(R.layout.newp);
                        dialog.setTitle("زیادكردن");
                        dialog.setCancelable(true);
                        dialog.show();
                        calenderB=dialog.findViewById(R.id.calenderB);
                        calenderE=dialog.findViewById(R.id.additem);
                        itemName=dialog.findViewById(R.id.ItemName);
                        calenderB.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                handDateButton();

                            }
                        });

                        imageButton=dialog.findViewById(R.id.imageButton2);
                        imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openGallery();
                            }
                        });
                        AddItem=dialog.findViewById(R.id.addP);
                        AddItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                try{

                                    if(calenderE.getText().toString().isEmpty()||itemName.getText().toString().isEmpty()){
                                        snackbar = Snackbar.make(drawerLayout, "تكایە زانیارییەكان پڕبەكەرەوە", Snackbar.LENGTH_SHORT);
                                        View view = snackbar.getView();
                                        TextView txt = view.findViewById(R.id.snackbar_text);
                                        view.setBackgroundColor( getResources().getColor(R.color.colorAccent));
                                        txt.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
                                        snackbar.show();

                                    }
                                    else {

                                        buildRecyclerView();
                                        int position = 0;
                                        insertItem(position);

                                        snackbar = Snackbar.make(drawerLayout, "شتومەكەكەت زیادكرا", Snackbar.LENGTH_SHORT);
                                        View view = snackbar.getView();
                                        TextView txtv = view.findViewById(R.id.snackbar_text);
                                        view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                        txtv.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

                                        snackbar.show();
                                        dialog.dismiss();
                                        additemtodatebase();
                                    }
                                }
                                catch (Exception e){




                                }


                            }
                        });



            }
        });





        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);



    }


    void additemtodatebase(){
        Map<String, Object> item = new HashMap<>();
        item.put("title", "hey");
        item.put("expiredate", "hahahahah");

        db.collection("items").add(item).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(Home.this,"item added to firestore",Toast.LENGTH_LONG).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Home.this,"failed added to firestore",Toast.LENGTH_LONG).show();

            }
        });


    }

    String getFileExtenstion(Uri uri){
        ContentResolver cR=getContentResolver();
        MimeTypeMap mime =MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));

    }
   /* void uploadimage(){
    if(imageUri!=null){
    StorageReference filereference=msReference.child(+System.currentTimeMillis()+"."+getFileExtenstion(imageUri));
    filereference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setProgress(0);
                }
            },500);
            Toast.makeText(Home.this,"بە سەركەوتووی ئەپوڵۆد بوو",Toast.LENGTH_LONG).show();
            String uploadid=myRef.push().getKey();


        }
    })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Home.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                        progressBar.setProgress((int)progress);
                }
            });
    }else{

    }
    }

*/



    public void insertItem(int position){
        exampleList.add(position,new AddItem(imageUri, "ناوی شتومەك: "+itemName.getText().toString(), "بەرواری بەەسەرچوون: " +calenderE.getText().toString()));
        mAdapter.notifyItemInserted(position);
        savetointernal();
        noitem.setVisibility(View.INVISIBLE);
    }

    private void savetointernal() {
        try {
            outputStream.write("\n".getBytes());
            outputStream.write(imageUri.toString().getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write(itemName.getText().toString().getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write(calenderE.getText().toString().getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write("-------------------".getBytes());
            outputStream.write("\n".getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeItem(int position) {
        exampleList.remove(position);
        mAdapter.notifyItemRemoved(position);
    }


    public void buildRecyclerView() {


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(Home.this);

        mAdapter = new ExampleAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new ExampleAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                exampleList.get(position);
                removeItem(position);
            }
        });
    }


    private void openGallery() {
        Intent gallery=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK && requestCode==PICK_IMAGE){
            imageUri=data.getData();
            Imageview=dialog.findViewById(R.id.imageView2);
            Imageview.setImageURI(imageUri);



        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

         searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

      searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    public void handDateButton(){
Calendar calender =Calendar.getInstance();
        int year=calender.get(Calendar.YEAR);
        int month=calender.get(Calendar.MONTH);
        int date=calender.get(Calendar.DATE);

        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calenderE=dialog.findViewById(R.id.additem);
                calenderE.setText(year+"/"+month+"/"+dayOfMonth);

            }
        },year,month,date);
        datePickerDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

       // FirebaseRecyclerAdapter<AddItem,ItemViewholder>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<com.example.productexpirationreminder.AddItem, ItemViewholder>
         //       (AddItem.class,R.layout.items,ItemViewholder.class,databaseReference) {
         //   @Override
          //  protected void populateViewHolder(ItemViewholder itemViewholder, com.example.productexpirationreminder.AddItem addItem, int i) {
          //      itemViewholder.settitle(addItem.getTextView());
           //     itemViewholder.setEdate(addItem.getTextView1());

           // }
        };
      //  mRecyclerView.setAdapter(firebaseRecyclerAdapter);

   // }
    public static class ItemViewholder extends RecyclerView.ViewHolder{
        View mview;
        public ItemViewholder(View itemview){
            super(itemview);
            mview=itemview;

        }
        public void settitle(String title){
           TextView item_title=mview.findViewById(R.id.t1);
           item_title.setText(title);
        }
        public void setEdate(String Edate){
            TextView Item_Edate=mview.findViewById(R.id.t2);
            Item_Edate.setText(Edate);
        }
    }

}
