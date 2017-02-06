package com.bylak.mule.mongo;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class PHIProcessor implements Callable {
	
	private static final String[] PHI_WORDS = new String[] {"Chava", "#", "Aloes", "##"};	

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		 Object payloadObject = eventContext.getMessage().getPayload();
		 
		 if (payloadObject instanceof String) {
			 String payload = (String)payloadObject;
			 
			 return processPHIwords(payload, PHI_WORDS);
		 }
	     
		 return payloadObject;
	}
		
	public static String processPHIwords(String subject, String... pairs) {
	    if (pairs.length % 2 != 0) throw new IllegalArgumentException(
	        "Strings to find and replace are not paired.");
	    StringBuilder sb = new StringBuilder();
	    int numPairs = pairs.length / 2;
	    for (int i = 0; i < subject.length(); i++) {
	        int longestMatchIndex = -1;
	        int longestMatchLength = -1;
	        for (int j = 0; j < numPairs; j++) {
	            String find = pairs[j * 2];
	            if (subject.regionMatches(i, find, 0, find.length())) {
	                if (find.length() > longestMatchLength) {
	                    longestMatchIndex = j;
	                    longestMatchLength = find.length();
	                }
	            }
	        }
	        if (longestMatchIndex >= 0) {
	            sb.append(pairs[longestMatchIndex * 2 + 1]);
	            i += longestMatchLength - 1;
	        } else {
	            sb.append(subject.charAt(i));
	        }
	    }
	    return sb.toString();
	}
}
