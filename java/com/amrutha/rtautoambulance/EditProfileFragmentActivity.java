package com.navya.rtautoambulance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditProfileFragmentActivity extends AppCompatActivity {
    EditText dsEditName,dsEditPhone,dsEditEmergencyNo1,dsEditEmergencyNo2,dsEditVehicleNo;
    Button dsSaveButton;
    FirebaseDatabase database;
    FirebaseUser user;
    DatabaseReference ref;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_fragment);

        user= FirebaseAuth.getInstance().getCurrentUser();
        userID=user.getUid();
        ref=FirebaseDatabase.getInstance().getReference().child("Users").child("Riders").child(userID);

        dsEditName=(EditText)findViewById(R.id.dsEditName);
        dsEditPhone=(EditText)findViewById(R.id.dsEditPhone);
        dsEditEmergencyNo1=(EditText)findViewById(R.id.dsEditEmergencyNo1);
        dsEditEmergencyNo2=(EditText)findViewById(R.id.dsEditEmergencyNo2);
        dsEditVehicleNo=(EditText)findViewById(R.id.dsEditVehicleNo);

        dsSaveButton=(Button)findViewById(R.id.dsSaveButton);

        dsSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference current_user_dsEditName=FirebaseDatabase.getInstance().getReference().child("Users").child("Riders").child(userID).child("Name");
                current_user_dsEditName.setValue(dsEditName.getText().toString());
                DatabaseReference current_user_dsEditPhone=FirebaseDatabase.getInstance().getReference().child("Users").child("Riders").child(userID).child("Phoneno");
                current_user_dsEditPhone.setValue(dsEditPhone.getText().toString());
                DatabaseReference current_user_dsEditEmergencyNo1=FirebaseDatabase.getInstance().getReference().child("Users").child("Riders").child(userID).child("EmergencyContact1");
                current_user_dsEditEmergencyNo1.setValue(dsEditEmergencyNo1.getText().toString());
                DatabaseReference current_user_dsEditEmergencyNo2=FirebaseDatabase.getInstance().getReference().child("Users").child("Riders").child(userID).child("EmergencyContact2");
                current_user_dsEditEmergencyNo2.setValue(dsEditEmergencyNo2.getText().toString());
                DatabaseReference current_user_dsEditVehicleNo=FirebaseDatabase.getInstance().getReference().child("Users").child("Riders").child(userID).child("VehicleNo");
                current_user_dsEditVehicleNo.setValue(dsEditVehicleNo.getText().toString());

                Intent intent=new Intent(EditProfileFragmentActivity.this,EditProfileFragmentActivity.class);
                startActivity(intent);

            }
        });
    }
}