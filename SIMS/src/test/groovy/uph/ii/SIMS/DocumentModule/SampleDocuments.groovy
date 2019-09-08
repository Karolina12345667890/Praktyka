package uph.ii.SIMS.DocumentModule

import groovy.transform.CompileStatic
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto

@CompileStatic
trait SampleDocuments {
    OswiadczenieDto oswiadczenie1 = createOswiadczenieDto(
            1L,
            "Adam",
            "Kowalski",
            "adamkowalski@gmail.com",
            "123456789")

    OswiadczenieDto oswiadczenie2 = createOswiadczenieDto(
            2L,
            "Andrzej",
            "Nowak",
            "an@gmail.com",
            "123456789")

    def createOswiadczenieDto(Long id, String opiekunI, String opiekunN, String opiekunMail, String opiekunTel) {
        return OswiadczenieDto.builder()
                .id(id)
                .opiekunI(opiekunI)
                .opiekunN(opiekunN)
                .opiekunMail(opiekunMail)
                .opiekunTel(opiekunTel)
                .build()
    }

}
