/**
 * FlashcardService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package flashcards;

public interface FlashcardService extends javax.xml.rpc.Service {
    public java.lang.String getFlashcardAddress();

    public flashcards.Flashcard getFlashcard() throws javax.xml.rpc.ServiceException;

    public flashcards.Flashcard getFlashcard(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
