package com.github.wxiaoqi.security.auth.module.oauth.custom.exception;

import org.springframework.security.oauth2.common.exceptions.*;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Base exception for OAuth 2 exceptions.
 *
 * @author Ryan Heaton
 * @author Rob Winch
 * @author Dave Syer
 */
@SuppressWarnings("serial")
@org.codehaus.jackson.map.annotate.JsonSerialize(using = OAuth2ExceptionJackson1Serializer.class)
@org.codehaus.jackson.map.annotate.JsonDeserialize(using = OAuth2ExceptionJackson1Deserializer.class)
@com.fasterxml.jackson.databind.annotation.JsonSerialize(using = OAuth2ExceptionJackson2Serializer.class)
@com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = OAuth2ExceptionJackson2Deserializer.class)
public class CustomOAuth2Exception extends OAuth2Exception {

	private String code ;
	public CustomOAuth2Exception(String msg,String code) {
		super(msg);
		this.code = code;

	}

	@Override
	public String toString() {
		return getSummary();
	}

	/**
	 * @return a comma-delimited list of details (key=value pairs)
	 */
	@Override
	public String getSummary() {

		StringBuilder builder = new StringBuilder();

		String delim = "";

		String error = code;
		if (error != null) {
			builder.append(delim).append("status=\"").append(error).append("\"");
			delim = ", ";
		}

		String errorMessage = this.getMessage();
		if (errorMessage != null) {
			builder.append(delim).append("msg=\"").append(errorMessage).append("\"");
			delim = ", ";
		}

		Map<String, String> additionalParams = this.getAdditionalInformation();
		if (additionalParams != null) {
			for (Map.Entry<String, String> param : additionalParams.entrySet()) {
				builder.append(delim).append(param.getKey()).append("=\"").append(param.getValue()).append("\"");
				delim = ", ";
			}
		}

		return builder.toString();

	}
}
