package com.example.appcommicbook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcommicbook.adapter.Adapter_Login_SignUp;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.tabs.TabLayout;

public class Login_SignUp extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ImageView img_btnGoogle,img_btnFacebook,img_btnTwiter;


    Button btnLogin;
    TextView textView;

    GoogleApiClient googleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        img_btnGoogle = findViewById(R.id.img_btnGoogle);
        img_btnFacebook = findViewById(R.id.img_btnFacebook);
        img_btnTwiter = findViewById(R.id.img_btnTwiter);



//        sẽ trả về các fragment để tương tác với các fragment được liên kết với acn này
        Adapter_Login_SignUp adapter_login_signUp = new Adapter_Login_SignUp(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter_login_signUp);
        tabLayout.setupWithViewPager(viewPager);


//       cài đặt cấu hình đăng nhập với Google
        GoogleSignInOptions googleSignInOptions =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

//      GoogleApoClient là một lớp trong google Service nó cho phép ứng dụng kết nối với GooglePlay Services hoặc có thể đăng nhập với tài khoản
//        google
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();

        Login_Google();

    }

    private void Login_Google() {
        img_btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                trả về intent tương ứng khi sử dụng phương thức getSingInIntent
                Intent SignInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(SignInIntent, 123);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if(requestCode == 123){
//                sẽ trả về infor người dùng khi đăng nhập thành công với google và đối tượng đóa là resulll
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignIn(result);
            }
    }

    private void handleSignIn(GoogleSignInResult result) {
        if(result.isSuccess()){
            Toast.makeText(this,"Đăng Nhập với google thành công!!",Toast.LENGTH_LONG).show();
        }
    }
}