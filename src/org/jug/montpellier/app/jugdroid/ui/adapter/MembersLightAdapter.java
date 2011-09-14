/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui.adapter;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.models.Speaker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * This adpater is used when the MemberDetailFragment is visible. It only displays few informations.
 * @author Eric Taix
 */
public class MembersLightAdapter extends MembersAdapter {

	public MembersLightAdapter(Activity activityP) {
		super(activityP);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent, LayoutInflater inflater) {
		Speaker speaker = speakers.get(position);
		if (speaker != null) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.member_light_item, parent, false);
				holder = new ViewHolder();
				holder.fullnameView = (TextView) convertView.findViewById(R.id.fullname);
				holder.memberFonctionView = (TextView) convertView.findViewById(R.id.memberFonction);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}
			// Set the fullname
			holder.fullnameView.setText(speaker.fullName);
			// Set the member position
			holder.memberFonctionView.setText(speaker.memberFct);
			return convertView;
		}
		return null;	
	}
}
