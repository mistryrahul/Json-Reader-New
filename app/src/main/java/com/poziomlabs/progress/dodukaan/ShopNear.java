package com.poziomlabs.progress.dodukaan;

        import android.graphics.Color;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.EditText;
        import android.widget.LinearLayout;
        import android.widget.TableLayout;
        import android.widget.TableRow;
        import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Scheduler.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Scheduler#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopNear extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Scheduler.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopNear newInstance(String param1, String param2) {
        ShopNear fragment = new ShopNear();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ShopNear() {

        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myInflatedView  = inflater.inflate(R.layout.fragment_shop_near, container, false);

        TextView order = (TextView) myInflatedView.findViewById(R.id.success);

        String j = order.getText().toString();
        j = j +MainActivity.dyn.getText().toString();
        j = j+ "\nThank you for preordering";
        order.setText(j);



        TextView t = (TextView) myInflatedView.findViewById(R.id.orders);

        TextView timing = (TextView) myInflatedView.findViewById(R.id.timing);


        String k = "";

        for(int i = 0; i< MainActivity.selectedItems.size();i++) {

            k = k + (((Product) MainActivity.listview.getAdapter().getItem(MainActivity.selectedItems.keyAt(i))).getName()) + "\n";

       }

        getActivity().findViewById(R.id.ofinder).setBackgroundColor(Color.WHITE);


         timing.setText("We will be in your complex on Tuesday from 7 AM to 8 AM. You can pick and choose your vegetables from the cart");



        t.setText(k);

        return myInflatedView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

/*

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/
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
        public void onFragmentInteraction(Uri uri);
    }

}
