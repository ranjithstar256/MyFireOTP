package com.am.myfireotp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity {
    String number;
    String mVerificationId;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        number=getIntent().getStringExtra("num");
        mAuth=FirebaseAuth.getInstance();
        sendVerificationCode(number);
    }

    private void sendVerificationCode(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91"+number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallback);
    }

    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback
            = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();

            if(code!=null){
                Log.i("123ac this is code",code);
                verifyVerficationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Log.i("sdfaaa",e.getMessage());
            Toast.makeText(VerifyPhoneActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId=s;
        }
    };

    private void verifyVerficationCode(String code) {

        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(mVerificationId,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyPhoneActivity.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Intent intent = new Intent(VerifyPhoneActivity.this,
                                            MainActivity2.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);

                                }else {
                                    Toast.makeText(VerifyPhoneActivity.this, "invaild code", Toast.LENGTH_SHORT).show();
                                }
                                
                            }
                        });

    }
}












