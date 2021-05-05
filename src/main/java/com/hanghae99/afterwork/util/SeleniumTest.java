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
import org.springframework.stereotype.Component;
import java.net.URLDecoder;

import javax.transaction.Transactional;
import java.util.*;

@Component
public class SeleniumTest implements ApplicationRunner {

    public static final String WEB_DRIVER_ID = "webdriver.chrome.driver"; // 드라이버 ID
    public static final String WEB_DRIVER_PATH = "C:\\Users\\Jason\\Downloads\\chromedriver.exe"; // 드라이버 경로
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final TalingMacro talingMacro;

    public SeleniumTest(ProductRepository productRepository, CategoryRepository categoryRepository, TalingMacro talingMacro){
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.talingMacro = talingMacro;
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
        //        for h2 checking purpose
//        String name = null;
//        for(int i = 0; i < 6; i++){
//            if(i == 0) name = "운동/건강";
//            if(i == 1) name = "요리";
//            if(i == 2) name = "아트";
//            if(i == 3) name = "교육";
//            if(i == 4) name = "공예";
//            if(i == 5) name = "음악";
//            categoryRepository.save(
//                    Category.builder()
//                            .name(name)
//                            .build()
//            );
//        }

//        hobbyful_crawl(options);
//        mochaclass_crawl(options);
//        SeleniumListResponse infoList = talingMacro.sorted();
//        taling_crawl(options, infoList);
    }

    @Transactional
    public void hobbyful_crawl(ChromeOptions options){
        //위에서 설정한 옵션들 담은 드라이버 객체 생성
        //옵션을 설정하지 않았을 때에는 생략 가능하다.seongbinko-springboot-webservice.cdpzofsjdpj2.ap-northeast-2.rds.amazonaws.com
        //WebDriver 객체가 곧 하나의 브라우저 창이라 생각한다.
        WebDriver driver = new ChromeDriver(options);
        //이동을 원하는 url
        String[] moveCategoryName = {"/embroidery", "/macrame", "/drawing", "/digital-drawing", "/knitting", "/ratan", "/leather"
        , "/soap-candle", "/jewelry-neonsign", "/calligraphy", "/kids"};
        int moveCategory = 0;
        while(moveCategory < moveCategoryName.length) {
            String url = "https://hobbyful.co.kr/list/class" + moveCategoryName[moveCategory];
            //webDriver를 해당 url로 이동한다.
            driver.get(url);
            String category_temp = null;

            if (moveCategory == 0) category_temp = "공예";
            else if (moveCategory == 1) category_temp = "공예";
            else if (moveCategory == 2) category_temp = "아트";
            else if (moveCategory == 3) category_temp = "아트";
            else if (moveCategory == 4) category_temp = "공예";
            else if (moveCategory == 5) category_temp = "공예";
            else if (moveCategory == 6) category_temp = "공예";
            else if (moveCategory == 7) category_temp = "공예";
            else if (moveCategory == 8) category_temp = "공예";
            else if (moveCategory == 9) category_temp = "아트";
            else if (moveCategory == 10) category_temp = "아트";

            //브라우저 이동시 생기는 로드시간을 기다린다.
            //HTTP 응답속도보다 자바의 컴파일 속도가 더 빠르기 때문에 임의적으로 1초를 대기한다.
//        class = "nav"인 모든 태그를 가진 WebElement리스트를 받아온다.
//        WebElement는 html의 태그를 가지는 클래스이다.
            final List<WebElement> base = driver.findElements(By.className("class-list"));
            int size = base.size();

            for (int i = 0; i < size; i++) {
                final List<WebElement> img = driver.findElements(By.className("class-list-thumb"));
                final List<WebElement> cont = driver.findElements(By.className("class-list-cont"));
                final List<WebElement> base2 = driver.findElements(By.className("class-list"));
                String imgUrl = img.get(i).findElement(By.tagName("img")).getAttribute("src");
                String title = cont.get(i).findElement(By.className("class-list-name")).getText();
                String author = cont.get(i).findElement(By.className("class-list-lecturer-name")).getText();
                String price_temp = cont.get(i).findElement(By.className("class-list-price")).getText();
                String price_info = price_temp;
                if (price_temp.contains("월")) {
                    price_temp = price_temp.replace("월", "");
                    int index = price_temp.indexOf("원");
                    price_temp = price_temp.substring(0, index);
                    price_temp = price_temp.replace(",", "");
                } else {
                    price_temp = price_temp.replace(" ", "");
                    price_temp = price_temp.replace(",", "");
                    price_temp = price_temp.replace("원", "");
                }
                int price = Integer.parseInt(price_temp);

                if (price_info.contains("개월")) {
                    price_info = price_info.replace("월", "");
                    int monthly_digit = (price_info.indexOf("개")) - 1;
                    price_info = price_info.replace(" ", "");
                    int indexOfWon = price_info.indexOf("원");
                    price_info = price_info.substring(0, indexOfWon + 1);
                    String additionTo = "/월 x ";
                    price_info = price_info + additionTo + monthly_digit;
                }

                boolean isOnline = false;
                String status = "Y";
                String siteName = "Hobbyful";
                String siteUrl = base2.get(i).findElement(By.tagName("a")).getAttribute("href");
                try{
                    siteUrl = URLDecoder.decode(siteUrl, "UTF-8");
                }catch(Exception e){
                    e.printStackTrace();
                }

                Category category = categoryRepository.findByName(category_temp).get();

                Product product = Product.builder()
                        .title(title)
                        .price(price)
                        .priceInfo(price_info)
                        .author(author)
                        .imgUrl(imgUrl)
                        .isOnline(isOnline)
                        .status(status)
                        .siteName(siteName)
                        .siteUrl(siteUrl)
                        .category(category)
                        .build();
                productRepository.save(product);
            }

            moveCategory++;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
    public void mochaclass_crawl(ChromeOptions options){
        WebDriver driver = new ChromeDriver();
        String[] moveCategoryName = {"핸드메이드·수공예", "쿠킹+클래스", "플라워+레슨", "드로잉", "음악", "요가·필라테스", "레져·스포츠", "자기계발", "Live+클래스"};

        int moveCategory = 0;
        while(moveCategory < moveCategoryName.length) {
            String category_temp = null;
            if(moveCategory == 0) category_temp = "공예";
            else if(moveCategory == 1) category_temp = "요리";
            else if(moveCategory == 2) category_temp = "아트";
            else if(moveCategory == 3) category_temp = "아트";
            else if(moveCategory == 4) category_temp = "음악";
            else if(moveCategory == 5) category_temp = "운동/건강";
            else if(moveCategory == 6) category_temp = "운동/건강";
            else if(moveCategory == 7) category_temp = "교육";
            else if(moveCategory == 8) category_temp = "교육";

            String url = "https://mochaclass.com/search?keyword=&location="+ "&category="+moveCategoryName[moveCategory];
            driver.get(url);

            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                WebElement base = null;
                try{
                    base = driver.findElement(By.className("MuiGrid-root"));
                }catch(Exception e){
                    break;
                }
                final List<WebElement> base2 = base.findElements(By.tagName("a"));
                final WebElement multiPage_base = driver.findElement(By.className("MuiPagination-ul"));
                final List<WebElement> multiPage = multiPage_base.findElements(By.tagName("li"));
                final String nextPage = multiPage.get(multiPage.size() - 1).findElement(By.tagName("button")).getAttribute("class");
                int size = base2.size();
                for (int i = 0; i < size; i++) {
                    final List<WebElement> desc = base2.get(i).findElements(By.tagName("p"));
                    String imgUrl = base2.get(i).findElement(By.tagName("img")).getAttribute("src");
                    if(imgUrl.length() > 255){
                        try{
                            imgUrl = URLDecoder.decode(imgUrl, "UTF-8");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        int front_index = imgUrl.indexOf("/images") + 7;
                        String front_url = imgUrl.substring(0, front_index);
                        String decode_url = imgUrl.substring(front_index, imgUrl.length());
                        String[] splitUrl = decode_url.split("\\?");
                        splitUrl[0] = splitUrl[0].replace(" ", "%20");
                        splitUrl[0] = splitUrl[0].replace("/", "%2F");
                        splitUrl[0] = splitUrl[0].replace("+", "%2B");
                        imgUrl = front_url + splitUrl[0] + "?" + splitUrl[1];
                    }
                    String title = desc.get(1).getText();
                    String location = desc.get(2).getText();
                    String price_temp = desc.get(3).getText();
                    String price_info = price_temp;
                    int price = 0;
                    if(price_temp.contains("문의")){
                        price = 0;
                    }else{
                        if(price_temp.contains("%")){
                            price_info = desc.get(5).getText();
                            price_temp = desc.get(5).getText();
                            price_temp = price_temp.replace(",", "");
                            price_temp = price_temp.replace("원", "");
                            price = Integer.parseInt(price_temp);
                        } else if(!price_temp.contains("%")){
                            price_temp = price_temp.replace(",", "");
                            price_temp = price_temp.replace("원", "");
                            price = Integer.parseInt(price_temp);
                        }
                    }
                    String siteName = "Mochaclass";
                    boolean isOnline = false;
                    if(moveCategory == moveCategoryName.length-1 || moveCategory == moveCategoryName.length-2){
                        isOnline = true;
                    }
                    String status = "Y";
                    String siteUrl = base2.get(i).getAttribute("href");
                    Category category = categoryRepository.findByName(category_temp).get();
                    Product product = Product.builder()
                            .title(title)
                            .price(price)
                            .priceInfo(price_info)
                            .imgUrl(imgUrl)
                            .location(location)
                            .isOnline(isOnline)
                            .status(status)
                            .siteName(siteName)
                            .siteUrl(siteUrl)
                            .category(category)
                            .build();
                    productRepository.save(product);
                    }
                    if (nextPage.contains("disabled")) {
                        break;
                    } else {
                        multiPage.get(multiPage.size() - 1).click();
                    }
            }
            moveCategory++;
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

    int totMinutes = 0;
    int seconds = 0;

    Timer timer = new Timer();
    TimerTask task = new TimerTask(){
      public void run(){
          System.out.println(totMinutes + ":" + seconds);
          seconds++;
          if(seconds == 60){
              totMinutes++;
              seconds = 0;
          }
      }
    };

    public void start(){
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    @Transactional
    public void taling_crawl(ChromeOptions options, SeleniumListResponse infoList){
        int productCount = 0;
        WebDriver driver = new ChromeDriver(options);
        List<CategorySort> cateList = infoList.getCateList();
        List<MainRegionSort> mainRegionList = infoList.getMainRegionList();
        int regionSize = 0;
        int pageCount = 1;
        int cateListCount = 0;
        int regionLayerCnt = 1;
        int mainRegionListNum = 0;
        int regionArrayCnt = 0;
        String url = "https://taling.me/Home/Search/?page="+pageCount+"&cateMain=&cateSub="+cateList.get(0).getCategoryNum()+"&region=&orderIdx=&query=&code=&org=&day=&time=&tType=&region=&regionMain=";
        driver.get(url);
//        start();
        while(true) {
            while (true) {
                while (true) {
                    String[] regionArray = null;
                    WebElement right = driver.findElement(By.className("right"));
                    List<WebElement> regionSubLayers = right.findElements(By.tagName("select"));
                    List<WebElement> regionLayer = regionSubLayers.get(regionLayerCnt).findElements(By.tagName("option"));
                    String regionUrl = regionLayer.get(0).getAttribute("value");
                    String regionName = null;
                    regionSize = regionSubLayers.size();
                    if(regionLayerCnt == 0){
                        url = "https://taling.me/Home/Search/?page=" + pageCount + "&cateMain=&cateSub=" + cateList.get(cateListCount).getCategoryNum() +
                                "&region=&orderIdx=&query=&code=&org=&day=&time=&region=" + regionUrl
                                + "&regionMain=";
                        regionLayerCnt = 1;
                    }else{
                        url = "https://taling.me/Home/Search/?page=" + pageCount + "&cateMain=&cateSub=" + cateList.get(cateListCount).getCategoryNum() +
                                "&region=&orderIdx=&query=&code=&org=&day=&time=&region=" + regionUrl
                                + "&regionMain=" + mainRegionListNum;
                    }
                    driver.get(url);
                    right = driver.findElement(By.className("right"));
                    regionSubLayers = right.findElements(By.tagName("select"));
                    regionLayer = regionSubLayers.get(regionLayerCnt).findElements(By.tagName("option"));
                    regionName = regionLayer.get(0).getText();

                    List<WebElement> regionSubLayer2 = regionSubLayers;
                    List<WebElement> regionLayer2 = regionSubLayer2.get(0).findElements(By.tagName("option"));
                    regionArray = new String[regionSubLayers.size() - 1];
                    for (int i = 1; i < regionSubLayer2.size(); i++) {
                        regionArray[i - 1] = regionLayer2.get(i).getText();
                    }

                    for (int i = 0; i < mainRegionList.size(); i++) {
                        if (regionArray[regionArrayCnt].contains(mainRegionList.get(i).getMainRegionLabel())) {
                            mainRegionListNum = mainRegionList.get(i).getMainRegionNum();
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final WebElement base = driver.findElement(By.className("cont2"));
                    final List<WebElement> product_base = base.findElements(By.className("cont2_class"));
                    int size = product_base.size();
                    for (int i = 0; i < size; i++) {
                        //                Category
                        String category_temp = cateList.get(cateListCount).getCategoryLabel();
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

                        for (int j = 0; j < mainRegionList.size(); j++) {
                            if (mainRegionListNum == mainRegionList.get(j).getMainRegionNum()) {
                                sb.append(mainRegionList.get(j).getMainRegionLabel());
                                sb.append(",");
                                break;
                            }
                        }

                        if (location_temp.contains("온라인") || location_temp.contains("온/오프라인") || location_temp.contains("Live")
                                || location_temp.contains("live")) {
                            isOnline = true;
                            arr = location_temp.split("온라인 Live|온/오프라인|지역없음|지역 없음|,");
                            int cnt = 0;
                            for (int j = 0; j < arr.length; j++) {
                                if (arr[j].equals("") || arr[j].equals(" ") || arr[j].equals("  ")) continue;
                                else {
                                    if (cnt > 0) {
                                        sb.append(",");
                                    }
                                    sb.append(arr[j]);
                                    cnt++;
                                }
                            }
                            String convertSb = sb.toString();
                            char replace = ',';
                            char sb_last = convertSb.charAt(sb.length()-1);
                            if(sb_last == replace){
                                convertSb = convertSb.substring(0, convertSb.length()-1);
                            }
                            location = convertSb;
                        } else if (location_temp.contains("지역 없음") || location_temp.contains("지억없음")) {
                            arr = location_temp.split("지역없음|지역 없음|,");
                            int cnt = 0;
                            for (int j = 0; j < arr.length; j++) {
                                if (arr[j].equals("") || arr[j].equals(" ") || arr[j].equals("  ")) continue;
                                else {
                                    if (cnt > 0) {
                                        sb.append(",");
                                    }
                                    sb.append(arr[j]);
                                    cnt++;
                                }
                            }
                            if (sb.length() == 3) {
                                location = sb.substring(0, sb.length() - 1);
                            } else {
                                location = sb.toString();
                            }
                        } else {
                            sb.append(location_temp);
                            location = sb.toString();
                        }
                        //                Price
                        String price_temp = product_base.get(i).findElement(By.className("price2")).getText();
                        String price_info = price_temp;
                        int price = 0;

                        if (price_temp.contains("시간")) {
                            int dash_pos = 0;
                            price_info = price_info.replace("￦", "");
                            dash_pos = price_info.indexOf("/");
                            price_info = price_info.substring(0, dash_pos);
                            price_info += "원/시간";

                            price_temp = price_temp.replace("￦", "");
                            price_temp = price_temp.replace(",", "");
                            price_temp = price_temp.replace("/시간", "");
                            price = Integer.parseInt(price_temp);
                        } else {
                            price_info = price_info.replace("￦", "");
                            price_info += "원";

                            price_temp = price_temp.replace("￦", "");
                            price_temp = price_temp.replace(",", "");
                            price = Integer.parseInt(price_temp);
                        }

                        //                Popularity
                        String popularity_temp = product_base.get(i).findElement(By.className("d_day")).getText();
                        int popularity = 0;
                        if (popularity_temp.contains("명")) {
                            popularity = Integer.parseInt(popularity_temp.substring(0, popularity_temp.indexOf("명")));
                        } else if (popularity_temp.contains("D")) {
                            try{
                                popularity_temp = product_base.get(i).findElement(By.className("review")).getText();
                                popularity = Integer.parseInt(popularity_temp.substring(1, popularity_temp.length() - 1));
                            }catch(Exception e){
                                popularity = 0;
                            }
                        }
                        //                Site Url/Name
                        String siteUrl = product_base.get(i).findElement(By.tagName("a")).getAttribute("href");
                        String siteName = "Taling";
                        //                Status
                        String status = null;
                        try {
                            WebElement find = product_base.get(i).findElement(By.className("soldoutbox"));
                            status = "N";
                        } catch (Exception e) {
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
                        productCount+=1;
                    }
                    if (size == 0) {
                        regionLayerCnt++;
                        regionArrayCnt++;
                        pageCount = 1;
                        break;
                    } else {
                        pageCount++;
                    }
                }
                if (regionLayerCnt == regionSize) {
                    regionLayerCnt = 0;
                    break;
                }
            }
            cateListCount++;
            regionArrayCnt = 0;
            if(cateListCount == cateList.size()){
                break;
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
