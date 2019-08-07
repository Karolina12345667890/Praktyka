package uph.ii.SIMS


import org.springframework.beans.factory.annotation.Autowired
import uph.ii.SIMS.DocumentModule.DocumentFacade

class AcceptanceSpec extends IntegrationSpec {
    @Autowired
    DocumentFacade documentFacade;

    def "positive scenario"() {
        given: ""
        when: ""
        then: true
    }

}
