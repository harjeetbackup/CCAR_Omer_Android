package com.spearheadinc.flashcards.omer;

import java.util.ArrayList;
import java.util.List;

import com.spearheadinc.flashcards.apputil.DBManager;
import com.spearheadinc.flashcards.omer.R;
import com.spearheadinc.flashcards.omer.Search.CustomAdapter;
import com.spearheadinc.flashcards.omer.Search.CustomAdapter.ViewHolder;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ListCardName extends Activity {
    public List<String[]> list=null;
    public List<String[]> listDeckName=null;
    private String DeckName;
    private static ListCardName screen;
    private String cardTypeSelected = "";
    List<String> listSearchCardName = new ArrayList<String>();
	List<String> listSearchCardid = new ArrayList<String>();
	List<String> listSearchCardColor = new ArrayList<String>();
	private FCDBHelper mFCDbHelper;
	boolean bookmaredCard;
	private ListView listCardView;
	private RelativeLayout deckCards;
	private Integer i;
	private TextView deckname;
	public List<String> colorList= new ArrayList<String>();
	
    public static ListCardName getInstance() 
    {
    	return screen;
	}
    public List<String> getListSearchCardName() {
		return listSearchCardName;
	}

	public List<String> getListSearchCardid() {
		return listSearchCardid;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_card_name);
        deckCards = (RelativeLayout)findViewById(R.id.deckCards);
        deckCards.bringToFront();
        screen = this;
     //   Bundle extras = getIntent().getExtras();
       // cardTypeSelected = extras.getString("com.android.flashcard.screen.cardDetail");
         i = (Integer) getIntent().getSerializableExtra("com.android.flashcard.screen.cardDetail");
        
        mFCDbHelper = DBManager.getInstance(this).getMyFCDbHelper();
        mFCDbHelper.openDataBase();
        
        Button backBut = (Button) findViewById(R.id.back_but);
        deckname =  (TextView) findViewById(R.id.deckName);
        listCardView = (ListView) findViewById(R.id.listCardView);
		backBut.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
				overridePendingTransition(R.anim.hold, R.anim.push_up_out);
			}
		});
		
		 if(i== -1)
   	  {
  		   list = mFCDbHelper.getAllFlashCard();
  		   bookmaredCard=false;
  		   DeckName = "All Cards";
           deckname.setText(DeckName);
           //listCardView.setBackgroundColor(Color.parseColor("#D3F3FF"));
     }
       else if(i==-2)
       {
          list = mFCDbHelper.getBookmarkedCards();
          bookmaredCard=true;
          DeckName = "Bookmarks & Notes";
          deckname.setText(DeckName);
         // listCardView.setBackgroundColor(Color.parseColor("#D3F3FF"));
         
       }
       else if(i==-3)
       {
          list = mFCDbHelper.getAllSongs();
          bookmaredCard=false;
          DeckName = "All Songs";
          deckname.setText(DeckName);
          //listCardView.setBackgroundColor(Color.parseColor("#D3F3FF"));
         
       }
       else
       {
    	   //String[] colorArr = {"#DCF9BD", "#FDBFDA", "#BFF2EF", "#F5BFE9", "#FFBFC0", "#9B9ED4", "#ECD7C3", "#C0E5CB", "#EBE6CB"};
       	 bookmaredCard=false;
       	 list = mFCDbHelper.getDeckCards(i);
       	       	  if(i==1)
       	       	  {
       	       	listCardView.setBackgroundResource(R.drawable.background1);
       	       	  }
       	       	  else if(i==2)
       	       	  {
       	       	listCardView.setBackgroundResource(R.drawable.background2);
       	       	  }
       	       	  else if(i==3)
    	       	  {
    	       	listCardView.setBackgroundResource(R.drawable.background3);
    	       	  }
       	       	  else if(i==4)
       	       	  {
       	       	listCardView.setBackgroundResource(R.drawable.background4);
       	       	  }
       	       	  else if(i==5)
       	       	  {
       	       	listCardView.setBackgroundResource(R.drawable.background5);
       	       	  }
       	       	  else if(i==6)
       	       	  {
       	       	listCardView.setBackgroundResource(R.drawable.background6);
       	       	  }
       	       	  else if(i==7)
       	       	  {
       	       	listCardView.setBackgroundResource(R.drawable.background7);
       	       	  }
        	//listCardView.setBackgroundColor(Color.parseColor(colorArr[0]));
       	 DeckName = mFCDbHelper.getDeckCardsName(i);      
       	 deckname.setText(Html.fromHtml(mFCDbHelper.getDeckCardsName(i)));
       }
       
       mFCDbHelper.close();
   	if( list == null )
   		Log.i("Search","List is null #################################");
   	for (int in = 0; in < list.size(); in++) {
			String[] strArr = list.get(in);
			listSearchCardid.add(strArr[0]);
			listSearchCardName.add(strArr[1]);
			listSearchCardColor.add(strArr[2]);
   	}
       
       listCardView.setAdapter(new CustomAdapter(this, listSearchCardid, listSearchCardName,listSearchCardColor)); 
       
    	listCardView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
				// TODO Auto-generated method stub
				String s =  parent.getItemAtPosition(pos).toString();
				Log.e("setOnItemClickListener", s);
				Intent i = new Intent(ListCardName.this, CardDetails.class);
				i.putExtra("FROM", "SEARCH");
				i.putExtra("POSITION", "" + pos);
				i.putExtra("SEARCHSTRING", "" + ""/*s*/);
				i.putExtra("TOTALCARDS", "" + listSearchCardid.size());
				i.putExtra("CLASSNAME", "LISTCARDNAME");
				i.putExtra("isBookmarked", bookmaredCard);
				startActivity(i);
			}
		});
    }

    public class CustomAdapter extends BaseAdapter 
    {
    	private Activity activity;
    	private  LayoutInflater inflater = null;
		private List<String> mCardID;
		private List<String> mCardName;
		private List<String> mCardColor;
        public CustomAdapter(Activity a, List<String> cardid, List<String> cardname, List<String> cardcolor) 
        {
            activity = a;
            mCardID = cardid;
            mCardName = cardname;
            mCardColor=cardcolor;
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public int getCount() {
            return mCardID.size();
        }
        
		@SuppressWarnings("unused")
		private int selectedPos = -1;	

		public void setSelectedPosition(int pos){
			selectedPos = pos;
			notifyDataSetChanged();
		}

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }
        
        public class ViewHolder{
        	public TextView address;
        	public TextView address2;        	
        }

        public View getView(int position, View convertView, ViewGroup parent) 
        {
        	View vi=convertView;
        	ViewHolder holder;        	
        	String[] cardNamearr=mCardName.get(position).split("-");
    		if(convertView==null)
    		{
    			vi = inflater.inflate(R.layout.cardname, null);
    			holder=new ViewHolder();
    		    holder.address=(TextView)vi.findViewById(R.id.list_card_name); 
    		    holder.address2=(TextView)vi.findViewById(R.id.list_card_name2);
    		    holder.address.setTag(mCardID.get(position));
    			vi.setTag(holder);    			
    		}
    		else    		
    			holder=(ViewHolder)vi.getTag();    		
    			holder.address.setText(Html.fromHtml(cardNamearr[0]));
    			holder.address2.setText(Html.fromHtml(cardNamearr[1]));
    			//vi.setBackgroundColor(Color2Hex(new String[]{mCardColor.get(position)}));
            return vi;
        }
    }
    
    @Override
    protected void onResume()
    {
    	
       super.onResume();
      
   	
       listCardView.setAdapter(new CustomAdapter(this, listSearchCardid, listSearchCardName,listSearchCardColor));  
       mFCDbHelper.openDataBase();
       if(bookmaredCard)
       {
    	   List<String[]> list1 = mFCDbHelper.getBookmarkedCards();
    	   if(list1.size()==0)
    		   finish();
       }
       mFCDbHelper.close();

    }
    
    
    public int Color2Hex( String[] args ) {
    	int cc = 0;
    	if (args.length != 3) {
    		String col = args[0];
    		String r = col.substring(0, col.indexOf(","));
    		String g = col.substring(col.indexOf(",") + 1, col.lastIndexOf(","));
    		String b = col.substring(col.lastIndexOf(",") + 1, col.length());
    		int red = Integer.parseInt(r);
    		int green = Integer.parseInt(g);
    		int blue = Integer.parseInt(b);
    		cc = Color.argb(255, red, green, blue);
    		
    	}
    	else {
    		int red = Integer.parseInt(args[0]);
    		int green = Integer.parseInt(args[1]);
    		int blue = Integer.parseInt(args[2]);
    		cc = Color.argb(255, red, green, blue);//rgb(red, green, blue);
        }
		return cc;
    }
    
}
