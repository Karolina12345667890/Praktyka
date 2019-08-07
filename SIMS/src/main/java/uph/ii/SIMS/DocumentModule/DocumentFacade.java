package uph.ii.SIMS.DocumentModule;


import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import uph.ii.SIMS.DocumentModule.Dto.OswiadczenieDto;
import uph.ii.SIMS.DocumentModule.Oswiadczenie.Oswiadczenie;
import uph.ii.SIMS.DocumentModule.Oswiadczenie.OswiadczenieFacade;
import uph.ii.SIMS.PdfCreationService.PdfBuilder;
import uph.ii.SIMS.UserModule.UserFacade;

@AllArgsConstructor
public class DocumentFacade {
    
    private PdfBuilder pdfBuilder;
    private OswiadczenieFacade oswiadczenieFacade;
    
    public byte[] createPdf(String templateName, String jsonToPopulateTemplate) throws Exception {
        return pdfBuilder.getPdf(templateName, jsonToPopulateTemplate);
    }
    
    public Page<Oswiadczenie> showUsersOswiadczenia(Long ownerId) throws Exception {
        return null;
    }
    
    public void storeOswiadczenie(OswiadczenieDto oswiadczenieDto) throws Exception{
        oswiadczenieFacade.save(oswiadczenieDto);
    }
    
    public OswiadczenieDto showOswiadczenie(Long id){
        return oswiadczenieFacade.find(id);
    }
    
    public Page<OswiadczenieDto> showMyDocuments() throws Exception{
        return oswiadczenieFacade.findMyDocuments();
    }
    
    
}
