package com.hanghae99.afterwork.service;

import com.hanghae99.afterwork.dto.*;
import com.hanghae99.afterwork.entity.*;
import com.hanghae99.afterwork.exception.ResourceNotFoundException;
import com.hanghae99.afterwork.repository.*;
import com.hanghae99.afterwork.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final InterestRepository interestRepository;
    private final CategoryRepository categoryRepository;
    private final CollectRepository collectRepository;

    public UserResponseDto getCurrentUser(UserPrincipal userPrincipal) {
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        return new UserResponseDto(
                user.getUserId(),
                user.getEmail(),
                user.getName(),
                user.getImageUrl(),
                user.getOffTime(),
                user.getLocations().stream().map(
                        location -> new LocationResponseDto(
                                location.getLocationId(),
                                location.getName()
                        )
                ).collect(Collectors.toList()),
                user.getInterests().stream().map(
                        interest -> new InterestResponseDto(
                                interest.getInterestId(),
                                interest.getCategory().getCategoryId()
                        )
                ).collect(Collectors.toList()),
                user.getCollects().stream().map(
                        collect -> new CollectResponseDto(
                                collect.getCollectId(),
                                collect.getProduct().getProductId()
                        )
                ).collect(Collectors.toList())
        );
    }

    public Long modifyUser(UserRequestDto userRequestDto, UserPrincipal userPrincipal){

        String offTime = userRequestDto.getOffTime();
        List<String> locationList = userRequestDto.getLocations();
        List<Long> categoryList = userRequestDto.getCategorys();

        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

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

    public void deleteUser(UserPrincipal userPrincipal){

        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

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
