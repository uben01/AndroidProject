package utobe.learn2code.activity.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import utobe.learn2code.R;

public class TestAdderFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    CheckBox[] checkBoxes = new CheckBox[4];
    EditText[] editTexts = new EditText[4];

    public TestAdderFragment() {
        // Required empty public constructor
    }

    public static TestAdderFragment newInstance() {
        TestAdderFragment fragment = new TestAdderFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        checkBoxes[0] = container.findViewById(R.id.cb_test_adder_0);
        checkBoxes[1] = container.findViewById(R.id.cb_test_adder_1);
        checkBoxes[2] = container.findViewById(R.id.cb_test_adder_2);
        checkBoxes[3] = container.findViewById(R.id.cb_test_adder_3);
        editTexts[0] = container.findViewById(R.id.et_test_adder_0);
        editTexts[1] = container.findViewById(R.id.et_test_adder_1);
        editTexts[2] = container.findViewById(R.id.et_test_adder_2);
        editTexts[3] = container.findViewById(R.id.et_test_adder_3);

        return inflater.inflate(R.layout.fragment_test_adder, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
