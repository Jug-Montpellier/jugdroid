/**
 * 
 */
package org.jug.montpellier.app.jugdroid.models;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * This class defines an event. An event can be a future event or a past event.<br/>
 * @author eric
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Event implements Serializable {

	// The talk title
	@JsonProperty(value="title")
	public String title;
	// The talk date
	@JsonProperty(value="date")
	public String date;
	// The location : is it the adress ?
	@JsonProperty(value="location")
	public String location;
	// The description of the talk
	@JsonProperty(value="name")
	public String description;
	// The capacity of this talk : is it the available capacity or the total capacity ?
	@JsonProperty(value="capacity")
	public int capacity;
	// Is this talk opened
	@JsonProperty(value="opened")
	public boolean opened;
}
