package com.example.appcommicbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.appcommicbook.adapter.Adapter_Pdf_User;
import com.example.appcommicbook.model.Pdf;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Pdf_List_User extends AppCompatActivity {

    private ImageView imageView;
    private String BookId;
    private ArrayList<Pdf> pdfArrayList;
    private RecyclerView RCL_PdfListUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_list_user);

        imageView = findViewById(R.id.img_back_ListPdfUer);
        RCL_PdfListUser = findViewById(R.id.RCL_PdfListUser);

        Intent intent = getIntent();
        BookId = intent.getStringExtra("categoryTitle");


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LoadData();
    }

    private void LoadData() {
        pdfArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books");
        ref.orderByChild("category")
                .equalTo(BookId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds:snapshot.getChildren()){
                            Pdf model = ds.getValue(Pdf.class);

                            pdfArrayList.add(model);
                        }
                        RCL_PdfListUser.setLayoutManager(new LinearLayoutManager(Pdf_List_User.this,RecyclerView.VERTICAL,false));
                        Adapter_Pdf_User adapter_pdf_user = new Adapter_Pdf_User(Pdf_List_User.this,pdfArrayList);
                        RCL_PdfListUser.setAdapter(adapter_pdf_user);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}