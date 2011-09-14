/**
 * 
 */
package org.jug.montpellier.app.jugdroid.models;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Defines a partner. A partner can be an annual partner or an event partner.
 * @author etaix
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Partner implements Serializable, Parcelable {

	// The partner's name
	@JsonProperty(value="name")
	public String name;
	// The partner's web site url
	@JsonProperty(value="url")
	public String websiteUrl;
	// The description of the partner
	@JsonProperty(value="description")
	public String description;
	// The partner's logo url
	@JsonProperty(value="logoURL")
	public String logoURL;
	
	/**
	 * Default constructor which is not called from Parcel
	 */
	public Partner() {		
	}
	
	public Partner(Parcel in) {
		readFromParcel(in);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	/**
	 * Write instance attributs in the Parcel dest
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(websiteUrl);
		dest.writeString(description);
		dest.writeString(logoURL);
	}
 
	/**
	 *
	 * Called from the constructor to create this
	 * object from a parcel.
	 *
	 * @param in parcel from which to re-create object
	 */
	private void readFromParcel(Parcel in) {
		name = in.readString();
		websiteUrl = in.readString();
		description = in.readString();
		logoURL = in.readString();
 	}
	
	/**
    *
    * This field is needed for Android to be able to
    * create new objects, individually or as arrays.
    *
    * This also means that you can use use the default
    * constructor to create the object and use another
    * method to hyrdate it as necessary.
    *
    * I just find it easier to use the constructor.
    * It makes sense for the way my brain thinks ;-)
    *
    */
   public static final Parcelable.Creator<Partner> CREATOR =
   	new Parcelable.Creator<Partner>() {
           public Partner createFromParcel(Parcel in) {
               return new Partner(in);
           }

           public Partner[] newArray(int size) {
               return new Partner[size];
           }
       };
}
