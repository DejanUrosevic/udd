package ftn.upp.app.util;

import java.io.File;

import org.apache.lucene.document.Document;

import ftn.upp.app.util.IncompleteIndexDocumentException;

public abstract class DocumentHandler {
	public abstract Document getDocument(File file) throws IncompleteIndexDocumentException;
}
