package ftn.upp.app.util;

import java.io.File;

import ftn.upp.app.util.SerbianAnalyzer;

public class SerbianSearcher extends AnalyzedSearcher {
	
	public SerbianSearcher() {
		super(new SerbianAnalyzer(v));
	}

	public SerbianSearcher(File indexDir) {
		super(indexDir, new SerbianAnalyzer(v));
	}
	
	public SerbianSearcher(String indexDirPath) {
		super(indexDirPath, new SerbianAnalyzer(v));
	}

}
