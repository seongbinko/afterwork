package com.hanghae99.afterwork.util;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

// (0)운동 건강: 27=PT/GX | 78=방송댄스 |  235=댄스 | 123=연기/무용 | 217=스포츠/레저 | 33=셀프케어

// (1)요리: 84=요리/베이킹| 83=커피/차/술 |

// (2)아트: 28=메이크업 | 79=사진 | 32=퍼스널컬러 | 222=취미/미술 | 232=디지털드로잉 | 76=캘리그래피 | 209=제품디자인 | 201=그래픽디자인

// (3)교육: 13=엑셀 | 188=마케팅 | 193=영상편집 | 34=웹 개발 | 41=영어회화 | 233=인문/교양 | 246=인테리어 | 214=투잡 |
// 88=반려동물 | 248=부모/육아 | 80=출판/글쓰기 | 127=사주/타로 | 103=심리상담 | 116=주식투자 | 244=부동산 | 213=금융지식
// 15=창업 | 250=마케팅2 | 239=실무역량 | 12=파워포인트 | 14=스피치 | 11=데이터분석 | 54=컴퓨터공학 | 182=작겨증/시험
// 199=영상제작 | 3=건축 | 42=중국어회화 | 43=일본어회화 | 44=어학자격증 | 51=기타 외국어

// (7)공예: 81=이색취미(공예) | 249=조향/캔들/비누 | 126=가죽/목공/도예

// (8)노래: 60=보컬 | 59=악기 | 61=작곡/디제잉

//(0)서울: 1=강남 | 4=신촌홍대 | 14=건대 | 2=사당 | 9=잠실 | 5=종로 | 19=마포 | 11=신림 | 6=영등포 |
// 7=성북 | 15=용산 | 10=왕십리 | 21=목동 | 8=혜화 | 77=강서 | 17=노원 | 22=구로 | 12=동작 | 24=은평 |
// 3=신사 | 13=회기 | 112=성수 | 18=수유 | 97=천호동 | 100=올림픽공원 | 25=미아 | 129=중구 | 16=충무로 |
// 134=상봉 | 135=삼성 | 102=한양대 | 75=마곡더랜드타워 | 103=명동 | 110=고덕 | 125=동대입구 | 122=대치
// 99=명일동 | 116=약수역 | 128=신당 | 20=정릉 | 131=월곡 | 124=옥수 | 76=셀렉티드연남 | 136=동소문

//(0)서울: 1=강남 | 4=신촌홍대 | 14=건대 | 2=사당 | 9=잠실 | 5=종로 | 19=마포 | 11=신림 | 6=영등포 |
// 7=성북 | 15=용산 | 10=왕십리 | 21=목동 | 8=혜화 | 77=강서 | 17=노원 | 22=구로 | 12=동작 | 24=은평 |
// 3=신사 | 13=회기 | 112=성수 | 18=수유 | 97=천호동 | 100=올림픽공원 | 25=미아 | 129=중구 | 16=충무로 |
// 134=상봉 | 135=삼성 | 102=한양대 | 75=마곡더랜드타워 | 103=명동 | 110=고덕 | 125=동대입구 | 122=대치
// 99=명일동 | 116=약수역 | 128=신당 | 20=정릉 | 131=월곡 | 124=옥수 | 76=셀렉티드연남 | 136=동소문

@Getter
@Setter
class RegionSort{

    public RegionSort(int regionNum, String regionLabel){
        this.regionNum = regionNum;
        this.regionLabel = regionLabel;
    }

    private int regionNum;
    private String regionLabel;
}

@Getter
@Setter
class CategorySort{

    public CategorySort(int categoryNum, String categoryLabel){
        this.categoryNum = categoryNum;
        this.categoryLabel = categoryLabel;
    }

    private int categoryNum;
    private String categoryLabel;
}

@Getter
@Setter
class MainRegionSort{

    public MainRegionSort(int mainRegionNum, String mainRegionLabel){
        this.mainRegionNum = mainRegionNum;
        this.mainRegionLabel = mainRegionLabel;
    }

    private int mainRegionNum;
    private String mainRegionLabel;
}

@Getter
@Setter
class SeleniumListResponse{

    public SeleniumListResponse(List<CategorySort> cateList, List<MainRegionSort> mainRegionList){
        this.cateList = cateList;
        this.mainRegionList = mainRegionList;
    }

    private List<CategorySort> cateList;
    private List<MainRegionSort> mainRegionList;
}

@Getter
@Component
public class TalingMacro {

    private final List<CategorySort> cateList = new ArrayList<>();
    private final List<MainRegionSort> mainRegionList = new ArrayList<>();
    private final List<SeleniumListResponse> responseList = new ArrayList<>();

    private final String[] cateArray = {"운동/건강", "요리", "아트", "교육", "공예", "음악"};
    private final int[][] cateSub = {{27, 78, 235, 123, 217, 33},
                                    {84, 83},
                                    {28, 79, 32, 222, 232, 76, 209, 201},
                                    {13, 188, 193, 34, 41, 233, 246, 214, 88, 248, 80, 127, 103, 116, 244, 213,
                                     15, 250, 239, 12, 14, 11, 54, 182, 199, 3, 42, 43, 44, 51},
                                    {81, 249, 126},
                                    {60, 59, 61}};
    private final String[] mainRegionArray = {"서울", "경기", "인천", "부산", "경상,대구,울산", "대전,충청", "강원", "광주,전라,제주", "온라인"};
    private final int[] mainRegionSub = {0, 1, 2, 3, 4, 5, 6, 7, 8};

    public SeleniumListResponse sorted (){


        for(int i = 0; i < cateSub.length; i++){
            for(int j = 0; j < cateSub[i].length; j++){
                CategorySort c = new CategorySort(cateSub[i][j], cateArray[i]);
                cateList.add(c);
            }
        }

        for(int i = 0; i < mainRegionArray.length; i++){
            MainRegionSort r = new MainRegionSort(mainRegionSub[i], mainRegionArray[i]);
            mainRegionList.add(r);
        }

        return new SeleniumListResponse(cateList, mainRegionList);
    }
}
