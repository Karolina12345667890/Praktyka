/**
 * <h2>
 *     Informacje ogólne
 * </h2>
 * <h3>
 * Architektura systemu
 * </h3>
 * <p>
 * System składa się z kilku modułów. Moduły komunikują się ze sobą tylko i wyłącznie za pomocą specjalnej klasy fasady.
 * Każdy moduł posiada tylko jedną fasadę, powinna to być jedyna klasa publiczna w danym module
 * (istnieją odstępstwa od tej reguły, np. {@link uph.ii.SIMS.DocumentModule.Document}. Dodatkowo każdy moduł
 * zawiera klasy DTO (data class - pozbawione logiki), które służą do przekazywania danych z i do modułu bez potrzeby
 * udostępniania na zewnątrz klas encji. Takie podejście zapewnia możliwość łatwych zmian wewnątrz modułu - jeśli
 * większość klas w module ma dostęp pakietowy, nie ma obawy, że zmieniając w nich coś zepsujemy pozostałe moduły.
 * </p>
 *
 * <h3>
 * szablony dokumentów - edycja, dodanie nowego itp.
 * </h3>
 *     <ul>
 *        <li>
 *            Metoda staticResourcesUri() w klasie PdfBuilderConfiguration określa, gdzie znajdować się bedą szablony.
 *        </li>
 *        <li>
 *            Do folderu tego należy dodać nowy szablon thymeleaf (ew. edytować istniejący).
 *        </li>
 *        <li>
 *            Obiekt z danymi powinien nazywać się "dto", ponieważ w klasie PdfBuilder obiekt dodawany do kontekstu ma taki identyfikator
 *        </li>
 *        <li>
 *            W razie potrzeby należy dodać własną klasę do pakietu {@link uph.ii.SIMS.PdfCreationService.Dto}, oraz dodać metodę
 *            analogiczną do {@link uph.ii.SIMS.PdfCreationService.PdfBuilder#getPdfFromObject(java.lang.String, uph.ii.SIMS.PdfCreationService.Dto.OswiadczeniePdfDto)}
 *            i {@link uph.ii.SIMS.PdfCreationService.PdfBuilder#getPdfFromObject(java.lang.String, uph.ii.SIMS.PdfCreationService.Dto.PorozumieniePdfDto)}
 *        </li>
 *        <li>
 *            W przypadku dodawania nowego typu dokumentu, należy dodać odpowiedni podpakiet do modułu {@link uph.ii.SIMS.DocumentModule}, analogicznie do już istniejących
 *            {@link uph.ii.SIMS.DocumentModule.Oswiadczenie} i {@link uph.ii.SIMS.DocumentModule.Porozumienie}
 *        </li>
 *        <li>
 *           Następnie należy dodać fasadę nowego podmodułu do {@link uph.ii.SIMS.DocumentModule.DocumentFacade}
 *           i mapowanie nowych funkcjonalności w kontrolerze.
 *        </li>
 *     </ul>
 *
 */
package uph.ii.SIMS;
