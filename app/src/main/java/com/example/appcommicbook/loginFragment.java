package com.example.appcommicbook;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link loginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class loginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public loginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment loginFragment.
     */

    // TODO: Rename and change types and number of parameters
    public static loginFragment newInstance(String param1, String param2) {
        loginFragment fragment = new loginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    Hàm xử lý

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //btnLogin = findViewById(R.id.btnLogin);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Button btnLogin;
    EditText edtEmail,edtPassEmail;
    CheckBox cbLogin;
    TextView tvDangKy;
    FirebaseAuth mAuth;

    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        btnLogin = (Button) view.findViewById(R.id.LG_BtnLogin);
        edtEmail = (EditText) view.findViewById(R.id.LG_Email);
        edtPassEmail = (EditText) view.findViewById(R.id.LG_Password);
        cbLogin = (CheckBox) view.findViewById(R.id.LG_CheckBox);
        tvDangKy = (TextView) view.findViewById(R.id.tv_DangKy);

//        ProgressDialog
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Please Wait...");
        progressDialog.setMessage("Chờ xíuuu");


//      các bước xử lý cho Checkbox,
//      B1: Tạo SharePrefernce với hai giá trị name và pass với giá trị rỗng được dùng để set giá trị cho Email và Pass
//       B2: Nếu mà CheckBox được check thì cta truyền vào giá trị của Email và Pass để cho Emai và Pass vẫn giữ nguyên được giá trị sau khi đăng xuất.
        mAuth = FirebaseAuth.getInstance();

        SharedPreferences myPref = getContext().getSharedPreferences("MySharePref", Context.MODE_PRIVATE);

        String nameSave = myPref.getString("name", "");
        String passSave = myPref.getString("pass", "");

        edtEmail.setText(nameSave);
        edtPassEmail.setText(passSave);

        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),signupFragment.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        //      sử dụng putExtra for send data to DashboardAdminActivity
//        String emailForDashBoard = edtEmail.getText().toString();
//        Intent i = new Intent(getContext(), DashboardAdminActivity.class);
//        i.putExtra("emailAdmin", emailForDashBoard);
//        startActivity(i);
        return view;
    }

    private void Login() {
        progressDialog.show();
        //   ktra điều kiện (tài khoản, mật khẩu không được trống , mật khẩu phải lớn hơn 6 ký tự)
        //   thực hiện việc đăng nhập với tài khoản đã được tạo trên fireBase
        String email = edtEmail.getText().toString().trim();
        String pass = edtPassEmail.getText().toString().trim();

        if(email.equals("admin@gmail.com") && pass.equals("admin123")){
            Intent myIntent = new Intent(getContext(), DashboardAdminActivity.class);
            startActivity(myIntent);
            Toast.makeText(getActivity(),"Bạn đã đăng nhập thành công với tài khoản ADMIN!!!",Toast.LENGTH_LONG).show();
        }else{
            if(edtEmail.getText().toString().trim().isEmpty()){
                edtEmail.setError("Không được để trống Email!");
            }else if(edtPassEmail.getText().toString().trim().length() < 6){
                edtPassEmail.setError("Mật khẩu phải lớn hơn 6 kí tự!");
            }else if (edtPassEmail.getText().toString().trim().length()>8){
                edtPassEmail.setError("Mật khẩu không lớn hơn 8 kí tự");
            }else if(edtPassEmail.getText().toString().trim().isEmpty()){
                edtPassEmail.setError("Không được để trống mật khẩu nha!");
            }else{
                mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            SharedPreferences myPref2 = getContext().getSharedPreferences("MySharePref",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = myPref2.edit();

                            if(cbLogin.isChecked()){
                                editor.putString("name",email);
                                editor.putString("email",pass);
                            }else{
                                editor.putString("name","");
                                editor.putString("email","");

                            }editor.commit();//commit dùng để đồng bộ và lưu kết quả thành công


                            Toast.makeText(getActivity(),"Hi, Bạn đã đăng nhập thành công",Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getContext(),MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getActivity(),"Thông tin tài khoản hoặc mật khẩu không chính xác!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }
    }
}