package com.example.smkccovid.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.smkccovid.R
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import id.voela.actrans.AcTrans
import kotlinx.android.synthetic.main.activity_login.*
import util.goTo

class LoginActivity : AppCompatActivity() {

    private var auth: FirebaseAuth? = null
    private val RC_SIGN_IN = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()

        auth = FirebaseAuth.getInstance()

        if (auth!!.currentUser == null) { }
        else {
            Log.d("TAG", "firebaseAuthWithGoogle:" + auth!!.currentUser!!.displayName)
            goTo(this, MainActivity(), true, null)
        }
    }

    override fun onBackPressed() {
        buttonBack()
    }

    private fun buttonBack() {
        super.onBackPressed()
        AcTrans.Builder(this).performFade()
    }

    private fun initView() {
        bt_back_login.setBackgroundColor(Color.TRANSPARENT)
        bt_tap.setBackgroundColor(Color.TRANSPARENT)
        bt_back_login.setOnClickListener { buttonBack() }
        bt_tap.setOnClickListener { buttonTap() }
        bt_sign.setOnClickListener { buttonSign() }
        et_email.clearFocus()
        bt_skip.setBackgroundColor(Color.TRANSPARENT)
        bt_skip.setOnClickListener { buttonSkip() }
    }

    private fun buttonSign() {
        signIn()
    }

    private fun buttonSkip() {
        goTo(this, MainActivity(), true, null)
    }

    private fun buttonTap() {
        goTo(this, ForgotActivity(), false, null)
    }

    private fun signIn() {
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        val mGoogleApiClient = GoogleApiClient.Builder(this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, options)
            .build()
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d("TAG", "firebaseAuthWithGoogle:")

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w("TAG", "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithCredential:success" + " " + auth!!.currentUser!!.displayName)
                    goTo(this, MainActivity(), true, null)
                } else {
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                }
            }
    }
}
