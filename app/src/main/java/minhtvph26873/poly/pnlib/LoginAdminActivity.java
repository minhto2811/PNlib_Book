package minhtvph26873.poly.pnlib;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import minhtvph26873.poly.pnlib.R;

import minhtvph26873.poly.pnlib.dao.AdminDAO;
import minhtvph26873.poly.pnlib.database.DataBase;
import minhtvph26873.poly.pnlib.model.Admin;
import minhtvph26873.poly.pnlib.model.ThuThu;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class LoginAdminActivity extends AppCompatActivity {
    private TextInputLayout til_user, til_pass;
    private EditText edt_user, edt_pass;
    private CheckBox chk_ghi_nho;
    private Button btn_loginA;
    private ImageButton btn_changeAccount;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        anhxa();
        checkghinho();
        xuli();
    }

    private void checkghinho() {
        if (DataLocalManager.getFirstInstall() != null) {
            Admin admin = DataLocalManager.getFirstInstall();
            if (admin.getUser().equals("null") && admin.getPass().equals("null")) {
                edt_user.setText("");
                edt_pass.setText("");
                chk_ghi_nho.setChecked(false);
            } else {
                edt_user.setText(admin.getUser());
                edt_pass.setText(admin.getPass());
                chk_ghi_nho.setChecked(true);
            }
        } else {
            chk_ghi_nho.setChecked(false);
        }
    }


    private void xuli() {
        btn_loginA.setOnClickListener(v -> {
            AdminDAO adminDAO = new AdminDAO(LoginAdminActivity.this);
            List<Admin> list = adminDAO.getAll();
            String user = edt_user.getText().toString();
            String pass = edt_pass.getText().toString();
            if (user.isEmpty()) {
                til_user.setError("Không được để trống trường này");
                edt_user.requestFocus();
                return;
            }
            til_user.setError("");
            if (pass.isEmpty()) {
                til_pass.setError("Không được để trống trường này");
                til_pass.requestFocus();
                return;
            }
            til_pass.setError("");
            if (!user.equals(list.get(0).getUser())) {
                til_user.setError("Tài khoản không tồn tại");
                edt_user.requestFocus();
            } else {
                til_user.setError("");
                if (!pass.equals(list.get(0).getPass())) {
                    til_pass.setError("Mật khẩu không chính xác");
                    edt_pass.setTransformationMethod(null);
                    edt_pass.setSelection(pass.length());
                    return;
                }
                if (chk_ghi_nho.isChecked()) {
                    DataLocalManager.setFirstInstall(new Admin(user, pass));
                    DataLocalManager.setFirstInstall1(null);
                } else {
                    DataLocalManager.setFirstInstall(null);
                    DataLocalManager.setFirstInstall1(null);
                }
                DataBase dataBase = new DataBase(LoginAdminActivity.this);
                dataBase.TaoBangAvatar(DataLocalManager.getFirstInstall().getUser());
                intent = new Intent(LoginAdminActivity.this, MainActivity2.class);
                intent.putExtra("admin", "admin");
                startActivity(intent);
                til_pass.setError("");
                edt_pass.setTransformationMethod(new PasswordTransformationMethod());
                edt_pass.clearFocus();
                edt_user.clearFocus();
                MediaPlayer player = MediaPlayer.create(this,R.raw.realme_rceec439);
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        player.start();
                    }
                });
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        player.release();
                    }
                });
            }

        });
        btn_changeAccount.setOnClickListener(v -> {
            intent = new Intent(LoginAdminActivity.this, LoginGuestActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.anim_enter_to_top,R.anim.anim_exit_to_top);
        });
    }

    private void anhxa() {
        til_user = findViewById(R.id.til_user);
        til_pass = findViewById(R.id.til_pass);
        edt_user = findViewById(R.id.edt_user);
        edt_pass = findViewById(R.id.edt_pass);
        chk_ghi_nho = findViewById(R.id.chk_ghi_nho);
        btn_loginA = findViewById(R.id.btn_loginA);
        btn_changeAccount = findViewById(R.id.btn_changeAccount);
    }


    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        checkghinho();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LoginAdminActivity.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_enter_to_right,R.anim.anim_exit_to_right);
    }
}