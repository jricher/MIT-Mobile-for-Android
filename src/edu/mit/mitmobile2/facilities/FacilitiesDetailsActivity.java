package edu.mit.mitmobile2.facilities;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import edu.mit.mitmobile2.Global;
import edu.mit.mitmobile2.R;
import edu.mit.mitmobile2.TwoLineActionRow;
import edu.mit.mitmobile2.objs.FacilitiesItem.LocationRecord;

//public class FacilitiesActivity extends ModuleActivity implements OnClickListener {
public class FacilitiesDetailsActivity extends Activity {

	public static final String TAG = "FacilitiesProblemTypeActivity";
	private Context mContext;	
	private TextView problemStringTextView;
    private static final int CAMERA_PIC_REQUEST = 1;
    private TwoLineActionRow addAPhotoActionRow;
    private TwoLineActionRow takePhotoActionRow;
    private TwoLineActionRow chooseExistingPhotoActionRow;
    private TwoLineActionRow cancelActionRow;    
	private View facilitiesCameraOptionsLayout;
	private ImageView selectedImage;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        this.mContext = this;
        createViews();
	}

	public void createViews() {
        setContentView(R.layout.facilities_details);        
        
    	facilitiesCameraOptionsLayout = (View)findViewById(R.id.facilitiesCameraOptionsLayout);

    	// Set problem string
        problemStringTextView = (TextView)findViewById(R.id.facilitiesProblemString);
        String problemString = "I'm reporting a problem with the " + Global.sharedData.getFacilitiesData().getProblemType();
        
        if (Global.sharedData.getFacilitiesData().getBuildingRoomName().equalsIgnoreCase("INSIDE")) {
        	problemString += " inside " + Global.sharedData.getFacilitiesData().getLocationId();
        }
        else if (Global.sharedData.getFacilitiesData().getBuildingRoomName().equalsIgnoreCase("OUTSIDE")) {
        	problemString += " outside " + Global.sharedData.getFacilitiesData().getLocationId();
        }
        else {
        	problemString += " at " + Global.sharedData.getFacilitiesData().getBuildingNumber() + " in " + Global.sharedData.getFacilitiesData().getBuildingRoomName();        	
        }

        problemStringTextView.setText(problemString);
        
        // Add A Photo
    	addAPhotoActionRow = (TwoLineActionRow)findViewById(R.id.facilitiesAddAPhotoActionRow);
    	addAPhotoActionRow.setActionIconResource(R.drawable.photoopp);
	
    	addAPhotoActionRow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TwoLineActionRow addAPhotoActionRow = (TwoLineActionRow)findViewById(R.id.facilitiesAddAPhotoActionRow);
				addAPhotoActionRow.setVisibility(View.GONE);
	            selectedImage = (ImageView)findViewById(R.id.selectedImage);
	            selectedImage.setVisibility(View.GONE);	
				View facilitiesCameraOptionsLayout = findViewById(R.id.facilitiesCameraOptionsLayout);
				facilitiesCameraOptionsLayout.setVisibility(View.VISIBLE);
			}
		});
    	
    	// Take Photo
    	takePhotoActionRow = (TwoLineActionRow)findViewById(R.id.facilitiesTakePhotoActionRow);

    	takePhotoActionRow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
            	Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // Normally you would populate this with your custom intent.
            	startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);  
			}
		});

    	
    	// Use Exisitng Photo
    	chooseExistingPhotoActionRow = (TwoLineActionRow)findViewById(R.id.facilitiesChooseExistingPhotoActionRow);
    	
    	// Cancel
    	cancelActionRow = (TwoLineActionRow)findViewById(R.id.facilitiesCancelActionRow);
    	cancelActionRow.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addAPhotoActionRow.setVisibility(View.VISIBLE);
				facilitiesCameraOptionsLayout.setVisibility(View.GONE);
			}
		});
	}
	
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (requestCode == CAMERA_PIC_REQUEST) {  
        	Bitmap thumbnail = (Bitmap) data.getExtras().get("data"); 
            ImageView imageView = (ImageView)findViewById(R.id.selectedImage);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageBitmap(thumbnail); 
        }  
    }

		
}
	

	
	