/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui.fragment;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.models.Speaker;
import org.jug.montpellier.app.jugdroid.ui.adapter.ImageLoader;
import org.jug.montpellier.app.jugdroid.ui.widget.RoundedImageView;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Fragment for members details
 * 
 * @author Eric Taix
 */
public class MemberDetailsFragment extends Fragment {

	private static final String SAVE_MEMBER = "member";
	private TextView fullNameView;
	private TextView jobPositionView;
	private TextView companyURLView;
	private TextView descView;
	private TextView personalView;
	private RoundedImageView photoView;
	// The current attached activity
	private Activity activity;
	// The asynchronous image loader 
  private ImageLoader imgLoader;

	
  /**
   * Create a new instance of DetailsFragment, initialized to
   * show the text at 'index'.
   */
  public static MemberDetailsFragment newInstance(Speaker member) {
      // Supply index input as an argument.
      Bundle args = new Bundle();
      args.putParcelable(SAVE_MEMBER, member);
      
      MemberDetailsFragment fragment = new MemberDetailsFragment();
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
		View view = inflater.inflate(R.layout.fragment_member_details, null);
		fullNameView = (TextView) view.findViewById(R.id.fullname);
		jobPositionView = (TextView) view.findViewById(R.id.jobposition);
		companyURLView = (TextView) view.findViewById(R.id.companyURL);
		companyURLView.setMovementMethod(LinkMovementMethod.getInstance());
		descView = (TextView) view.findViewById(R.id.description);
		personalView = (TextView) view.findViewById(R.id.personalURL);
		photoView = (RoundedImageView) view.findViewById(R.id.photo);
		Speaker member = getArguments().getParcelable(SAVE_MEMBER);
		showMember(member);
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
	private void showMember(Speaker member) {
		// If the member has been set in the intent's extra information 
		if (member != null) { 
			// Set fullname, job position, company
			fullNameView.setText(member.fullName);
			jobPositionView.setText(member.activity);
			companyURLView.setText(Html.fromHtml("<a href='"+member.companyURL+"'>"+member.company+"</a>"));
			descView.setText(member.description);
			personalView.setText(member.personalURL);
			// Set the image URL which will ne download in the background
			if (member.photoUrl != null && member.photoUrl.length() > 0) {
				imgLoader.displayImage(member.photoUrl, activity, photoView, true);
			}
		}
		
	}
}