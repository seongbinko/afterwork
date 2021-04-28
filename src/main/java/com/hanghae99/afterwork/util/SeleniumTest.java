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
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

@Component
public class SeleniumTest implements ApplicationRunner {

    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver"; // 드라이버 ID
    public static final String WEB_DRIVER_PATH = "C:\\Users\\Jason\\Downloads\\chromedriver.exe"; // 드라이버 경로
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public SeleniumTest(ProductRepository productRepository, CategoryRepository categoryRepository){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

//    @Scheduled(fixedDelay = 1000)
//    public void keepTrack(){
//        System.out.println("DB update till t - " + new Date());
//    }

//    @Scheduled(cron = "*/5 * * * * *")
    public void run(ApplicationArguments args) throws Exception{
        //드라이버 설정
        try {
            System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        } catch (Exception e){
            e.printStackTrace();
        }
        // 크롬 설정을 담은 객체 생성
        ChromeOptions options = new ChromeOptions();
        // 브라우저가 눈에 보이지 않고 내부적으로 돈다.
        // 설정하지 않을 시 실제 크롬 창이 생성되고, 어떤 순서로 진행되는지 확인할 수 있다.
        options.addArguments("headless");

//        hobbyful_crawl();
//        mochaclass_crawl();
        taling_crawl();
    }

    public void hobbyful_crawl(){
        //위에서 설정한 옵션들 담은 드라이버 객체 생성
        //옵션을 설정하지 않았을 때에는 생략 가능하다.
        //WebDriver 객체가 곧 하나의 브라우저 창이라 생각한다.
        WebDriver driver = new ChromeDriver();
        //이동을 원하는 url
        String url = "https://hobbyful.co.kr/list/class";
        //webDriver를 해당 url로 이동한다.
        driver.get(url);
        //브라우저 이동시 생기는 로드시간을 기다린다.
        //HTTP 응답속도보다 자바의 컴파일 속도가 더 빠르기 때문에 임의적으로 1초를 대기한다.
//        class = "nav"인 모든 태그를 가진 WebElement리스트를 받아온다.
//        WebElement는 html의 태그를 가지는 클래스이다.
        final List<WebElement> products = driver.findElements(By.className("class-list"));
        int size = products.size();

        for(int i = 0; i < size; i++){
            final List<WebElement> img = driver.findElements(By.className("class-list-thumb"));
            final List<WebElement> cont = driver.findElements(By.className("class-list-cont"));
            final List<WebElement> product = driver.findElements(By.className("class-list"));
            String imgUrl = img.get(i).findElement(By.tagName("img")).getAttribute("src");
            String title = cont.get(i).findElement(By.className("class-list-name")).getText();
            String author = cont.get(i).findElement(By.className("class-list-lecturer-name")).getText();
            String price = cont.get(i).findElement(By.className("class-list-price")).getText();
            String site = product.get(i).findElement(By.tagName("a")).getAttribute("href");
            System.out.println(imgUrl + " " + title + " " + author + " " + price + " " + site);
            i++;
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public void mochaclass_crawl(){
        WebDriver driver = new ChromeDriver();
        String url = "https://mochaclass.com/search?keyword=&location=%EC%A0%84%EC%B2%B4&category=%EC%A0%84%EC%B2%B4";
        driver.get(url);
        while(true) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final WebElement base = driver.findElement(By.className("MuiGrid-root"));
            final List<WebElement> product = base.findElements(By.tagName("a"));
            final WebElement multiPage_base = driver.findElement(By.className("MuiPagination-ul"));
            final List<WebElement> multiPage = multiPage_base.findElements(By.tagName("li"));
            final String nextPage = multiPage.get(multiPage.size()-1).findElement(By.tagName("button")).getAttribute("class");
            int size = product.size();
            for (int i = 0; i < size; i++) {
                final List<WebElement> desc = product.get(i).findElements(By.tagName("p"));
                String imgUrl = product.get(i).findElement(By.tagName("img")).getAttribute("src");
                String category = desc.get(0).getText();
                String title = desc.get(1).getText();
                String location = desc.get(2).getText();
                String price = desc.get(3).getText();
                String site = product.get(i).getAttribute("href");
                System.out.println(imgUrl + " " + title + " " + category + " " + location + " " + price + " " + site);
            }
            if(nextPage.contains("disabled")){
                System.out.println("bot exit");
                break;
            }else{
                multiPage.get(multiPage.size()-1).click();
                System.out.println("clicked!");
            }
        }
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


    @Transactional
    public void taling_crawl(){
        WebDriver driver = new ChromeDriver();
        String category_temp = "운동/건강";
        int pageCount = 1;

        while(true) {
            String url = "https://taling.me/Home/Search/?page="+pageCount+"&cateMain=&cateSub=27&region=1,7,4,5,9,2,6,14,10,3,22,17,15,21,11,122,123,24,109,19,16,13,135,77,136,25,112,129,8&orderIdx=&query=&code=&org=&day=&time=&tType=&region=1,7,4,5,9,2,6,14,10,3,22,17,15,21,11,122,123,24,109,19,16,13,135,77,136,25,112,129,8&regionMain=0";
            driver.get(url);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final WebElement base = driver.findElement(By.className("cont2"));
            final List<WebElement> product_base = base.findElements(By.className("cont2_class"));
            int size = product_base.size();

            for (int i = 0; i < size; i++) {
//                Image Url
                String imgUrl_temp = product_base.get(i).findElement(By.className("img")).getAttribute("style");
                String http = "https://";
                int imgUrl_size = imgUrl_temp.length();
                String imgUrl = http + imgUrl_temp.substring(25, (imgUrl_size - 1) - 2);
//                Author
                String author = product_base.get(i).findElement(By.className("name")).getText();
//                Title
                String title = product_base.get(i).findElement(By.className("title")).getText();
//                Location
                String location_temp = product_base.get(i).findElement(By.className("location")).getText();
                String location = null;
                boolean isOnline = false;
                String[] arr = null;
                StringBuilder sb = new StringBuilder();
                sb.append("서울,");
                if (location_temp.contains("온라인") || location_temp.contains("온/오프라인") || location_temp.contains("Live")
                        || location_temp.contains("live")) {
                    isOnline = true;
                    arr = location_temp.split("온라인 Live|온/오프라인|지역없음|지역 없음|,");
                    int cnt = 0;
                    for(int j = 0; j < arr.length; j++){
                        if(arr[j].equals("") || arr[j].equals(" ") || arr[j].equals("  ")) continue;
                        else {
                            if(cnt > 0){
                                sb.append(",");
                            }
                            sb.append(arr[j]);
                            cnt++;
                        }
                    }
                    location = sb.toString();
                }else if(location_temp.contains("지역 없음") || location_temp.contains("지억없음")){
                    arr = location_temp.split("지역없음|지역 없음|,");
                    int cnt = 0;
                    for(int j = 0; j < arr.length; j++){
                        if(arr[j].equals("") || arr[j].equals(" ") || arr[j].equals("  ")) continue;
                        else {
                            if(cnt > 0){
                                sb.append(",");
                            }
                            sb.append(arr[j]);
                            cnt++;
                        }
                    }
                    if(sb.length() == 3){
                        location = sb.substring(0, sb.length()-1);
                    }else{
                        location = sb.toString();
                    }
                }else{
                    sb.append(location_temp);
                    location = sb.toString();
                }
//                Price
                String price_temp = product_base.get(i).findElement(By.className("price2")).getText();
                String price_info = price_temp;
                StringBuilder sb2 = new StringBuilder();
                String[] arr2 = null;
                int price = 0;
                if (price_temp.contains("시간")) {
                    price_temp = price_temp.substring(1, price_temp.length() - 3);
                } else {
                    price_temp = price_temp.substring(1);
                }
                arr2 = price_temp.split(",");
                for(int j = 0; j < arr2.length; j++){
                    sb2.append(arr2[j]);
                }
                price = Integer.parseInt(String.valueOf(sb2));
                System.out.println(price);

//                Popularity
                String popularity_temp = product_base.get(i).findElement(By.className("d_day")).getText();
                int popularity = 0;
                if(popularity_temp.contains("명")){
                    popularity = Integer.parseInt(popularity_temp.substring(0, popularity_temp.indexOf("명")));
                }else if(popularity_temp.contains("D")){
                    popularity_temp = product_base.get(i).findElement(By.className("review")).getText();
                    popularity = Integer.parseInt(popularity_temp.substring(1, popularity_temp.length()-1));
                }
//                Site Url/Name
                String siteUrl = product_base.get(i).findElement(By.tagName("a")).getAttribute("href");
                String siteName = "Taling";
//                Status
                String status = null;
                try{
                    WebElement find = product_base.get(i).findElement(By.className("soldoutbox"));
                    status = "N";
                }catch(Exception e){
                    status = "Y";
                }

                Category category = categoryRepository.findByName(category_temp).get();


                Product product = Product.builder()
                        .title(title)
                        .price(price)
                        .priceInfo(price_info)
                        .author(author)
                        .imgUrl(imgUrl)
                        .isOnline(isOnline)
                        .location(location)
                        .popularity(popularity)
                        .status(status)
                        .siteName(siteName)
                        .siteUrl(siteUrl)
                        .category(category)
                        .build();
                productRepository.save(product);

            }
            if(size < 15){
                break;
            }else{
                pageCount++;
            }
        }
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
