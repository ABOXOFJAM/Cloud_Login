package com.e.cloud_login.Main_Funcation.Pan_Funcation;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

public class FragmentActivity extends androidx.fragment.app.FragmentActivity {

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("BaseActivity onActivityResult");

        FragmentManager fragmentManager = getSupportFragmentManager();

        for (int i = 0; i < fragmentManager.getFragments().size(); i++) {

            Fragment fragment = fragmentManager.getFragments().get(i);

            if (fragment == null) {

            } else {

                handleResult(fragment, requestCode, resultCode, data);

            }

        }

    }

    private void handleResult(Fragment fragment, int requestCode, int resultCode, Intent data) {

        fragment.onActivityResult(requestCode, resultCode, data);//调用每个Fragment的onActivityResult

        List<Fragment> childFragment = fragment.getChildFragmentManager().getFragments(); //找到第二层Fragment

        if (childFragment != null)

            for (Fragment f : childFragment)

                if (f != null) {

                    handleResult(f, requestCode, resultCode, data);

                }

        if (childFragment == null) {
        } }
}
