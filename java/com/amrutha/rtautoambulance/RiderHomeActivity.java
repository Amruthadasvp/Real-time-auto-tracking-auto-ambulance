package com.navya.rtautoambulance;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RiderHomeActivity extends AppCompatActivity {
    DrawerLayout drawer;
    NavigationView navigationView;
    FrameLayout frameLayout;
    ImageView imageView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    View header;
    TextView name,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_home);

        toolbar=(Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        drawer=(DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView=(NavigationView)findViewById(R.id.nav_view);
        frameLayout=(FrameLayout)findViewById(R.id.frame);
        header=navigationView.getHeaderView(0);
        imageView=(ImageView)header.findViewById(R.id.nav_img) ;
        name=(TextView)header.findViewById(R.id.username);
        phone=(TextView)header.findViewById(R.id.userphone);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        toggle=new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        loadFragment(new HomeFragmentActivity());
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String user_id=user.getUid();
        DatabaseReference dr = FirebaseDatabase.getInstance().getReference();
        dr.child("Users").child("Riders").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String uname=snapshot.child("Name").getValue().toString();
                String phno=snapshot.child("Phoneno").getValue().toString();
                name.setText(uname);
                phone.setText(phno);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                if(id==R.id.nav_profile){
                    loadFragment(new ProfileFragmentActivity());
                }
                else if(id==R.id.nav_report){
                    loadFragment(new ReportFragmentActivity());
                }
                else if(id==R.id.nav_about){
                    loadFragment(new AboutFragmentActivity());
                }
                else if(id==R.id.nav_Home){
                    loadFragment(new HomeFragmentActivity());
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }



    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }
        else{
        super.onBackPressed();}
    }
    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }


}