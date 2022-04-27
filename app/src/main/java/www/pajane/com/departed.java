package www.pajane.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class departed extends AppCompatActivity {

    private RecyclerView recMore;
    private DatabaseReference database;
    private MoreAdapter busAdapter;
    private ArrayList<Bus> list;
    private ImageView backbtn;
    private EditText searchView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departed);


        searchView = (EditText) findViewById(R.id.searchEditText);
        recMore = findViewById(R.id.recMore);
        database = FirebaseDatabase.getInstance().getReference("Buses");
        recMore.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recMore.setLayoutManager(layoutManager);

        list = new ArrayList<>();
        busAdapter = new MoreAdapter(this, list);
        recMore.setAdapter(busAdapter);




        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Bus bus = dataSnapshot.getValue(Bus.class);
                    list.add(bus);
                }
                busAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(departed.this, Home.class );
                intent.putExtra("from_home", false);
                startActivity(intent);
                finish();
            }
        });

    }
}