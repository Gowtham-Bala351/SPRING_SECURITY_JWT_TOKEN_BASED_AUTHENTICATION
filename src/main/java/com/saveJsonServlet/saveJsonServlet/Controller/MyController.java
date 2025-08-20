package com.saveJsonServlet.saveJsonServlet.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.saveJsonServlet.saveJsonServlet.Bean.SessionId;
import com.saveJsonServlet.saveJsonServlet.Bean.Usersk;
import com.saveJsonServlet.saveJsonServlet.Exceptions.ReadActionNotFoundException;
import com.saveJsonServlet.saveJsonServlet.Exceptions.UserNameNotFoundException;
import com.saveJsonServlet.saveJsonServlet.Repo.TransactionRepository;
import com.saveJsonServlet.saveJsonServlet.Repo.UserRepo;
import com.saveJsonServlet.saveJsonServlet.Service.JWTService;
import com.saveJsonServlet.saveJsonServlet.Service.LogPathInfo;
import com.saveJsonServlet.saveJsonServlet.Service.MyService;
import com.saveJsonServlet.saveJsonServlet.Service.UserService;

import ch.qos.logback.classic.Logger;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;


@RestController

public class MyController {
	@Autowired
	MyService myservice;
	
	@Autowired
	JWTService jwtService;
	
	@Autowired
    private UserRepo userRepo;
	
	@Autowired
	private UserService userser;
	
	@Autowired
	private LogPathInfo logapthInfo;
	
	@Autowired
	private TransactionRepository trnsReo;
	
	@Value("${scan.application.screen.path}")
	private String scanApplicationScreenPath;
    @GetMapping("/SaveJsonServlet")  
	public JsonObject getMethod(@RequestBody String stringData,HttpServletResponse a,HttpServletRequest request) throws ReadActionNotFoundException {
       String username = logapthInfo.getUserNamediffEndPointAuth(request);
       Logger logS = logapthInfo.getLogFile(username);
       logS.info(logapthInfo.userInfo(request));
        
        
    	//System.out.println(a.);
    	//System.out.println("hiii");
    	JsonObject jsonObject = null;
    	JsonParser jp=new JsonParser();
    	JsonObject jsonData = (JsonObject) jp.parse(stringData);
    	String fileName = myservice.togetFileName(jsonData);
    	String actionName = myservice.togetAction(jsonData);
    	if(actionName.equals("R")) {
    		try {
				 jsonObject = myservice.toReadJson(fileName,scanApplicationScreenPath);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	else {

    			
    			ReadActionNotFoundException ex = new ReadActionNotFoundException("Action W can't Read File");
    		    logS.error("Read action failed: " + ex.getMessage(), ex);
    		    throw ex;  
    		}
       
		return jsonObject;
		
	} 
    
    

	@PostMapping("/convert")
    public String postMethodName(HttpServletRequest request) throws IOException, DocumentException {
		String username = logapthInfo.getUserNamediffEndPointAuth(request);
    	Logger logS = logapthInfo.getLogFile(username);
       logS.info(logapthInfo.userInfo(request));
       
       
    	String fileNameData = "file";
    	String fileNameData1 = "pdfFile";
    	
    	String fileName = scanApplicationScreenPath + "//" + fileNameData + ".xlsx";
        String pdfPath = scanApplicationScreenPath + "//" + fileNameData1 + ".pdf";
            List<String[]> data = readExcel(fileName);
            createPdf(pdfPath, data.toArray(new String[0][0]));

//            FileInputStream fis = new FileInputStream(new File(fileName));
//            Workbook workbook = new XSSFWorkbook(fis);
//            Sheet sheet = workbook.getSheetAt(0);
//            for (Row row : sheet) {
//                String[] rowData = new String[row.getPhysicalNumberOfCells()];
//                for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
//                    rowData[i] = row.getCell(i).toString();
//                }
//                data.add(rowData);
//            }
//            workbook.close();
//            fis.close();

            
        
        return "Successfully created";
    }
    
    public static List<String[]> readExcel(String filePath) throws IOException {
    	List<String[]> data = new ArrayList<>();
        FileInputStream fis = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(fis);
        Sheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
        	String[] rowData = new String[row.getPhysicalNumberOfCells()];
        	for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
        		rowData[i] = row.getCell(i).toString();
        	}
        	data.add(rowData);
        	}
        workbook.close();
        fis.close();
     return data;

    }
    public static void createPdf(String dest, String[][] data) throws IOException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();

        PdfPTable table = new PdfPTable(data[0].length);
        for (String[] row : data) {
            for (String cell : row) {
                PdfPCell pdfCell = new PdfPCell(new Paragraph(cell));
                pdfCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(pdfCell);
            }
        }
        
        document.add(table);
        document.close();  
    }
    @PostMapping("/unlock")
    public String toUnlockTheUser(@RequestParam("user_id") String user_id) throws UserNameNotFoundException  {
    	
        Usersk user = userRepo.findByUsername(user_id);

        if (user != null) {
            user.setStatus("N");
            user.setCountOfLogin(0);
            userRepo.save(user);
            return "User " + user_id + " unlocked successfully!";
        } else {
        	Logger logs = logapthInfo.getLogFile(user_id);
        	UserNameNotFoundException ex = new UserNameNotFoundException("User Not found/Please enter the Correct user");
        	logs.error(ex.getMessage(),ex);
        	throw ex;
        }

    }
    
    @GetMapping("/Insert")
    public String getMethodName(@RequestBody JsonObject data) {
    	String logsRespose = userser.toInsertLogs(data);
   
        return logsRespose;
    }
    
    @GetMapping("/Proceed")
    public String toClrPrvSessionCrtNw(@RequestParam("session_id") String user_id) {
    	
    	Optional<SessionId> nname = trnsReo.findById(user_id);
    	SessionId sss = nname.get();
    	sss.setActive(false);
        trnsReo.save(sss);
    	SessionId ss = new SessionId();
    	ss.setActive(true);
    	ss.setCreatedAt(LocalDateTime.now());
    	ss.setTransactionID(userser.toGenerateTransactionId(sss.getUsername()));
    	ss.setUsername(sss.getUsername());
    	
    	return "Previous Session Cleared and New Session created";
    }

}
