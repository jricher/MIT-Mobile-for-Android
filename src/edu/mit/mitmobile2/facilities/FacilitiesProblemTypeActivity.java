package edu.mit.mitmobile2.facilities;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import edu.mit.mitmobile2.Global;
import edu.mit.mitmobile2.R;

//public class FacilitiesActivity extends ModuleActivity implements OnClickListener {
public class FacilitiesProblemTypeActivity extends ListActivity {

	public static final String TAG = "FacilitiesProblemTypeActivity";
	String[] problemTypes;
	private Context mContext;	
	ListView mListView;

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        this.mContext = this;
        setContentView(R.layout.facilities_problem_type);
        
        // get problem type list
        Resources res = getResources();
		problemTypes = res.getStringArray(R.array.facilities_problem_types);		

        setListAdapter(new ArrayAdapter<String>(this,
            R.layout.simple_row, problemTypes));
	}

    public void onListItemClick(ListView parent, View v,int position, long id) {   
    	Object o =  problemTypes[position];
    	selectProblemType(problemTypes[position]);
    } 
    
	public void selectProblemType(String problem) {		
    	Global.sharedData.getFacilitiesData().setProblemType(problem);
    	Intent i = new Intent(mContext, FacilitiesDetailsActivity.class);
		startActivity(i);
	}
}
	
