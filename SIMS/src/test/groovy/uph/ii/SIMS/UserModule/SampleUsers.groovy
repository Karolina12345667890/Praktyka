package uph.ii.SIMS.UserModule

import groovy.transform.CompileStatic
import uph.ii.SIMS.UserModule.Dto.UserDto

@CompileStatic
trait SampleUsers {
    UserDto user1 = createUserDto(1L)
    UserDto user2 = createUserDto(2L)

    UserDto createUserDto(Long id) {
        return new UserDto(1L, "12345", "Name", "Surname", "test@mail.com")
    }
}
