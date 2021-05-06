package com.hanghae99.afterwork.util;

import com.hanghae99.afterwork.model.Category;
import com.hanghae99.afterwork.model.Product;
import com.hanghae99.afterwork.repository.CategoryRepository;
import com.hanghae99.afterwork.repository.ProductRepository;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SeleniumClass101 implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(SeleniumHobbyinthebox.class);

    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver"; // 드라이버 ID
    public static final String WEB_DRIVER_PATH = "chromedriver.exe"; // 드라이버 경로

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public SeleniumClass101(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        //드라이버 설정
        try {
            System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //status 상태 Y -> N 처리
//        statusChange("class101");

//        class101();
    }

    public void class101() {
        // 크롬 설정을 담은 객체 생성
        ChromeOptions options = new ChromeOptions();
        // 브라우저가 눈에 보이지 않고 내부적으로 돈다.
        // 설정하지 않을 시 실제 크롬 창이 생성되고, 어떤 순서로 진행되는지 확인할 수 있다.

        options.addArguments("headless");

        WebDriver driver = new ChromeDriver(options);

        List<Class101Category> enumValues = Arrays.asList(Class101Category.values());
        for (int i = 0; i < enumValues.size(); i++) {

            String krCategory = enumValues.get(i).getKrCategory();
            String categoryCode = enumValues.get(i).getCategoryCode();

            String url = "https://class101.net/search?category=" + categoryCode + "&sort=likedOrder&state=sales";

            //webDriver를 해당 url로 이동한다.
            driver.get(url);

            //브라우저 이동시 생기는 로드시간을 기다린다.
            //HTTP 응답속도보다 자바의 컴파일 속도가 더 빠르기 때문에 임의적으로 1초를 대기한다.
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 무한 스크롤
            InfiniteScroll(driver);

            List<WebElement> elList = driver.findElements(By.className("sc-iNqMTl"));

            for (int j = 0; j < elList.size(); j++) {
                String strTitle = null;
                String strAuthor = null;
                int intPrice = 0;
                String strPrice = null;
                String strPriceInfo = null;
                String strImgUrl = null;
                String strSiteUrl = null;
                String strSiteName = "class101";
                String strCategory = null;
                String strStatus = "Y";
                int intPopularity = 0;
                boolean isOnline = true;

                try{
                    //제목
                    strTitle = elList.get(j).findElement(By.className("sc-bQdQlF")).getText();
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
                strAuthor = elList.get(j).findElement(By.className("dbRciT")).getText().split("・")[1];

                String strPopularity = elList.get(j).findElement(By.className("CountTag__Container-rjlblo-0")).getText();
                intPopularity = PriceStringToInt(strPopularity);

                strSiteUrl = elList.get(j).findElement(By.className("gfCFNQ")).getAttribute("href");

                try{
                    //int가격을 위한 사전작업
                    strPrice = elList.get(j).findElement(By.className("iLryzw")).getText();
                    strPriceInfo = strPrice;
                    intPrice = PriceStringToInt(strPrice.split("원")[0]);

                    //할인가 적용 가격
                    if (strPrice.contains("월"))
                    {
                        strPrice = strPrice.replace(" ","");
                        strPrice = strPrice.replace("월","");
                        strPrice = strPrice.replace("개","");
                        strPrice = strPrice.replace("(","");
                        strPrice = strPrice.replace(")","");

                        strPriceInfo = strPrice.split("원")[0] + "원/월 x " + strPrice.split("원")[1];
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                try{
                    //이미지 경로
                    strImgUrl = elList.get(j).findElement(By.tagName("picture")).findElement(By.tagName("img")).getAttribute("src");
//                    strImgUrl = elList.get(j).findElement(By.className("blDEng")).findElement(By.tagName("img")).getAttribute("src");
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }

                System.out.println("strTitle = " + strTitle);
                System.out.println("strAuthor = " + strAuthor);
                System.out.println("intPopularity = " + intPopularity);
                System.out.println("intPrice = " + intPrice);
                System.out.println("strPriceInfo = " + strPriceInfo);
                System.out.println("strImgUrl = " + strImgUrl);
                System.out.println("isOnline = " + isOnline);
                System.out.println("strSiteUrl = " + strSiteUrl);
                System.out.println("strSiteName = " + strSiteName);

                //카테고리 변환
                if (krCategory.contains("운동")) {
                    strCategory = "운동/건강";
                } else if (krCategory.contains("커리어")
                        || krCategory.contains("외국어")
                        || krCategory.contains("교육")
                        || krCategory.contains("창업")
                        || krCategory.contains("비즈니스")
                        || krCategory.contains("콘텐츠")
                        || krCategory.contains("자기계발")
                        || krCategory.contains("온라인쇼핑몰")
                        || krCategory.contains("부동산")
                        || krCategory.contains("데이터")) {
                    strCategory = "교육";
                } else if (krCategory.contains("공예")
                        || krCategory.contains("글쓰기")
                ) {
                    strCategory = "공예";
                } else if (krCategory.contains("라이프")
                        || krCategory.contains("미술")
                        || krCategory.contains("사진")
                        || krCategory.contains("디지털")
                        || krCategory.contains("영상")) {
                    strCategory = "아트";
                } else if (krCategory.contains("음악")) {
                    strCategory = "음악";
                } else if (krCategory.contains("요리")) {
                    strCategory = "요리";
                } else {
                    System.out.println("No Category");
                }
                System.out.println("category = " + strCategory);

                Category category = categoryRepository.findByName(strCategory).orElse(null);

                Product product = productRepository.findByTitleLikeAndCategory(strTitle,category).orElse(null);

                if (product == null) {
                    product = Product.builder()
                            .title(strTitle)
                            .author(strAuthor)
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
                } else {
                    product.setPopularity(intPopularity);
                    product.setPrice(intPrice);
                    product.setPriceInfo(strPriceInfo);
                    product.setImgUrl(strImgUrl);
                    product.setStatus(strStatus);
                }

                productRepository.save(product);
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

            jse.executeScript("window.scrollTo({top:document.body.scrollHeight, left:0, behavior:'smooth'})");

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            jse.executeScript("window.scrollTo({top:0, left:0, behavior:'smooth'})");

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
        price = price.replace("월", "");
        price = price.replace("(", "");
        price = price.replace(")", "");
        price = price.replace("개", "");

        return Integer.parseInt(price);
    }

    public void statusChange(String siteName) {
        List<Product> productList = productRepository.findAllBySiteName(siteName);

        for (Product product : productList) {
            product.setStatus("N");

            productRepository.save(product);
        }
    }
}
