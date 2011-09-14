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
 * Define a speaker or a JUG member
 * @author etaix
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Speaker implements Serializable, Parcelable {

	// Job position
	@JsonProperty(value="activity")
	public String activity;
	// Actual company
	@JsonProperty(value="company")
	public String company;
	// Firstname + name
	@JsonProperty(value="fullName")
	public String fullName;

	
	// The company Web site
	@JsonProperty(value="url")
	public String companyURL;
	// Personnal Web site
	@JsonProperty(value="personalUrl")
	public String personalURL;
	// Description / Bio
	@JsonProperty(value="description")
	public String description;
	// The photo URL
	@JsonProperty(value="photoUrl")
	public String photoUrl = null;
	// The member fonction
	@JsonProperty(value="memberFct")
	public String memberFct;
	
	/**
	 * Default constructor which is not called from Parcel
	 */
	public Speaker() {		
	}
	
	public Speaker(Parcel in) {
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
		dest.writeString(activity);
		dest.writeString(company);
		dest.writeString(fullName);
		dest.writeString(companyURL);
		dest.writeString(personalURL);
		dest.writeString(description);
		dest.writeString(photoUrl);
	}
 
	/**
	 *
	 * Called from the constructor to create this
	 * object from a parcel.
	 *
	 * @param in parcel from which to re-create object
	 */
	private void readFromParcel(Parcel in) {
		activity = in.readString();
		company = in.readString();
		fullName = in.readString();
		companyURL = in.readString();
		personalURL = in.readString();
		description = in.readString();
		photoUrl = in.readString();
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
   public static final Parcelable.Creator<Speaker> CREATOR =
   	new Parcelable.Creator<Speaker>() {
           public Speaker createFromParcel(Parcel in) {
               return new Speaker(in);
           }

           public Speaker[] newArray(int size) {
               return new Speaker[size];
           }
       };
}
