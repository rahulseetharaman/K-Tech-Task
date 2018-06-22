package com.example.hp.k_tech_task;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LeaderboardEasy.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LeaderboardEasy#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaderboardMedium extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String LEVEL_TAG = "level";
    private FirebaseDatabase fdb;
    private Query dbref;
    // TODO: Rename and change types of parameters
    private String mParamLevel;

    private OnFragmentInteractionListener mListener;

    public LeaderboardMedium() {
        // Required empty public constructor
    }

    public static LeaderboardMedium newInstance(String level) {
        LeaderboardMedium fragment = new LeaderboardMedium();
        Bundle args = new Bundle();
        args.putString(LEVEL_TAG, level);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamLevel = getArguments().getString(LEVEL_TAG);
        }
        fdb=FirebaseDatabase.getInstance();
        //Log.d("MYTAG",mParamLevel);
        dbref=fdb.getReference(mParamLevel).orderByValue().limitToLast(3);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view=inflater.inflate(R.layout.fragment_leaderboard_easy, container, false);
        final int[] textViews = {R.id.firstView,R.id.secondView,R.id.thirdView};
        final int[] scoreViews= {R.id.firstScore,R.id.secondScore,R.id.thirdScore};

        dbref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Log.d("MYTAG","Level is "+mParamLevel);
                int counter=0;
                ArrayList<String>usernames=new ArrayList<String>();
                ArrayList<String>scores=new ArrayList<String>();
                for(DataSnapshot user:dataSnapshot.getChildren())
                {
                    usernames.add(user.getKey());
                    scores.add(user.getValue().toString());
                }
                int n=usernames.size();
                for(int i=0;i<n;i++)
                {
                    TextView tw1=(TextView)view.findViewById(textViews[i]);
                    TextView tw2=(TextView)view.findViewById(scoreViews[i]);
                    tw1.setText(usernames.get(n-i-1));
                    tw2.setText(scores.get(n-i-1));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
