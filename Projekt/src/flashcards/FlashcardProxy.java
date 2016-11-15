package flashcards;

public class FlashcardProxy implements flashcards.Flashcard {
  private String _endpoint = null;
  private flashcards.Flashcard flashcard = null;
  
  public FlashcardProxy() {
    _initFlashcardProxy();
  }
  
  public FlashcardProxy(String endpoint) {
    _endpoint = endpoint;
    _initFlashcardProxy();
  }
  
  private void _initFlashcardProxy() {
    try {
      flashcard = (new flashcards.FlashcardServiceLocator()).getFlashcard();
      if (flashcard != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)flashcard)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)flashcard)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (flashcard != null)
      ((javax.xml.rpc.Stub)flashcard)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public flashcards.Flashcard getFlashcard() {
    if (flashcard == null)
      _initFlashcardProxy();
    return flashcard;
  }
  
  
}