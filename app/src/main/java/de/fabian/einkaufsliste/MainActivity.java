package de.fabian.einkaufsliste;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    ImageButton add;
    ArrayList<String> addArray = new ArrayList<>();
    EditText txt;
    ListView lv;
    ArrayAdapter<String> adapter;
    Button delete;
    CheckBox selectAll;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (EditText)findViewById(R.id.textInput);
        selectAll = (CheckBox) findViewById(R.id.checkboxSelect);
        lv = (ListView)findViewById(R.id.listView);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        delete = (Button)findViewById(R.id.buttonDelete);
        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_multiple_choice, addArray);
        lv.setAdapter(adapter);
        final SparseBooleanArray checkedPositions = lv.getCheckedItemPositions();
        add = (ImageButton)findViewById(R.id.buttonAdd);
        add.setOnClickListener(new View.OnClickListener()
        {
            @ Override
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
                    selectAll.setChecked(false);
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
                    selectAll.setChecked(false);
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Keine Auswahl!", Toast.LENGTH_LONG).show();
                }
            }
        });

        selectAll.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                for (int i = 0; i < lv.getCount(); i++)
                {
                    checkedPositions.put(i, selectAll.isChecked());
                }
                lv.invalidateViews();
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if (lv.getCheckedItemCount() == lv.getCount())
                {
                    selectAll.setChecked(true);
                }
                else
                {
                    selectAll.setChecked(false);
                }
            }
        });
    }
}