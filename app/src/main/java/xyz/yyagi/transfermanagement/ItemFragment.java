package xyz.yyagi.transfermanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import xyz.yyagi.transfermanagement.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ItemFragment extends Fragment implements AbsListView.OnItemClickListener {
    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;
    private List<TransferData> mDataList;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    public static ItemFragment newInstance() {
        ItemFragment fragment = new ItemFragment();
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataList = TransferData.findWithQuery(TransferData.class, "select * from Transfer_data order by id DESC limit 20");
        mAdapter = new ArrayAdapter<TransferData>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, mDataList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("確認");
        alertDialogBuilder.setMessage("このデータを削除しますか?");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TransferData transferData = mDataList.get(position);
                        transferData.delete();
                        resetDataList();
                        Toast.makeText(getActivity(), "削除しました", Toast.LENGTH_SHORT).show();
                    }
                });
        alertDialogBuilder.setNegativeButton("NG",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        alertDialogBuilder.setCancelable(true);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    public void resetDataList() {
        mDataList = TransferData.findWithQuery(TransferData.class, "select * from Transfer_data order by id DESC limit 20");
        mAdapter = new ArrayAdapter<TransferData>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, mDataList);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
    }
}
