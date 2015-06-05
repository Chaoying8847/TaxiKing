package com.taxiking.customer;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.taxiking.customer.base.BaseFragment;
import com.taxiking.customer.bean.Ticket;
import com.taxiking.customer.model.TicketOpen;
import com.taxiking.customer.model.TicketSub;

public final class TicketFragment extends BaseFragment  {
	
	private ListView listView;
	private LinearLayout headerView;
	private TicketAdapter adapter;
	private LayoutInflater layoutInflater;
	
	private List<TicketOpen> mTicketOpens;
	private List<TicketSub> mTicketSubs;
	private List<TicketSub> mTicketCloseds;
	
	private List<Ticket> aryTickets;
	private int ticketType;
	
	public static TicketFragment newInstance() {
		TicketFragment fragment = new TicketFragment();
		fragment.ticketType = Ticket.OPEN_TICKET;
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		layoutInflater = inflater;
		View rootview = inflater.inflate(R.layout.fragment_ticket, null);
		headerView = (LinearLayout)rootview.findViewById(R.id.view_header);
		listView = (ListView)rootview.findViewById(R.id.list_ticket);
		adapter = new TicketAdapter(parent, R.layout.view_ticket_item);
		listView.setAdapter(adapter);
		
		displayHeader();

		return rootview;
	}
	
	public void setTicketType(int type) {
		switch (type) {
		case Ticket.OPEN_TICKET:
			setOpenData(mTicketOpens);
			break;
		case Ticket.SUB_TICKET:
			setSubData(mTicketSubs);
			break;
		case Ticket.CLOSED_TICKET:
			setClosedData(mTicketCloseds);
		default:
			break;
		}
	}

	public void setOpenData(List<TicketOpen> ticketOpens) {
		if (aryTickets != null)
			aryTickets.clear();
		else
			aryTickets = new ArrayList<Ticket>();
		
		mTicketOpens = ticketOpens;
		ticketType = Ticket.OPEN_TICKET;
		
		if (mTicketOpens!=null) {
			int count = mTicketOpens.size();
			for (int i=0; i < count; i++) {
				Ticket newTicket = new Ticket();
				newTicket.showText1 = mTicketOpens.get(i).loadNo;
				newTicket.showText2 = mTicketOpens.get(i).loadDate;
				newTicket.showText3 = mTicketOpens.get(i).lease;
				newTicket.showText4 = mTicketOpens.get(i).facilityID;
				newTicket.showText5 = mTicketOpens.get(i).delivLoc;
				newTicket.showText6 = "START";
				newTicket.comment = mTicketOpens.get(i).comments;
				newTicket.id = mTicketOpens.get(i).ticket_id;
				aryTickets.add(newTicket);
			}
		}
		if (adapter != null)
			adapter.notifyDataSetChanged();
		displayHeader();

	}
	
	public void setSubData(List<TicketSub> ticketSubs) {

		if (aryTickets != null)
			aryTickets.clear();
		else
			aryTickets = new ArrayList<Ticket>();
		
		mTicketSubs = ticketSubs;
		ticketType = Ticket.SUB_TICKET;
		
		if (mTicketSubs!=null) {
			int count = mTicketSubs.size();
			for (int i=0; i < count; i++) {
				Ticket newTicket = new Ticket();
				newTicket.showText1 = mTicketSubs.get(i).leaseNo;
				newTicket.showText2 = mTicketSubs.get(i).leaseCompName;
				newTicket.showText3 = mTicketSubs.get(i).nameReceiver;
				newTicket.showText4 = mTicketSubs.get(i).date;
				newTicket.showText5 = mTicketSubs.get(i).ticketNo;
				newTicket.showText6 = "PRINT";
				newTicket.comment = mTicketSubs.get(i).comments;
				newTicket.id = mTicketSubs.get(i).ticket_id;
				aryTickets.add(newTicket);
			}
		}
		if (adapter != null)
			adapter.notifyDataSetChanged();
		displayHeader();
	}
	
	public void notifyDataSetChanged() {
		if (adapter != null)
			adapter.notifyDataSetChanged();
	}
	
	public void setClosedData(List<TicketSub> ticketCloseds) {

		if (aryTickets != null)
			aryTickets.clear();
		else
			aryTickets = new ArrayList<Ticket>();
		
		mTicketCloseds = ticketCloseds;
		ticketType = Ticket.CLOSED_TICKET;
		
		
		if (mTicketCloseds!=null) {
			int count = mTicketCloseds.size();
			for (int i=0; i < count; i++) {
				Ticket newTicket = new Ticket();
				newTicket.showText1 = mTicketCloseds.get(i).leaseNo;
				newTicket.showText2 = mTicketCloseds.get(i).leaseCompName;
				newTicket.showText3 = mTicketCloseds.get(i).nameReceiver;
				newTicket.showText4 = mTicketCloseds.get(i).date;
				newTicket.showText5 = mTicketCloseds.get(i).ticketNo;
				newTicket.showText6 = "PRINT";
				newTicket.comment = mTicketCloseds.get(i).comments;
				newTicket.id = mTicketCloseds.get(i).ticket_id;
				aryTickets.add(newTicket);
			}
		}
		if (adapter != null)
			adapter.notifyDataSetChanged();
		displayHeader();
	}
	@Override
	public void onResume() {
		super.onResume();
	}
	
	private void displayHeader() {
		if (layoutInflater != null) {
			View newHeader;
			switch (ticketType) {
				case Ticket.SUB_TICKET:
				case Ticket.CLOSED_TICKET:
					newHeader = layoutInflater.inflate(R.layout.view_table_header_for_sub, null);
					break;
				case Ticket.OPEN_TICKET:
				default:
					newHeader = layoutInflater.inflate(R.layout.view_table_header_for_open, null);
					break;
			}
			headerView.removeAllViews();
			headerView.addView(newHeader, headerView.getLayoutParams());
		}
	}
	
	class TicketAdapter extends ArrayAdapter<Ticket> {
		LayoutInflater inflater;
		
		public TicketAdapter(Context context, int resource) {
			super(context, resource);
			
			inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			ViewHolder holder;

			convertView = inflater.inflate(R.layout.view_ticket_item, null, false);
		
			holder = new ViewHolder();
			holder.btnLoadNo = (Button) convertView.findViewById(R.id.button_load_no);
			holder.btnLoadNo.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					switch (ticketType) {
					case Ticket.OPEN_TICKET:
						TicketFragment.this.parent.showFragment(TicketOpenDetailFragment.newInstance(aryTickets.get(position), ticketType), true);
						break;
					case Ticket.CLOSED_TICKET:
						TicketFragment.this.parent.showFragment(TicketSubDetailFragment1.newInstance(mTicketCloseds.get(position)), true);
						break;
					case Ticket.SUB_TICKET:
						TicketFragment.this.parent.showFragment(TicketSubDetailFragment1.newInstance(mTicketSubs.get(position)), true);
						break;
					default:
						break;
					}
				}
			});
			holder.txtLoadDate = (TextView) convertView.findViewById(R.id.text_load_date);
			holder.txtLease = (TextView) convertView.findViewById(R.id.text_lease);
			holder.txtFaciltyId = (TextView) convertView.findViewById(R.id.text_facility_id);
			holder.txtDelivLoc = (TextView) convertView.findViewById(R.id.text_deliv_loc);
			holder.txtFunctions = (Button) convertView.findViewById(R.id.button_functions);
			holder.txtFunctions.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					switch (ticketType) {
					case Ticket.OPEN_TICKET:
						TicketFragment.this.parent.showFragment(TicketStartFragment.newInstance(mTicketOpens.get(position)), true);
						break;
					case Ticket.CLOSED_TICKET:
						TicketFragment.this.parent.showFragment(TicketPrintFragment.newInstance(mTicketCloseds.get(position)), true);
						break;
					case Ticket.SUB_TICKET:
						TicketFragment.this.parent.showFragment(TicketPrintFragment.newInstance(mTicketSubs.get(position)), true);
						break;
					default:
						break;
					}
				}
			});
			
			Ticket ticketItem = aryTickets.get(position);
			
			if (ticketItem != null) {
				
				holder.btnLoadNo.setText(ticketItem.showText1);
				holder.txtLoadDate.setText(ticketItem.showText2);
				holder.txtLease.setText(ticketItem.showText3);
				holder.txtFaciltyId.setText(ticketItem.showText4);
				holder.txtDelivLoc.setText(ticketItem.showText5);
				holder.txtFunctions.setText(ticketItem.showText6);
				
			}

			return convertView;
		}

		class ViewHolder {
			Button btnLoadNo;
			TextView txtLoadDate;
			TextView txtLease;
			TextView txtFaciltyId;
			TextView txtDelivLoc;
			Button txtFunctions;
		}
		
		@Override
		public int getCount () {
			return aryTickets.size();
		}
	}
	
}
