package mat06.sim.missingitemreminder;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mat06.sim.missingitemreminder.adapters.RecyclerAdapter;
import mat06.sim.missingitemreminder.adapters.SpinnerAdapter;
import mat06.sim.missingitemreminder.database.RealmDatabase;
import mat06.sim.missingitemreminder.models.CategoryItem;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int TYPE_ALL_CATEGORIES = 0;

    @BindView(R.id.s_category)
    Spinner spinner;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private RecyclerAdapter adapter = new RecyclerAdapter();
    private SpinnerAdapter spinnerAdapter = new SpinnerAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        spinnerAdapter.addData(createSpinnerAdapterData(RealmDatabase.getAllCategories(this)));
        filterItems(spinner.getSelectedItemPosition());
    }

    private void filterItems(int position) {
        SpinnerAdapter spinnerAdapter = (SpinnerAdapter) spinner.getAdapter();
    }

    private List<CategoryItem> createSpinnerAdapterData(List<CategoryItem> categoryItemList) {
        List<CategoryItem> list = new ArrayList<>();
        list.add(new CategoryItem(getString(R.string.all_category)));
        list.addAll(categoryItemList);
        return list;
    }

    @OnClick(R.id.fab)
    void onClick(View view) {
        Intent toAddItemActivity = new Intent(this, AddItemActivity.class);
        startActivity(toAddItemActivity);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        filterItems(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
