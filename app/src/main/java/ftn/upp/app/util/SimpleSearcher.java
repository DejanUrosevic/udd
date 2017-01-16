package ftn.upp.app.util;

import java.io.File;

import org.apache.lucene.analysis.core.SimpleAnalyzer;

public class SimpleSearcher extends AnalyzedSearcher {
	
	public SimpleSearcher(){
		super(new SimpleAnalyzer(v));
	}
	
	public SimpleSearcher(String path){
		super(path, new SimpleAnalyzer(v));
	}
	
	public SimpleSearcher(File indexDir){
		super(indexDir, new SimpleAnalyzer(v));
	}

}
