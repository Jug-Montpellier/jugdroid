/**
 * 
 */
package org.jug.montpellier.app.jugdroid.models;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Define a speaker or a JUG member
 * @author etaix
 */
public class Speaker {

	// Job position
	@JsonProperty(value="activity")
	public String activity;
	// Actual company
	@JsonProperty(value="company")
	public String company;
	// Firstname + name
	@JsonProperty(value="fullName")
	public String fullName;
	// Speaker email
	@JsonProperty(value="email")
	public String email;	
	// Unique ID of this member
	@JsonProperty(value="id")
	public int id;
	// TRUE if he's a JUG member
	@JsonProperty(value="jugmember")
	public boolean jugMember;
	// If jugMember is TRUE then this attribut defines his position
	@JsonProperty(value="memberFct")
	public String memberFct;
	// The company Web site
	@JsonProperty(value="url")
	public String companyURL;
	// Personnal Web site
	@JsonProperty(value="personalUrl")
	public String personalURL;
	// Description / Bio
	@JsonProperty(value="description")
	public String description;
	
}
