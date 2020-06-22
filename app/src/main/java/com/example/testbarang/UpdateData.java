package com.example.testbarang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateData<FirebaseAuth> extends AppCompatActivity {
    //Deklarasi Variable
    private EditText KodeBaru, namaBaru;
    private Button update;
    private DatabaseReference database;
    private FirebaseAuth auth;
    private String cekKode, cekNama;
    private Object Brg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        getSupportActionBar().setTitle("Update Data");
        KodeBaru = findViewById(R.id.updateNo);
        namaBaru = findViewById(R.id.updateNama);
        update = findViewById(R.id.btnUpdate);

        database = FirebaseDatabase.getInstance().getReference();
        getData();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Mendapatkan Data Mahasiswa yang akan dicek
                cekKode = KodeBaru.getText().toString();
                cekNama = namaBaru.getText().toString();

                //Mengecek agar tidak ada data yang kosong, saat proses update
                if(isEmpty(cekKode) || isEmpty(cekNama)){
                    Toast.makeText(UpdateData.this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
                }else {
                    Barang setBarang = new Barang();
                    setBarang.setKode(KodeBaru.getText().toString());
                    setBarang.setNama(namaBaru.getText().toString());
                    updateData(setBarang);
                }
            }
        });
    }

    // Mengecek apakah ada data yang kosong, sebelum diupdate
    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }

    //Menampilkan data yang akan di update
    private void getData(){
        //Menampilkan data dari item yang dipilih sebelumnya
        final String getKode = getIntent().getExtras().getString("dataKode");
        final String getNama = getIntent().getExtras().getString("dataNama");
        KodeBaru.setText(getKode);
        namaBaru.setText(getNama);
    }

    //Proses Update data yang sudah ditentukan
    private void updateData(Barang barangs){
        String getKey = getIntent().getExtras().getString("getPrimaryKey");
        database.child("Barang").push().setValue(barangs).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                KodeBaru.setText("");
                namaBaru.setText("");
                Toast.makeText(UpdateData.this, "Data Berhasil diubah", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}
