package hire.omnify.com.omnifysorting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.Random;

public class HomeFragment extends Fragment {

    Button regenerate_btn, sorted_btn;
    ListView number_listview;
    int max,min,random_no;
    ArrayList<Integer> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        max = 99;
        min = 10;

        regenerate_btn = (Button)view.findViewById(R.id.Regerate_button);
        sorted_btn = (Button)view.findViewById(R.id.sorted_button);
        number_listview = (ListView)view.findViewById(R.id.number_listview);


        list=new ArrayList<Integer>();


        sorted_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SortedFragment fragment = new SortedFragment();

                Bundle bundle = new Bundle();
                bundle.putIntegerArrayList("arraylist", list);
                fragment.setArguments(bundle);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.commit();
            }
        });

        regenerate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            generateRandomNumber();

            }
        });

        generateRandomNumber();

        return view;
    }

    public void generateRandomNumber(){

        list.clear();

        for (int i=0; i<20;i++) {
            random_no = new Random().nextInt((max - min) + 1) + min;
            list.add(random_no);

        }

        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(
                getContext(),
                android.R.layout.simple_list_item_1,
                list );

        number_listview.setAdapter(arrayAdapter);

    }

}
