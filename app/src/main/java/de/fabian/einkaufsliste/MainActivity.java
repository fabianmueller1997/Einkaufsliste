/*
This is a project by Fabian Fleischmann and Fabian Müller.
It contains an Android app called 'Einkaufsliste' which helps you create a shopping list on your Android phone.
The app was programmed from May to July 2017.
 */

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
    ImageButton add;    //Button for adding new list items
    ArrayList<String> addArray = new ArrayList<>(); //Array for saving text input
    EditText txt;   //Text input field
    ListView lv;    //List for displaying shopping list items
    ArrayAdapter<String> adapter;   //Array adapter for converting an ArrayList into a View item
    Button delete;  //Button for deleting list items
    CheckBox selectAll; //Checkbox for selecting all list items

    @Override
    protected void onCreate(Bundle savedInstanceState)  //Initialises the activity
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (EditText) findViewById(R.id.textInput);
        selectAll = (CheckBox) findViewById(R.id.checkboxSelect);
        lv = (ListView) findViewById(R.id.listView);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        delete = (Button) findViewById(R.id.buttonDelete);
        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_multiple_choice, addArray);
        lv.setAdapter(adapter);
        add = (ImageButton) findViewById(R.id.buttonAdd);

        add();
        delete();
        selectAll();
        check();
    }

    public void add()   //Method for adding items to the list
    {
        add.setOnClickListener(new View.OnClickListener()   //Invokes callback when the add button is clicked
        {
            @Override
            public void onClick(View view)  //Method that defines what happens when the add button is clicked
            {
                String getInput = txt.getText().toString(); //Gets text input and converts it
                if (addArray.contains(getInput))    //True if text input matches an item that already exists
                {
                    Toast.makeText(getBaseContext(), "Bereits vorhanden!", Toast.LENGTH_LONG).show();
                } else if (getInput.trim().equals(""))  //True if nothing was entered
                {
                    Toast.makeText(getBaseContext(), "Keine Eingabe!", Toast.LENGTH_LONG).show();
                } else
                {
                    addArray.add(getInput);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_multiple_choice, addArray);  //Defines the array adapter
                    lv.setAdapter(adapter); //Assigns the correct array adapter to the ListView
                    ((EditText) findViewById(R.id.textInput)).setText("");  //Adds text input to the list
                    adapter.notifyDataSetChanged(); //Ensures that the list will be refreshed when items are added
                    selectAll.setChecked(false);    //Unchecks the selectAll button
                }
            }
        });
    }

    public void delete()    //Method for removing items from the list
    {
        delete.setOnClickListener(new View.OnClickListener()    //Invokes callback when the delete button is clicked
        {
            @Override
            public void onClick(View view)  //Method that defines what happens when the delete button is clicked
            {
                SparseBooleanArray checkedPositions = lv.getCheckedItemPositions(); //Array that contains the positions of the selected items
                int count = lv.getCount();  //Reflects the current amount of list items

                if (checkedPositions.size() > 0)    //True if at least one list item is selected
                {
                    for (int i = count - 1; i >= 0; i--)
                    {
                        if (checkedPositions.get(i))
                        {
                            adapter.remove(addArray.get(i));    //Removes the selected element(s)
                        }
                    }
                    checkedPositions.clear();
                    lv.invalidateViews();   //Refreshes the ListView
                    selectAll.setChecked(false);    //Unchecks the selectAll button
                } else
                {
                    Toast.makeText(getBaseContext(), "Keine Auswahl!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void selectAll() //Method for selecting all list items using the select all button
    {
        selectAll.setOnClickListener(new View.OnClickListener() //Invokes callback when the select all button is clicked
        {
            @Override
            public void onClick(View v) //Method that defines what happens when the select all button is clicked
            {
                final SparseBooleanArray checkedPositions = lv.getCheckedItemPositions();   //Array that contains the positions of the selected items
                for (int i = 0; i < lv.getCount(); i++)
                {
                    checkedPositions.put(i, selectAll.isChecked()); //Checks or unchecks all list items
                }
                lv.invalidateViews();   //Refreshes the ListView
            }
        });
    }

    public void check() //Method for checking the select all button when appropriate
    {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() //Invokes callback when a ListView element is clicked
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)    //Method that defines what happens when a ListView item is clicked
            {
                if (lv.getCheckedItemCount() == lv.getCount())  //True if number of selected list items equals number of existing list items
                {
                    selectAll.setChecked(true); //Checks the select all button
                } else
                {
                    selectAll.setChecked(false);    //Unchecks the select all button
                }
            }
        });
    }
}