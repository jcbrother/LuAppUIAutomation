package com.lufax.ui.auto.services;

import com.lufax.ui.auto.caseobj.Case;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Jc on 17/1/8.
 */

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE) //每次容器获取新实例
public class ReportingService {

    public String caseTitleNodeStr = "<li id=\"$!{CASE_ID}\" onclick=\"showCaseDetails(this)\">[$!{CASE_ID}]$!{CASE_TITLE}</li>";
    public String caseDetailStepNodeStr = "<li>STEP$!{STEP_IDX}: $!{STEP_DETAIL}</li>";
    public String testResultStepNodeStr = "<li>STEP$!{STEP_IDX}>> Expect:  <span class=\"expect-result\">$!{EXPECT_RESULT}</span>   Actual:  <span class=\"$!{RESULT_STYLE_TYPE}\">$!{ACTUAL_RESULT}</span></li>";
    public String snapshootDataNodeStr = "<li id=\"$!{STEP_IDX}\" class=\"snapshoot-data\">$!{SNAPSHOOT_PATH}</li>";

    public LinkedList<String> caseTitleNodeStrList = new LinkedList<String>();
    public LinkedList<String> caseDetailStepNodeStrList = new LinkedList<String>();
    public LinkedList<String> testResultStepNodeStrList = new LinkedList<String>();
    public LinkedList<String> snapshootDataNodeStrList = new LinkedList<String>();

    public LinkedList<String> caseDetailDivsList = new LinkedList<String>();

    //初始化测试结果元素样式类型
    public Map<String,String> testResultStyleType = new HashMap<String,String>(){
        {
            put("PASS","actual-result-pass");
            put("FAIL","actual-result-fail");
        }
    };


    public void addCaseTitle(String caseId, String caseTitle){
        caseTitleNodeStrList.add(caseTitleNodeStr.replace("$!{CASE_ID}", caseId)
                .replace("$!{CASE_TITLE}", caseTitle));
    }

    public void addDetailStep(String stepIdx, String stepDetail){
        caseDetailStepNodeStrList.add(caseDetailStepNodeStr.replace("$!{STEP_IDX}", stepIdx)
                .replace("$!{STEP_DETAIL}", stepDetail));
    }

    public void addTestResultStep(String stepIdx, String expectResult, String resultStyleType, String actualResult){
        testResultStepNodeStrList.add(testResultStepNodeStr.replace("$!{STEP_IDX}", stepIdx)
                .replace("$!{EXPECT_RESULT}", expectResult)
                .replace("$!{RESULT_STYLE_TYPE}", resultStyleType)
                .replace("$!{ACTUAL_RESULT}", actualResult));
    }

    public void addSnapshoot(String stepId, String snapshootPath){
        snapshootDataNodeStrList.add(snapshootDataNodeStr.replace("$!{STEP_IDX}", stepId)
                .replace("$!{SNAPSHOOT_PATH}", snapshootPath));
    }

    public String buildMergedStrFromList(LinkedList<String> strList){
        StringBuilder sb = new StringBuilder();
        Iterator<String> it = strList.iterator();
        while (it.hasNext()){
            sb.append(it.next());
            if(it.hasNext()){
                sb.append("\n\t\t");
            }
        }
        return sb.toString();
    }

    public String buildCaseDetailsDiv(Case aCase) throws IOException, DocumentException {
        String detailsDiv = readTemplate("case-details-templates.xml");
        String firstPicPath = getFirstSnapshootElement(snapshootDataNodeStrList).getText();
        detailsDiv = detailsDiv.replace("$!{CASE_ID}","CASE-" + aCase.getId())
                                .replace("$!{CASE_TITLE}", aCase.getTitle())
                                .replace("$!{DETAIL_STEP_STR_LIST}", buildMergedStrFromList(caseDetailStepNodeStrList))
                                .replace("$!{TEST_RESULT_STEP_STR_LIST}", buildMergedStrFromList(testResultStepNodeStrList))
                                .replace("$!{FIRST_PIC_NAME}",Arrays.asList(firstPicPath.split(File.separator)).get(firstPicPath.split(File.separator).length-1))
                                .replace("$!{FIRST_SNAPSHOOT_PIC}",getFirstSnapshootElement(snapshootDataNodeStrList).getText())
                                .replace("$!{FIRST_PIC_IDX}",getFirstSnapshootElement(snapshootDataNodeStrList).attribute("id").getValue())
                                .replace("$!{SNAPSHOOT_LIST_LENGTH}",String.valueOf(snapshootDataNodeStrList.size()))
                                .replace("$!{SNAPSHOOT-LIST}",buildMergedStrFromList(snapshootDataNodeStrList));
        System.out.println(detailsDiv);
        return detailsDiv;
    }

    public String readTemplate(String tempName) throws IOException {
        String templateFilePath = this.getClass().getClassLoader().getResource("report-templates" + File.separator + tempName).getPath();
        FileUtils.readFileToString(new File(templateFilePath));
        return FileUtils.readFileToString(new File(templateFilePath));
    }


    public Element getFirstSnapshootElement(LinkedList<String> aList) throws DocumentException {
        String firstElementStr = aList.getFirst();
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(new ByteArrayInputStream(firstElementStr.getBytes()));
        return doc.getRootElement();
    }

    public static void main(String[] args) throws IOException, DocumentException {
        ReportingService rs = new ReportingService();
        System.out.println("hello");
        System.out.println(rs.getClass().getClassLoader().getResource("report-templates/case-titles-templates.xml").getPath());
        String s = rs.caseTitleNodeStr.replace("$!{CASE_ID}", "abc").replace("$!{CASE_TITLE}", "看直播");

        rs.addCaseTitle("case1", "我们的故事1");
        rs.addCaseTitle("case2", "我们的故事2");

        rs.addDetailStep("1", "告诉别人我们的故事1");
        rs.addDetailStep("2", "告诉别人我们的故事2");

        rs.addTestResultStep("1","没告诉我","actual-result-pass","pass");
        rs.addSnapshoot("1", "abc/sdh1");
        rs.addSnapshoot("2", "abc/sdh2");
        Case aCase = new Case();
        aCase.setId(1);
        aCase.setTitle("我们的故事");
        rs.buildCaseDetailsDiv(aCase);

    }
}
