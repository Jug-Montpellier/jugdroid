package org.jug.montpellier.app.jugdroid.ui.fragment;

import org.jug.montpellier.app.jugdroid.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DashboardFragment extends Fragment {


	/**
	 * Main method of fragment. Creates the view which will be attached to the
	 * activity
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_dashboard, null, false);
		return view;
	}

}
