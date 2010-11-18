package edu.mit.mitmobile.maps;

import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.os.Handler;

import edu.mit.mitmobile.MobileWebApi;
import edu.mit.mitmobile.MobileWebApi.JSONArrayResponseListener;
import edu.mit.mitmobile.MobileWebApi.DefaultErrorListener;
import edu.mit.mitmobile.objs.MapItem;
import edu.mit.mitmobile.objs.SearchResults;

public class MITMapsDataModel {

	private static HashMap<String, List<MapItem>> sSearchCache = new HashMap<String, List<MapItem>>();
	
	private static HashMap<String, List<MapItem>> sCategoryCache = new HashMap<String, List<MapItem>>();
	
	public static void executeSearch(final String searchTerm, final Handler uiHandler, Context context) {
		if(sSearchCache.containsKey(searchTerm)) {
			
		}
		
		
		HashMap<String, String> params = new HashMap<String, String>();

		params.put("q", searchTerm);
		params.put("command", "search");
		MobileWebApi webApi = new MobileWebApi(false, true, "Campus map", context, uiHandler);
		webApi.requestJSONArray("/map", params, new JSONArrayResponseListener(new DefaultErrorListener(uiHandler), null) {

			@Override
			public void onResponse(JSONArray array) throws JSONException {
				
				List<MapItem> mapsItems = MapParser.parseMapItems(array);
				
				for (MapItem m : mapsItems) {
					m.query = searchTerm;
				}
				
				sSearchCache.put(searchTerm, mapsItems); 
				
				MobileWebApi.sendSuccessMessage(uiHandler, new SearchResults<MapItem>(searchTerm, mapsItems));
			}
			
		});
		
		
		
		
	}
	
	public static void fetchCategory(final String categoryId, final Handler uiHandler, Context context) {
		
		HashMap<String, String> params = new HashMap<String, String>();

		params.put("id", categoryId);
		params.put("command", "category");
		MobileWebApi webApi = new MobileWebApi(false, true, "Campus map", context, uiHandler);
		
		webApi.requestJSONArray("/map", params, new JSONArrayResponseListener(new DefaultErrorListener(uiHandler), null) {

			@Override
			public void onResponse(JSONArray array) throws JSONException {
				List<MapItem> mapItems = MapParser.parseMapItems(array);
				
				sCategoryCache.put(categoryId, mapItems); 
				
				MobileWebApi.sendSuccessMessage(uiHandler);
			}
			
		});		
	}
	
	public static List<MapItem> getCategory(String categoryId) {
		return sCategoryCache.get(categoryId);
	}
	
	public static List<MapItem> getSearchResults(String searchTerm) {
		return sSearchCache.get(searchTerm);
	}
}

