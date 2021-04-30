package com.hanghae99.afterwork.util;


import com.hanghae99.afterwork.model.Category;
import com.hanghae99.afterwork.model.Product;
import com.hanghae99.afterwork.repository.CategoryRepository;
import com.hanghae99.afterwork.repository.ProductRepository;
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

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public SeleniumMybiskit(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //드라이버 설정
        try {
            System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        mybiskit();
    }

    // mybiskit 크롤링
    public void mybiskit() throws Exception{
        // 크롬 설정을 담은 객체 생성
        ChromeOptions options = new ChromeOptions();
        // 브라우저가 눈에 보이지 않고 내부적으로 돈다.
        // 설정하지 않을 시 실제 크롬 창이 생성되고, 어떤 순서로 진행되는지 확인할 수 있다.

        options.addArguments("headless");

        WebDriver driver = new ChromeDriver(options);//

        String strUrl = "https://www.mybiskit.com/lecture";

        //webDriver를 해당 url로 이동한다.
        driver.get(strUrl);

        //브라우저 이동시 생기는 로드시간을 기다린다.
        //HTTP 응답속도보다 자바의 컴파일 속도가 더 빠르기 때문에 임의적으로 1초를 대기한다.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 무한 스크롤
        InfiniteScroll(driver);

        List<WebElement> elList = driver.findElements(By.className("class_summary"));

        int count = 0;

        for (int i = 0; i < elList.size(); i++) {
            String strTitle = null;
            String strAuthor = null;
            int intPrice = 0;
            String strPrice = null;
            String strPriceInfo = "사전예약";
            String strImgUrl = null;
            String strSiteUrl = null;
            String strSiteName = "mybiskit";
            String strCategory = null;
            String strStatus = "Y";
            int intPopularity = 0;
            boolean isOnline = true;

            strTitle = elList.get(i).findElement(By.className("class_tit")).getText();
            intPopularity = Integer.parseInt(elList.get(i).findElement(By.className("cnt")).getText());

            // 가격이 없는 경우 예외처리
            try {
//                strPrice = elList.get(i).findElement(By.className("compo_price")).findElement(By.className("num")).getText();
                strPrice = elList.get(i).findElement(By.className("real_price")).findElement(By.className("num")).getText();
                strPriceInfo = strPrice + "원";
                intPrice = PriceStringToInt(strPrice);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                strPriceInfo += "/월 x " + elList.get(i).findElement(By.className("installment")).findElement(By.className("num")).getText();
            } catch (Exception e) {
                e.printStackTrace();
            }

            strImgUrl = elList.get(i).findElement(By.className("fixed")).findElement(By.tagName("img")).getAttribute("data-src");

            // 연결 사이트 mybiskit에 맞춰넣음
            strSiteUrl = "https://www.mybiskit.com/lecture/" + strTitle.replace(" ", "-") + "-" + strImgUrl.split("/")[4];
            strCategory = elList.get(i).findElement(By.className("ctag")).getText();

            System.out.println("strTitle = " + strTitle);
            System.out.println("intPopularity = " + intPopularity);
            System.out.println("strPrice = " + strPrice);
            System.out.println("strPriceInfo = " + strPriceInfo);
            System.out.println("strImgUrl = " + strImgUrl);
            System.out.println("isOnline = " + isOnline);
            System.out.println("siteUrl = " + strSiteUrl);
            System.out.println("siteName = " + strSiteName);

            //카테고리 변환
            if (strCategory.contains("운동")) {
                strCategory = "운동/건강";
            } else if (strCategory.contains("부업")) {
                strCategory = "교육";
            } else if (strCategory.contains("자수")
                    || strCategory.contains("비누")) {
                strCategory = "공예";
            } else if (strCategory.contains("디지털디자인")
                    || strCategory.contains("캘리")
                    || strCategory.contains("사진")) {
                strCategory = "아트";
            } else if (strCategory.contains("음악")) {
                strCategory = "음악";
            } else if (strCategory.contains("요리")) {
                strCategory = "요리";
            } else if (strCategory.contains("원데이")) {

            } else {
                System.out.println("No Category");
            }

            Category category = categoryRepository.findByName(strCategory).orElse(null);

            Product product = Product.builder()
                    .title(strTitle)
                    .popularity(intPopularity)
                    .price(intPrice)
                    .priceInfo(strPriceInfo)
                    .imgUrl(strImgUrl)
                    .isOnline(isOnline)
                    .siteUrl(strSiteUrl)
                    .siteName(strSiteName)
                    .status(strStatus)
                    .category(category)
                    .build();

            productRepository.save(product);

            count++;

            if (count == 100)
            {
                break;
            }
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

    public void InfiniteScroll(WebDriver driver){
        // 현재 켜져있는 drvier 무한스크롤 제일 밑으로 내려가기 위한 코드
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        // 현재 스크롤 높이
        Object last_height = jse.executeScript("return document.body.scrollHeight");
        while (true) {
            jse.executeScript("window.scrollTo(0,document.body.scrollHeight)");

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            jse.executeScript("window.scrollTo(0,document.body.scrollHeight - 50)");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Object new_height = jse.executeScript("return document.body.scrollHeight");

            System.out.println("last_height = " + last_height);
            System.out.println("new_height = " + new_height);

            // 스크롤을 내렸음에도 불구하고 이전과 같다면 제일 밑으로 확인된다.
            if (new_height.toString().equals(last_height.toString())) {
                break;
            }

            // 현재 스크롤 높이
            last_height = new_height;
        }
    }

    public int PriceStringToInt(String price){
        price = price.replace(" ", "");
        price = price.replace(",", "");
        price = price.replace("원", "");

        return Integer.parseInt(price);
    }

}

