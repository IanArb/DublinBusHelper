package com.ianarbuckle.dublinbushelper.firebase.dagger;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.ianarbuckle.dublinbushelper.ApplicationScope;
import com.ianarbuckle.dublinbushelper.firebase.authentication.FirebaseAuthHelper;
import com.ianarbuckle.dublinbushelper.firebase.authentication.FirebaseAuthHelperImpl;
import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelper;
import com.ianarbuckle.dublinbushelper.firebase.database.DatabaseHelperImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ian Arbuckle on 19/08/2017.
 *
 */
@Module
public class FirebaseModule {
  private FirebaseAuth firebaseAuth;
  private FirebaseDatabase firebaseDatabase;

  @ApplicationScope
  @Provides
  FirebaseAuthHelper provideFirebaseAuth(Context context) {
    if(!FirebaseApp.getApps(context).isEmpty()) {
      firebaseAuth = FirebaseAuth.getInstance();
    }
    return new FirebaseAuthHelperImpl(firebaseAuth);
  }

  @ApplicationScope
  @Provides
  DatabaseHelper provideDatabaseHelper(Context context) {
    if(!FirebaseApp.getApps(context).isEmpty()) {
      FirebaseDatabase.getInstance().setPersistenceEnabled(true);
      firebaseDatabase = FirebaseDatabase.getInstance();
    }
    return new DatabaseHelperImpl(firebaseDatabase);
  }
}
