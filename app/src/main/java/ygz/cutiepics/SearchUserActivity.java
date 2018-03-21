package ygz.cutiepics;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Raina on 2018-03-20.
 */

public class SearchUserActivity extends Activity {
    private EditText searchField;
    private Button search;
    private GridLayout selected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        searchField = findViewById(R.id.searchField);
        search = findViewById(R.id.searchBut);
        selected = findViewById(R.id.selectedGrid);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }
}
