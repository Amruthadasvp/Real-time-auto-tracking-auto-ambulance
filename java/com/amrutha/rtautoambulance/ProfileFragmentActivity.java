package com.navya.rtautoambulance;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.Executor;
import java.util.jar.Attributes;


public class ProfileFragmentActivity extends Fragment {
    private TextView dsEnterName,dsEnterPhone,dsEnterEmergencyNo1,dsEmergencyNo2,dsVehicleNo;
    private Button dsUpdateButton;
    private FirebaseUser user;
    private DatabaseReference ref;
    private String userID;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       View rootView=inflater.inflate(R.layout.activity_profile_fragment,container,false);

       user=FirebaseAuth.getInstance().getCurrentUser();
       userID=user.getUid();
       ref=FirebaseDatabase.getInstance().getReference();

       final TextView dsEnterName=(TextView)rootView.findViewById(R.id.dsEnterName);
       final TextView dsEnterPhone=(TextView)rootView.findViewById(R.id.dsEnterPhone);
       final TextView dsEnterEmergencyNo1=(TextView)rootView.findViewById(R.id.dsEnterEmergencyNo1);
       final TextView dsEmergencyNo2=(TextView)rootView.findViewById(R.id.dsEmergencyNo2);
       final TextView dsVehicleNo=(TextView)rootView.findViewById(R.id.dsVehicleNo);

       dsUpdateButton=(Button)rootView.findViewById(R.id.dsUpdateButton);

        ref.child("Users").child("Riders").child(userID).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               String name=snapshot.child("Name").getValue().toString();
               String phone=snapshot.child("Phoneno").getValue().toString();
               String emergencyno1=snapshot.child("EmergencyContact1").getValue().toString();
               String emergencyno2=snapshot.child("EmergencyContact2").getValue().toString();
               String vehicleno=snapshot.child("VehicleNo").getValue().toString();

               dsEnterName.setText(name);
               dsEnterPhone.setText(phone);
               dsEnterEmergencyNo1.setText(emergencyno1);
               dsEmergencyNo2.setText(emergencyno2);
               dsVehicleNo.setText(vehicleno);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

        dsUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEditProfileActivity();
            }
        });

       return rootView;
    }

    public void openEditProfileActivity(){
        Intent intent = new Intent(ProfileFragmentActivity.this.getActivity(),EditProfileFragmentActivity.class);
        startActivity(intent);
    }
}