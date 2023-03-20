package minhtvph26873.poly.pnlib;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import minhtvph26873.poly.pnlib.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import minhtvph26873.poly.pnlib.dao.ThuThuDAO;
import minhtvph26873.poly.pnlib.database.DataBase;
import minhtvph26873.poly.pnlib.model.Admin;
import minhtvph26873.poly.pnlib.model.ThuThu;

public class LoginGuestActivity extends AppCompatActivity {
    private TextInputLayout til_user1, til_pass1;
    private EditText edt_user1, edt_pass1;
    private CheckBox chk_ghi_nho1;
    private Button btn_loginA1;
    private ImageButton btn_changeAccount1;
    private Intent intent1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_guest);
        anhxa();
        checkghinho();
        xuli();
    }

    private void checkghinho() {
        if (DataLocalManager.getFirstInstall1() != null) {
            ThuThu thuThu = DataLocalManager.getFirstInstall1();
            if (thuThu.getMaTT().equals("null") && thuThu.getMatKhau().equals("null") && thuThu.getHoTen().equals("null")) {
                edt_user1.setText("");
                edt_pass1.setText("");
                chk_ghi_nho1.setChecked(false);
            } else {
                edt_user1.setText(thuThu.getMaTT());
                edt_pass1.setText(thuThu.getMatKhau());
                chk_ghi_nho1.setChecked(true);
            }
        }

    }

    private void xuli() {
        btn_loginA1.setOnClickListener(v -> {
            ThuThuDAO thuThuDAO = new ThuThuDAO(LoginGuestActivity.this);
            List<ThuThu> list = thuThuDAO.getAll();
            String user = edt_user1.getText().toString();
            String pass = edt_pass1.getText().toString();
            String ten = "";
            if (user.isEmpty()) {
                til_user1.setError("Không được để trống trường này");
                edt_user1.requestFocus();
                return;
            }
            til_user1.setError("");
            if (pass.isEmpty()) {
                til_pass1.setError("Không được để trống trường này");
                til_pass1.requestFocus();
                return;
            }
            til_pass1.setError("");
            int indexUser = 0;
            int indexPass = 0;
            for (int i = 0; i < list.size(); i++) {
                if (user.equals(list.get(i).getMaTT())) {
                    indexUser++;
                    ten = list.get(i).getHoTen();
                    if (pass.equals(list.get(i).getMatKhau())) {
                        indexPass++;
                    }
                }

            }

            if (indexUser == 0) {
                til_user1.setError("Tài khoản không tồn tại");
                edt_user1.requestFocus();
            } else {
                til_user1.setError("");
                if (indexPass == 0) {
                    til_pass1.setError("Mật khẩu không chính xác");
                    edt_pass1.setTransformationMethod(null);
                    edt_pass1.setSelection(pass.length());
                    return;
                }
                if (chk_ghi_nho1.isChecked()) {
                    DataLocalManager.setFirstInstall1(new ThuThu(user, ten, pass));
                    DataLocalManager.setFirstInstall(null);
                } else {
                    DataLocalManager.setFirstInstall1(null);
                    DataLocalManager.setFirstInstall(null);
                }
                DataBase dataBase = new DataBase(LoginGuestActivity.this);
                dataBase.TaoBangAvatar(DataLocalManager.getFirstInstall1().getMaTT());
                intent1 = new Intent(LoginGuestActivity.this, MainActivity2.class);
                intent1.putExtra("thuthu", "thuthu");
                intent1.putExtra("tentt", ten);
                intent1.putExtra("passtt", pass);
                startActivity(intent1);
                til_pass1.setError("");
                edt_pass1.setTransformationMethod(new PasswordTransformationMethod());
                edt_pass1.clearFocus();
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
        btn_changeAccount1.setOnClickListener(v -> {
            intent1 = new Intent(LoginGuestActivity.this, LoginAdminActivity.class);
            startActivity(intent1);
            overridePendingTransition(R.anim.anim_enter_to_bottom,R.anim.anim_exit_to_bottom);
        });
    }

    private void anhxa() {
        til_user1 = findViewById(R.id.til_user1);
        til_pass1 = findViewById(R.id.til_pass1);
        edt_user1 = findViewById(R.id.edt_user1);
        edt_pass1 = findViewById(R.id.edt_pass1);
        chk_ghi_nho1 = findViewById(R.id.chk_ghi_nho1);
        btn_loginA1 = findViewById(R.id.btn_loginA1);
        btn_changeAccount1 = findViewById(R.id.btn_changeAccount1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkghinho();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LoginGuestActivity.this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.anim_enter_to_right,R.anim.anim_exit_to_right);
    }
}

