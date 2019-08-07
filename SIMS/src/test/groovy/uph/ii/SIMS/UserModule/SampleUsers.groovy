package uph.ii.SIMS.UserModule

import groovy.transform.CompileStatic
import uph.ii.SIMS.UserModule.Dto.UserDto

@CompileStatic
trait SampleUsers {
    UserDto user1 = createUserDto(1L)
    UserDto user2 = createUserDto(2L)

    def UserDto createUserDto(Long id){
        return UserDto.builder()
                .id(id)
                .build()
    }
}
