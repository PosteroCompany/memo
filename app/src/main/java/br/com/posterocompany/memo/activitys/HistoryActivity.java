package br.com.posterocompany.memo.activitys;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.List;

import br.com.posterocompany.memo.R;
import br.com.posterocompany.memo.models.Note;
import br.com.posterocompany.memo.models.NoteUpdate;
import br.com.posterocompany.memo.utils.Text;

public class HistoryActivity extends AppCompatActivity {

    private Note note;

    private SectionsPagerAdapter pagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Long id = getIntent().getLongExtra("noteId", 0);
        if (id != 0) {
            note = Note.findById(Note.class, id);
        }

        pagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), note.getUpdates());

        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(pagerAdapter.getCount() - 1, true);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    public static class PlaceholderFragment extends Fragment {

        private NoteUpdate update;

        private TextView lblDateCreate;
        private TextView lblTimeUpdate;
        private TextView lblText;

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(NoteUpdate update) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            fragment.update = update;
            return fragment;

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_history, container, false);

            lblDateCreate = (TextView) rootView.findViewById(R.id.lblDateCreate);
            lblTimeUpdate = (TextView) rootView.findViewById(R.id.lblTimeUpdate);
            lblText = (TextView) rootView.findViewById(R.id.lblText);

            lblDateCreate.setText(Text.toDate(update.dateCreate));
            String timeUpdate = Text.toTime(update.dateCreate) + " ... " + Text.toTime(update.dateUpdate);
            lblTimeUpdate.setText(timeUpdate);
            lblText.setText(update.newText);

            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<NoteUpdate> updates;

        public SectionsPagerAdapter(FragmentManager fm, List<NoteUpdate> updates) {
            super(fm);
            this.updates = updates;
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(updates.get(position));
        }

        @Override
        public int getCount() {
            return updates.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            NoteUpdate update = updates.get(position);

            //return Text.toTime(update.dateCreate) + " ... " + Text.toTime(update.dateUpdate);
            return "Version " + (position + 1);
        }
    }
}
