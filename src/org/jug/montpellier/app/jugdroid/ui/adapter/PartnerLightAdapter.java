/**
 * 
 */
package org.jug.montpellier.app.jugdroid.ui.adapter;

import org.jug.montpellier.app.jugdroid.R;
import org.jug.montpellier.app.jugdroid.models.Partner;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * This adpater is used when the PartnerDetailFragment is visible. It displays
 * less informations then the 'heavy' adapter.
 * 
 * @author Eric Taix
 */
public class PartnerLightAdapter extends PartnersAdapter {

	public PartnerLightAdapter(Activity activityP) {
		super(activityP);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent, LayoutInflater inflater) {
		Partner partner = partners.get(position);
		if (partner != null) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.partner_light_item, parent, false);
				holder = new ViewHolder();
				holder.nameView = (TextView) convertView.findViewById(R.id.name);
				holder.urlView = (TextView) convertView.findViewById(R.id.webSite);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}
			// Set the fullname
			holder.nameView.setText(partner.name);
			// Set the web site URL
			holder.urlView.setText(partner.websiteUrl);
			return convertView;
		}
		return null;
	}
}
