package com.example.manejosalas.controlador;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.*;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import com.example.manejosalas.DAO.SalaDAO;
import com.example.manejosalas.entidad.Sala;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReporteServicio {

	@Autowired
	DataSourceConfig dataSourceConfig;
	
    @Autowired
    private SalaDAO repository;
    
    public String exportReport(String nombre, String reportFormat, JasperPrint jasperPrint) throws FileNotFoundException, JRException, SQLException {
    	Path currentRelativePath = Paths.get("");
    	String s = currentRelativePath.toAbsolutePath().toString();    	    
    	
        String path = s + "/reports";
                            
        String finalPath = path;
        
        if (reportFormat.equalsIgnoreCase("html")) {
        	finalPath = path + "/" + nombre + ".html";
            JasperExportManager.exportReportToHtmlFile(jasperPrint, finalPath);
        }
        else if (reportFormat.equalsIgnoreCase("pdf")) {
        	finalPath = path + "/" + nombre + ".pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint, finalPath);
        }
        else{
        	finalPath = path;
        }     

        return finalPath;
    }
    
    
    public String generateReport(int id, TipoReporte tipoReporte) throws FileNotFoundException, JRException, SQLException{
    	
    	String finalPath = "";
    	
    	//Determine the kind of report
    	if(TipoReporte.SUPER_SALAS.compareTo(tipoReporte) == 0){
    		
    		//load file and compile it
            File file = ResourceUtils.getFile("classpath:super_salas.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
            
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("createdBy", "GESCOL");
            //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);        
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSourceConfig.getDataSource().getConnection());
            
            String currentDate = ( new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ss.SSSXXX" ) ).format( Calendar.getInstance().getTime() );            
            //            nombreReporte = String.valueOf(id) + "_" +  Calendar.getInstance().getTime().toString();          
            String auxDate = String.valueOf(id)+String.valueOf(Math.abs(currentDate.hashCode()));           
            
            finalPath = exportReport(auxDate, "pdf", jasperPrint);
    	}
    	else if(TipoReporte.ADMIN_SALAS.compareTo(tipoReporte) == 0){
    		finalPath = "";
    	}
    	    	
    	return finalPath;
    }
    
    
    public void deleteDocument(String src) throws IOException{
    	
	    File fileExist = new File(src);
	    
	    BufferedWriter bw = new BufferedWriter(new FileWriter(fileExist));

	    fileExist.delete();
//    	bw.flush();	    
    	
	    bw.close();	    	    
    }
    
    @SuppressWarnings("deprecation")
	public String getPagePdf(String src, int page) throws IOException, DocumentException {
	    PdfReader reader = new PdfReader(src);
	    int n = reader.getNumberOfPages();
	    reader.close();
	    String path2 = "";
	    String path = "";
	    PdfStamper stamper;
	    
	    String[] pathBroken = src.split("/");
	    
	    
	    for(int i = 0; i < pathBroken.length - 1; i++){
	    	path += pathBroken[i] + "/";
	    }
	   	    
	    path2 = pathBroken[pathBroken.length - 1];
	    
	    for (int i = 1; i <= n; i++) {
	        reader = new PdfReader(src);
	        reader.selectPages(String.valueOf(i));
	        String path3 = String.format(path + "%s.pdf", i);
	        stamper = new PdfStamper(reader,new FileOutputStream(path3));
	        stamper.close();
	        reader.close();
	    }        
	    

	    PDFMergerUtility ut = new PDFMergerUtility();
	    
	    for(int i = 1; i < page + 1; i++){
	    	File f1 = new File(path + String.valueOf(i) + ".pdf");
	    	ut.addSource(f1);
	    }
    	
	    ut.setDestinationFileName(src);
	    ut.mergeDocuments();
	    
    	return src;    
    }
    
    public static boolean renameFile(File toBeRenamed, String new_name) {
        //need to be in the same path
        File fileWithNewName = new File(toBeRenamed.getParent(), new_name);
        /*
        if (fileWithNewName.exists()) {
            return false;
        }
        */
        // Rename file (or directory)
        return toBeRenamed.renameTo(fileWithNewName);
    }    
    
    
}

