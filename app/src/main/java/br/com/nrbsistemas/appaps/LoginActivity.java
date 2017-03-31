package br.com.nrbsistemas.appaps;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText edtLogin, edtSenha;
    private Button btnLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtLogin = (EditText) findViewById(R.id.edt_login);
        edtSenha = (EditText) findViewById(R.id.edt_senha);
        btnLogar = (Button) findViewById(R.id.btn_logar);
        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _logar();
            }
        });
    }

    private void _logar() {
        String login = edtLogin.getText().toString();
        String senha = edtSenha.getText().toString();
        if (login.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preencha o campo "+login , Toast.LENGTH_SHORT).show();
            edtLogin.setError("Preecha os campo " + login);
            edtLogin.setText("");
        } else if (senha.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preencha o campo "+senha, Toast.LENGTH_SHORT).show();
            edtSenha.setError("Preecha os campo " + senha);
            edtSenha.setText("");
        } else if (login.isEmpty() && senha.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Preeencha os campos ", Toast.LENGTH_SHORT).show();
        } else if ("leitor".equals(login) && "123".equals(senha)) {
            startActivity(new Intent(this, DashBoardActivity.class));
            finish();
        }else {
            AlertDialog.Builder msg = new AlertDialog.Builder(this);
            msg.setTitle("\tAtenção")
                    .setMessage("Uáruio e ou senha inválidos")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    })
                    .create()
                    .show();
        }
    }
}
