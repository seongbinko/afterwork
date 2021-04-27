package com.hanghae99.afterwork.util;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SeleniumMybiskit implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(SeleniumMybiskit.class);

    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver"; // 드라이버 ID
    public static final String WEB_DRIVER_PATH = "chromedriver.exe"; // 드라이버 경로

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //드라이버 설정
        try {
            System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 크롬 설정을 담은 객체 생성
        ChromeOptions options = new ChromeOptions();
        // 브라우저가 눈에 보이지 않고 내부적으로 돈다.
        // 설정하지 않을 시 실제 크롬 창이 생성되고, 어떤 순서로 진행되는지 확인할 수 있다.

        options.addArguments("headless");

        mybiskit();
    }

    // mybiskit 크롤링
    public void mybiskit() throws Exception{
        WebDriver driver = new ChromeDriver();//

        String url = "https://www.mybiskit.com/lecture";

        //webDriver를 해당 url로 이동한다.
        driver.get(url);

        //브라우저 이동시 생기는 로드시간을 기다린다.
        //HTTP 응답속도보다 자바의 컴파일 속도가 더 빠르기 때문에 임의적으로 1초를 대기한다.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 현재 켜져있는 drvier 무한스크롤 제일 밑으로 내려가기 위한 코드
        JavascriptExecutor jse = (JavascriptExecutor)driver;

        // 현재 스크롤 높이
        Object last_height = jse.executeScript("return document.body.scrollHeight");
        System.out.println(last_height);
        while(true){
            jse.executeScript("window.scrollTo(0,document.body.scrollHeight)");

            Thread.sleep(1000);
            jse.executeScript("window.scrollTo(0,document.body.scrollHeight - 50)");
            Thread.sleep(1000);

            Object new_height = jse.executeScript("return document.body.scrollHeight");

            System.out.println("last_height = " + last_height);
            System.out.println("new_height = " + new_height);

            // 스크롤을 내렸음에도 불구하고 이전과 같다면 제일 밑으로 확인된다.
            if (new_height.toString().equals(last_height.toString()))
            {
                break;
            }

            // 현재 스크롤 높이
            last_height = new_height;
        }

        List<WebElement> elList = driver.findElements(By.className("class_summary"));

        for (int i = 0; i < elList.size(); i++) {
            String title = elList.get(i).findElement(By.className("class_tit")).getText();

            // 가격이 없는 경우 예외처리
            // 한달 가격으로 표시되는 상품은 코드 추가 필요
            String str_price;
            String priceInfo;
            try {
                str_price = elList.get(i).findElement(By.className("compo_price")).findElement(By.className("num")).getText();
                // 공백 제거
                str_price = str_price.replace(" ","");
                // 쉼표 제거
                str_price = str_price.replace(",","");

                priceInfo = elList.get(i).findElement(By.className("price_discount")).getText();
            } catch (Exception e) {
                e.printStackTrace();
                str_price = "0";
                priceInfo = "";
            }
            int price = Integer.parseInt(str_price);



            String img_url = elList.get(i).findElement(By.className("fixed")).findElement(By.tagName("img")).getAttribute("data-src");
            boolean isOnline = true;

            // 연결 사이트 mybiskit에 맞춰넣음
            String siteUrl = "https://www.mybiskit.com/lecture/" + title.replace(" ", "-") + "-" + img_url.split("/")[4];
            String siteName = "mybiskit";

            String category = elList.get(i).findElement(By.className("ctag")).getText();

            System.out.println("title = " + title);
            System.out.println("price = " + price);
            System.out.println("priceInfo = " + priceInfo);
            System.out.println("img_url = " + img_url);
            System.out.println("isOnline = " + isOnline);
            System.out.println("siteUrl = " + siteUrl);
            System.out.println("siteName = " + siteName);

            //카테고리 변환
            if (category.contains("운동")) {
                category = "운동/건강";
            } else if (category.contains("부업")){
                category = "교육";
            } else if (category.contains("자수") || category.contains("비누")){
                category = "공예";
            } else if (category.contains("디지털디자인") || category.contains("캘리") || category.contains("사진")){
                category = "아트";
            } else if (category.contains("음악")){
                category = "음악";
            } else if (category.contains("요리")){
                category = "요리";
            } else if (category.contains("원데이")){

            } else{
                System.out.println("No Category");
            }
            System.out.println("category = " + category);
        }

        // 크롤링이 끝났을 경우 driver 종료
        try {
            //드라이버가 null이 아니라면
            if (driver != null) {
                // 드라이버 연결 종료
                driver.close(); // 드라이버 연결해제
                // 프로세스 종료
                driver.quit();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }

}

