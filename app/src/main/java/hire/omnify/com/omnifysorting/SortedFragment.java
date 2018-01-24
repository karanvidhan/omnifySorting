package hire.omnify.com.omnifysorting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;


public class SortedFragment extends Fragment {

    ArrayList<Integer> quicklist;
    ArrayList<Integer> mergelist;
    Button back;

    TableLayout quicktable,mergetable;
    private ResponseReceiver receiver;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for getContext() fragment
        View view= inflater.inflate(R.layout.fragment_sorted, container, false);

        quicklist = getArguments().getIntegerArrayList("arraylist");
        mergelist = getArguments().getIntegerArrayList("arraylist");

        quicktable = (TableLayout)view.findViewById(R.id.table_main);
        mergetable = (TableLayout)view.findViewById(R.id.table_main2);
        back = (Button)view.findViewById(R.id.back);






        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HomeFragment fragment = new HomeFragment();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.commit();

            }
        });


        Intent msgIntent = new Intent(getActivity(), MyIntentService.class);
        msgIntent.putExtra(MyIntentService.PARAM_IN_MSG, quicklist);
        getActivity().startService(msgIntent);



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ResponseReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        getActivity().registerReceiver(receiver, filter);


    }

    @Override
    public void onPause(){
        super.onPause();

        getActivity().unregisterReceiver(receiver);
    }


    public void init() {

        for (int i = 0; i < quicklist.size(); i++) {
            TableRow tbrow = new TableRow(getContext());
            TableRow tbrow2 = new TableRow(getContext());
            TextView t1v = new TextView(getContext());
            t1v.setText("" + quicklist.get(i));
            t1v.setTextColor(Color.BLUE);
            t1v.setGravity(Gravity.CENTER);
            t1v.setTextSize(20);
            t1v.setPadding(16,16,16,16);
            tbrow.addView(t1v);
            TextView t2v = new TextView(getContext());
            t2v.setText("" + mergelist.get(i));
            t2v.setTextColor(Color.BLUE);
            t2v.setGravity(Gravity.CENTER);
            t2v.setTextSize(20);
            t2v.setPadding(16,16,16,16);
            tbrow2.addView(t2v);

            quicktable.addView(tbrow);
            mergetable.addView(tbrow2);
        }

    }


    public class ResponseReceiver extends BroadcastReceiver {
        public static final String ACTION_RESP =
                "PROCESSED";

        @Override
        public void onReceive(Context context, Intent intent) {

            quicklist = intent.getIntegerArrayListExtra("quicklist");
            mergelist = intent.getIntegerArrayListExtra("mergelist");

            if(quicklist.size()!=0 && mergelist.size()!=0)
            init();


        }
    }


}
