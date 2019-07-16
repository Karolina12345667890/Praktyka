package uph.ii.SIMS.DocumentModule;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DocumentFacade {
    
    private PdfBuilder pdfBuilder;
    
    public byte[] createPdf(String templateName, String jsonToPopulateTemplate) throws Exception {
        return pdfBuilder.getPdf(templateName, jsonToPopulateTemplate);
    }
    
    
}
