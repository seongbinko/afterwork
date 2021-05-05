package com.hanghae99.afterwork.service;

import com.hanghae99.afterwork.dto.UserRequestDto;
import com.hanghae99.afterwork.model.*;
import com.hanghae99.afterwork.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final InterestRepository interestRepository;
    private final CategoryRepository categoryRepository;
    private final CollectRepository collectRepository;

    public Long modifyUser(UserRequestDto userRequestDto, User user){

        String offTime = userRequestDto.getOffTime();
        List<String> locationList = userRequestDto.getLocations();
        List<Long> categoryList = userRequestDto.getCategorys();

        deleteLocation(user);
        deleteInterest(user);

        //관심 위치 생성
        for (String strLocation : locationList) {
            Location location = Location.builder()
                    .user(user)
                    .name(strLocation)
                    .build();

            locationRepository.save(location);
        }

        for (Long longCategory : categoryList) {

            Category category = categoryRepository.findByCategoryId(longCategory).orElse(null);

            Interest interest = Interest.builder()
                    .user(user)
                    .category(category)
                    .build();

            interestRepository.save(interest);
        }

        user.setOffTime(offTime);

        userRepository.save(user);
        return user.getUserId();
    }

    public void deleteUser(User user){

        deleteLocation(user);
        deleteInterest(user);
        deleteCollect(user);

        userRepository.delete(user);
    }

    public void deleteLocation(User user) {
        List<Location> currentLocationList = locationRepository.findAllByUser(user);

        //현재 관심 위치값 삭제
        for (Location location : currentLocationList){
            locationRepository.delete(location);
        }
    }

    public void deleteInterest(User user) {
        List<Interest> currentInterestList = interestRepository.findAllByUser(user);

        //현재 관심취미 삭제
        for (Interest interest : currentInterestList){
            interestRepository.delete(interest);
        }
    }

    public void deleteCollect(User user) {
        List<Collect> currentCollectList = collectRepository.findAllByUser(user);

        //현재 관심취미 삭제
        for (Collect collect : currentCollectList){
            collectRepository.delete(collect);
        }
    }

}
