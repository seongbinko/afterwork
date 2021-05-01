package com.hanghae99.afterwork.util;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.swing.plaf.synth.Region;
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
class RegionSort{

    public RegionSort(int regionNum, String regionLabel){
        this.regionNum = regionNum;
        this.regionLabel = regionLabel;
    }

    private int regionNum;
    private String regionLabel;
}

class SeleniumListResponse{

    public SeleniumListResponse(List<CategorySort> cateList, List<RegionSort> regionList){
        this.cateList = cateList;
        this.regionList = regionList;
    }

    private List<CategorySort> cateList;
    private List<RegionSort> regionList;
}

@Component
public class TalingMacro {

    private final List<CategorySort> cateList = new ArrayList<>();
    private final List<RegionSort> regionList = new ArrayList<>();
    private final List<SeleniumListResponse> responseList = new ArrayList<>();
    private final String[] cateArray = {"운동/건강", "요리", "아트", "교육", "공예", "음악"};
    private final int[][] cateSub = {{27, 78, 235, 123, 217, 33},
                                    {84, 83},
                                    {28, 79, 32, 222, 232, 76, 209, 201},
                                    {13, 188, 193, 34, 41, 233, 246, 214, 88, 248, 80, 127, 103, 116, 244, 213,
                                     15, 250, 239, 12, 14, 11, 54, 182, 199, 3, 42, 43, 44, 51},
                                    {81, 249, 126},
                                    {60, 59, 61}};
    private final String[] regionArray = {"서울", "경기", "인천", "부산", "경상,대구,울산", "대전,충청", "광주,전라,제주", "온라인"};
    private final int[] regionSub = {0, 1, 2, 3, 4, 5, 6, 7};

    public List<SeleniumListResponse> sorted (){

        for(int i = 0; i < cateSub.length; i++){
            for(int j = 0; j < cateSub[i].length; j++){
                CategorySort c = new CategorySort(cateSub[i][j], cateArray[i]);
                cateList.add(c);
            }
        }

        for(int i = 0; i < regionArray.length; i++){
            RegionSort r = new RegionSort(regionSub[i], regionArray[i]);
            regionList.add(r);
        }

        SeleniumListResponse s = new SeleniumListResponse(cateList, regionList);
        responseList.add(s);

        return responseList;
    }
}
