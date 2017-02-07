package com.dominikk.sidebar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dominikk.sidebar.adapter.IdTextAdapter;
import com.dominikk.sidebar.adapter.IdTextInterface;
import com.dominikk.sidebar.model.Person;
import com.dominikk.sidebar.view.ListIndexView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.listIndexView)
    ListIndexView listIndexView;
    @BindView(R.id.tvIndexLetter)
    TextView tvIndexLetter;
    @BindView(R.id.letterContainer)
    LinearLayout letterContainer;
    @BindView(R.id.listView)
    ListView listView;

    private ArrayList<IdTextInterface> arrayList = new ArrayList<>();
    private IdTextAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        adapter = new IdTextAdapter(this, R.layout.id_text_row, arrayList);
        arrayList.clear();
        fillWithData(arrayList);

        Collections.sort(arrayList, new Comparator<IdTextInterface>() {
            @Override
            public int compare(IdTextInterface o1, IdTextInterface o2) {
                return o1.getText().compareTo(o2.getText());
            }
        });

        listView.setAdapter(adapter);

        adapter.refreshIndexMap();
        listIndexView.init(adapter, letterContainer, tvIndexLetter, listView);
    }

    private void fillWithData(ArrayList<IdTextInterface> arrayList) {
        arrayList.add(new Person(1, "John"));
        arrayList.add(new Person(2, "Kate"));
        arrayList.add(new Person(3, "Paul"));
        arrayList.add(new Person(4, "Margareth"));
        arrayList.add(new Person(5, "Emma"));
        arrayList.add(new Person(6, "Vincent"));
        arrayList.add(new Person(7, "Robert"));
        arrayList.add(new Person(8, "Richard"));
        arrayList.add(new Person(9, "Dwayne"));
        arrayList.add(new Person(10, "Anna"));
        arrayList.add(new Person(11, "Anabelle"));
        arrayList.add(new Person(12, "Monica"));
        arrayList.add(new Person(13, "Kelly"));
        arrayList.add(new Person(14, "Alicia"));
        arrayList.add(new Person(15, "Angelina"));
        arrayList.add(new Person(16, "Zoey"));
        arrayList.add(new Person(17, "Brad"));
        arrayList.add(new Person(18, "Matt"));
        arrayList.add(new Person(19, "Ron"));
        arrayList.add(new Person(20, "Bob"));
        arrayList.add(new Person(21, "Donald"));
        arrayList.add(new Person(22, "Jackie"));
        arrayList.add(new Person(23, "Jennifer"));
        arrayList.add(new Person(24, "Keith"));
        arrayList.add(new Person(25, "Mark"));
        arrayList.add(new Person(26, "Emilia"));
        arrayList.add(new Person(27, "Angela"));
        arrayList.add(new Person(28, "George"));
        arrayList.add(new Person(29, "Charlize"));
        arrayList.add(new Person(30, "Rachel"));
    }
}
