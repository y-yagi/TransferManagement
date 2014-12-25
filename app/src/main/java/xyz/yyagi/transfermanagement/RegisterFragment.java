package xyz.yyagi.transfermanagement;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterFragment extends Fragment implements View.OnClickListener{
    private Spinner mTypeSpinner;
    private Spinner mYearSpinner;
    private Spinner mMonthSpinner;

    public static RegisterFragment newInstance() {
        RegisterFragment f = new RegisterFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        Button registryButton = (Button) view.findViewById(R.id.button_regist);
        mTypeSpinner = (Spinner)view.findViewById(R.id.type);
        mYearSpinner = (Spinner)view.findViewById(R.id.year);
        mMonthSpinner= (Spinner)view.findViewById(R.id.month);

        registryButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        registerData(v);
    }

    public void registerData(View view) {
        String type = mTypeSpinner.getSelectedItem().toString();
        String year = mYearSpinner.getSelectedItem().toString();
        String month = mMonthSpinner.getSelectedItem().toString();

        if (TransferData.isExist(type, year, month)) {
            Toast.makeText(getActivity(), "そのデータは既に登録済みです", Toast.LENGTH_LONG).show();
        } else {
            TransferData transferData = new TransferData(type, year, month);
            transferData.save();
            Toast.makeText(getActivity(), "データを登録しました", Toast.LENGTH_LONG).show();
        }
    }
}

