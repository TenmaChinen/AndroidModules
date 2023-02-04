package com.softramen.modules.fadeRecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.softramen.modules.R;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

	private final String TAG = "RECYCLER_VIEW_ADAPTER";
	private final String[] stringArray;

	public RecyclerViewAdapter( final String[] stringArray ) {
		this.stringArray = stringArray;
	}

	public void updateItem( final int position ) {
		Log.d( TAG , "UpdateItem" );
		notifyItemChanged( position );
	}


	@Override
	public long getItemId( final int position ) {
		return position;
	}

	@Override
	public int getItemViewType( final int position ) {
		return position;
	}

	@NonNull
	@Override
	public ViewHolder onCreateViewHolder( @NonNull final ViewGroup viewGroup , final int viewType ) {
		final View itemView = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.recycler_view_item , viewGroup , false );
		return new ViewHolder( itemView );
	}

	@Override
	public void onBindViewHolder( @NonNull final ViewHolder viewHolder , final int position ) {
		Log.d( TAG , "onBindViewHolder > position : " + position );
		viewHolder.setText( "TEXT " + position );
	}

	@Override
	public int getItemCount() {
		return stringArray.length;
	}

	class ViewHolder extends RecyclerView.ViewHolder {
		private final TextView textView;

		public ViewHolder( @NonNull final View itemView ) {
			super( itemView );
			textView = itemView.findViewById( R.id.text_view );
		}

		public void setText( final String text ) {
			textView.setText( text );
		}
	}
}
