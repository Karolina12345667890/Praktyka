package uph.ii.SIMS.PdfCreationService.Dto;

import lombok.Builder;
import lombok.Getter;
import uph.ii.SIMS.UserModule.Dto.GroupSummaryDto;

import java.util.List;

@Getter
@Builder
public class PodsumowanieGrupyPdfDto {

    public String documentContents;
    public String groupAdminName;
    public String groupAdminSurname;
    public List<GroupSummaryDto> groupSummaryDtos;

}
