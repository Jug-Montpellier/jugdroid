/**
 * 
 */
package org.jug.montpellier.app.jugdroid.models;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Defines a partner. A partner can be an annual partner or an event partner.
 * @author etaix
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown=true)
public class Partner implements Serializable {

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
	
}
