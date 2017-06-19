package de.fabian.einkaufsliste;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.CountDownLatch;

public class MainActivity extends AppCompatActivity
{
    Button add;
    ArrayList<String> addArray = new ArrayList<>();
    EditText txt;
    ListView lv;
    ArrayAdapter<String> adapter;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (EditText)findViewById(R.id.textInput);
        lv = (ListView)findViewById(R.id.listView);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        delete = (Button)findViewById(R.id.buttonDelete);
        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_multiple_choice, addArray);
        lv.setAdapter(adapter);
        add = (Button)findViewById(R.id.buttonAdd);
        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String getInput = txt.getText().toString();
                if (addArray.contains(getInput))
                {
                    Toast.makeText(getBaseContext(), "Bereits vorhanden!", Toast.LENGTH_LONG).show();
                } else if (getInput.trim().equals(""))
                {
                    Toast.makeText(getBaseContext(), "Keine Eingabe!", Toast.LENGTH_LONG).show();
                } else
                {
                    addArray.add(getInput);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_multiple_choice, addArray);
                    lv.setAdapter(adapter);
                    ((EditText) findViewById(R.id.textInput)).setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SparseBooleanArray checkedPositions = lv.getCheckedItemPositions();
                int count = lv.getCount();

                if (checkedPositions.size() > 0)
                {
                    for (int i = count - 1; i >= 0; i--)
                    {
                        if (checkedPositions.get(i))
                        {
                            adapter.remove(addArray.get(i));
                        }
                    }
                    checkedPositions.clear();
                    lv.invalidateViews();
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Keine Auswahl!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}