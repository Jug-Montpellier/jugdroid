/**
 * 
 */
package org.jug.montpellier.app.jugdroid.core;

/**
 * A core exception which is throwed when a problem occured while requesting the
 * backend or while parsing the backend response. Log should be done before this exception is throwed.<br/>
 * This exception should be catched to display an human readable message.
 * 
 * @author etaix
 */
@SuppressWarnings("serial")
public class BackendException extends Exception {

}
