package com.wu.my.android_letterindexviewrecyclerview.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.wu.my.android_letterindexviewrecyclerview.R;
import com.wu.my.android_letterindexviewrecyclerview.adapter.MyAdapter;
import com.wu.my.android_letterindexviewrecyclerview.bean.UserBean;
import com.wu.my.android_letterindexviewrecyclerview.decoration.DividerItemDecoration;
import com.wu.my.android_letterindexviewrecyclerview.helper.ChineseToPinyinHelper;
import com.wu.my.android_letterindexviewrecyclerview.view.LetterIndex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView_main;
    private Context mContext = this;
    private List<UserBean> totalList = new ArrayList<>();
    private MyAdapter adapter = null;
    private TextView textView_main_show;
    private LetterIndex letterIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        String[] arrUserNames = getResources().getStringArray(R.array.arrUsernames);
        String[] arrIconUrl = getResources().getStringArray(R.array.arrIconUrl);
        for (int i = 0; i < arrUserNames.length; i++) {
            UserBean userBean = new UserBean();
            String username = arrUserNames[i];
            String pinyin = "";
            String firstLetter = "";
            if (username.matches("^[\u4e00-\u9fa5]+")) {
                //username是汉字则转化成拼音，获取首字母
                pinyin = ChineseToPinyinHelper.getInstance().getPinyin(username);
                firstLetter = pinyin.substring(0, 1).toUpperCase();
            } else if (username.matches("^[a-zA-Z]+")) {
                //username是英文的，直接获取首字母
                pinyin = username;
                firstLetter = username.substring(0, 1).toUpperCase();
            } else {
                //username既不是汉字也不是英文，则都归属于#
                pinyin = "#" + username;
                firstLetter = "#";
            }
            userBean.setUsername(username);
            userBean.setIconUrl(arrIconUrl[i]);
            userBean.setPinyin(pinyin);
            userBean.setFirstLetter(firstLetter);
            totalList.add(userBean);
        }
        //totalList排序
        Collections.sort(totalList, new Comparator<UserBean>() {
            @Override
            public int compare(UserBean lhs, UserBean rhs) {
                return lhs.getPinyin().toLowerCase().compareTo(rhs.getPinyin().toLowerCase());
            }
        });
    }

    private void initView() {
        letterIndex = (LetterIndex) findViewById(R.id.letterIndex_main);
        textView_main_show = (TextView) findViewById(R.id.textView_main_show);
        recyclerView_main = (RecyclerView) findViewById(R.id.recyclerView_main);
        recyclerView_main.setHasFixedSize(true);
        recyclerView_main.setItemAnimator(new DefaultItemAnimator());
        recyclerView_main.addItemDecoration(new DividerItemDecoration(mContext,
                DividerItemDecoration.VERTICAL_LIST));
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView_main.setLayoutManager(layoutManager);
        adapter = new MyAdapter(mContext, totalList);
        recyclerView_main.setAdapter(adapter);
        letterIndex.setOnLetterClickedListener(textView_main_show, new LetterIndex
                .OnLetterClickedListener() {
            @Override
            public void onLetterClicked(String letter) {
                int position = adapter.getPositionForSection(letter.charAt(0));
                recyclerView_main.scrollToPosition(position);
            }
        });
    }
}
