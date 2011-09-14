package org.jug.montpellier.app.jugdroid.ui;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.ui.widget.NewInfoButton;
import org.jug.montpellier.app.jugdroid.ui.widget.NewInfoProvider;
import org.jug.montpellier.app.jugdroid.ui.widget.RandomNewInfoProvider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBar;
import android.support.v4.app.FragmentActivity;

import com.googlecode.androidannotations.annotations.BeforeCreate;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

/**
 * The Dashboard activity (the main activity).
 * 
 * @author etaix
 */
@EActivity(R.layout.activity_home)
public class DashboardActivity extends FragmentActivity {

	// Define Dashboard buttons
	@ViewById(R.id.home_btn_schedule)
	NewInfoButton schedule;
	@ViewById(R.id.home_btn_sessions)
	NewInfoButton sessions;
	@ViewById(R.id.home_btn_partners)
	NewInfoButton partners;
	@ViewById(R.id.home_btn_members)
	NewInfoButton members;
	@ViewById(R.id.home_btn_news)
	NewInfoButton news;
	@ViewById(R.id.home_btn_about)
	NewInfoButton about;
	// The NewInfoProvider implementation
	private NewInfoProvider infoProvider = new RandomNewInfoProvider();
	

	/**
	 * Hack de Fragment API issue with AndroidAnnotations
	 */
	@BeforeCreate
	public void onBeforeCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

	@Override
	public void onCreate(Bundle savedInstanceState) {
        // Don't call super.onCreate() here.

		// Add action items
		final ActionBar ab = getSupportActionBar();
		// Set defaults for logo & home up
		ab.setDisplayHomeAsUpEnabled(false);
		ab.setDisplayUseLogoEnabled(false);		

		// Inject the NewInfoProvider: may be we can do injection with RoboGuice ?
		schedule.setInfoProvider(infoProvider);
		sessions.setInfoProvider(infoProvider);
		partners.setInfoProvider(infoProvider);
		members.setInfoProvider(infoProvider);
		news.setInfoProvider(infoProvider);
	}

	//======  Click handlers for Dashboard buttons  ======
	@Click(R.id.home_btn_schedule)
	void scheduleClicked() {
//		startActivity(new Intent(this, EventsActivity_.class));
	}

	@Click(R.id.home_btn_sessions)
	void sessionsClicked() {
	}

	@Click(R.id.home_btn_partners)
	void partnersClicked() { 
		startActivity(new Intent(this, PartnersActivity_.class));
	}
	
	@Click(R.id.home_btn_members)
	void membersClicked() {
		startActivity(new Intent(this, MembersActivity_.class));
	}
	
	@Click(R.id.home_btn_news)
	void newsClicked() {
	}
	
	@Click(R.id.home_btn_about)
	void aboutClicked() {
	}
	
	/**
	 * Handle click on action items
	 */
//    public boolean onHandleActionBarItemClick(ActionBarItem item, int position) {
//        switch (item.getItemId()) {
//            case R.id.action_bar_refresh:
//                return true;
//            case R.id.action_bar_info:
//        		startActivity(new Intent(this, InfoTabActivity.class));
//                return true;
//            default:
//                return super.onHandleActionBarItemClick(item, position);
//        }
//    }

}