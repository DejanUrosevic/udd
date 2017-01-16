package ftn.upp.app.util;

import java.io.IOException;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class LCFilter extends TokenFilter {
	
	private CharTermAttribute cta;
	
	public LCFilter(TokenStream input) {
		super(input);
		// TODO
		this.cta = (CharTermAttribute) input.addAttribute(CharTermAttribute.class); 
	}

	@Override
	public boolean incrementToken() throws IOException {
		// TODO
		if(input.incrementToken()){
			String text = cta.toString();
			cta.setEmpty();
			cta.append(text.toLowerCase());
			return true;
		}
		
		return false;
	}

}
