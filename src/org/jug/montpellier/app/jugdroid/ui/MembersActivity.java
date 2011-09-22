/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.ui.fragment.MemberListFragment;

import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ActionBar.Tab;
import android.view.View;

import com.googlecode.androidannotations.annotations.BeforeCreate;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * Shows the members list
 * 
 * @author etaix
 */
@EActivity(R.layout.activity_member)
public class MembersActivity extends FragmentActivity implements ActionBar.TabListener{

	@ViewById(R.id.members_list_frame)
	View leftContainer;
	
	/**
	 * Hack due to a Fragment API issue with AndroidAnnotations
	 */
	@BeforeCreate
	public void onBeforeCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		final ActionBar ab = getSupportActionBar();
		// Setup navigation mode to tabs
		ab.setDisplayShowTitleEnabled(false);
		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		create();
	}
	
  private void create() {
		MemberListFragment frag = new MemberListFragment();
		// Execute a transaction, replacing any existing fragment with this one inside the frame.
//		Animation.alpha(leftContainer, 1.0f, 0.0f, 3000, 0);
		getSupportFragmentManager().beginTransaction()
		                    .replace(R.id.members_list_frame, frag)
//		                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
		                    .commit();
//		Animation.alpha(leftContainer, 0.0f, 1.0f, 3000, 0);
  }

	/**
	 * The user has clicked on a new tab
	 */
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Animation.rotateLeft(leftContainer, 0, 360, 300);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}
}
