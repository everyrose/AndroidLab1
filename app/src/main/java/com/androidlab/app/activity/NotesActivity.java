package com.androidlab.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import com.androidlab.app.R;
import com.androidlab.app.adapter.NoteAdapter;
import com.androidlab.app.constant.Priority;
import com.androidlab.app.domain.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotesActivity extends Activity  implements AdapterView.OnItemClickListener  {

    private ListView notesListView;
    private List<Note> noteList;

    private AlertDialog newBtnDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_layout);

        notesListView = (ListView) findViewById(R.id.notesListView);


        Note note = getIntent().getParcelableExtra("note");
        noteList = populateList();

        if (note != null) {
            noteList.add(note);
        }

        NoteAdapter adapter = new NoteAdapter(noteList, this, R.layout.note_layout);
        notesListView.setAdapter(adapter);
        notesListView.setOnItemClickListener(this);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Choose an Option");
        builder.setItems(R.array.new_note_arr, dialogListener);
        newBtnDialog = builder.create();

        registerForContextMenu(notesListView);
        builder.setTitle("Sort by");
        builder.setItems(R.array.sort_options_arr, dialogListener);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
      // if (v.getId() == R.id.notes_button) {

//        selectedId = menuInfo.
            getMenuInflater().inflate(R.menu.contextmenu_browse, menu);
            menu.setHeaderTitle("Choose an Option");
          //  menu.setHeaderIcon(R.drawable.ic_dialog_menu_generic);
       // }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int id = (int) info.id;

        switch (item.getItemId()) {
            case R.id.menu_edit:
                Note note = new Note();
                for(int i=0; i<noteList.size(); i++){
                    if(noteList.get(i).getId() == id){
                        note = noteList.get(i);

                        Intent intent = new Intent(getApplicationContext(), AddNoteActivity.class);
                        intent.putExtra("note", note);

                        startActivity(intent);
//                        break;
                    }
                }

                break;

            case R.id.menu_delete:
                for(int i=0; i<noteList.size(); i++){
                    if(noteList.get(i).getId() == id){
                        noteList.remove(i);
                        break;
                    }
                }

                NoteAdapter adapter = new NoteAdapter(noteList, this, R.layout.note_layout);
                notesListView.setAdapter(adapter);

                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        getMenuInflater().inflate(R.menu.note, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Note note = getIntent().getParcelableExtra("note");
                List<Note> noteList = populateList();
                if (note != null) {
                    noteList.add(note);
                }

                NoteAdapter adapter = new NoteAdapter(noteList, this, R.layout.note_layout);
                notesListView.setAdapter(adapter);
                notesListView.setOnItemClickListener(this);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    private DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            System.out.println("qweeqwe");
                Intent intent = new Intent();
                switch (which) {
                    case 0:


                      //  intent.setClass(BrowseActivity.this, BasicActivity.class);
                        break;
                    case 1:
                      //  intent.setClass(BrowseActivity.this, ChecklistActivity.class);
                        break;
                    case 2:

                      //  intent.setClass(BrowseActivity.this, SnapshotActivity.class);
                        break;
                }
                startActivity(intent);


        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        openNote(id, this);
    }

    static void openNote(long id, Context ctx) {

    }
    public void onClick(View v) {
        newBtnDialog.show();
        LinearLayout checkitemLL;
        System.out.println("qweeqwe");
        switch (v.getId()) {
//            case R.id.additem_btn:
//                checkitemLL = (LinearLayout) inflater.inflate(R.layout.row_checkitem, null);
//                EditText itemEdit = (EditText) checkitemLL.findViewById(R.id.item_et);
//                itemEdit.setTypeface(font);
//                checklistLL.addView(checkitemLL);
//                break;
//
//            case R.id.deleteitem_btn:
//                checkitemLL = (LinearLayout) v.getParent();
//                TextView itemId = (TextView) checkitemLL.findViewById(R.id.item_id);
//                if (!TextUtils.isEmpty(itemId.getText()))
//                    new CheckItem(Long.parseLong(itemId.getText().toString())).delete(db);
//                checklistLL.removeView(checkitemLL);
//                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(getApplicationContext(), AddNoteActivity.class);

            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private List<Note> populateList() {
        return new ArrayList<Note>() {{
            add(new Note() {{
                setId(13);
                setTitle("1title");
                setDateTime(new Date(0));
                setPriority(Priority.HIGH);
                setImageId("priority_high");
                setId(54);
            }});
            add(new Note() {{
                setId(25);
                setTitle("2title");
                setDateTime(new Date(0));
                setPriority(Priority.HIGH);
                setImageId("priority_high");
                setId(12);
            }});
        }};
    }
}
