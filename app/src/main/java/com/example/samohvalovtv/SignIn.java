package com.example.samohvalovtv;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity {
    EditText username, password;
    Button btnLogin ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        username = findViewById(R.id.edemail);
        password = findViewById(R.id.edpass);
        btnLogin = findViewById(R.id.loginbutton);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(username.getText().toString())||TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(SignIn.this, "Заполните все поля", Toast.LENGTH_LONG).show();
                }else{
                    login();
                }
            }
        });

    }
    public void login(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(username.getText().toString());
        loginRequest.setPassword(password.getText().toString());
        Call<LoginResponse> loginResponseCall = APIClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>()
        {
            @Override

            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(SignIn.this, "Вход успешен", Toast.LENGTH_LONG).show();
                    Intent i;
                    i = new Intent(SignIn.this, MainActivity.class);
                    startActivity(i);
                }else {
                    Toast.makeText(SignIn.this, "Проверьте правильность введенных данных", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(SignIn.this, "Throwable"+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }

        });
    }
}