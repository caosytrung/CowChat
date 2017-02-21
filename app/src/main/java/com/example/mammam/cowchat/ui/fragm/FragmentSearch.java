package com.example.mammam.cowchat.ui.fragm;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mammam.cowchat.R;
import com.example.mammam.cowchat.controll.ManagerUser;
import com.example.mammam.cowchat.models.UserChat;
import com.example.mammam.cowchat.ui.activity.SearchActivity;
import com.example.mammam.cowchat.ui.adapter.RecyclerViewSearchAdapter;
import com.example.mammam.cowchat.ui.interf.IClickItemRycyclerView;
import com.example.mammam.cowchat.ui.interf.IListUser;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mam  Mam on 12/22/2016.
 */

public class FragmentSearch extends BaseFragment implements IListUser, TextWatcher {
    private static final String TAG = "FragmentSearch";
    private RecyclerView rvSearch;
    private RecyclerViewSearchAdapter adapter;
    private List<UserChat> userChats;
    private ManagerUser mManagerUser;
    private EditText edtSearch;
    private List<UserChat> allUser;
    private EventBus eventBus;

    @Override
    public View getViews(LayoutInflater inflater, ViewGroup viewGroup) {
        return inflater.inflate(R.layout.dialog_ssearch,viewGroup,false);
    }

    @Override
    public void initViews(View view) {
        rvSearch = (RecyclerView) view.findViewById(R.id.rvSeach);
        adapter = new RecyclerViewSearchAdapter(userChats, getContext(), new IClickItemRycyclerView() {
            @Override
            public void onItemClick(int pos, View v) {
                SearchActivity searchActivity = (SearchActivity) getActivity();
                searchActivity.openFragmentViewInfor();
                eventBus.postSticky(userChats.get(pos));

            }

            @Override
            public void onItemLongCLick(int pos, View v) {

            }
        });

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rvSearch.setLayoutManager(manager);
        rvSearch.setAdapter(adapter);
        edtSearch = (EditText) view.findViewById(R.id.edtSearch);
        edtSearch.addTextChangedListener(this);
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                   // performSearch();
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(edtSearch.getWindowToken(), 0);
                    String strSearch = edtSearch.getText().toString();
                    userChats.clear();

                    if (null != allUser){
                        for (int i = 0; i < allUser.size() ; i++){
                            String fullName = allUser.get(i).getFirstName() + allUser.get(i).getLastName();
                            String email = allUser.get(i).getEmail();
                            if (fullName.contains(strSearch) || email.contains(strSearch)){
                                userChats.add(allUser.get(i));
                            }
                        }
                        Log.d(TAG,"Curen : " + userChats.size());
                        adapter.setmUserChats(userChats);
                        adapter.notifyDataSetChanged();

                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void initComponents() {
        eventBus = EventBus.getDefault();
        mManagerUser = new ManagerUser(getContext());
        mManagerUser.setiListUser(this);
        mManagerUser.getAllUser();
        // setContentView(R.layout.dialog_ssearch);
        userChats = new ArrayList<>();
    }

    @Override
    public void setEventViews() {


    }

    @Override
    public void listUserEvent(List<UserChat> userChats) {
        this.allUser = userChats;
        Log.d(TAG,userChats.size() + " size");

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String strSearch = editable.toString();
        userChats.clear();

        if (null != allUser){
            for (int i = 0; i < allUser.size() ; i++){
                String fullName = allUser.get(i).getFirstName() + allUser.get(i).getLastName();
                String email = allUser.get(i).getEmail();
                if (fullName.contains(strSearch) || email.contains(strSearch)){
                    userChats.add(allUser.get(i));
                }
            }
            Log.d(TAG,"Curen : " + userChats.size());
            adapter.setmUserChats(userChats);
            adapter.notifyDataSetChanged();

        }
    }
}
