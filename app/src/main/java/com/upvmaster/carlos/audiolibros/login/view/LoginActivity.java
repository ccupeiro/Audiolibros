package com.upvmaster.carlos.audiolibros.login.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.upvmaster.carlos.audiolibros.R;
import com.upvmaster.carlos.audiolibros.common.data.datasources.FirebaseDatabaseSingleton;
import com.upvmaster.carlos.audiolibros.login.data.datasources.FirebaseAuthSingleton;
import com.upvmaster.carlos.audiolibros.login.data.datasources.FirebaseStorage;
import com.upvmaster.carlos.audiolibros.login.data.datasources.FirebaseStorageSharedPreferences;
import com.upvmaster.carlos.audiolibros.main.data.datasources.LibrosSingleton;
import com.upvmaster.carlos.audiolibros.main.data.datasources.User;
import com.upvmaster.carlos.audiolibros.main.view.MainActivity;

import java.util.Arrays;

/**
 * Created by carlos.cupeiro on 02/02/2017.
 */
public class LoginActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuthSingleton.getInstance().getAuth();
        doLogin();
    }

    private void doLogin() {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            guardarUsuario(currentUser);
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            String provider = currentUser.getProviders().get(0);
            FirebaseStorageSharedPreferences.getInstance(this).saveUser(name, email, provider);
            //if(currentUser.isEmailVerified()){
            Intent i = new Intent(this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            //}else{
            //Scar notificacion y enviar correo
            // Toast.makeText(this,"No ha verificado su email "+currentUser.getEmail()+", se le envia otra vez el correo de ocnfirmación",Toast.LENGTH_LONG).show();

            //currentUser.sendEmailVerification();
            //}

        } else {
            startActivityForResult(AuthUI.getInstance()
                    .createSignInIntentBuilder().setProviders(Arrays.asList(
                            new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build(),
                            new AuthUI.IdpConfig.Builder(AuthUI.TWITTER_PROVIDER).build()))
                    .setIsSmartLockEnabled(true).build(), RC_SIGN_IN);
        }
    }

    void guardarUsuario(final FirebaseUser user) {
        DatabaseReference userReference = FirebaseDatabaseSingleton.getInstance().getUsersReference().child(user.getUid());
        userReference.setValue(new User(user.getDisplayName(), user.getEmail()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == ResultCodes.OK) {
                doLogin();
                finish();
            }
        }
    }


}
