/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui.fragment;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.models.Partner;
import org.jug.montpellier.app.jugdroid.ui.adapter.ImageLoader;
import org.jug.montpellier.app.jugdroid.ui.widget.RoundedImageView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Fragment for partner details
 * 
 * @author Eric Taix
 */
public class PartnerDetailsFragment extends Fragment {

	private static final String SAVE_PARTNER = "partner";
	private TextView nameView;
	private TextView webSiteView;
	private TextView descriptionView;
	private RoundedImageView logoView;
	// The current attached activity
	private Activity activity;
	// The asynchronous image loader 
  private ImageLoader imgLoader;

	
  /**
   * Create a new instance of DetailsFragment, initialized to
   * show the text at 'index'.
   */
  public static PartnerDetailsFragment newInstance(Partner member) {
      // Supply index input as an argument.
      Bundle args = new Bundle();
      args.putParcelable(SAVE_PARTNER, member);
      
      PartnerDetailsFragment fragment = new PartnerDetailsFragment();
      fragment.setArguments(args);

      return fragment;
  }

  
	/**
	 * Main method of fragment. Creates the view which will be attached to the
	 * activity
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// We have different layouts, and in one of them this fragment's containing
		// frame doesn't exist. The fragment may still be created from its saved
		// state, but there is no reason to try to create its view hierarchy because
		// it won't be displayed. Note this is not needed -- we could
		// just run the code below, where we would create and return the view
		// hierarchy; it would just never be used.
		if (container == null) {
			return null;
		}
		View view = inflater.inflate(R.layout.fragment_partner_details, null);
		nameView = (TextView) view.findViewById(R.id.name);
		webSiteView = (TextView) view.findViewById(R.id.webSite);
		descriptionView = (TextView) view.findViewById(R.id.description);
		logoView = (RoundedImageView) view.findViewById(R.id.logo);
		Partner partner = getArguments().getParcelable(SAVE_PARTNER);
		showPartner(partner);
		return view;
	}

	@Override
	public void onAttach(FragmentActivity activityP) {
		super.onAttach(activityP);
		activity = activityP;
		imgLoader = new ImageLoader(activityP);

	}

	/**
	 * Show member's informations
	 * @param member
	 */
	private void showPartner(Partner partner) {
		// If the partner has been set in the intent's extra information 
		if (partner != null) {
			// Set fullname, job position, company
			nameView.setText(partner.name);
			webSiteView.setText(partner.websiteUrl);
			descriptionView.setText(partner.description);
			// Set the image URL which will be downloaded in a background task
			if (partner.logoURL != null && partner.logoURL.length() > 0) {
				imgLoader.displayImage(partner.logoURL, activity, logoView, true);
			}
		}
		
	}
}