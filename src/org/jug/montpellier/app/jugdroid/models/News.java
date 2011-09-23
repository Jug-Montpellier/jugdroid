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
 * This class defines a news.<br/>
 * @author Eric Taix
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown=true)
public class News implements Serializable , Parcelable {
	// The talk title
	@JsonProperty(value="title")
	public String title;

	// The talk date
	@JsonProperty(value="date")
	public String date;
	
	// The description of the talk
	@JsonProperty(value="content")
	public String content;
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	/**
	 * Default constructor which is not called from Parcel
	 */
	public News() {
	}

	public News(Parcel in) {
		readFromParcel(in);
	}

	/**
	 * Write instance attributs in the Parcel dest
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(title);
		dest.writeString(date);
		dest.writeString(content);
	}

	/**
	 * 
	 * Called from the constructor to create this object from a parcel.
	 * 
	 * @param in
	 *          parcel from which to re-create object
	 */
	private void readFromParcel(Parcel in) {
		title = in.readString();
		date = in.readString();
		content = in.readString();
	}

	/**
	 * 
	 * This field is needed for Android to be able to create new objects,
	 * individually or as arrays.
	 * 
	 * This also means that you can use use the default constructor to create the
	 * object and use another method to hyrdate it as necessary.
	 * 
	 * I just find it easier to use the constructor. It makes sense for the way my
	 * brain thinks ;-)
	 * 
	 */
	public static final Parcelable.Creator<News> CREATOR = new Parcelable.Creator<News>() {
		public News createFromParcel(Parcel in) {
			return new News(in);
		}

		public News[] newArray(int size) {
			return new News[size];
		}
	};
}
