package com.arrking.express.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.arrking.android.component.RoundedImageView;
import com.arrking.android.database.Properties;
import com.arrking.android.util.Constants;
import com.arrking.express.LoginPageActivity;
import com.arrking.express.MainActivity;
import com.arrking.express.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by hain on 05/01/2015.
 */
public class ProfilePageFragment extends Fragment {

    private static final String CLASSNAME = ProfilePageFragment.class.getName();
    private FrameLayout prf_layout;
    private TextView prf_email;
    private TextView prf_name;
    private TextView prf_id;
    private RoundedImageView avatarImageView;
    private ImageView logoutBtn;
    Properties p;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        p = Properties.getInstance(getActivity());
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
        this.prf_email.setText(p.get(com.arrking.express.common.Constants.USER_EMAIL));
        this.prf_name.setText(String.format("%s %s",
                p.get(com.arrking.express.common.Constants.USER_LAST_NAME),
                p.get(com.arrking.express.common.Constants.USER_FIRST_NAME)));
        this.prf_id.setText(p.get(com.arrking.express.common.Constants.USER_ID));
        ImageLoader.getInstance().displayImage("http://icons.iconarchive.com/icons/custom-icon-design/pretty-office-11/96/customer-service-icon.png",
                avatarImageView, Constants.UIL_USER_AVATAR_DISPLAY_OPTIONS);
    }

    private void initView() {
        this.prf_email = (TextView) this.prf_layout.findViewById(R.id.email_textview);
        this.prf_name = (TextView) this.prf_layout.findViewById(R.id.fullname_edittext);
        this.prf_id = (TextView) this.prf_layout.findViewById(R.id.phone_edittext);
        this.avatarImageView = (RoundedImageView) this.prf_layout.findViewById(R.id.avatar_imageview);
        this.logoutBtn = (ImageView) this.prf_layout.findViewById(R.id.logout_imagebutton);
        this.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.rm(com.arrking.express.common.Constants.USER_FIRST_NAME);
                p.rm(com.arrking.express.common.Constants.USER_LAST_NAME);
                p.rm(com.arrking.express.common.Constants.USER_EMAIL);
                p.rm(com.arrking.express.common.Constants.USER_ID);
                p.rm(com.arrking.express.common.Constants.USER_URL);
                p.rm(com.arrking.express.common.Constants.USER_PASSWORD);
                Intent i = new Intent(getActivity(), LoginPageActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });
    }
}
