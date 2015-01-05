package com.arrking.express.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.arrking.android.component.RoundedImageView;
import com.arrking.android.util.Constants;
import com.arrking.express.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by hain on 05/01/2015.
 */
public class ProfilePageFragment extends Fragment {

    private FrameLayout prf_layout;
    private TextView prf_email;
    private EditText prf_name;
    private EditText prf_phone;
    private RoundedImageView avatarImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        this.prf_layout = (FrameLayout) inflater.inflate(R.layout.profile, container, false);
        initView();
        loadUserData();
        return this.prf_layout;
    }

    private void loadUserData() {
        this.prf_email.setText("foo@arrking.com");
        this.prf_name.setText("张三");
        this.prf_phone.setText("+86-15888888888");
//        ImageLoader.getInstance().displayImage("http://pic.jschina.com.cn/0/12/19/62/12196279_843728.jpg",
//                avatarImageView, Constants.UIL_USER_AVATAR_DISPLAY_OPTIONS);
    }

    private void initView() {
        this.prf_email = (TextView) this.prf_layout.findViewById(R.id.email_textview);
        this.prf_name = (EditText) this.prf_layout.findViewById(R.id.fullname_edittext);
        this.prf_phone = (EditText) this.prf_layout.findViewById(R.id.phone_edittext);
//        this.avatarImageView = (RoundedImageView) this.prf_layout.findViewById(R.id.avatar_imageview);
    }
}
