package uph.ii.SIMS.DocumentModule

import groovy.transform.CompileStatic
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto

@CompileStatic
trait SampleDocuments {
    OswiadczenieDto oswiadczenie1 = createOswiadczenieDto(
            1L,
            1L,
            1L,
            "Adam",
            "Kowalski",
            "adamkowalski@gmail.com",
            "123456789")

    OswiadczenieDto oswiadczenie2 = createOswiadczenieDto(
            2L,
            1L,
            1L,
            "Andrzej",
            "Nowak",
            "an@gmail.com",
            "123456789")

    def createOswiadczenieDto(Long id, Long groupId, Long ownerId, String opiekunI, String opiekunN, String opiekunMail, String opiekunTel) {
        return new OswiadczenieDto(id, groupId, ownerId,  opiekunI, opiekunN, opiekunMail, opiekunTel, "", "")
    }

}
