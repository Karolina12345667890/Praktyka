package uph.ii.SIMS.DocumentModule

import spock.lang.Specification
import uph.ii.SIMS.PdfCreationService.PdfBuilder
import uph.ii.SIMS.UserModule.SampleUsers
import uph.ii.SIMS.UserModule.UserFacade

class DocumentSpec extends Specification implements SampleDocuments, SampleUsers {

    UserFacade userFacade = Stub(UserFacade){
        userFacade.getCurrentUser() >>> [user1, user2]
    }
    PdfBuilder pdfBuilder = Mock(PdfBuilder)
    DocumentFacade facade = new DocumentConfiguration().documentFacadeInMemoryIO(pdfBuilder, userFacade)


    def "Should show a document"() {
        given: "There is a document in the system"
            facade.storeOswiadczenie(oswiadczenie1)

        expect: "System should show the document"
            facade.fetchOswiadczenie(oswiadczenie1.id) == oswiadczenie1
    }

    def "Should show my documents"(){
        setup: "There are 2 different users"
            userFacade.getCurrentUser() >>> [user1, user2]

        and: "Each user stores a document"
            facade.storeOswiadczenie(oswiadczenie1)
            facade.storeOswiadczenie(oswiadczenie2)

        expect: "System should show only document owned by the current user"
            facade.showMyDocuments().contains(oswiadczenie2)
            facade.showMyDocuments().totalElements == 1

    }



}
