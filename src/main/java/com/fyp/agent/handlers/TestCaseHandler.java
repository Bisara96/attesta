package com.fyp.agent.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fyp.agent.dbhandlers.TestCaseDBHandler;
import com.fyp.agent.dbhandlers.UserStoryDBHandler;
import com.fyp.agent.models.*;
import com.fyp.agent.sessionfactory.TestCaseStepsFactory;
import com.fyp.agent.utilities.WordsToNumbersUtil;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TestCaseHandler {

    TestCaseDBHandler testCaseDBH;
    UserStoryDBHandler uDBHandler;

    public TestCaseHandler() {

        testCaseDBH = new TestCaseDBHandler();
        uDBHandler = new UserStoryDBHandler();
    }

    public List<TestCase> getStoryTestCases(int id){
        return testCaseDBH.getStoryTestCases(id);
    }

    public TestCase getTestCase(int id){
        return testCaseDBH.getTestCase(id);
    }

    public List<TestCaseSteps> getTestCaseSteps(int id){
        return testCaseDBH.getTestCaseSteps(id);
    }

    public List<TestCaseResult> getTCLastResult(int id) {
        return testCaseDBH.getTestCaseLastResult(id);
    }

    public List<TestCaseResult> getStoryTestCases_Result(int id){
        List<TestCase> testCases = testCaseDBH.getStoryTestCases(id);
        List<TestCaseResult> results = new ArrayList<TestCaseResult>();
        for (TestCase tc: testCases) {
//            results.add().get(0));
        }

        return results;
    }

    private String getDateNow() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String generateTestCases(int id) {
        UserStory story = uDBHandler.getUserStory(id);
        List<TestStep> testSteps = testCaseDBH.getStorySteps(id);
        List<AcceptanceCriteria> acList = testCaseDBH.getStoryACriteria(id);
        String[] rulesList = getRulesOfAcceptanceCriteria(acList);
        int[] tsIndexls = null;
        int tc_no = 1;
        TestCase tc = null;
        testCaseDBH.removeOldTestCases(id);
        List<TestCase> tcs = new ArrayList<TestCase>();

        for (int i = 0; i < acList.size(); i++) {

            String[] words;
            int wordCount = 0;

            switch (rulesList[i].toUpperCase()) {
                case "REQUIRED":
                    words = criteriaElements(acList.get(i).getAcceptanceCriteria());
                    tsIndexls = getTestSteps(acList.get(i).getAcceptanceCriteria(), testSteps, words);
                    wordCount = 0;
                    for (int index : tsIndexls) {
                        List<TestStep> requiredSteps = new ArrayList<>(testSteps);
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Date date = new Date();

                        tc = new TestCase("tc_"+tc_no, words[wordCount]+" is entered",story, acList.get(i), "PASS", getDateNow());
                        tcs.add(tc);
                        tc.setId(testCaseDBH.addTestCase(tc));
                        tc_no++;

                        for(TestStep ts : requiredSteps){
                            TestCaseSteps tcSteps = new TestCaseSteps(tc, ts, "PASS");
                            testCaseDBH.addTestCaseStep(tcSteps);
                        }

                        tc.setScreenshot(requiredSteps.get(requiredSteps.size()-1).getScreenshot());
                        testCaseDBH.updateTestCase(tc);

                        tc = new TestCase("tc_"+tc_no, words[wordCount]+" is not entered",story, acList.get(i), "FAIL", getDateNow());
                        tcs.add(tc);
                        tc.setId(testCaseDBH.addTestCase(tc));
                        tc_no++;
                        requiredSteps.remove(index);
                        for(TestStep ts : requiredSteps){
                            TestCaseSteps tcSteps = new TestCaseSteps(tc, ts, "PASS");
                            testCaseDBH.addTestCaseStep(tcSteps);
                        }
                        tc.setScreenshot(requiredSteps.get(requiredSteps.size()-1).getScreenshot());
                        testCaseDBH.updateTestCase(tc);

                        wordCount++;
                    }
                    break;
                case "MINCHARACTERS":
                    words = criteriaElements(acList.get(i).getAcceptanceCriteria());
                    String testString = "testString";
                    tsIndexls = getTestSteps(acList.get(i).getAcceptanceCriteria(), testSteps, words);
                    wordCount = 0;
                    for (int index : tsIndexls) {
                        List<TestStep> mcTestSteps = new ArrayList<>(testSteps);
                        int val = getNumbersInString(acList.get(i).getAcceptanceCriteria());

                        tc = new TestCase("tc_"+tc_no, val+" character string is entered for "+words[wordCount],story, acList.get(i), "PASS", getDateNow());
                        tcs.add(tc);
                        tc.setId(testCaseDBH.addTestCase(tc));
                        tc_no++;

                        for(int x = 0; x < mcTestSteps.size();x++){
                            if(x == index && mcTestSteps.get(x).getStepType() == TestStepTypes.TYPE) {
                                ((TypeStep) mcTestSteps.get(x)).setValue(randomString(val));
                                mcTestSteps.get(x).setId(testCaseDBH.addTestStep(mcTestSteps.get(x)));
                            }
                            TestCaseSteps tcSteps = new TestCaseSteps(tc, mcTestSteps.get(x), "PASS");
                            testCaseDBH.addTestCaseStep(tcSteps);
                        }

                        tc.setScreenshot(mcTestSteps.get(mcTestSteps.size()-1).getScreenshot());
                        testCaseDBH.updateTestCase(tc);

                        val = val - 1;

                        tc = new TestCase("tc_"+tc_no, val+" character string is entered for "+words[wordCount],story, acList.get(i), "FAIL", getDateNow());
                        tcs.add(tc);
                        tc.setId(testCaseDBH.addTestCase(tc));
                        tc_no++;

                        for(int x = 0; x < mcTestSteps.size();x++){
                            if(x == index && mcTestSteps.get(x).getStepType() == TestStepTypes.TYPE) {
                                ((TypeStep) mcTestSteps.get(x)).setValue(randomString(val));
                                mcTestSteps.get(x).setId(testCaseDBH.addTestStep(mcTestSteps.get(x)));
                            }
                            TestCaseSteps tcSteps = new TestCaseSteps(tc, mcTestSteps.get(x), "PASS");
                            testCaseDBH.addTestCaseStep(tcSteps);
                        }

                        tc.setScreenshot(mcTestSteps.get(mcTestSteps.size()-1).getScreenshot());
                        testCaseDBH.updateTestCase(tc);

                        wordCount++;
                    }
                    break;
                case "Data":
                    break;
                case "Type":
                    break;
                case "Dropdown":
                    break;
            }
        }
        return null;
    }

    private int getNumbersInString(String str) {
        int number = -9999;
        number = Integer.parseInt(str.replaceAll("\\D+",""));

        if(number == -9999){
            WordsToNumbersUtil wordsUtil = new WordsToNumbersUtil();
            str = wordsUtil.convertTextualNumbersInDocument(str);
            number = Integer.parseInt(str.replaceAll("\\D+",""));
        }
        return number;
    }

    private String randomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvwxyz"
                + "0123456789";
        String str = new Random().ints(length, 0, chars.length())
                .mapToObj(i -> "" + chars.charAt(i))
                .collect(Collectors.joining());

        return str;
    }
    private String[] getRulesOfAcceptanceCriteria(List<AcceptanceCriteria> acList) {
        String[] rulesArray = new String[acList.size()];

        JSONArray array = new JSONArray();
        for (AcceptanceCriteria cr : acList){
            array.put(cr.getAcceptanceCriteria());
        }

        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead

        try {

            HttpPost request = new HttpPost("http://127.0.0.1:5000/predict");
            StringEntity requestEntity = new StringEntity(
                    array.toString(),
                    ContentType.APPLICATION_JSON);
            request.addHeader("Content-Type", "application/json");
            request.setEntity(requestEntity);
            HttpResponse response = httpClient.execute(request);

            String responseJSON = EntityUtils.toString(response.getEntity());
            array = new JSONArray(responseJSON);

            for(int i = 0; i < array.length();i++){
                rulesArray[i] = array.getString(i);
            }

        }catch (Exception ex) {
        }
        return rulesArray;
    }

    public int[] getTestSteps(String criteria, List<TestStep> testSteps, String[] words) {
        int[] tsIndex = new int[words.length];
        for (int x = 0; x < words.length; x++) {
            int maxScore = 0;
            int maxIndex = -999;
            int curIndex = 0;
            for (TestStep ts : testSteps) {
                UIObject uiObj = ts.getUiObject();
                ObjectMapper oMapper = new ObjectMapper();
                Map<String, String> map = oMapper.convertValue(uiObj, Map.class);
                Iterator<Map.Entry<String, String>> objIt = map.entrySet().iterator();
                int score = 0;
                while (objIt.hasNext()) {
                    Map.Entry<String, String> obj = objIt.next();
                    if(obj.getKey().equalsIgnoreCase("id")) continue;
//                    if (obj.getValue().equalsIgnoreCase(words[x])) {
//                        score += 1;
//                    }
                    score += noOfSimilarCharacters(words[x],obj.getValue());
                }
                if (maxScore < score) {
                    maxScore = score;
                    maxIndex = curIndex;
                }
                curIndex++;
            }
            if(maxIndex != -999){
                tsIndex[x] = maxIndex;
            }
        }
        return tsIndex;
    }

    private int noOfSimilarCharacters(String element, String attr){
        int count = 0;
        for(int i = 0; i < element.length() && i < attr.length(); i++) {
            if(element.charAt(i) == attr.charAt(i)){
                count++;
            }
        }
        return count;
    }

    private String[] criteriaElements(String criteria) {
        String[] words = null;
                JSONArray array = new JSONArray();
        array.put(criteria);

        HttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead

        try {

            HttpPost request = new HttpPost("http://127.0.0.1:5000/nlp/get_elements");
//            StringEntity params = new StringEntity(criteria);
            StringEntity requestEntity = new StringEntity(
                    array.toString(),
                    ContentType.APPLICATION_JSON);
            request.addHeader("Content-Type", "application/json");
            request.setEntity(requestEntity);
            HttpResponse response = httpClient.execute(request);

            String responseJSON = EntityUtils.toString(response.getEntity());
            array = new JSONArray(responseJSON);
            words = new String[array.length()];
            for(int i = 0; i < array.length();i++){
                words[i] = array.getString(i);
            }

        }catch (Exception ex) {
        }

        return words;
    }

}
