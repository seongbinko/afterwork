package com.hanghae99.afterwork.util;

import com.hanghae99.afterwork.model.Category;
import com.hanghae99.afterwork.model.Product;
import com.hanghae99.afterwork.repository.CategoryRepository;
import com.hanghae99.afterwork.repository.ProductRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;

@Component
public class SeleniumHobbyinthebox implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(SeleniumHobbyinthebox.class);

    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver"; // 드라이버 ID
    public static final String WEB_DRIVER_PATH = "chromedriver.exe"; // 드라이버 경로

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public SeleniumHobbyinthebox(ProductRepository productRepository, CategoryRepository categoryRepository) {
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

//        hobbyinthebox();
    }

    public void hobbyinthebox() {
        // 크롬 설정을 담은 객체 생성
        ChromeOptions options = new ChromeOptions();
        // 브라우저가 눈에 보이지 않고 내부적으로 돈다.
        // 설정하지 않을 시 실제 크롬 창이 생성되고, 어떤 순서로 진행되는지 확인할 수 있다.

        options.addArguments("headless");

        WebDriver driver = new ChromeDriver(options);//

        List<HobyintheboxCategory> enumValues = Arrays.asList(HobyintheboxCategory.values());

        for (int i = 0; i < enumValues.size(); i++) {
            int pageNum = 1;
            while (true) {
                String krCategory = enumValues.get(i).getKrCategory();
                int numCategory = enumValues.get(i).getNum();

                String strUrl = "https://hobbyinthebox.co.kr/category/" + krCategory + "/" + numCategory + "/?page=" + pageNum;

                //webDriver를 해당 url로 이동한다.
                driver.get(strUrl);

                //브라우저 이동시 생기는 로드시간을 기다린다.
                //HTTP 응답속도보다 자바의 컴파일 속도가 더 빠르기 때문에 임의적으로 1초를 대기한다.
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                List<WebElement> elList = driver.findElements(By.className("sp-product-item"));

                if (elList.isEmpty()) {
                    break;
                }

                for (int j = 0; j < elList.size(); j++) {
                    String strTitle = null;
                    String strAuthor = null;
                    int intPrice = 0;
                    String strPrice = null;
                    String strPriceInfo = null;
                    String strImgUrl = null;
                    String strSiteUrl = null;
                    String strSiteName = "hobyinthebox";
                    String strCategory = null;
                    String strStatus = "Y";
                    int intPopularity = 0;
                    boolean isOnline = true;

                    strTitle = elList.get(j).findElement(By.className("sp-product-item-thumb-origin")).findElement(By.tagName("img")).getAttribute("alt");

                    // 가격이 없는 경우 예외처리
                    try {
                        List<WebElement> desc = elList.get(j).findElement(By.className("sp-product-description")).findElements(By.tagName("div"));

                        // 사이즈가 3일경우 즉 할인가격이 없을 경우
                        if (desc.size() == 3) {
                            strPrice = desc.get(0).getText();
                            strPriceInfo = desc.get(0).getText();
                            strAuthor = desc.get(1).getText();
                        }
                        //설명사이즈가 3이상인 경우 즉 할인 가격인 경우
                        else {
                            strPrice = desc.get(1).getText();
                            strPriceInfo = desc.get(1).getText();
                            strAuthor = desc.get(2).getText();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    intPrice = PriceStringToInt(strPrice);

                    strImgUrl = elList.get(j).findElement(By.className("sp-product-item-thumb-origin")).findElement(By.tagName("img")).getAttribute("src");

                    // 연결 사이트 mybiskit에 맞춰넣음
                    strSiteUrl = elList.get(j).findElement(By.className("sp-product-item-thumb")).findElement(By.tagName("a")).getAttribute("href");
                    try {
                        strSiteUrl = URLDecoder.decode(strSiteUrl, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    System.out.println("strTitle = " + strTitle);
                    System.out.println("strAuthor = " + strAuthor);
                    System.out.println("intPrice = " + intPrice);
                    System.out.println("strPriceInfo = " + strPriceInfo);
                    System.out.println("strImgUrl = " + strImgUrl);
                    System.out.println("strSiteUrl = " + strSiteUrl);
                    System.out.println("strSiteName = " + strSiteName);
                    System.out.println("isOnline = " + isOnline);

                    // 카테고리 변환
                    if (krCategory.contains("운동")) {
                        strCategory = "운동/건강";
                    } else if (krCategory.contains("자수")
                            || krCategory.contains("비누")
                            || krCategory.contains("공예")
                            || krCategory.contains("가드닝")
                            || krCategory.contains("바늘")) {
                        strCategory = "공예";
                    } else if (krCategory.contains("디지털디자인")
                            || krCategory.contains("캘리")
                            || krCategory.contains("사진")
                            || krCategory.contains("아트")
                            || krCategory.contains("라이프")
                            || krCategory.contains("미술")
                            || krCategory.contains("퍼즐")) {
                        strCategory = "아트";
                    } else if (krCategory.contains("밀키트")
                            || krCategory.contains("베이킹")) {
                        strCategory = "요리";
                    } else {
                        System.out.println("No Category");
                    }
                    System.out.println("category = " + strCategory);

                    Category category = categoryRepository.findByName(strCategory).orElse(null);

                    Product product = Product.builder()
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

                    productRepository.save(product);
                }
                pageNum++;
            }
        }
    }

    public int PriceStringToInt(String price){
        price = price.replace(" ", "");
        price = price.replace(",", "");
        price = price.replace("원", "");

        return Integer.parseInt(price);
    }
}
