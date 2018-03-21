package ygz.cutiepics;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Raina on 2018-03-20.
 */

public class SearchUserActivity extends Activity {
    private EditText searchField;
    private Button search, share;
    private GridLayout selected;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        searchField = findViewById(R.id.searchField);
        search = findViewById(R.id.searchBut);
        share = findViewById(R.id.shareTo);
        selected = findViewById(R.id.selectedGrid);

        // Write a message to the database
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = searchField.getText().toString();

                Query query = myRef.child("users").orderByChild("email").equalTo(email);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            searchField.setText("");

                            // dataSnapshot is the users node with all children with id 0
                            for (DataSnapshot users : dataSnapshot.getChildren()) {
                                // do something with the individual "issues"
                                Log.d("search user", "find user with email "+email);

                                final TextView tv = new TextView(SearchUserActivity.this);
                                tv.setText(email);
                                tv.setBackgroundResource(R.drawable.my_button);
                                tv.setPadding(10,4,10,4);
                                selected.addView(tv);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


    }
}
